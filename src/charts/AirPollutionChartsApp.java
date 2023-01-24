package charts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AirPollutionChartsApp extends Application {
    private ArrayList<AirPollutantEmission> emissions = new ArrayList<>();
    private LineChart<Number, Number> lineChart;
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

        reader.close();

    }

    public Parent createContent() {
        xAxis = new NumberAxis("Year", 1970d, 2016d, 10d);
        yAxis = new NumberAxis("Emissions", 0.0d, 30000000.0d, 100000.0d);
        lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> usNoxSeries = new XYChart.Series<>();
        usNoxSeries.setName("Nitrogen oxides (NOx) - US");
        XYChart.Series<Number, Number> usSo2Series = new XYChart.Series<>();
        usSo2Series.setName("Sulphur dioxide (SO₂) - US");
        XYChart.Series<Number, Number> ukNoxSeries = new XYChart.Series<>();
        ukNoxSeries.setName("Nitrogen oxides (NOx) - UK");
        XYChart.Series<Number, Number> ukSo2Series = new XYChart.Series<>();
        ukSo2Series.setName("Sulphur dioxide (SO₂) - UK");

        for (AirPollutantEmission emission : emissions) {
            if ("GBR".equals(emission.getCountryCode())) {
                ukNoxSeries.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getNox())
                    );
                ukSo2Series.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getSo2())
                    );
        
            } else if ("USA".equals(emission.getCountryCode())) {
                usNoxSeries.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getNox())
                    );
                usSo2Series.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getSo2())
                    );
                }

        }

        lineChart.getData().add(usNoxSeries);
        lineChart.getData().add(usSo2Series);
        lineChart.getData().add(ukNoxSeries);
        lineChart.getData().add(ukSo2Series);
            
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
