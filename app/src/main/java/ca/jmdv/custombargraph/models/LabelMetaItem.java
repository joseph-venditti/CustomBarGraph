package ca.jmdv.custombargraph.models;

/**
 * Created by jvenditti on 2017-08-10.
 */

public class LabelMetaItem {

    private float barStartBounds;
    private float barEndBounds;
    private float longestLabelWidth;

    public LabelMetaItem(float barStartBounds, float barEndBounds, float longestLabelWidth) {
        super();
        this.barStartBounds = barStartBounds;
        this.barEndBounds = barEndBounds;
        this.longestLabelWidth = longestLabelWidth;
    }

    public float getBarStartBounds() {
        return barStartBounds;
    }

    public void setBarStartBounds(float barStartBounds) {
        this.barStartBounds = barStartBounds;
    }

    public float getBarEndBounds() {
        return barEndBounds;
    }

    public void setBarEndBounds(float barEndBounds) {
        this.barEndBounds = barEndBounds;
    }

    public float getLongestLabelWidth() {
        return longestLabelWidth;
    }

    public void setLongestLabelWidth(float longestLabelWidth) {
        this.longestLabelWidth = longestLabelWidth;
    }
}
