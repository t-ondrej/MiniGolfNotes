package sk.upjs.ics.minigolf.course;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private Parcelable mListState;
    private static final String LIST_STATE_KEY = "testt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.ranking_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            rankingPlayersRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);//restore

        }

        Bundle bundle = getArguments();

        Log.i("Created rank fragment:", getArguments().toString());

        Game game = Game.fromBundle(getArguments());
        RankingAdapter adapter = new RankingAdapter(game, getContext(), bundle.getInt("position"));
        rankingPlayersRecyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mListState = rankingPlayersRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    // So fragment is refreshed on selection
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.i("METHOD", "HERE");
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
