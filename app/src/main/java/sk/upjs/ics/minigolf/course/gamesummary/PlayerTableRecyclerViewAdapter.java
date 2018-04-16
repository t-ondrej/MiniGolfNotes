package sk.upjs.ics.minigolf.course.gamesummary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

public class PlayerTableRecyclerViewAdapter extends RecyclerView.Adapter<PlayerTableRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playerRankImageView) ImageView playerRankImageView;
        @BindView(R.id.playerNameEditText) EditText playerNameEditText;
        @BindView(R.id.playerScoreEditText) EditText playerScoreEditText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            playerNameEditText.setFocusable(false);
            playerNameEditText.setEnabled(false);
            playerScoreEditText.setFocusable(false);
            playerScoreEditText.setEnabled(false);
        }
    }

    private final List<Player> players;

    public PlayerTableRecyclerViewAdapter(Game game) {
        this.players = game.getPlayersSortedByScore();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.playerRankImageView.setBackgroundResource(R.drawable.ic_arrow_forward_black_24dp);
        } else if (position == 1) {
            holder.playerRankImageView.setBackgroundResource(R.drawable.ic_check_black_24dp);
        } else if (position == 2) {
            holder.playerRankImageView.setBackgroundResource(R.drawable.ic_clear_black_24dp);
        }

        holder.playerNameEditText.setText(players.get(position).getName());
        holder.playerScoreEditText.setText(Integer.toString(players.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
