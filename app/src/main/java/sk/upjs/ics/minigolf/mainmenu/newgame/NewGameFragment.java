package sk.upjs.ics.minigolf.mainmenu.newgame;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.course.CourseActivity;
import sk.upjs.ics.minigolf.dataaccess.Contract;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

import static android.content.Context.LOCATION_SERVICE;
import static sk.upjs.ics.minigolf.Utils.verifyLocationPermissions;

public class NewGameFragment extends Fragment {

    @BindView(R.id.newgameRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.addPlayerFloatingActionButton)
    FloatingActionButton addPlayerButton;
    @BindView(R.id.startGameImageButton)
    ImageButton startGameImageButton;
    @BindView(R.id.saveLocationSwitch)
    Switch saveLocationSwitch;
    @BindView(R.id.hitCountField)
    EditText hitCountField;
    @BindView(R.id.holesCountField)
    EditText holesCountField;

    private Game game = new Game();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newgame_fragment, container, false);
        ButterKnife.bind(this, view);

        game.addPlayer(Player.createWithDefaultName(1, getResources()));

        NewGamePlayersRecyclerAdapter adapter = new NewGamePlayersRecyclerAdapter(game, this.getContext());
        recyclerView.setAdapter(adapter);

        configureButtons(adapter);

        return view;
    }

    private void configureButtons(final NewGamePlayersRecyclerAdapter adapter) {
        addPlayerButton.setOnClickListener(v -> {
            game.addPlayer(Player.createWithDefaultName(game.getPlayerCount() + 1, getResources()));
            adapter.notifyItemInserted(game.getPlayerCount() - 1);
            adapter.notifyItemRangeChanged(game.getPlayerCount() - 1, game.getPlayerCount());
            Log.i("Number of items: ", Integer.toString(game.getPlayers().size()));
        });

        startGameImageButton.setOnClickListener(v -> {
            game.setHoleCount(Integer.parseInt(holesCountField.getText().toString()));
            game.setHitCountMax(Integer.parseInt(hitCountField.getText().toString()));
            for (Player player : game.getPlayers())
                player.createScoreArray(game.getHoleCount());

            if (saveLocationSwitch.isEnabled()) {
                LocationManager locationManager = (LocationManager) this.getContext()
                        .getSystemService(LOCATION_SERVICE);

                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        verifyLocationPermissions(this.getActivity());
                    }
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        game.setLatitude(location.getLatitude());
                        game.setLongitude(location.getLongitude());
                    }
                }
            }

            Intent intent = new Intent(getActivity(), CourseActivity.class);
            intent = intent.putExtras(game.toBundle());
            startActivity(intent);
        });
    }
}
