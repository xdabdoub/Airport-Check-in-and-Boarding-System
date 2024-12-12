package me.yhamarsheh.airport.ui.screens.sub;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Operation;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.GeneralUtils;
import me.yhamarsheh.airport.utilities.UIUtils;

public class FlightOperationScreen extends YazanScreen {

    public FlightOperationScreen() {
        super("Al Aqsa International Airport (AQIA)", "Flight Operations Screen", true);
    }

    @Override
    public Node setup() {
        VBox details = new VBox(20);

        VBox flightId = new VBox(5);
        Label label = UIUtils.label("Flight ID", null, 0, null);
        TextField textField = new TextField();
        textField.setPromptText("(e.g) 101");

        flightId.getChildren().addAll(label, textField);

        HBox actions = new HBox(10);
        Button undo = new Button("Undo");
        undo.setPrefWidth(200);
        undo.setPrefHeight(40);
        undo.setFont(new Font(18));

        undo.setOnAction(e -> {
            Flight flight = GeneralUtils.getFlightById(Integer.parseInt(textField.getText()));
            if (flight == null) {
                UIUtils.alert("The entered flight seems to be invalid. Are you sure it exists?", Alert.AlertType.WARNING).show();
                return;
            }

            Operation operation = flight.getUndoOperations().pop();
            if (operation == null) return;
            operation.undo();

            UIUtils.alert("Un-did the latest operation successfully. More Details: " + operation.getDescription(), Alert.AlertType.INFORMATION).show();
            flight.getRedoOperations().push(new Operation("Undo", " | " + operation.getType() + " | " + operation.getDescription()) {
                @Override
                public void undo() {
                    operation.redo();
                }

                @Override
                public void redo() {
                    operation.undo();
                }
            });
        });

        Button redo = new Button("Redo");
        redo.setPrefWidth(200);
        redo.setPrefHeight(40);
        redo.setFont(new Font(18));
        redo.setOnAction(e -> {
            Flight flight = GeneralUtils.getFlightById(Integer.parseInt(textField.getText()));
            if (flight == null) {
                UIUtils.alert("The entered flight seems to be invalid. Are you sure it exists?", Alert.AlertType.WARNING).show();
                return;
            }

            Operation operation = flight.getRedoOperations().pop();
            if (operation == null) return;
            operation.redo();

            UIUtils.alert("Re-did the latest operation successfully. More Details: " + operation.getDescription(), Alert.AlertType.INFORMATION).show();
            flight.getUndoOperations().push(new Operation("Redo", " | " + operation.getType() + " | " + operation.getDescription()) {
                @Override
                public void undo() {
                    operation.redo();
                }

                @Override
                public void redo() {
                    operation.undo();
                }
            });
        });

        actions.getChildren().addAll(undo, redo);
        return null;
    }
}
