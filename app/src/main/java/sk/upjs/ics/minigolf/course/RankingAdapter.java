package sk.upjs.ics.minigolf.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.models.Player;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankedPlayerViewHolder> {

    class RankedPlayerViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.playerNameTextView)  TextView nameTextView;
        @BindView(R.id.scoreEditText)       EditText scoreEditText;
      //  @BindView(R.id.pointSpinner)        Spinner pointSpinner;
        @BindView(R.id.standingImageView)   ImageView standingImageView;

        RankedPlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(RankingAdapter.this.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    game.getPossibleScores());
            integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //   pointSpinner.setAdapter(integerArrayAdapter);
        }
    }

    private Game game;
    private Context context;

    public RankingAdapter(Game game, Context context) {
        this.game = game;
        this.context = context;
    }

    @Override
    public RankingAdapter.RankedPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View otherItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_info, parent, false);
      //  Resources res = context.getResources();
        RankedPlayerViewHolder other = new RankedPlayerViewHolder(otherItemView);
       /* other.nameTextView.setText(res.getString(R.string.player, game.getPlayers().size() - 1));
        other.nameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //playerList.get(viewType - 1).setName(s.toString());
            }
        });
*/
        return other;
    }

    @Override
    public void onBindViewHolder(RankingAdapter.RankedPlayerViewHolder holder, int position) {
            Player player = game.findPlayerByPosition(position);
          //  holder.nameTextView.setText(player.getName());
            holder.scoreEditText.setText(player.getNameWithScore());

    }

    @Override
    public int getItemCount() {
        return game.getPlayers().size();
    }
}
