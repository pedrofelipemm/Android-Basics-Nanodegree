package study.pmoreira.project5.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import study.pmoreira.project5.R;
import study.pmoreira.project5.ui.hotel.HotelsFragment;
import study.pmoreira.project5.ui.museum.MuseumsFragment;
import study.pmoreira.project5.ui.park.ParksFragment;
import study.pmoreira.project5.ui.restaurant.RestaurantsFragment;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new HotelsFragment();
        } else if (position == 1) {
            fragment = new ParksFragment();
        } else if (position == 2) {
            fragment = new RestaurantsFragment();
        } else {
            fragment = new MuseumsFragment();
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        if (position == 0) {
            title = mContext.getString(R.string.category_hotels);
        } else if (position == 1) {
            title = mContext.getString(R.string.category_parks);
        } else if (position == 2) {
            title = mContext.getString(R.string.category_food);
        } else {
            title = mContext.getString(R.string.category_museums);
        }

        return title;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
