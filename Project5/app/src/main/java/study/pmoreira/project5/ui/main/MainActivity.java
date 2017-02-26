package study.pmoreira.project5.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project5.R;
import study.pmoreira.project5.ui.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager pager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pager.setAdapter(new CategoryAdapter(this, getSupportFragmentManager()));

        tabs.setupWithViewPager(pager);
    }
}
