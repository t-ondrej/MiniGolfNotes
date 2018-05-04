package sk.upjs.ics.minigolf.course.gamesummary;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.Utils;
import sk.upjs.ics.minigolf.dataaccess.Contract;
import sk.upjs.ics.minigolf.mainmenu.MainActivity;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

import static sk.upjs.ics.minigolf.Utils.verifyStoragePermissions;
import static sk.upjs.ics.minigolf.dataaccess.Constants.NO_COOKIE;
import static sk.upjs.ics.minigolf.dataaccess.Constants.REQUEST_IMAGE_CAPTURE;

public class GameSummaryActivity extends AppCompatActivity {

    @BindView(R.id.photoLayout)             CoordinatorLayout photoLayout;
    @BindView(R.id.gamesummaryTabLayout)    TabLayout gamesummaryTabLayout;
    @BindView(R.id.gamesummaryPager)        ViewPager gamesummaryPager;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamesummary_activity);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            game = Game.fromBundle(savedInstanceState);
        } else {
            Bundle extras = getIntent().getExtras();
            game = Game.fromBundle(extras);
        }

        if (game.getPhotoPath() != null) {
            setPic();
        }

        configureTabLayout();
    }

    private void configureTabLayout() {
        GamesummaryPagerAdapter adapter = new GamesummaryPagerAdapter
                (getSupportFragmentManager(), game);

        gamesummaryPager.setAdapter(adapter);
        gamesummaryPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(gamesummaryTabLayout));
        gamesummaryTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(gamesummaryPager));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        game.toBundle(outState);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPic();
    }

    public void onAddPhotoButtonClick(View view) {
        verifyStoragePermissions(this);
        dispatchTakePictureIntent();
    }

    public void onEndGameButtonClicked(View view) {
        ContentValues gameValues = game.toContentValues();

        // I am not sure if this is correct solution of inserting m-n entities using content provider
        @SuppressLint("HandlerLeak") AsyncQueryHandler gameInsertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Log.i("INSERTED:", "Game " + uri.getLastPathSegment());
                long gameId = Long.parseLong(uri.getLastPathSegment());

                for (Player player : game.getPlayers()) {
                    AsyncQueryHandler playerInsertHandler = new AsyncQueryHandler(getContentResolver()) {
                        @Override
                        protected void onInsertComplete(int token, Object cookie, Uri uri) {
                            Log.i("INSERTED:", "Player " + uri.getLastPathSegment());
                            long playerId = Long.parseLong(uri.getLastPathSegment());

                            AsyncQueryHandler playerInsertHandler = new AsyncQueryHandler(getContentResolver()) {
                                @Override
                                protected void onInsertComplete(int token, Object cookie, Uri uri) {
                                    Log.i("INSERTED:", "Player_to_game of " + playerId);
                                }
                            };

                            ContentValues values = new ContentValues();
                            values.put(Contract.GamePlayer.IDGAME, gameId);
                            values.put(Contract.GamePlayer.IDPLAYER, playerId);
                            playerInsertHandler.startInsert(0, NO_COOKIE, Contract.Game.buildPlayersUri(gameId), values);
                        }
                    };

                    ContentValues playerValues = player.toContentValues();
                    playerInsertHandler.startInsert(0, NO_COOKIE, Contract.Player.CONTENT_URI, playerValues);
                }
            }
        };

        gameInsertHandler.startInsert(0, NO_COOKIE, Contract.Game.CONTENT_URI, gameValues);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = Utils.createImageFile();
                game.setPhotoPath(photoFile.getAbsolutePath());
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "sk.upjs.ics.minigolf.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // TODO: add imageview
    private void setPic() {
        ImageView mImageView = new ImageView(this);
        int width = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                ? 1440
                : 720;
        mImageView.setLayoutParams(new Toolbar.LayoutParams(width, 320));
        mImageView.setFitsSystemWindows(true);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Get the dimensions of the View
        int targetW = width;
        int targetH = 320;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(game.getPhotoPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(game.getPhotoPath(), bmOptions);

        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        int dstWidth = (int)(srcWidth*0.8f);
        int dstHeight = (int)(srcHeight*0.8f);
        Bitmap dstBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
        mImageView.setImageBitmap(dstBitmap);

        this.photoLayout.removeAllViews();
        this.photoLayout.addView(mImageView);
    }
}
