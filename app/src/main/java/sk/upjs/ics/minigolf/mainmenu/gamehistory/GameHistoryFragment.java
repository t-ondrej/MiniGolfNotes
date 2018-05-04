package sk.upjs.ics.minigolf.mainmenu.gamehistory;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.dataaccess.Constants;
import sk.upjs.ics.minigolf.dataaccess.Contract;
import sk.upjs.ics.minigolf.models.Game;

public class GameHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.gameCardsRecyclerView) RecyclerView gameCardsRecyclerView;

    private static final int GAMES_LOADER_ID = 0;
    private GameCardsRecyclerAdapter adapter;

    public static GameHistoryFragment createNewInstance() {
        return new GameHistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(GAMES_LOADER_ID, Bundle.EMPTY, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.gamehistory_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader loader = new CursorLoader(getContext());
        loader.setUri(Contract.Game.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursor.setNotificationUri(getContext().getContentResolver(), Contract.Game.CONTENT_URI); // TODO: set notification uri during query
        adapter = new GameCardsRecyclerAdapter(getContext(), cursor);
        gameCardsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        this.adapter.swapCursor(Constants.NO_CURSOR);
    }
}
