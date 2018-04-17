package sk.upjs.ics.minigolf.mainmenu.gamehistory;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.CursorRecyclerViewAdapter;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class GameCardsRecyclerAdapter extends CursorRecyclerViewAdapter<GameCardsRecyclerAdapter.GameCardViewHolder> {

    class GameCardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gamePhotoImageView) ImageView gamePhotoImageView;
        @BindView(R.id.addressTextView) TextView adressTextView;
        @BindView(R.id.dateTextView) TextView dateTextView;
        @BindView(R.id.deleteGameImageView) ImageView deleteGameImageView;

        GameCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;

    public GameCardsRecyclerAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        this.context = context;
    }

    @Override
    public GameCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_cardview, parent, false);
        return new GameCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameCardViewHolder holder, Cursor cursor) {
        Game game = Game.fromCursorWithoutPlayers(cursor);

        holder.adressTextView.setText(game.getAddress(context));
        holder.dateTextView.setText(game.getDate().toString());
    }
}
