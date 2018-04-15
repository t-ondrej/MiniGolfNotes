package sk.upjs.ics.minigolf.mainmenu.newgame;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

/**
 * Created by Tomas on 7.6.2017.
 */

public class NewGamePlayersRecyclerAdapter extends RecyclerView.Adapter<NewGamePlayersRecyclerAdapter.PlayerViewHolder> {

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playerNameEditText) EditText playerNameEditText;

        PlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            addClearButtonClickHandler();
        }

        private void addClearButtonClickHandler() {
            playerNameEditText.setOnTouchListener((v, event) -> {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (playerNameEditText.getRight() - playerNameEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        removeAt(getAdapterPosition());

                        return true;
                    }
                }
                return false;
            });
        }

        public void bindEditText(Player player) {
            playerNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    player.setName(s.toString());
                }
            });
        }
    }

    private Game game;
    private Context context;

    public NewGamePlayersRecyclerAdapter(Game game, Context context) {
        this.game = game;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View otherItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newgame_player, parent, false);
        PlayerViewHolder other = new PlayerViewHolder(otherItemView);

        Resources res = context.getResources();

        return other;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = game.getPlayers().get(position);
        holder.playerNameEditText.setText(player.getName());
        holder.bindEditText(player);
    }

    @Override
    public int getItemCount() {
        return game.getPlayers().size();
    }

    private void removeAt(int position) {
        game.removePlayerByPosition(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, game.getPlayerCount());
    }
}
