package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.io.File;

public class FileSelectionScreen extends YazanScreen {

    private Stage stage;

    public FileSelectionScreen(Stage stage) {
        super("Wait!", "Prior to starting the application, please make sure to select the appropriate data files", true);

        this.stage = stage;
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);

        VBox majorsBox = new VBox(5);
        Label selectMajors = UIUtils.label("Select Flights Data File", FontWeight.BOLD, 14, null);

        Button selectMButton = new Button("Click here to select a file");
        selectMButton.setPrefHeight(30);
        selectMButton.setPrefWidth(220);

        VBox studentsBox = new VBox(5);
        Label selectStudents = UIUtils.label("Select Passengers Data File", FontWeight.BOLD, 14, null);

        Button selectSButton = new Button("Click here to select a file");
        selectSButton.setPrefHeight(30);
        selectSButton.setPrefWidth(220);
        selectSButton.setDisable(true);

        selectMButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;

            Airport.PRIMARY_MANAGER.getFileSystem().setMajorsData(file);
            Airport.PRIMARY_MANAGER.getFileSystem().readMajorsData();

            selectMButton.setDisable(true);
            selectMButton.setText("Selected: " + file.getName());
            selectSButton.setDisable(false);
        });

        selectSButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;

            Airport.PRIMARY_MANAGER.getFileSystem().setStudentsData(file);
            Airport.PRIMARY_MANAGER.getFileSystem().readStudentsData();

            selectSButton.setText("Selected: " + file.getName());
            selectSButton.setDisable(true);

            UIHandler.getInstance().open(new HomeScreen(), 800, 500);
        });

        majorsBox.getChildren().addAll(selectMajors, selectMButton);
        majorsBox.setAlignment(Pos.CENTER);

        studentsBox.getChildren().addAll(selectStudents, selectSButton);
        studentsBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(majorsBox, studentsBox);

        return vBox;
    }
}

