package sk.upjs.ics.minigolf.course;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.course.RankingAdapter;
import sk.upjs.ics.minigolf.models.Game;

public class RankingFragment extends Fragment {

    @BindView(R.id.rankingPlayersRecyclerView) RecyclerView rankingPlayersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.ranking_fragment, container, false);
        ButterKnife.bind(this, view);

        Log.i("Created rank fragment:", getArguments().toString());

        Game game = Game.fromBundle(getArguments());
        RankingAdapter adapter = new RankingAdapter(game, getContext());
        rankingPlayersRecyclerView.setAdapter(adapter);

        return view;
    }
}
