package sk.upjs.ics.minigolf.mainmenu.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import sk.upjs.ics.minigolf.course.GolfCourseActivity;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

public class NewGameFragment extends Fragment {

    @BindView(R.id.newgameRecyclerView)             RecyclerView recyclerView;
    @BindView(R.id.addPlayerFloatingActionButton)   FloatingActionButton addPlayerButton;
    @BindView(R.id.startGameImageButton)            ImageButton startGameImageButton;
    @BindView(R.id.saveLocationSwitch)              Switch saveLocationSwitch;
    @BindView(R.id.hitCountField)                   EditText hitCountField;
    @BindView(R.id.holesCountField)                 EditText holesCountField;

    private Game game = new Game();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newgame_fragment, container, false);
        ButterKnife.bind(this, view);

        // Now it is set in xml
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        game.getPlayers().add(new Player("Hráč 1"));

        NewGamePlayersRecyclerAdapter adapter = new NewGamePlayersRecyclerAdapter(game, this.getContext());
        recyclerView.setAdapter(adapter);

        configureButtons(adapter);

        return view;
    }

    private void configureButtons(final NewGamePlayersRecyclerAdapter adapter) {
        addPlayerButton.setOnClickListener(v -> {
            game.addPlayer(new Player("Hráč " + (game.getPlayerCount() + 1)));
            adapter.notifyItemInserted(game.getPlayerCount() - 1);
            adapter.notifyItemRangeChanged(game.getPlayerCount()  - 1, game.getPlayerCount() );
            Log.i("Number of items: ", Integer.toString(game.getPlayers().size()));
        });

        startGameImageButton.setOnClickListener(v -> {
            game.setHoleCount(Integer.parseInt(holesCountField.getText().toString()));
            game.setHitCountMax(Integer.parseInt(hitCountField.getText().toString()));
            game.setSaveLocation(saveLocationSwitch.isEnabled());

            Intent intent = new Intent(getActivity(), GolfCourseActivity.class);
            intent = intent.putExtras(game.toBundle());
            startActivity(intent);
        });
    }


}
