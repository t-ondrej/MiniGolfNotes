package sk.upjs.ics.minigolf.mainmenu.newgame;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        @BindView(R.id.editTextPlayer) EditText nameTextView;
        int position;

        PlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            addClearButtonClickHandler();
        }

        private void addClearButtonClickHandler() {
            nameTextView.setOnTouchListener((v, event) -> {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (nameTextView.getRight() - nameTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        removeAt(position);

                        return true;
                    }
                }
                return false;
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

        /*other.nameTextView.setText(res.getString(R.string.player, game.getPlayers().size() - 1));
        other.nameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                game.getPlayers().get(viewType).setName(s.toString());
            }
        });*/

        return other;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = game.getPlayers().get(position);
        holder.nameTextView.setText(player.getName());
        holder.position = position;
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
