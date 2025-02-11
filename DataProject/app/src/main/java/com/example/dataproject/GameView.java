package com.example.dataproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import androidx.annotation.Nullable;

public class GameView extends View {

    private int numberOfPlayers;
    private ArrayList<String> playerNames;
    private Paint paint;
    private RectF circleBounds;
    private Handler handler;
    private Random random;
    private boolean gameStarted;
    private long startSignalTime;
    private int tapOrder;
    private HashMap<Integer, Integer> roundScores;

    public interface GameListener {
        void onRoundEnd(HashMap<Integer, Integer> roundScores);
        void onGameEnd(HashMap<Integer, Integer> finalScores);
    }

    private GameListener listener;

    public GameView(Context context, int numberOfPlayers, ArrayList<String> playerNames, GameListener listener) {
        super(context);
        init(numberOfPlayers, playerNames, listener);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // Domyślne wartości – do zmiany
        init(4, new ArrayList<String>() {{ add("Gracz 1"); add("Gracz 2"); add("Gracz 3"); add("Gracz 4"); }}, null);
    }

    private void init(int numberOfPlayers, ArrayList<String> playerNames, GameListener listener) {
        this.numberOfPlayers = numberOfPlayers;
        this.playerNames = playerNames;
        this.listener = listener;
        paint = new Paint();
        paint.setAntiAlias(true);
        handler = new Handler();
        random = new Random();
        roundScores = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            roundScores.put(i, 0);
        }
        tapOrder = 0;
        startNewRound();
    }

    public void startNewRound() {
        gameStarted = false;
        tapOrder = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            roundScores.put(i, 0);
        }
        invalidate();
        int delay = 2000 + random.nextInt(3000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameStarted = true;
                startSignalTime = System.currentTimeMillis();
                invalidate();
            }
        }, delay);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float cx = width / 2f;
        float cy = height / 2f;
        float radius = Math.min(cx, cy) - 20;

        if (circleBounds == null) {
            circleBounds = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        }

        float sweepAngle = 360f / numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {
            paint.setColor(gameStarted ? Color.GREEN : Color.RED);
            float startAngle = i * sweepAngle;
            canvas.drawArc(circleBounds, startAngle, sweepAngle, true, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            float angleRad = (float) Math.toRadians(startAngle + sweepAngle / 2);
            float textX = cx + (radius / 2) * (float) Math.cos(angleRad);
            float textY = cy + (radius / 2) * (float) Math.sin(angleRad);
            canvas.drawText(playerNames.get(i), textX - 20, textY, paint);
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        if (!gameStarted) {
            canvas.drawText("Czekaj...", cx - 80, cy, paint);
        } else {
            canvas.drawText("START!", cx - 50, cy, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int width = getWidth();
            int height = getHeight();
            float cx = width / 2f;
            float cy = height / 2f;
            float dx = x - cx;
            float dy = y - cy;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance > circleBounds.width() / 2) {
                return false;
            }
            // Obliczenie kąta dotknięcia
            float touchAngle = (float) Math.toDegrees(Math.atan2(dy, dx));
            if (touchAngle < 0) touchAngle += 360;
            float sweepAngle = 360f / numberOfPlayers;
            int sector = (int) (touchAngle / sweepAngle);

            if (!gameStarted) {
                Toast.makeText(getContext(), "Za wcześnie! -1 punkt dla " + playerNames.get(sector), Toast.LENGTH_SHORT).show();
                roundScores.put(sector, -1);
            } else {
                if (roundScores.get(sector) != 0) {
                    Toast.makeText(getContext(), "Gracz " + playerNames.get(sector) + " już zareagował!", Toast.LENGTH_SHORT).show();
                } else {
                    tapOrder++;
                    int points = 0;
                    if (tapOrder == 1) points = 5;
                    else if (tapOrder == 2) points = 3;
                    else if (tapOrder == 3) points = 1;
                    roundScores.put(sector, points);
                    Toast.makeText(getContext(), "Gracz " + playerNames.get(sector) + " zdobywa " + points + " pkt", Toast.LENGTH_SHORT).show();
                }
            }

            if (allPlayersResponded()) {
                if (listener != null) {
                    listener.onRoundEnd(roundScores);
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean allPlayersResponded() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (roundScores.get(i) == 0) return false;
        }
        return true;
    }
}
