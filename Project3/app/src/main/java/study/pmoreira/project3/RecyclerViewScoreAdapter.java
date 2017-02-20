package study.pmoreira.project3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class RecyclerViewScoreAdapter extends RecyclerView.Adapter<RecyclerViewScoreAdapter.MyViewHolder> {

    private List<ScoreNode> scoreNodes;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView scoreTextView;

        public MyViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.text_view_score_name);
            scoreTextView = (TextView) view.findViewById(R.id.text_view_score_value);
        }
    }


    public RecyclerViewScoreAdapter(List<ScoreNode> scoreNodes) {
        this.scoreNodes = scoreNodes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyler_view_score_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Collections.sort(scoreNodes);
        ScoreNode node = scoreNodes.get(position);

        holder.nameTextView.setText(node.getName());
        holder.scoreTextView.setText(String.valueOf(node.getScore()));
    }

    @Override
    public int getItemCount() {
        return scoreNodes.size();
    }

}
