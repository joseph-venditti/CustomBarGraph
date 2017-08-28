package ca.jmdv.custombargraph.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import ca.jmdv.custombargraph.R;
import ca.jmdv.custombargraph.models.DataItem;
import ca.jmdv.custombargraph.models.LabelMetaItem;

public class CustomBarGraph extends View {

    private static final String TAG = CustomBarGraph.class.getSimpleName();

    private static final int MIN_SCALE_LINE_REPEAT_PERCENTAGE = 1;
    private static final int MAX_SCALE_LINE_REPEAT_PERCENTAGE = 100;
    private static final int DEFAULT_SCALE_LINE_REPEAT_PERCENTAGE = 10;
    private static final int DEFAULT_SCALE_LINE_WIDTH = 2;
    private static final String PERCENTAGE_SYMBOL = "%";
    private static final String TEXT_CLIPPED_SYMBOL = ".";

    private float maxValue = MAX_SCALE_LINE_REPEAT_PERCENTAGE;
    private float currentFillLength = 0;
    private int barHeight;
    private int scaleLineHeight;
    private int scaleLineWidth;
    private int scaleLineRepeatPercentage;
    private int scaleLineTopPadding;
    private int scaleLineBottomPadding;
    private int scaleLabelHeight;
    private int primaryLabelHeight;
    private int secondaryLabelHeight;
    private int primaryLabelTopPadding;
    private int primaryLabelBottomPadding;
    private int secondaryLabelTopPadding;
    private int secondaryLabelBottomPadding;
    private Paint barBasePaint;
    private Paint scaleLinePaint;
    private Paint cornerOverlayPaint;
    private Paint scaleLinePercentagePaint;
    private Paint primaryLabelPaint;
    private Paint secondaryLabelPaint;
    private Paint transparentPaint;
    private boolean scaleHideStartLine;
    private boolean scaleHideEndLine;
    private boolean scaleShowLinePercentages;
    private boolean showPrimaryLabels;
    private boolean showSecondaryLabels;

    ArrayList<DataItem> rawData;
    ArrayList<LabelMetaItem> readyLabelMeta;
    ArrayList<Paint> readyColors;
    ArrayList<Float> readyValues;
    ArrayList<String> readyPrimaryLabels;
    ArrayList<String> readySecondaryLabels;

    public CustomBarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setMaxValue(float maxValue) {
        //Log.d(TAG, "setMaxValue: " + maxValue);
        this.maxValue = maxValue;
        invalidate();
        requestLayout();
    }

    public void setData(ArrayList<DataItem> data) {
        //Log.d(TAG, "setData");
        this.rawData = data;
    }

    public void setShowLinePercentages(boolean showLinePercentages) {
        //Log.d(TAG, "setShowLinePercentages");
        this.scaleShowLinePercentages = showLinePercentages;
    }

    public void setScaleLineRepeatPercentage(int scaleLineRepeatPercentage) {
        //Log.d(TAG, "setScaleLineRepeatPercentage");
        this.scaleLineRepeatPercentage = scaleLineRepeatPercentage;
    }

    private void processData() {
        //Log.d(TAG, "processData");
        currentFillLength = 0;
        readyColors = new ArrayList<>();
        readyValues = new ArrayList<>();
        readyPrimaryLabels = new ArrayList<>();
        readySecondaryLabels = new ArrayList<>();
        readyLabelMeta = new ArrayList<>();
        Paint paint;
        if (rawData != null) {
            for (DataItem item : rawData) {
                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(item.getColor());
                readyColors.add(paint);
                readyValues.add(setValue(item.getValue()));
                readyPrimaryLabels.add((!TextUtils.isEmpty(item.getPrimaryLabel())) ? item.getPrimaryLabel() : "");
                readySecondaryLabels.add((!TextUtils.isEmpty(item.getSecondaryLabel())) ? item.getSecondaryLabel() : "");
            }
        }
    }

    private float setValue(float value) {
        float returnValue;
        if (value < 0) {
            returnValue = 0;
        } else if (value > maxValue) {
            returnValue = maxValue;
        } else {
            returnValue = value;
        }
        return returnValue;
    }

    private void init(Context context, AttributeSet attrs) {

        //Log.d(TAG, "init");

        setSaveEnabled(true);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBarGraph, 0, 0);

        // Dimensions
        barHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_barHeight, 0);
        scaleLineHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineHeight, 0);
        scaleLineWidth = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineWidth, DEFAULT_SCALE_LINE_WIDTH);
        scaleLineTopPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineTopPadding, 0);
        scaleLineBottomPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineBottomPadding, 0);
        primaryLabelTopPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_primaryLabelTopPadding, 0);
        primaryLabelBottomPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_primaryLabelBottomPadding, 0);
        secondaryLabelTopPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_secondaryLabelTopPadding, 0);
        secondaryLabelBottomPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_secondaryLabelBottomPadding, 0);
        int scaleLinePercentageTextSize = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLinePercentageTextSize, 0);
        int primaryLabelTextSize = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_primaryLabelTextSize, 0);
        int secondaryLabelTextSize = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_secondaryLabelTextSize, 0);

        // Integers
        scaleLineRepeatPercentage = ta.getInteger(R.styleable.CustomBarGraph_scaleLineRepeatPercentage, DEFAULT_SCALE_LINE_REPEAT_PERCENTAGE);

        // Booleans
        scaleHideStartLine = ta.getBoolean(R.styleable.CustomBarGraph_scaleHideStartLine, true);
        scaleHideEndLine = ta.getBoolean(R.styleable.CustomBarGraph_scaleHideEndLine, true);
        scaleShowLinePercentages = ta.getBoolean(R.styleable.CustomBarGraph_scaleShowLinePercentages, true);
        showPrimaryLabels = ta.getBoolean(R.styleable.CustomBarGraph_showPrimaryLabels, false);
        showSecondaryLabels = ta.getBoolean(R.styleable.CustomBarGraph_showSecondaryLabels, false);

        // Colors
        int baseColor = ta.getColor(R.styleable.CustomBarGraph_baseColor, Color.BLACK);
        int scaleLineColor = ta.getColor(R.styleable.CustomBarGraph_scaleLineColor, Color.BLACK);
        int cornerOverlayColor = ta.getColor(R.styleable.CustomBarGraph_cornerOverlayColor, Color.TRANSPARENT);
        int scaleLinePercentageTextColor = ta.getColor(R.styleable.CustomBarGraph_scaleLinePercentageTextColor, Color.BLACK);
        int primaryLabelTextColor = ta.getColor(R.styleable.CustomBarGraph_primaryLabelTextColor, Color.BLACK);
        int secondaryLabelTextColor = ta.getColor(R.styleable.CustomBarGraph_secondaryLabelTextColor, Color.BLACK);

        ta.recycle();

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(baseColor);

        scaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleLinePaint.setColor(scaleLineColor);

        cornerOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cornerOverlayPaint.setColor(cornerOverlayColor);

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setColor(Color.TRANSPARENT);

        scaleLinePercentagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleLinePercentagePaint.setColor(scaleLinePercentageTextColor);
        scaleLinePercentagePaint.setTextSize(scaleLinePercentageTextSize);
        scaleLinePercentagePaint.setTextAlign(Paint.Align.LEFT);
        scaleLinePercentagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        primaryLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        primaryLabelPaint.setColor(primaryLabelTextColor);
        primaryLabelPaint.setTextSize(primaryLabelTextSize);
        primaryLabelPaint.setTextAlign(Paint.Align.LEFT);
        primaryLabelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        secondaryLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondaryLabelPaint.setColor(secondaryLabelTextColor);
        secondaryLabelPaint.setTextSize(secondaryLabelTextSize);
        secondaryLabelPaint.setTextAlign(Paint.Align.LEFT);
        secondaryLabelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Calculate the different label heights
        String label = PERCENTAGE_SYMBOL;
        Rect fillRect;

        fillRect = new Rect();
        scaleLinePercentagePaint.getTextBounds(label, 0, label.length(), fillRect);
        scaleLabelHeight = fillRect.height();

        fillRect = new Rect();
        primaryLabelPaint.getTextBounds(label, 0, label.length(), fillRect);
        primaryLabelHeight = fillRect.height();

        fillRect = new Rect();
        secondaryLabelPaint.getTextBounds(label, 0, label.length(), fillRect);
        secondaryLabelHeight = fillRect.height();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Log.d(TAG, "onDraw");
        processData();

        drawBase(canvas);
        if (readyValues != null && !readyValues.isEmpty()) {
            int size = readyValues.size();
            for (int i = 0; i < size; i++) {
                drawBar(canvas, readyColors.get(i), readyValues.get(i));
            }
            drawCornerOverlays(canvas);
            drawScaleLines(canvas);
            if (scaleShowLinePercentages) {
                drawScaleLabels(canvas);
            }
            if (showPrimaryLabels) {
                drawPrimaryLabels(canvas);
            }
            if (showSecondaryLabels) {
                drawSecondaryLabels(canvas);
            }
        }
    }

    private void drawBase(Canvas canvas) {

        //Log.d(TAG, "drawBase");

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft();
        float right = left + barLength;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRect(rect, barBasePaint);
    }

    private void drawBar(Canvas canvas, Paint fillPaint, float valueToDraw) {

        //Log.d(TAG, "drawBar");

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft() + currentFillLength;

        float percentFilled = valueToDraw / maxValue;
        float right = left + (barLength * percentFilled);
        currentFillLength = currentFillLength + barLength * percentFilled;
        RectF fillRect = new RectF(left, top, right, bottom);
        canvas.drawRect(fillRect, fillPaint);

        // Populate meta item array (needed for primary/secondary labels)
        readyLabelMeta.add(new LabelMetaItem(left, right));
    }

    private void drawCornerOverlays(Canvas canvas) {

        //Log.d(TAG, "drawCornerOverlays");

        Path path = new Path();

        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float left = getPaddingLeft();
        float right = getWidth() - getPaddingRight();
        float top = getPaddingTop();
        float bottom = top + barHeight;

        // top left corner
        path.moveTo(left, barCenter);
        path.cubicTo(
                left,
                barCenter,
                left,
                top,
                left + halfBarHeight,
                top
        );
        path.lineTo(left, top);
        path.lineTo(left, barCenter);
        path.close();
        canvas.drawPath(path, cornerOverlayPaint);

        // bottom left corner
        path.moveTo(left, barCenter);
        path.cubicTo(
                left,
                barCenter,
                left,
                bottom,
                left + halfBarHeight,
                bottom
        );
        path.lineTo(left, bottom);
        path.lineTo(left, barCenter);
        path.close();
        canvas.drawPath(path, cornerOverlayPaint);

        // top right corner
        path.moveTo(right, barCenter);
        path.cubicTo(
                right,
                barCenter,
                right,
                top,
                right - halfBarHeight,
                top
        );
        path.lineTo(right, top);
        path.lineTo(right, barCenter);
        path.close();
        canvas.drawPath(path, cornerOverlayPaint);

        // bottom right corner
        path.moveTo(right, barCenter);
        path.cubicTo(
                right,
                barCenter,
                right,
                bottom,
                right - halfBarHeight,
                bottom
        );
        path.lineTo(right, bottom);
        path.lineTo(right, barCenter);
        path.close();
        canvas.drawPath(path, cornerOverlayPaint);
    }

    private void drawScaleLines(Canvas canvas) {

        //Log.d(TAG, "drawScaleLines");

        scaleLineRepeatPercentage = boundPercentage(scaleLineRepeatPercentage);

        RectF fillRect;
        float left;
        float right;

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter + halfBarHeight + scaleLineTopPadding;
        float bottom = top + scaleLineHeight;

        int lineCount = (MAX_SCALE_LINE_REPEAT_PERCENTAGE / scaleLineRepeatPercentage) + 1;
        float lineSpace = (barLength - (lineCount * scaleLineWidth)) / (lineCount - 1);

        for (int i = 0; i < lineCount; i++) {
            left = getPaddingLeft() + (i * scaleLineWidth) + (i * lineSpace);
            right = left + scaleLineWidth;
            fillRect = new RectF(left, top, right, bottom);
            if ((scaleHideStartLine && i == 0) || (scaleHideEndLine && i == lineCount - 1)) {
                canvas.drawRect(fillRect, transparentPaint);
            } else {
                canvas.drawRect(fillRect, scaleLinePaint);
            }
        }
    }

    private void drawScaleLabels(Canvas canvas) {

        //Log.d(TAG, "drawScaleLabels");

        scaleLineRepeatPercentage = boundPercentage(scaleLineRepeatPercentage);

        Rect fillRect;
        float left;

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter + halfBarHeight + scaleLineTopPadding + scaleLineBottomPadding + scaleLineHeight + scaleLabelHeight;

        int lineCount = (MAX_SCALE_LINE_REPEAT_PERCENTAGE / scaleLineRepeatPercentage) + 1;
        float lineSpace = (barLength - (lineCount * scaleLineWidth)) / (lineCount - 1);

        String label;
        float valueF;

        for (int i = 0; i < lineCount; i++) {
            fillRect = new Rect();
            valueF = (i * (maxValue / (lineCount - 1)));
            valueF = BigDecimal.valueOf(valueF)
                    .setScale(3, RoundingMode.HALF_UP)
                    .floatValue();
            label = valueF + PERCENTAGE_SYMBOL;
            scaleLinePercentagePaint.getTextBounds(label, 0, label.length(), fillRect);
            left = getPaddingLeft() + (i * scaleLineWidth) + (i * lineSpace) - (fillRect.width() / 2);
            canvas.drawText(label, left, top, scaleLinePercentagePaint);
        }
    }

    private void drawPrimaryLabels(Canvas canvas) {

        //Log.d(TAG, "drawPrimaryLabels");

        Rect fillRect;
        LabelMetaItem item;
        String primaryLabel;

        float boundsWidth;
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;

        float primaryTop = barCenter +
                halfBarHeight +
                scaleLineTopPadding +
                scaleLineHeight +
                scaleLineBottomPadding +
                scaleLabelHeight +
                primaryLabelTopPadding +
                primaryLabelHeight;

        int size = readyLabelMeta.size();
        for (int i = 0; i < size; i++) {

            item = readyLabelMeta.get(i);
            primaryLabel = readyPrimaryLabels.get(i);
            boundsWidth = item.getEndBounds() - item.getStartBounds();

            fillRect = new Rect();
            primaryLabelPaint.getTextBounds(primaryLabel, 0, primaryLabel.length(), fillRect);

            if (fillRect.width() > boundsWidth) {
                //Log.d(TAG, "------ primaryLabel clipped: '" + fillRect.width() + "' > '" + boundsWidth + "' for label '" + primaryLabel + "'");
                canvas.drawText(TEXT_CLIPPED_SYMBOL, item.getStartBounds(), primaryTop, primaryLabelPaint);
            } else {
                canvas.drawText(primaryLabel, item.getStartBounds(), primaryTop, primaryLabelPaint);
            }
        }
    }

    private void drawSecondaryLabels(Canvas canvas) {

        //Log.d(TAG, "drawSecondaryLabels");

        Rect fillRect;
        LabelMetaItem item;
        String secondaryLabel;

        float boundsWidth;
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;

        float primaryTop = barCenter +
                halfBarHeight +
                scaleLineTopPadding +
                scaleLineHeight +
                scaleLineBottomPadding +
                scaleLabelHeight +
                primaryLabelTopPadding +
                primaryLabelHeight;

        float secondaryTop = primaryTop +
                primaryLabelBottomPadding +
                secondaryLabelTopPadding +
                secondaryLabelHeight;

        int size = readyLabelMeta.size();
        for (int i = 0; i < size; i++) {

            item = readyLabelMeta.get(i);
            secondaryLabel = readySecondaryLabels.get(i);
            boundsWidth = item.getEndBounds() - item.getStartBounds();

            fillRect = new Rect();
            secondaryLabelPaint.getTextBounds(secondaryLabel, 0, secondaryLabel.length(), fillRect);

            if (fillRect.width() > boundsWidth) {
                //Log.d(TAG, "------ secondaryLabel clipped: '" + fillRect.width() + "' > '" + boundsWidth + "' for label '" + secondaryLabel + "'");
                canvas.drawText(TEXT_CLIPPED_SYMBOL, item.getStartBounds(), secondaryTop, secondaryLabelPaint);
            } else {
                canvas.drawText(secondaryLabel, item.getStartBounds(), secondaryTop, secondaryLabelPaint);
            }
        }
    }

    private float getBarCenter() {
        float barCenter =
                (getHeight() -
                        scaleLineTopPadding -
                        scaleLineHeight -
                        scaleLineBottomPadding -
                        scaleLabelHeight -
                        primaryLabelTopPadding -
                        primaryLabelHeight -
                        primaryLabelBottomPadding -
                        secondaryLabelTopPadding -
                        secondaryLabelHeight -
                        secondaryLabelBottomPadding -
                        getPaddingTop() -
                        getPaddingBottom()
                ) / 2;
        barCenter += getPaddingTop();
        return barCenter;
    }

    private int measureHeight(int measureSpec) {

        int size = barHeight +
                scaleLineTopPadding +
                scaleLineHeight +
                scaleLineBottomPadding +
                scaleLabelHeight +
                primaryLabelTopPadding +
                primaryLabelHeight +
                primaryLabelBottomPadding +
                secondaryLabelTopPadding +
                secondaryLabelHeight +
                secondaryLabelBottomPadding +
                getPaddingTop() +
                getPaddingBottom();

        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingLeft() + getPaddingRight();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int boundPercentage(int percentage) {
        if (percentage < MIN_SCALE_LINE_REPEAT_PERCENTAGE) {
            percentage = MIN_SCALE_LINE_REPEAT_PERCENTAGE;
        } else if (percentage > MAX_SCALE_LINE_REPEAT_PERCENTAGE) {
            percentage = MAX_SCALE_LINE_REPEAT_PERCENTAGE;
        }
        return percentage;
    }
}