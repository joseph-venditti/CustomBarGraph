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
        ColorValuePair pair0 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorOrange_800), 10.0f);
        ColorValuePair pair1 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_200), 5.0f);
        ColorValuePair pair2 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_300), 1.0f);
        ColorValuePair pair3 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorBlue_400), 1.0f);
        ColorValuePair pair4 = new ColorValuePair(ContextCompat.getColor(this, R.color.colorGreen_200), 0.5f);

        // Add pairs to array
        ArrayList<ColorValuePair> values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);
        values.add(pair4);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph1 = (CustomBarGraph) findViewById(R.id.barGraph1);
        barGraph1.setMaxValue(20.0f);
        barGraph1.setData(values);
    }
}
