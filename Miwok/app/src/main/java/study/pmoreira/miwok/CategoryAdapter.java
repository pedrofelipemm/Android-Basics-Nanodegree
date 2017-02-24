package study.pmoreira.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context context;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new NumbersFragment();
        } else if (position == 1) {
            fragment = new FamilyFragment();
        } else if (position == 2) {
            fragment = new ColorsFragment();
        } else {
            fragment = new PhrasesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        if (position == 0) {
            title = context.getString(R.string.category_numbers);
        } else if (position == 1) {
            title = context.getString(R.string.category_family);
        } else if (position == 2) {
            title = context.getString(R.string.category_colors);
        } else {
            title = context.getString(R.string.category_phrases);
        }

        return title;
    }
}
