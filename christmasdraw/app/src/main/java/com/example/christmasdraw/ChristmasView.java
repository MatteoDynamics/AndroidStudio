package com.example.christmasdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ChristmasView extends View {

    public ChristmasView(Context context) {
        super(context);
    }

    public ChristmasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        float centerX = canvasWidth / 2f;

        paint.setColor(Color.GREEN);
        Path path = new Path();

        float treeBaseWidth = canvasWidth / 3f;
        float treeHeight = 150;

        float bottomTriangleTopY = 250;
        float bottomTriangleBottomY = bottomTriangleTopY + treeHeight;
        path.reset();
        path.moveTo(centerX, bottomTriangleTopY);
        path.lineTo(centerX - treeBaseWidth / 2, bottomTriangleBottomY);
        path.lineTo(centerX + treeBaseWidth / 2, bottomTriangleBottomY);
        canvas.drawPath(path, paint);

        float middleTriangleTopY = bottomTriangleTopY - 80;
        float middleTriangleBottomY = middleTriangleTopY + treeHeight;
        float middleBaseWidth = treeBaseWidth * 0.8f;
        path.reset();
        path.moveTo(centerX, middleTriangleTopY);
        path.lineTo(centerX - middleBaseWidth / 2, middleTriangleBottomY);
        path.lineTo(centerX + middleBaseWidth / 2, middleTriangleBottomY);
        path.close();
        canvas.drawPath(path, paint);

        float topTriangleTopY = middleTriangleTopY - 80;
        float topTriangleBottomY = topTriangleTopY + treeHeight;
        float topBaseWidth = middleBaseWidth * 0.8f;
        path.reset();
        path.moveTo(centerX, topTriangleTopY);
        path.lineTo(centerX - topBaseWidth / 2, topTriangleBottomY);
        path.lineTo(centerX + topBaseWidth / 2, topTriangleBottomY);
        path.close();
        canvas.drawPath(path, paint);


        paint.setColor(Color.rgb(101, 67, 33));
        float trunkWidth = treeBaseWidth / 6;
        float trunkHeight = 40;
        float trunkLeft = centerX - trunkWidth / 2;
        float trunkTop = bottomTriangleBottomY;
        canvas.drawRect(trunkLeft, trunkTop, trunkLeft + trunkWidth, trunkTop + trunkHeight, paint);

        float presentY = trunkTop + trunkHeight + 20;
        float presentWidth = 60;
        float presentHeight = 40;

        paint.setColor(Color.RED);
        canvas.drawRect(centerX - treeBaseWidth, presentY, centerX - treeBaseWidth + presentWidth, presentY + presentHeight, paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(centerX + treeBaseWidth - presentWidth, presentY, centerX + treeBaseWidth, presentY + presentHeight, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(centerX - presentWidth / 2, presentY + presentHeight + 10, centerX + presentWidth / 2, presentY + presentHeight + 10 + presentHeight, paint);

        float santaHeadRadius = 20;

        float santaHeadCenterX = centerX - treeBaseWidth - 50;
        float santaHeadCenterY = topTriangleTopY + 20;
        paint.setColor(Color.rgb(255, 224, 189));
        canvas.drawCircle(santaHeadCenterX, santaHeadCenterY, santaHeadRadius, paint);

        paint.setColor(Color.RED);
        Path hatPath = new Path();
        hatPath.moveTo(santaHeadCenterX - santaHeadRadius, santaHeadCenterY);
        hatPath.lineTo(santaHeadCenterX, santaHeadCenterY - santaHeadRadius - 15);
        hatPath.lineTo(santaHeadCenterX + santaHeadRadius, santaHeadCenterY);
        hatPath.close();
        canvas.drawPath(hatPath, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(santaHeadCenterX, santaHeadCenterY - santaHeadRadius - 15, 5, paint);

        paint.setColor(Color.BLACK);
        canvas.drawCircle(santaHeadCenterX - 7, santaHeadCenterY - 5, 2, paint);
        canvas.drawCircle(santaHeadCenterX + 7, santaHeadCenterY - 5, 2, paint);
    }
}
