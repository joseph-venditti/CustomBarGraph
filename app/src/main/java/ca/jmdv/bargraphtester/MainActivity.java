package ca.jmdv.bargraphtester;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import ca.jmdv.bargraphtester.models.ColorValuePair;
import ca.jmdv.bargraphtester.views.CustomBarGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create color/value pairs
        ColorValuePair pair0 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorOrange_800), 4.0f);
        ColorValuePair pair1 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_200), 0.1f);
        ColorValuePair pair2 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_300), 0.2f);
        ColorValuePair pair3 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_400), 0.5f);
        ColorValuePair pair4 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorGreen_200), 0.9f);
        ColorValuePair pair5 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_600), 1.6f);
        ColorValuePair pair6 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorPurple_500), 3.2f);
        ColorValuePair pair7 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorOrange_500), 2.2f);
        ColorValuePair pair8 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorGreen_700), 5.7f);
        ColorValuePair pair9 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorGreen_800), 2.5f);

        // Add pairs to array
        ArrayList<ColorValuePair> values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);
        values.add(pair4);
        values.add(pair5);
        values.add(pair6);
        values.add(pair7);
        values.add(pair8);
        values.add(pair9);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph1 = (CustomBarGraph) findViewById(R.id.barGraph1);
        barGraph1.setMaxValue(25.0f);
        barGraph1.setData(values);
    }
}
