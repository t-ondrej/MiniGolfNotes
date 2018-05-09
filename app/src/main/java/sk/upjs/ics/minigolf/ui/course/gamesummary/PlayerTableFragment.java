package sk.upjs.ics.minigolf.ui.course.gamesummary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class PlayerTableFragment extends Fragment {

    private Game game;

    public PlayerTableFragment() {
        // Placeholder
    }

    public static PlayerTableFragment newInstance(Game game) {
        PlayerTableFragment fragment = new PlayerTableFragment();
        Bundle args = game.toBundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            game = Game.fromBundle(savedInstanceState);
        } else {
            game = Game.fromBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new PlayerTableRecyclerViewAdapter(game));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        game.toBundle(outState);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
