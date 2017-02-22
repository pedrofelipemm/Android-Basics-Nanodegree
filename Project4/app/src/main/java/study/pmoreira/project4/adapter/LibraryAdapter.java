package study.pmoreira.project4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project4.R;
import study.pmoreira.project4.activity.DetailActivity;
import study.pmoreira.project4.activity.NowPlayingActivity;
import study.pmoreira.project4.model.Track;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<Track> trackList;

    public LibraryAdapter(Context context, List<Track> trackList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.trackList = trackList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.library_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = trackList.get(position);

        holder.artistTextView.setText(track.getArtist().getName());
        holder.trackTextView.setText(track.getName());

        holder.libraryCoverButton.setBackground(ContextCompat.getDrawable(context, track.getCoverThumbId()));
        holder.libraryCoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.COVER_ID, track.getCoverId());
                intent.putExtra(DetailActivity.ARTIST_DESCRIPTION, track.getArtist().getDescription());
                context.startActivity(intent);
            }
        });

        holder.libraryPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NowPlayingActivity.class);
                intent.putExtra(NowPlayingActivity.COVER_ID, track.getCoverId());
                intent.putExtra(NowPlayingActivity.MUSIC_LENGTH, track.getLength());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.library_cover_buttom)
        ImageView libraryCoverButton;

        @BindView(R.id.library_play_button)
        ImageView libraryPlayButton;

        @BindView(R.id.artist_textview)
        TextView artistTextView;

        @BindView(R.id.track_textview)
        TextView trackTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
