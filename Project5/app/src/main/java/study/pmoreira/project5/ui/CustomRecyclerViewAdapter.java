package study.pmoreira.project5.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Hotel;
import study.pmoreira.project5.entity.Place;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private List<Place> mPlaces;

    public CustomRecyclerViewAdapter(Context context, List<Place> places) {
        mInflater = LayoutInflater.from(context);

        mPlaces = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.recycler_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Place place = mPlaces.get(position);

        holder.nameTextView.setText(place.getName());
        holder.descriptionTextView.setText(place.getDescription());

        if (place instanceof Hotel) {
            holder.pictureImageview.setImageResource(((Hotel) place).getImageId());
            holder.pictureImageview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pictureImageview;
        TextView nameTextView;
        TextView descriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);

            pictureImageview = (ImageView) itemView.findViewById(R.id.picture_imageview);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_textview);
        }
    }
}
