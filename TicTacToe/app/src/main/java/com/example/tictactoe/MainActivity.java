package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

enum Player {
    one,
    two
}

public class MainActivity extends AppCompatActivity {

    private Player currentPlayer = Player.one;
    private Button[] buttons = new Button[9];
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons in the array
        buttons[0] = findViewById(R.id.button6);
        buttons[1] = findViewById(R.id.button5);
        buttons[2] = findViewById(R.id.button4);
        buttons[3] = findViewById(R.id.button7);
        buttons[4] = findViewById(R.id.button8);
        buttons[5] = findViewById(R.id.button9);
        buttons[6] = findViewById(R.id.button10);
        buttons[7] = findViewById(R.id.button11);
        buttons[8] = findViewById(R.id.button12);

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick((Button) v);
            }
        };

        for (Button button : buttons) {
            button.setOnClickListener(clickListener);
        }
    }

    private void handleButtonClick(Button button) {
        if (gameOver || !button.getText().toString().isEmpty()) {
            return;
        }

        if (currentPlayer == Player.one) {
            button.setText("O");
            currentPlayer = Player.two;
        } else {
            button.setText("X");
            currentPlayer = Player.one;
        }

        if (checkWinner()) {
            gameOver = true;
            String winner = (currentPlayer == Player.one) ? "Player X" : "Player O";
            Toast.makeText(this, winner + " wins!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkWinner() {
        int[][] winningCombinations = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        for (int[] combination : winningCombinations) {
            String first = buttons[combination[0]].getText().toString();
            String second = buttons[combination[1]].getText().toString();
            String third = buttons[combination[2]].getText().toString();

            if (!first.isEmpty() && first.equals(second) && second.equals(third)) {
                return true;
            }
        }

        return false;
    }

    private void restartGame() {
        for (Button button : buttons) {
            button.setText("");
        }

        currentPlayer = Player.one;
        gameOver = false;
    }
}
