package ca.jmdv.custombargraph.models;

import android.support.annotation.ColorInt;

/**
 * Created by jvenditti on 2017-08-08.
 */

public class DataItem {

    private int color;
    private Double value;
    private String primaryLabel;
    private String secondaryLabel;

    public DataItem(@ColorInt int color, Double value, String primaryLabel, String secondaryLabel) {
        super();
        this.color = color;
        this.value = value;
        this.primaryLabel = primaryLabel;
        this.secondaryLabel = secondaryLabel;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getPrimaryLabel() {
        return primaryLabel;
    }

    public void setPrimaryLabel(String primaryLabel) {
        this.primaryLabel = primaryLabel;
    }

    public String getSecondaryLabel() {
        return secondaryLabel;
    }

    public void setSecondaryLabel(String secondaryLabel) {
        this.secondaryLabel = secondaryLabel;
    }
}
