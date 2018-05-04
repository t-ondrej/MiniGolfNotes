package sk.upjs.ics.minigolf.mainmenu.gamehistory;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.upjs.ics.minigolf.CursorRecyclerViewAdapter;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.dataaccess.Contract;
import sk.upjs.ics.minigolf.models.Game;

public class GameCardsRecyclerAdapter extends CursorRecyclerViewAdapter<GameCardsRecyclerAdapter.GameCardViewHolder> {

    class GameCardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gamePhotoImageView)  ImageView gamePhotoImageView;
        @BindView(R.id.addressTextView)     TextView adressTextView;
        @BindView(R.id.dateTextView)        TextView dateTextView;
        @BindView(R.id.deleteGameImageView) ImageView deleteGameImageView;
        @BindView(R.id.gameCardView)        CardView gameCardView;

        Game game;

        GameCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.gameCardView)
        public void onGameCardViewCliced(View view) {
            // open detail
            Intent intent = new Intent(context, GameHistoryDetailActivity.class);
            intent = intent.putExtra(GameHistoryDetailActivity.GAME_ID, game.getId());
            context.startActivity(intent);
        }

        @OnClick(R.id.deleteGameImageView)
        public void onDeleteGameImageViewClicked(View view) {
            AsyncQueryHandler gameDeleteHandler = new AsyncQueryHandler(context.getContentResolver()) {
                @Override
                protected void onDeleteComplete(int token, Object cookie, int result) {
                    super.onDeleteComplete(token, cookie, result);
                    GameCardsRecyclerAdapter.this.notifyDataSetChanged();
                }
            };

            gameDeleteHandler.startDelete(0, null, Contract.Game.buildGameUri(game.getId()), null, null);
        }
    }

    private Context context;

    public GameCardsRecyclerAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        this.context = context;
    }

    @Override
    public GameCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_cardview, parent, false);
        return new GameCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameCardViewHolder holder, Cursor cursor) {
        Game game = Game.fromCursorWithoutPlayers(cursor);
        holder.game = game;

        holder.adressTextView.setText(game.getAddress(context));
        holder.dateTextView.setText(game.getDate().toString());

        if (game.getPhotoPath() != null) {
            // Get the dimensions of the View
            int targetW = 700;
            int targetH = 700;

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
            holder.gamePhotoImageView.setImageBitmap(dstBitmap);
        }
    }
}
