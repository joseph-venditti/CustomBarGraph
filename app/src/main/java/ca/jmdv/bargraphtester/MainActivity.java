package ca.jmdv.bargraphtester;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import ca.jmdv.bargraphtester.views.CustomBarGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        // TODO: Pass an array of color/value pairs

        final CustomBarGraph barGraph1 = (CustomBarGraph) findViewById(R.id.barGraph1);
        barGraph1.setMaxValue(5.0f);
        barGraph1.setValue1(1.0f);
        barGraph1.setValue2(1.0f);
        barGraph1.setValue3(1.0f);
        barGraph1.setValue4(1.0f);
        barGraph1.setValue5(1.0f);
        barGraph1.setColor1(ContextCompat.getColor(this, R.color.colorBlue_200));
        barGraph1.setColor2(ContextCompat.getColor(this, R.color.colorBlue_300));
        barGraph1.setColor3(ContextCompat.getColor(this, R.color.colorBlue_400));
        barGraph1.setColor4(ContextCompat.getColor(this, R.color.colorBlue_500));
        barGraph1.setColor5(ContextCompat.getColor(this, R.color.colorBlue_600));

        final CustomBarGraph barGraph2 = (CustomBarGraph) findViewById(R.id.barGraph2);
        barGraph2.setMaxValue(25.0f);
        barGraph2.setValue1(10.0f);
        barGraph2.setValue2(6.0f);
        barGraph2.setValue3(2.0f);

        final CustomBarGraph barGraph3 = (CustomBarGraph) findViewById(R.id.barGraph3);
        barGraph3.setMaxValue(4.0f);
        barGraph3.setValue1(1.0f);
        barGraph3.setValue2(1.0f);
        barGraph3.setValue3(0.19f);
        barGraph3.setValue4(0.1f);

        final CustomBarGraph barGraph4 = (CustomBarGraph) findViewById(R.id.barGraph4);
        barGraph4.setMaxValue(5.0f);
        barGraph4.setValue1(2.0f);
        barGraph4.setValue2(1.0f);
        barGraph4.setValue4(0.5f);
        barGraph4.setValue5(0.1f);
    }
}
