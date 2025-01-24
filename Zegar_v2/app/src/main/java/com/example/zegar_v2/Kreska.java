package com.example.zegar_v2; // Replace with your app's package name

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Kreska extends View {
    private Paint paint;

    public Kreska(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the Paint object
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED); // Set the color to red
        paint.setStrokeWidth(5);  // Set the line thickness
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a red diagonal line across the view
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }
}
