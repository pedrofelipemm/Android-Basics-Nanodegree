package study.pmoreira.project5.ui.restaurant;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Place;
import study.pmoreira.project5.ui.BaseFragment;

public class RestaurantsFragment extends BaseFragment {

    @Override
    protected List<Place> fetchPlaces() {
        final List<Place> places = new ArrayList<>();
        places.add(new Place(getString(R.string.restaurant1_name), getString(R.string.restaurant1_description)));
        places.add(new Place(getString(R.string.restaurant2_name), getString(R.string.restaurant2_description)));
        places.add(new Place(getString(R.string.restaurant3_name), getString(R.string.restaurant3_description)));
        places.add(new Place(getString(R.string.restaurant4_name), getString(R.string.restaurant4_description)));
        places.add(new Place(getString(R.string.restaurant5_name), getString(R.string.restaurant5_description)));
        places.add(new Place(getString(R.string.restaurant6_name), getString(R.string.restaurant6_description)));

        return places;
    }
}