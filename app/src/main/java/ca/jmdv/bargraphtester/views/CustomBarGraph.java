package ca.jmdv.bargraphtester.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ca.jmdv.bargraphtester.R;
import ca.jmdv.bargraphtester.models.ColorValuePair;

public class CustomBarGraph extends View {

    // TODO 1: Add line labels
    // TODO 2: Add ability for primary labels
    // TODO 3: Add ability for secondary labels

    private static final String TAG = CustomBarGraph.class.getSimpleName();

    private static final int MIN_SCALE_LINE_REPEAT_PERCENTAGE = 1;
    private static final int MAX_SCALE_LINE_REPEAT_PERCENTAGE = 100;
    private static final int DEFAULT_SCALE_LINE_REPEAT_PERCENTAGE = 10;
    private static final int DEFAULT_SCALE_LINE_WIDTH = 2;

    private float maxValue = MAX_SCALE_LINE_REPEAT_PERCENTAGE;
    private float currentFillLength = 0;
    private int barHeight;
    private int scaleLineHeight;
    private int scaleLineWidth;
    private int scaleLineRepeatPercentage;
    private int scaleLineTopPadding;
    private Paint barBasePaint;
    private Paint scaleLinePaint;
    private Paint cornerOverlayPaint;
    private Paint transparentPaint;
    private boolean scaleHideStartLine;
    private boolean scaleHideEndLine;
    private boolean scaleShowLinePercentages;

    ArrayList<ColorValuePair> rawData;
    ArrayList<Paint> readyColors;
    ArrayList<Float> readyValues;

    public CustomBarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        invalidate();
        requestLayout();
    }

    public void setData(ArrayList<ColorValuePair> data) {
        this.rawData = data;
        processData();
    }

    private void processData() {
        readyColors = new ArrayList<>();
        readyValues = new ArrayList<>();
        Paint paint;
        for (ColorValuePair item : rawData) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(item.getColor());
            readyValues.add(setValue(item.getValue()));
            readyColors.add(paint);
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

        setSaveEnabled(true);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBarGraph, 0, 0);

        // Dimensions
        barHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_barHeight, 0);
        scaleLineHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineHeight, 0);
        scaleLineWidth = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineWidth, DEFAULT_SCALE_LINE_WIDTH);
        scaleLineTopPadding = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_scaleLineTopPadding, 0);

        // Integers
        scaleLineRepeatPercentage = ta.getInteger(R.styleable.CustomBarGraph_scaleLineRepeatPercentage, DEFAULT_SCALE_LINE_REPEAT_PERCENTAGE);

        // Booleans
        scaleHideStartLine = ta.getBoolean(R.styleable.CustomBarGraph_scaleHideStartLine, true);
        scaleHideEndLine = ta.getBoolean(R.styleable.CustomBarGraph_scaleHideEndLine, true);
        scaleShowLinePercentages = ta.getBoolean(R.styleable.CustomBarGraph_scaleShowLinePercentages, true);

        // Colors
        int baseColor = ta.getColor(R.styleable.CustomBarGraph_baseColor, Color.BLACK);
        int scaleLineColor = ta.getColor(R.styleable.CustomBarGraph_scaleLineColor, Color.BLACK);
        int cornerOverlayColor = ta.getColor(R.styleable.CustomBarGraph_cornerOverlayColor, Color.TRANSPARENT);

        ta.recycle();

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(baseColor);

        scaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleLinePaint.setColor(scaleLineColor);

        cornerOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cornerOverlayPaint.setColor(cornerOverlayColor);

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setColor(Color.TRANSPARENT);
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
        drawBase(canvas);
        for (int i = 0; i < readyValues.size(); i++) {
            drawBar(canvas, readyColors.get(i), readyValues.get(i));
        }
        drawCornerOverlays(canvas);
        drawScaleLines(canvas);
        drawScaleLabels(canvas);
    }

    private void drawBase(Canvas canvas) {
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

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft() + currentFillLength;

        float percentFilled = valueToDraw / maxValue;
        float fillPosition = left + (barLength * percentFilled);
        currentFillLength = currentFillLength + barLength * percentFilled;
        RectF fillRect = new RectF(left, top, fillPosition, bottom);
        canvas.drawRect(fillRect, fillPaint);
    }

    private void drawCornerOverlays(Canvas canvas) {

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
        int lineCount = (MAX_SCALE_LINE_REPEAT_PERCENTAGE / scaleLineRepeatPercentage);
        Log.d(TAG, "drawScaleLabels: " + scaleShowLinePercentages + " | " + lineCount + " | " + maxValue + " | " + (maxValue / lineCount));
        // TODO: Display percentages below lines

    }

    private float getBarCenter() {
        float barCenter = (getHeight() - scaleLineHeight - scaleLineTopPadding - getPaddingTop() - getPaddingBottom()) / 2;
        barCenter += getPaddingTop();
        return barCenter;
    }

    private int measureHeight(int measureSpec) {
        int size = barHeight + scaleLineTopPadding + scaleLineHeight + getPaddingTop() + getPaddingBottom();
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