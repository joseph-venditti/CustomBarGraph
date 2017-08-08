package ca.jmdv.bargraphtester.models;

import android.support.annotation.ColorInt;

/**
 * Created by jvenditti on 2017-08-08.
 */

public class ColorValuePair {

    private int color;
    private float value;

    public ColorValuePair(@ColorInt int color, float value) {
        super();
        this.color = color;
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
