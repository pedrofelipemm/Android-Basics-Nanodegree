package study.pmoreira.quakereport.ui.earthquake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.quakereport.R;
import study.pmoreira.quakereport.entity.Earthquake;

import static study.pmoreira.quakereport.R.id.date;
import static study.pmoreira.quakereport.R.id.magnitude;

class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    private LayoutInflater mInflator;

    static class ViewHolder {

        @BindView(magnitude)
        TextView mMagnitudeTextview;

        @BindView(R.id.location_offset)
        TextView mLocationOffsetTextview;

        @BindView(R.id.primary_location)
        TextView mPrimaryLocationTextview;

        @BindView(date)
        TextView mDateTextview;

        @BindView(R.id.time)
        TextView mTimeTextview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
        mInflator = context.getLayoutInflater();
    }

    @Override
    public
    @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.earthquake_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       final Earthquake earthquake = getItem(position);
        if (earthquake != null) {

            String magnitude = new DecimalFormat("0.00").format(earthquake.getMagnitude());

            String originalLocation = earthquake.getLocation();
            String primaryLocation;
            String locationOffset;

            if (originalLocation.contains(LOCATION_SEPARATOR)) {
                String[] parts = originalLocation.split(LOCATION_SEPARATOR);
                locationOffset = parts[0] + LOCATION_SEPARATOR;
                primaryLocation = parts[1];
            } else {
                locationOffset = getContext().getString(R.string.near_the);
                primaryLocation = originalLocation;
            }

            String date = new SimpleDateFormat("LLL dd, yyyy", Locale.US).format(earthquake.getTimeInMillis());
            String time = new SimpleDateFormat("h:mm a", Locale.US).format(earthquake.getTimeInMillis());

            holder.mMagnitudeTextview.setText(magnitude);
            holder.mLocationOffsetTextview.setText(locationOffset);
            holder.mPrimaryLocationTextview.setText(primaryLocation);
            holder.mDateTextview.setText(date);
            holder.mTimeTextview.setText(time);

            setMagnitudeColor(holder.mMagnitudeTextview, earthquake.getMagnitude());


        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl())));
            }
        });

        return convertView;
    }

    private void setMagnitudeColor(View magnitudeView, double magnitude) {
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        int magnitudeColor = getMagnitudeColor(magnitude);

        magnitudeCircle.setColor(magnitudeColor);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
