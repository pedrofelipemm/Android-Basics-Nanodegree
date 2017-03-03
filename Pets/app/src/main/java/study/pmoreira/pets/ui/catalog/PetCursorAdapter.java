package study.pmoreira.pets.ui.catalog;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.pets.R;
import study.pmoreira.pets.data.PetContract.PetEntry;

public class PetCursorAdapter extends CursorAdapter {

    static class ViewHolder {

        @BindView(R.id.name)
        TextView nameTextView;

        @BindView(R.id.summary)
        TextView summaryTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);

        String breed = cursor.getString(breedColumnIndex);
        if (TextUtils.isEmpty(breed)) {
            breed = context.getString(R.string.unknown_breed);
        }

        viewHolder.nameTextView.setText(cursor.getString(nameColumnIndex));
        viewHolder.summaryTextView.setText(breed);
    }
}
