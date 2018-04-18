package sk.upjs.ics.minigolf.mainmenu.gamehistory;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.course.gamesummary.PlayerTableRecyclerViewAdapter;
import sk.upjs.ics.minigolf.dataaccess.Contract;
import sk.upjs.ics.minigolf.models.Game;

public class GameHistoryDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.playerRankingsRecyclerView)  RecyclerView rankingPlayersRecyclerView;
    @BindView(R.id.averageScoreBarChart)        BarChart averageScoreBarChart;

    private static final int GAME_WITH_PLAYERS_LOADER_ID = 1;
    private static final String GAME_ID = "gameId";
    private long gameId;
    private Game game;

    public void onBackImageViewClicked(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamehistory_detail_activity);

        if (getIntent().getExtras() != null)
            gameId = getIntent().getExtras().getLong(GAME_ID);

        ButterKnife.bind(this);
        getLoaderManager().initLoader(GAME_WITH_PLAYERS_LOADER_ID, Bundle.EMPTY, this);
    }

    private void initAverageScoreBarChart() {
        float[] averageScores = game.getAverageScoresAtHoles();

        for (int i = 0; i < averageScores.length; i++)
            averageScoreBarChart.addBar(new BarModel(averageScores[i], 0xFFB9CC66)); // 0xFF63CBB0

        averageScoreBarChart.startAnimation();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(Contract.Game.buildPlayersUri(gameId));
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            game = Game.fromCursor(cursor);
        }

        PlayerTableRecyclerViewAdapter adapter = new PlayerTableRecyclerViewAdapter(game);
        rankingPlayersRecyclerView.setAdapter(adapter);

        initAverageScoreBarChart();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Placeholder
    }
}