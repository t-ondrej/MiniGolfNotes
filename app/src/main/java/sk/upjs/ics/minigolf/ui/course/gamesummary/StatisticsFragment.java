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

        if (savedInstanceState != null) {
            game = Game.fromBundle(savedInstanceState);
        } else {
            game = Game.fromBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);

        initAverageScoreBarChart();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        game.toBundle(outState);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    private void initAverageScoreBarChart() {
        float[] averageScores = game.getAverageScoresAtHoles();

        for (int i = 0; i < averageScores.length; i++) {
            averageScoreBarChart.addBar(new BarModel(averageScores[i], 0xFFB9CC66)); // 0xFF63CBB0
        }

        averageScoreBarChart.startAnimation();
    }
}
