package study.pmoreira.project6.ui.book;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import study.pmoreira.project6.R;
import study.pmoreira.project6.entity.Book;

class BookAdapter extends ArrayAdapter<Book> {

    private LayoutInflater mInflator;

    BookAdapter(Activity activity, List<Book> books) {
        super(activity, 0, books);
        mInflator = activity.getLayoutInflater();
    }

    @Override
    public
    @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflator.inflate(R.layout.book_list_item, parent, false);
        }

        final Book book = getItem(position);
        if (book != null) {
            TextView titleTextvView = (TextView) view.findViewById(R.id.title_textview);
            titleTextvView.setText(book.getTitle());

            TextView authorTextView = (TextView) view.findViewById(R.id.author_textview);
            authorTextView.setText(book.getAuthor());

            TextView languageTextView = (TextView) view.findViewById(R.id.language_textview);
            languageTextView.setText(book.getLanguage());
        }

        return view;
    }

}