package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LogFileScreen extends YazanScreen {

    public LogFileScreen() {
        super("Al Aqsa International Airport (AQIA)", "Log File Screen", true);
    }

    @Override
    public Node setup() {
        VBox root = new VBox(20);
        Button print = new Button("Print Log File");
        print.setPrefWidth(220);
        print.setPrefHeight(30);

        Button export = new Button("Export Log File");
        export.setPrefWidth(220);
        export.setPrefHeight(30);

        print.setOnAction(e -> {
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setText(Airport.PRIMARY_MANAGER.getLogFileContents());

            Scene scene = new Scene(textArea, 150, 150);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Log File");
            stage.show();
        });

        export.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(UIHandler.getInstance().getStage());
            if (file == null) return;

            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println(Airport.PRIMARY_MANAGER.getLogFileContents());
                UIUtils.alert("Successfully exported the log file to " + file.getName(), Alert.AlertType.INFORMATION).show();
            } catch (FileNotFoundException ex) {
                System.out.println("Selected log file couldn't be found.");
            }
        });

        Button back = new Button("Back");
        back.setPrefHeight(30);
        back.setPrefWidth(220);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 800);
        });

        root.getChildren().addAll(print, export, back);
        root.setAlignment(Pos.CENTER);

        return root;
    }
}
