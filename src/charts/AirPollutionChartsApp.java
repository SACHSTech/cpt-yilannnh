package charts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class AirPollutionChartsApp extends Application {
    private ArrayList<AirPollutantEmission> emissions = new ArrayList<>();
    private LineChart lineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public void loadRawData() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("air-pollutant-emissions.csv"));
        String line = reader.readLine();

        while (line != null) {
            System.out.println(line);
            // read next line
            line = reader.readLine();
            if (line != null) {
                String [] values = line.split(",");
                AirPollutantEmission emission = new AirPollutantEmission(
                    values[0],
                    values[1],
                    Integer.valueOf(values[2]),
                    Float.valueOf(values[3]),
                    Float.valueOf(values[4]),
                    Float.valueOf(values[5])
                );
                emissions.add(emission);
            }
        }

    }

    public Parent createContent() {
        xAxis = new NumberAxis("Year", 1970d, 2016d, 10d);
        yAxis = new NumberAxis("Emissions", 0.0d, 3000.0d, 1000.0d);
        lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series noxSeries = new XYChart.Series();
        noxSeries.setName("Nitrogen oxides (NOx)");
        for (AirPollutantEmission emission : emissions) {
            noxSeries.getData().add(
                new XYChart.Data(emission.getYear(), emission.getNox())
                );

        }

        lineChart.getData().add(noxSeries);
            
        return lineChart;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadRawData();

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

}
