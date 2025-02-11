package com.example.dataproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private int numberOfPlayers;
    private ArrayList<String> playerNames;
    private int roundsPerGame;
    private int currentRound;
    private HashMap<Integer, Integer> totalScores;
    private DatabaseHelper dbHelper;

    private static final String KEY_CURRENT_ROUND = "currentRound";
    private static final String KEY_TOTAL_SCORES = "totalScores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        numberOfPlayers = prefs.getInt("numberOfPlayers", 4);
        roundsPerGame = prefs.getInt("roundsPerGame", 5);

        playerNames = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerNames.add("Gracz " + (i + 1));
        }

        totalScores = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            totalScores.put(i, 0);
        }
        currentRound = 1;

        dbHelper = new DatabaseHelper(this);

        gameView = new GameView(this, numberOfPlayers, playerNames, new GameView.GameListener() {
            @Override
            public void onRoundEnd(HashMap<Integer, Integer> roundScores) {
                for (int i = 0; i < numberOfPlayers; i++) {
                    int prev = totalScores.get(i);
                    totalScores.put(i, prev + roundScores.get(i));
                }
                currentRound++;

                if (currentRound > roundsPerGame || isTie(totalScores)) {
                    dbHelper.insertGameHistory(totalScores, System.currentTimeMillis());
                } else {
                    gameView.startNewRound();
                }
            }

            @Override
            public void onGameEnd(HashMap<Integer, Integer> finalScores) {
            }
        });

        setContentView(gameView);
        if (savedInstanceState != null) {
            currentRound = savedInstanceState.getInt(KEY_CURRENT_ROUND, 1);
            totalScores = (HashMap<Integer, Integer>) savedInstanceState.getSerializable(KEY_TOTAL_SCORES);
        }
    }
    private boolean isTie(HashMap<Integer, Integer> scores) {
        int max = -1000;
        int count = 0;
        for (int score : scores.values()) {
            if (score > max) {
                max = score;
                count = 1;
            } else if (score == max) {
                count++;
            }
        }
        return count > 1;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CURRENT_ROUND, currentRound);
        outState.putSerializable(KEY_TOTAL_SCORES, totalScores);
        super.onSaveInstanceState(outState);
    }
}
