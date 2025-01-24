package com.example.zegar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Kreska extends View
{
    private Paint p;

    public Kreska(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, 0, 100, 100, p);
    }

// jeśli chcecie poznać rozmiar komponentu, to dodatkowo przedefiniujcie funkcję
// onSizeChanged(...)
}
