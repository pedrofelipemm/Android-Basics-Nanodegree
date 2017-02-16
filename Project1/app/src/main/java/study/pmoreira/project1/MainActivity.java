package study.pmoreira.project1;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurbackground();
    }

    private void blurbackground() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        ImageView background = (ImageView) findViewById(R.id.imageView);
        background.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gastown, options));
    }
}
