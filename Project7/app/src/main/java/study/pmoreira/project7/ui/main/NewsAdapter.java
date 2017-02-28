package study.pmoreira.project7.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project7.R;
import study.pmoreira.project7.entity.News;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<News> mNews;

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;

        @BindView(R.id.title_card)
        TextView mTitleTextView;

        @BindView(R.id.date_card)
        TextView mDateTextView;

        @BindView(R.id.section_card)
        TextView mSectionTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    NewsAdapter(Context context, List<News> news) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

        mNews = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final News news = mNews.get(position);

        holder.mTitleTextView.setText(news.getTitle());
        holder.mDateTextView.setText(formatDate(news.getDate()));
        holder.mSectionTextView.setText(news.getSection());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(news.getWebUrl())));
            }
        });

    }

    private String formatDate(long longDate) {
        Locale locale = new Locale(
                mContext.getString(R.string.locale_language),
                mContext.getString(R.string.locale_country));

        String datePattern = mContext.getString(R.string.date_pattern);

        return new SimpleDateFormat(datePattern, locale).format(longDate);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    void addAll(List<News> news) {
        mNews.addAll(news);
        notifyDataSetChanged();
    }

    void clear() {
        int size = mNews.size();
        mNews.clear();
        notifyItemRangeRemoved(0, size);
    }
}
