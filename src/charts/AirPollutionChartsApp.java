package charts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AirPollutionChartsApp extends Application {
    private ArrayList<AirPollutantEmission> emissions = new ArrayList<>();
    private LineChart<Number, Number> lineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private String selectedCountry = "GBR";

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

    public double findMinYear() {
        double min = 2999;
        for (AirPollutantEmission e : emissions) {
            if (e.getYear() < min) 
                min = e.getYear();
        }

        return min;
    }

    public double findMaxYear() {
        double max = 1999;
        for (AirPollutantEmission e : emissions) {
            if (e.getYear() > max) 
                max = e.getYear();
        }

        return max;
    }

    public void prepareChart() {
        XYChart.Series<Number, Number> noxSeries = new XYChart.Series<>();
        noxSeries.setName("Nitrogen oxides (NOx)");
        XYChart.Series<Number, Number> so2Series = new XYChart.Series<>();
        so2Series.setName("Sulphur dioxide (SO₂)");
        XYChart.Series<Number, Number> vocsSeries = new XYChart.Series<>();
        vocsSeries.setName("Non-methane volatile organic compounds (VOCs)");

        for (AirPollutantEmission emission : emissions) {
            if (selectedCountry.equals(emission.getCountryCode())) {
                noxSeries.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getNox())
                    );
                so2Series.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getSo2())
                    );
                vocsSeries.getData().add(
                    new XYChart.Data<>(emission.getYear(), emission.getVocs())
                    );    
        
            }
        }

        lineChart.getData().clear();
        lineChart.getData().add(noxSeries);
        lineChart.getData().add(so2Series);
        lineChart.getData().add(vocsSeries);

    }

    public Parent createContent() {
        xAxis = new NumberAxis("Year", findMinYear(), findMaxYear(), 1);

        yAxis = new NumberAxis();
        yAxis.setLabel("Emissions");
        yAxis.setAutoRanging(true);

        lineChart = new LineChart<>(xAxis, yAxis);


        BorderPane pane = new BorderPane();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().add(new Label("Country:"));
        ObservableList<String> countryList = FXCollections.observableArrayList("USA", "GBR");
        ComboBox<String> countryCB = new ComboBox<>(countryList);
        countryCB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                selectedCountry = countryCB.getValue();
                prepareChart();
            }
        });
        
        hbox.getChildren().add(countryCB);

        pane.setTop(hbox);
        pane.setCenter(lineChart);

        return pane;
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
