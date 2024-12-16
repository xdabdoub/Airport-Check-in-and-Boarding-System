package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Operation;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.GeneralUtils;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.util.Optional;

public class FlightEditorScreen extends YazanScreen {

    private Flight currentFlight;
    public FlightEditorScreen(Flight currentFlight) {
        super("title", "Flight Editor", false);
        this.currentFlight = currentFlight;

        setCenter(setup());
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);
        GridPane gp = new GridPane();

        Label idL = UIUtils.label("ID", FontWeight.BOLD, 12, null);
        TextField idF = new TextField(currentFlight == null ? "" : currentFlight.getId() + "");
        idF.setPrefHeight(20);
        idF.setPrefWidth(120);

        Label destination = UIUtils.label("Destination", FontWeight.BOLD, 12, null);
        TextField destinationF = new TextField(currentFlight == null ? "" : currentFlight.getDestination());
        destinationF.setPrefHeight(20);
        destinationF.setPrefWidth(120);


        Label activeStatus = UIUtils.label("Active Status", FontWeight.BOLD, 12, null);
        CheckBox activeSF = new CheckBox();
        activeSF.setSelected(currentFlight != null && currentFlight.isActiveStatus());

        gp.setHgap(20);
        gp.setVgap(20);

        gp.setAlignment(Pos.CENTER);

        gp.add(idL, 0, 0);
        gp.add(idF, 0, 1);

        gp.add(destination, 0, 2);
        gp.add(destinationF, 0, 3);

        gp.add(activeStatus, 1, 0);
        gp.add(activeSF, 1, 1);

        HBox actions = new HBox(10);
        Button insert = new Button("Insert");
        insert.setPrefHeight(20);
        insert.setPrefWidth(120);

        insert.setDisable(currentFlight != null);

        insert.setOnAction(e -> {
            if (!allFilledAndCorrect(idF, destinationF)) {
                UIUtils.alert("One or more of the fields contained invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            Flight flight = new Flight(Integer.parseInt(idF.getText()), destinationF.getText(), activeSF.isSelected());
            if (GeneralUtils.flightExists(flight)) {
                UIUtils.alert("The entered flight ID already exists!", Alert.AlertType.ERROR).show();
                return;
            }

            Airport.PRIMARY_MANAGER.getFlightsManager().addFlight(flight);
            flight.getUndoOperations().push(new Operation("Flight Created", " | Created a new flight with the id " + idF.getText() +
                    ", destination " + destinationF.getText() + ", active " + activeSF.isSelected()) {

                @Override
                public void undo() {
                    Airport.PRIMARY_MANAGER.getFlightsManager().removeFlight(flight);
                }

                @Override
                public void redo() {
                    Airport.PRIMARY_MANAGER.getFlightsManager().addFlight(flight);
                }
            });

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();

            idF.clear();
            destinationF.clear();
            activeSF.setSelected(false);
        });

        Button update = new Button("Update");
        update.setPrefHeight(20);
        update.setPrefWidth(120);

        update.setDisable(currentFlight == null);

        update.setOnAction(e -> {
            if (!allFilledAndCorrect(idF, destinationF)) {
                UIUtils.alert("One or more of the fields contained invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to update these values?", Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.CANCEL) return;

            if (currentFlight == null) return;
            int originalId = currentFlight.getId();
            String originalDestination = currentFlight.getDestination();
            boolean originalActiveStatus = currentFlight.isActiveStatus();

            int newId = Integer.parseInt(idF.getText());
            String newDestination = destinationF.getText();
            boolean newActiveStatus = activeSF.isSelected();

            if (GeneralUtils.getFlightById(newId) != null) {
                UIUtils.alert("Unable to create a flight with this ID because another flight has this ID.", Alert.AlertType.ERROR).show();
                return;
            }

            currentFlight.setId(newId);
            currentFlight.setDestination(newDestination);
            currentFlight.setActiveStatus(newActiveStatus);

            currentFlight.getUndoOperations().push(new Operation("Flight Details Update", " | " + originalId + " to " + newId +
                    " | " + originalDestination + " to " + newDestination + " | " + originalActiveStatus + " to " + newActiveStatus) {

                @Override
                public void undo() {
                    currentFlight.setId(originalId);
                    currentFlight.setDestination(originalDestination);
                    currentFlight.setActiveStatus(originalActiveStatus);
                }

                @Override
                public void redo() {
                    currentFlight.setId(newId);
                    currentFlight.setDestination(newDestination);
                    currentFlight.setActiveStatus(newActiveStatus);
                }
            });

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();

            idF.clear();
            destinationF.clear();
            activeSF.setSelected(false);
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new FlightsScreen(), 800, 500);
        });

        actions.getChildren().addAll(insert, update, back);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(gp, actions);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private boolean allFilledAndCorrect(TextField id, TextField destination) {
        if (id.getText().isEmpty() || destination.getText().isEmpty())
            return false;

        try {
            int idF = Integer.parseInt(id.getText());
            if (idF < 0) return false;
        } catch (NumberFormatException ex) { return false; }

        return true;
    }
}
