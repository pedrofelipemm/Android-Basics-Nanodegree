package study.pmoreira.project5.ui.park;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Place;
import study.pmoreira.project5.ui.BaseFragment;

public class ParksFragment extends BaseFragment {

    @Override
    protected List<Place> fetchPlaces() {
        final List<Place> places = new ArrayList<>();
        places.add(new Place(getString(R.string.park1_name), getString(R.string.park1_description)));
        places.add(new Place(getString(R.string.park2_name), getString(R.string.park2_description)));
        places.add(new Place(getString(R.string.park3_name), getString(R.string.park3_description)));
        places.add(new Place(getString(R.string.park4_name), getString(R.string.park4_description)));
        places.add(new Place(getString(R.string.park5_name), getString(R.string.park5_description)));
        places.add(new Place(getString(R.string.park6_name), getString(R.string.park6_description)));

        return places;
    }

}
