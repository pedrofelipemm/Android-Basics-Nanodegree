package study.pmoreira.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorResourceId;

    public WordAdapter(Context context, List<Word> words, int colorResourceId) {
        super(context, 0, words);
        this.colorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);

        View listView = convertView;
        if (convertView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView miwokTextview = (TextView) listView.findViewById(R.id.miwok_textview);
        miwokTextview.setText(word.getMiwokTransaltion());

        TextView defaultTextview = (TextView) listView.findViewById(R.id.default_textview);
        defaultTextview.setText(word.getDefaultTransaltion());

        ImageView imageView = (ImageView) listView.findViewById(R.id.image);
        if (word.hasImage()) {
            imageView.setImageResource(word.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listView.findViewById(R.id.text_container);
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), colorResourceId));

        return listView;
    }
}
