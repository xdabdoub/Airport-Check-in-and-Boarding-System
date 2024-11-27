package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.io.FileNotFoundException;

public class HomeScreen extends YazanScreen {

    public HomeScreen() {
        super("Al Quds International Airport (AQIA)",
              "مــطــار اﻟــقــدس اﻟــدولــي - بــوابــتــك لــفــلــســطــيــن و بــوابــة اﻟــفــلــســطــيــنــيــيــن إﻟــى اﻟــعــالــم",
                true);
    }

    @Override
    public Node setup() {

        VBox vBox = new VBox(20);

        Button viewFlights = new Button("Flights Viewer");
        viewFlights.setPrefHeight(30);
        viewFlights.setPrefWidth(220);

        Button viewLogFile = new Button("Log File Menu");
        viewLogFile.setPrefHeight(30);
        viewLogFile.setPrefWidth(220);

        Button viewStatistics = new Button("System Statistics");
        viewStatistics.setPrefHeight(30);
        viewStatistics.setPrefWidth(220);

        Button saveToFile = new Button("Save Recent Changes");
        saveToFile.setPrefHeight(30);
        saveToFile.setPrefWidth(220);

        viewFlights.setOnAction(e -> {
            UIHandler.getInstance().open(new FlightsScreen(), 800, 500);
        });

        viewLogFile.setOnAction(e -> {
            UIHandler.getInstance().open(new LogFileScreen(), 800, 500);
        });

        viewStatistics.setOnAction(e -> {
            UIHandler.getInstance().open(new StatisticsScreen(), 800, 500);
        });

        saveToFile.setOnAction(e -> {
            try {
                Airport.PRIMARY_MANAGER.getFileSystem().update();
            } catch (FileNotFoundException ex) {
                UIUtils.alert("Original files couldn't be found!", Alert.AlertType.ERROR).show();
                return;
            }

            UIUtils.alert("Successfully saved recent changes to the original files selected!", Alert.AlertType.INFORMATION).show();
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(viewFlights, viewLogFile, viewStatistics, saveToFile);
        return vBox;
    }
}
