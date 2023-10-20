package com.company;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class M {
    int humidity, pressure;
    double temp_max, temp_min, temp;

    @Override
    public String toString() {
        return "M{" +
                "humidity=" + humidity +
                ", pressure=" + pressure +
                ", temp_max=" + temp_max +
                ", temp_min=" + temp_min +
                ", temp=" + temp +
                '}';
    }
}

class Weather {
    M main;
}

class CityTemperature {
    String city;
    int temperature;

    public CityTemperature(String city, int temperature) {
        this.city = city;
        this.temperature = temperature;
    }
}

class MyThread extends Thread {
    String city;
    CityTemperature cityTemperature;

    public MyThread(String city) {
        this.city = city;
    }

    @Override
    public void run() {
        try {
            String apiUrl = String.format(Main.API_URL, city);
            URL weatherUrl = new URL(apiUrl);
            InputStream stream = (InputStream) weatherUrl.getContent();
            Gson gson = new Gson();
            Weather weather = gson.fromJson(new InputStreamReader(stream), Weather.class);
            int temperatureCelsius = (int) (weather.main.temp - 273.15);  // конвертация из Кельвинов в градусы Цельсия
            cityTemperature = new CityTemperature(city, temperatureCelsius);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    final static String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=f90eebbb02f939d9b5edc9413be1d9ce";

    public static void main(String[] args) throws InterruptedException {
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose", "Paris","Irkutsk"};

        List<MyThread> threads = new ArrayList<>();
        List<CityTemperature> cityTemperatures = new ArrayList<>();

        // создание и запуск потоков
        for (String city : cities) {
            MyThread weatherThread = new MyThread(city);
            threads.add(weatherThread);
            weatherThread.start();
        }

        // ожидание завершения всех потоков
        for (MyThread thread : threads) {
            thread.join();
            cityTemperatures.add(thread.cityTemperature);
        }

        // сортировка списка городов по температуре в порядке убывания
        Collections.sort(cityTemperatures, Comparator.comparingInt(cityTemperature -> -cityTemperature.temperature));

        // вывод результатов
        for (CityTemperature cityTemperature : cityTemperatures) {
            System.out.println(cityTemperature.city + ": " + cityTemperature.temperature + "°C");
        }
    }
}
