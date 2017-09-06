package ca.jmdv.custombargraph;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import ca.jmdv.custombargraph.models.DataItem;
import ca.jmdv.custombargraph.views.CustomBarGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // *************************************************************************************
        // SAMPLE 1

        // Create color/value pairs
        DataItem pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorOrange_800), 1.0f, null, null);
        DataItem pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_200), 0.1f, null, null);
        DataItem pair2 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_300), 0.05f, null, null);
        DataItem pair3 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_400), 1.0f, null, null);
        DataItem pair4 = new DataItem(ContextCompat.getColor(this, R.color.colorGreen_200), 1.1f, null, null);

        // Add pairs to array
        ArrayList<DataItem> values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);
        values.add(pair4);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph1 = (CustomBarGraph) findViewById(R.id.barGraph1);
        barGraph1.setMaxValue(4.3f);
        barGraph1.setData(values);

        // *************************************************************************************
        // SAMPLE 2

        // Create color/value pairs
        pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_300), 0.5f, getString(R.string.primary_label_1), getString(R.string.secondary_full_label_1));
        pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_400), 0.5f, getString(R.string.primary_full_label_2), getString(R.string.secondary_label_2));
        pair2 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_500), 0.5f, getString(R.string.primary_label_3), getString(R.string.secondary_label_3));
        pair3 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_600), 6.0f, getString(R.string.primary_label_4), getString(R.string.secondary_label_4));
        pair4 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_700), 0.5f, getString(R.string.primary_label_5), getString(R.string.secondary_label_5));

        // Add pairs to array
        values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);
        values.add(pair4);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph2 = (CustomBarGraph) findViewById(R.id.barGraph2);
        barGraph2.setMaxValue(15.0f);
        barGraph2.setLabelPadding(5);
        barGraph2.setData(values);

        // *************************************************************************************
        // SAMPLE 3

        // Create color/value pairs
        pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorPurple_400), 4.0f, getString(R.string.primary_full_label_1), "");
        pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorPurple_700), 11.0f, getString(R.string.primary_full_label_2), "");

        // Add pairs to array
        values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph3 = (CustomBarGraph) findViewById(R.id.barGraph3);
        barGraph3.setMaxValue(15.0f);
        barGraph3.setData(values);

        // *************************************************************************************
        // SAMPLE 4

        // Create color/value pairs
        pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorGreen_600), 2.0f, getString(R.string.primary_label_1), getString(R.string.secondary_label_1));
        pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorGreen_700), 4.0f, getString(R.string.primary_label_2), getString(R.string.secondary_label_2));
        pair2 = new DataItem(ContextCompat.getColor(this, R.color.colorGreen_800), 1.0f, getString(R.string.primary_label_3), getString(R.string.secondary_label_3));

        // Add pairs to array
        values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph4 = (CustomBarGraph) findViewById(R.id.barGraph4);
        barGraph4.setMaxValue(10.0f);
        barGraph4.setData(values);

        // *************************************************************************************
        // SAMPLE 5

        // Create color/value pairs
        pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorOrange_300), 2.0f, null, null);
        pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorOrange_400), 0.5f, null, null);
        pair2 = new DataItem(ContextCompat.getColor(this, R.color.colorOrange_500), 2.0f, null, null);

        // Add pairs to array
        values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph5 = (CustomBarGraph) findViewById(R.id.barGraph5);
        barGraph5.setMaxValue(8.0f);
        barGraph5.setData(values);

        // *************************************************************************************
        // SAMPLE 6

        // Create color/value pairs
        pair0 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_300), 13.0f, getString(R.string.primary_label_1), getString(R.string.secondary_label_1));
        pair1 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_400), 0.5f, getString(R.string.primary_label_2), getString(R.string.secondary_label_2));
        pair2 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_500), 0.5f, getString(R.string.primary_label_3), getString(R.string.secondary_label_3));
        pair3 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_600), 0.5f, getString(R.string.primary_label_4), getString(R.string.secondary_label_4));
        pair4 = new DataItem(ContextCompat.getColor(this, R.color.colorBlue_700), 0.5f, getString(R.string.primary_label_5), getString(R.string.secondary_label_5));

        // Add pairs to array
        values = new ArrayList<>();
        values.add(pair0);
        values.add(pair1);
        values.add(pair2);
        values.add(pair3);
        values.add(pair4);

        // Set max value and data for bar graph
        final CustomBarGraph barGraph6 = (CustomBarGraph) findViewById(R.id.barGraph6);
        barGraph6.setMaxValue(15.0f);
        barGraph6.setData(values);
    }
}
