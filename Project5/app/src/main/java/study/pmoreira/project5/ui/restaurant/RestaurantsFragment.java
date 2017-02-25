package study.pmoreira.project5.ui.restaurant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project5.R;
import study.pmoreira.project5.entity.Place;
import study.pmoreira.project5.ui.BaseFragment;
import study.pmoreira.project5.ui.CustomRecyclerViewAdapter;

public class RestaurantsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(getContext(), fetchPlaces()));

        return view;
    }

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