package study.pmoreira.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Context context, List<Word> words) {
        super(context, 0, words);
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

        return listView;
    }
}
