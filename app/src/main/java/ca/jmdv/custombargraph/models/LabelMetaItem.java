package ca.jmdv.custombargraph.models;

/**
 * Created by jvenditti on 2017-08-10.
 */

public class LabelMetaItem {

    private float startBounds;
    private float endBounds;

    public LabelMetaItem(float startBounds, float endBounds) {
        super();
        this.startBounds = startBounds;
        this.endBounds = endBounds;
    }

    public float getStartBounds() {
        return startBounds;
    }

    public void setStartBounds(float startBounds) {
        this.startBounds = startBounds;
    }

    public float getEndBounds() {
        return endBounds;
    }

    public void setEndBounds(float endBounds) {
        this.endBounds = endBounds;
    }
}
