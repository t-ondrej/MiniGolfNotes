package sk.upjs.ics.minigolf.course.gamesummary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class StatisticsFragment extends Fragment {

    @BindView(R.id.averageScoreBarChart) BarChart averageScoreBarChart;
    private Game game;


    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance(Game game) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = game.toBundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            game = Game.fromBundle(args);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        initAverageScoreBarChart();

        return view;
    }

    private void initAverageScoreBarChart() {
        float[] averageScores = game.getAverageScoresAtHoles();

        for (int i = 0; i < averageScores.length; i++) {
            averageScoreBarChart.addBar(new BarModel(averageScores[i], 0xFFB9CC66)); // 0xFF63CBB0
        }

        averageScoreBarChart.startAnimation();
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
