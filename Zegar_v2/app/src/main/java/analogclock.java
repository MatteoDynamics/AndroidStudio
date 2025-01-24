
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AnalogClock extends View {

    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    private Paint circlePaint;
    private Paint hourPaint;
    private Paint minutePaint;
    private Paint secondPaint;

    private int centerX;
    private int centerY;
    private int radius;

    public AnalogClock(Context context) {
        super(context);
        init();
    }

    public AnalogClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        hourPaint = new Paint();
        hourPaint.setColor(Color.BLUE);
        hourPaint.setStrokeWidth(8);

        minutePaint = new Paint();
        minutePaint.setColor(Color.GREEN);
        minutePaint.setStrokeWidth(5);

        secondPaint = new Paint();
        secondPaint.setColor(Color.RED);
        secondPaint.setStrokeWidth(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(centerX, centerY) - 10; // Trochę marginesu
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Rysuj okrąg
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Oblicz kąty wskazówek
        float secondAngle = second * 6f; // 360 stopni / 60 sekund
        float minuteAngle = minute * 6f + second / 10f; // Dodajemy wpływ sekund na minuty
        float hourAngle = (hour % 12) * 30f + minute / 2f;  // Dodajemy wpływ minut na godziny

        // Rysuj wskazówki
        drawHand(canvas, hourAngle, radius * 0.5f, hourPaint);
        drawHand(canvas, minuteAngle, radius * 0.7f, minutePaint);
        drawHand(canvas, secondAngle, radius * 0.9f, secondPaint);

        //Wymusza odświeżanie widoku co sekundę - ważne dla animacji sekundnika
        postInvalidateDelayed(1000);
    }

    private void drawHand(Canvas canvas, float angle, float length, Paint paint) {
        double radians = Math.toRadians(angle - 90); // -90 bo 0 stopni jest "na godzinie 3"
        float x = (float) (centerX + Math.cos(radians) * length);
        float y = (float) (centerY + Math.sin(radians) * length);
        canvas.drawLine(centerX, centerY, x, y, paint);
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        invalidate(); // Wymusza ponowne narysowanie widoku
    }
}