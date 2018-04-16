package sk.upjs.ics.minigolf.course;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class RankingFragment extends Fragment {

    @BindView(R.id.rankingPlayersRecyclerView) RecyclerView rankingPlayersRecyclerView;


    private Parcelable mListState;
    private static final String LIST_STATE_KEY = "testt";

    private Game game;
    private int position;
    private static final String ARG_POSITION = "position";

    public RankingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RankingFragment newInstance(Game game, int position) {
        RankingFragment fragment = new RankingFragment();
        Bundle bundle = game.toBundle();
        bundle.putInt(ARG_POSITION, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            position = getArguments().getInt(ARG_POSITION);
            game = Game.fromBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.course_player_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            rankingPlayersRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);//restore
        }

        Log.i("Created rank fragment:", getArguments().toString());

        RankingAdapter adapter = new RankingAdapter(game, getContext(), position);
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
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}