package com.example.colortiles;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkColor;
    int brightColor;
    View[][] tiles = new View[4][4];
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.gridLayout);

        // Инициализируем цвета
        darkColor = ContextCompat.getColor(this, R.color.dark);
        brightColor = ContextCompat.getColor(this, R.color.bright);

        // Инициализация массива tiles и присвоение ID
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String tileID = "tile" + i + j;
                int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                tiles[i][j] = findViewById(resID);
                tiles[i][j].setBackgroundColor(new Random().nextBoolean() ? darkColor : brightColor);
                tiles[i][j].setTag(tileID);
            }
        }
    }

    public void onClick(View v) {
        // Получаем тэг на кнопке
        String tag = (String) v.getTag();
        int x = Character.getNumericValue(tag.charAt(4));
        int y = Character.getNumericValue(tag.charAt(5));

        // Изменяем цвет на самом тайле и всех тайлах с таким же x и таким же y
        changeColor(x, y);

        // Проверка на выигрыш после каждого нажатия
        if (checkWin()) {
            Toast.makeText(this, "Все плитки одного цвета! Вы выиграли!", Toast.LENGTH_LONG).show();
        }
    }

    private void changeColor(int x, int y) {
        for (int i = 0; i < 4; i++) {
            // Изменяем цвет в ряду
            invertColor(tiles[x][i]);
            // Изменяем цвет в колонне
            invertColor(tiles[i][y]);
        }
    }

    private void invertColor(View tile) {
        if (((ColorDrawable) tile.getBackground()).getColor() == brightColor) {
            tile.setBackgroundColor(darkColor);
        } else {
            tile.setBackgroundColor(brightColor);
        }
    }

    private boolean checkWin() {
        int color = ((ColorDrawable) tiles[0][0].getBackground()).getColor();
        for (View[] row : tiles) {
            for (View tile : row) {
                if (((ColorDrawable) tile.getBackground()).getColor() != color) {
                    return false;
                }
            }
        }
        return true;
    }
}