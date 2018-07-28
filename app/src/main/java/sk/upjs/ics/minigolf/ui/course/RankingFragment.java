package sk.upjs.ics.minigolf.ui.course;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.GameHolder;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class RankingFragment extends Fragment {

    @BindView(R.id.rankingPlayersRecyclerView)  RecyclerView rankingPlayersRecyclerView;
    @BindView(R.id.holeNameTextView)            TextView holeNameTextView;

    private Game game = GameHolder.INSTANCE.getGame();
    private int holeIdx;
    private static final String ARG_HOLE_INDEX = "hole_index";
    private static final String LIST_STATE_KEY = "list_state";

    public RankingFragment() {
        // Placeholder
    }

    public static RankingFragment newInstance(Game game, int position) {
        RankingFragment fragment = new RankingFragment();
        Bundle bundle = game.toBundle();
        bundle.putInt(ARG_HOLE_INDEX, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            holeIdx = savedInstanceState.getInt(ARG_HOLE_INDEX);
        } else {
            Bundle arguments = getArguments();
            holeIdx = arguments.getInt(ARG_HOLE_INDEX);
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

        holeNameTextView.setText(getHoleName());
        RankingAdapter adapter = new RankingAdapter(game, getContext(), holeIdx);
        rankingPlayersRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_HOLE_INDEX, holeIdx);
        Parcelable mListState = rankingPlayersRecyclerView.getLayoutManager().onSaveInstanceState();
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

    private String getHoleName() {
        return (holeIdx + 1) + ". Jamka";
    }

}