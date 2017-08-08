package ca.jmdv.bargraphtester.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import ca.jmdv.bargraphtester.R;
import ca.jmdv.bargraphtester.models.ColorValuePair;

public class CustomBarGraph extends View {

    private static final String TAG = CustomBarGraph.class.getSimpleName();

    private float maxValue = 100;
    private float currentFillLength = 0;
    private int barHeight;
    private Paint barBasePaint;
    private Paint cornerOverlayPaint;

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

    private void init(Context context, AttributeSet attrs) {

        setSaveEnabled(true);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBarGraph, 0, 0);
        barHeight = ta.getDimensionPixelSize(R.styleable.CustomBarGraph_barHeight, 0);

        int baseColor = ta.getColor(R.styleable.CustomBarGraph_baseColor, Color.BLACK);
        int cornerOverlayColor = ta.getColor(R.styleable.CustomBarGraph_cornerOverlayColor, Color.TRANSPARENT);

        ta.recycle();

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(baseColor);

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
        for (int i = 0; i < readyValues.size(); i++) {
            drawBar(canvas, readyColors.get(i), readyValues.get(i));
        }
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