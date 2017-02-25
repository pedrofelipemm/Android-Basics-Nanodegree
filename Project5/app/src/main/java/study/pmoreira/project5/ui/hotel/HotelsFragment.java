package study.pmoreira.project5.ui.hotel;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Hotel;
import study.pmoreira.project5.entity.Place;
import study.pmoreira.project5.ui.BaseFragment;

public class HotelsFragment extends BaseFragment {

    @Override
    protected List<Place> fetchPlaces() {
        final List<Place> hotels = new ArrayList<>();
        hotels.add(new Hotel(getString(R.string.hotel1_name), getString(R.string.hotel1_description), R.drawable.royal_palm_plaza_resort));
        hotels.add(new Hotel(getString(R.string.hotel2_name), getString(R.string.hotel2_description), R.drawable.melia_campinas));
        hotels.add(new Hotel(getString(R.string.hotel3_name), getString(R.string.hotel3_description), R.drawable.hotel_mercure));
        hotels.add(new Hotel(getString(R.string.hotel4_name), getString(R.string.hotel4_description), R.drawable.comfort_suites));
        hotels.add(new Hotel(getString(R.string.hotel5_name), getString(R.string.hotel5_description), R.drawable.royal_palm_towerjpg));
        hotels.add(new Hotel(getString(R.string.hotel6_name), getString(R.string.hotel6_description), R.drawable.hotel_vila_rica));

        return hotels;
    }
}
