package com.example.analogclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AnalogClockView extends View {
    private int hours = 0, minutes = 0, seconds = 0;
    private float width, height, radius;
    private Paint paint;

    public AnalogClockView(Context context) {
        super(context);
        init();
    }

    public AnalogClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setTextSize(40f);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTime(int hours, int minutes, int seconds) {
        this.hours = hours % 12;
        this.minutes = minutes % 60;
        this.seconds = seconds % 60;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = Math.min(width, height) / 2 * 0.8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = width / 2;
        float centerY = height / 2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(centerX, centerY, radius, paint);

        for (int i = 0; i < 12; i++) {
            float angle = (float) Math.toRadians(i * 30);
            float startX = centerX + (float) (radius * 0.9 * Math.sin(angle));
            float startY = centerY - (float) (radius * 0.9 * Math.cos(angle));
            float endX = centerX + (float) (radius * Math.sin(angle));
            float endY = centerY - (float) (radius * Math.cos(angle));
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        paint.setColor(Color.BLUE);
        drawHand(canvas, centerX, centerY, (hours + minutes / 60f) * 30, radius * 0.5f);

        paint.setColor(Color.GREEN);
        drawHand(canvas, centerX, centerY, (minutes + seconds / 60f) * 6, radius * 0.7f);

        paint.setColor(Color.RED);
        drawHand(canvas, centerX, centerY, seconds * 6, radius * 0.9f);
    }

    private void drawHand(Canvas canvas, float cx, float cy, float angle, float length) {
        angle = (float) Math.toRadians(angle - 90);
        float endX = cx + (float) (length * Math.cos(angle));
        float endY = cy + (float) (length * Math.sin(angle));
        canvas.drawLine(cx, cy, endX, endY, paint);
    }
}
