package com.example.randommovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.*;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private TextView movieInfo;
    private Button btnRandomMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Используйте правильные идентификаторы, как в XML
        btnRandomMovie = findViewById(R.id.button_random_movie);
        // Если вы хотите использовать один TextView для отображения всей информации,
        // у вас должен быть один TextView с id, который вы будете использовать для установки текста.
        movieInfo = findViewById(R.id.text_view_title);

        // Загружаем список фильмов из JSON файла
        loadMoviesFromAssets();

        btnRandomMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movieList.isEmpty()) {
                    // Выбираем случайный фильм и отображаем информацию
                    Collections.shuffle(movieList);
                    Movie movie = movieList.remove(movieList.size() - 1);
                    displayMovieInfo(movie);
                } else {
                    // Фильмы закончились
                    movieInfo.setText("Все фильмы были показаны.");
                    btnRandomMovie.setEnabled(false);
                }
            }
        });
    }

    private void loadMoviesFromAssets() {
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("movies.json"));
            movieList = new Gson().fromJson(isr, new TypeToken<ArrayList<Movie>>() {
            }.getType());
        } catch (Exception e) {
            movieInfo.setText("Ошибка при загрузке фильмов.");
        }
    }

    private void displayMovieInfo(Movie movie) {
        String info = "Название: " + movie.getTitle() + "\n" +
                "Год: " + movie.getYear() + "\n" +
                "Длительность: " + movie.getDuration() + "\n" +
                "Жанр: " + movie.getGenre() + "\n" +
                "Режиссёр: " + movie.getDirector();
        movieInfo.setText(info);
    }

}