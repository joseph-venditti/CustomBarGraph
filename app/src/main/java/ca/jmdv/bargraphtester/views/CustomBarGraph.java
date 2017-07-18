package ca.jmdv.bargraphtester.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import ca.jmdv.bargraphtester.R;

public class CustomBarGraph extends View {

    private static final String TAG = CustomBarGraph.class.getSimpleName();

    private float maxValue = 100;

    private float currentFillLength = 0;

    private int barHeight;
    private Paint barBasePaint;
    private Paint cornerOverlayPaint;

    private float currentValue1 = 0;
    private float currentValue2 = 0;
    private float currentValue3 = 0;
    private float currentValue4 = 0;
    private float currentValue5 = 0;

    private float valueToDraw1;
    private float valueToDraw2;
    private float valueToDraw3;
    private float valueToDraw4;
    private float valueToDraw5;

    private Paint fillPaint1;
    private Paint fillPaint2;
    private Paint fillPaint3;
    private Paint fillPaint4;
    private Paint fillPaint5;

    public CustomBarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        invalidate();
        requestLayout();
    }

    public void setColor1(@ColorInt int color) {
        fillPaint1.setColor(color);
    }

    public void setColor2(@ColorInt int color) {
        fillPaint2.setColor(color);
    }

    public void setColor3(@ColorInt int color) {
        fillPaint3.setColor(color);
    }

    public void setColor4(@ColorInt int color) {
        fillPaint4.setColor(color);
    }

    public void setColor5(@ColorInt int color) {
        fillPaint5.setColor(color);
    }

    public void setValue1(float newValue) {
        valueToDraw1 = currentValue1 = setValue(newValue);
        invalidate();
    }

    public void setValue2(float newValue) {
        valueToDraw2 = currentValue2 = setValue(newValue);
        invalidate();
    }

    public void setValue3(float newValue) {
        valueToDraw3 = currentValue3 = setValue(newValue);
        invalidate();
    }

    public void setValue4(float newValue) {
        valueToDraw4 = currentValue4 = setValue(newValue);
        invalidate();
    }

    public void setValue5(float newValue) {
        valueToDraw5 = currentValue5 = setValue(newValue);
        invalidate();
    }

    private float setValue(float newValue) {
        float currentValue;
        if (newValue < 0) {
            currentValue = 0;
        } else if (newValue > maxValue) {
            currentValue = maxValue;
        } else {
            currentValue = newValue;
        }
        return currentValue;
    }

    public float getValue1() {
        return currentValue1;
    }

    public float getValue2() {
        return currentValue2;
    }

    public float getValue3() {
        return currentValue3;
    }

    public float getValue4() {
        return currentValue4;
    }

    public float getValue5() {
        return currentValue5;
    }

    private void init(Context context, AttributeSet attrs) {

        setSaveEnabled(true);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBarGraph, 0, 0);
        barHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_barHeight, 0);

        int baseColor = ta.getColor(R.styleable.CustomBarGraph_baseColor, Color.BLACK);
        int cornerOverlayColor = ta.getColor(R.styleable.CustomBarGraph_cornerOverlayColor, Color.TRANSPARENT);

        int fillColor1 = ta.getColor(R.styleable.CustomBarGraph_fillColor1, Color.TRANSPARENT);
        int fillColor2 = ta.getColor(R.styleable.CustomBarGraph_fillColor2, Color.TRANSPARENT);
        int fillColor3 = ta.getColor(R.styleable.CustomBarGraph_fillColor3, Color.TRANSPARENT);
        int fillColor4 = ta.getColor(R.styleable.CustomBarGraph_fillColor4, Color.TRANSPARENT);
        int fillColor5 = ta.getColor(R.styleable.CustomBarGraph_fillColor5, Color.TRANSPARENT);

        ta.recycle();

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(baseColor);

        fillPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint1.setColor(fillColor1);

        fillPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint2.setColor(fillColor2);

        fillPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint3.setColor(fillColor3);

        fillPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint4.setColor(fillColor4);

        fillPaint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint5.setColor(fillColor5);

        cornerOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cornerOverlayPaint.setColor(cornerOverlayColor);
    }

    private int measureHeight(int measureSpec) {
        int size = barHeight + getPaddingTop() + getPaddingBottom();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingLeft() + getPaddingRight();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBase(canvas);
        drawBar1(canvas);
        drawBar2(canvas);
        drawBar3(canvas);
        drawBar4(canvas);
        drawBar5(canvas);
        drawCornerOverlays(canvas);
    }

    private void drawBase(Canvas canvas) {
        Rect maxValueRect = new Rect();
        float barLength = getWidth() - getPaddingRight() - getPaddingLeft() - maxValueRect.width();
        float barCenter = getBarCenter();
        float halfBarHeight = barHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft();
        float right = getPaddingLeft() + barLength;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRect(rect, barBasePaint);
    }

    private void drawBar1(Canvas canvas) {
        drawBar(canvas, fillPaint1, valueToDraw1);
    }

    private void drawBar2(Canvas canvas) {
        drawBar(canvas, fillPaint2, valueToDraw2);
    }

    private void drawBar3(Canvas canvas) {
        drawBar(canvas, fillPaint3, valueToDraw3);
    }

    private void drawBar4(Canvas canvas) {
        drawBar(canvas, fillPaint4, valueToDraw4);
    }

    private void drawBar5(Canvas canvas) {
        drawBar(canvas, fillPaint5, valueToDraw5);
    }

    private void drawBar(Canvas canvas, Paint fillPaint, float valueToDraw) {

        Rect maxValueRect = new Rect();

        float barLength = getWidth() - getPaddingRight() - getPaddingLeft() - maxValueRect.width();
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
        float bottom = getPaddingTop() + barHeight;

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

    private float getBarCenter() {
        float barCenter = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        barCenter += getPaddingTop();
        return barCenter;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
    }
}