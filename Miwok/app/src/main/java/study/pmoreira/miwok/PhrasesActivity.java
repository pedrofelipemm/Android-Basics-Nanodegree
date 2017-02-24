package study.pmoreira.miwok;

import android.os.Bundle;

public class PhrasesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        replaceContainer(new PhrasesFragment());
    }
}
