package study.pmoreira.project5.ui.museum;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Place;
import study.pmoreira.project5.ui.BaseFragment;

public class MuseumsFragment extends BaseFragment {

    @Override
    protected List<Place> fetchPlaces() {
        final List<Place> places = new ArrayList<>();
        places.add(new Place(getString(R.string.museum1_name), getString(R.string.museum1_description)));
        places.add(new Place(getString(R.string.museum2_name), getString(R.string.museum2_description)));
        places.add(new Place(getString(R.string.museum3_name), getString(R.string.museum3_description)));
        places.add(new Place(getString(R.string.museum4_name), getString(R.string.museum4_description)));
        places.add(new Place(getString(R.string.museum5_name), getString(R.string.museum5_description)));
        places.add(new Place(getString(R.string.museum6_name), getString(R.string.museum6_description)));

        return places;
    }

}
