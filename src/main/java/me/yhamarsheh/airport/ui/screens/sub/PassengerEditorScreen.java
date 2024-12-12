package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Operation;
import me.yhamarsheh.airport.objects.Passenger;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.GeneralUtils;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.util.Optional;

public class PassengerEditorScreen extends YazanScreen {

    private final Passenger passenger;
    private final Flight flight;

    public PassengerEditorScreen(Passenger passenger, Flight flight) {
        super("Al Aqsa Internation Airport (AQIA)", "Passenger Editor", false);
        this.passenger = passenger;
        this.flight = flight;
        setCenter(setup());
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);
        GridPane gp = new GridPane();

        Label idL = UIUtils.label("ID", FontWeight.BOLD, 12, null);
        TextField idF = new TextField(passenger == null ? "" : passenger.getId());
        idF.setPrefHeight(20);
        idF.setPrefWidth(120);

        idF.setDisable(passenger != null);

        Label nameL = UIUtils.label("Name", FontWeight.BOLD, 12, null);
        TextField nameF = new TextField(passenger == null ? "" : passenger.getName());
        nameF.setPrefHeight(20);
        nameF.setPrefWidth(120);

        Label flightId = UIUtils.label("Flight ID", FontWeight.BOLD, 12, null);
        TextField flightIdF = new TextField(passenger == null ? flight.getId() + "" : passenger.getFlightId() + "");
        flightIdF.setPrefHeight(20);
        flightIdF.setPrefWidth(120);

        flightIdF.setDisable(true);

        Label vipStatus = UIUtils.label("VIP Status", FontWeight.BOLD, 12, null);
        ComboBox<String> vipStatusF = new ComboBox<>();
        vipStatusF.setPrefHeight(20);
        vipStatusF.setPrefWidth(120);

        vipStatusF.getItems().addAll("VIP", "Regular");
        vipStatusF.getSelectionModel().select("Regular");

        gp.setHgap(20);
        gp.setVgap(20);

        gp.setAlignment(Pos.CENTER);

        gp.add(idL, 0, 0);
        gp.add(idF, 0, 1);

        gp.add(nameL, 1, 0);
        gp.add(nameF, 1, 1);

        gp.add(flightId, 0, 2);
        gp.add(flightIdF, 0, 3);

        gp.add(vipStatus, 1, 2);
        gp.add(vipStatusF, 1, 3);

        HBox actions = new HBox(10);

        Button insert = new Button("Insert");
        insert.setPrefHeight(20);
        insert.setPrefWidth(120);

        insert.setDisable(passenger != null);

        insert.setOnAction(e -> {
            if (!allFilledAndCorrect(idF, nameF)) {
                UIUtils.alert("One or more of the fields contain invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            if (GeneralUtils.passengerExists(flight, idF.getText())) {
                UIUtils.alert("The entered passenger ID already exists!", Alert.AlertType.ERROR).show();
                return;
            }

            Passenger newPassenger = new Passenger(idF.getText(), nameF.getText(), flight.getId(), vipStatusF.getSelectionModel().getSelectedItem().equals("VIP"));

            idF.clear();
            nameF.clear();
            vipStatusF.getSelectionModel().select("Regular");

            flight.addPassenger(newPassenger);

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();
        });

        Button update = new Button("Update");
        update.setPrefHeight(20);
        update.setPrefWidth(120);

        update.setDisable(passenger == null);

        update.setOnAction(e -> {
            if (!allFilledAndCorrect(idF, nameF)) {
                UIUtils.alert("One or more of the fields contain invalid values", Alert.AlertType.ERROR).show();
                return;
            }

            if (passenger == null) return;
            if (flight == null) return;

            if (GeneralUtils.passengerExists(flight, idF.getText())) {
                UIUtils.alert("The entered passenger ID already exists!", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to update these values?", Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.NO) return;

            String originalId = passenger.getId();
            String originalName = passenger.getName();
            boolean originalVIPStatus = passenger.isVipMember();

            String newId = idF.getText();
            String newName = nameF.getText();
            boolean newVIPStatus = vipStatusF.getSelectionModel().getSelectedItem().equals("VIP");

            passenger.setId(newId);
            passenger.setName(newName);
            passenger.setVipStatus(newVIPStatus);

            flight.getUndoOperations().push(new Operation("Update Passenger", " | " + originalId + " to " + newId +
                    " | " + originalName + " to " + newName + " | " + (originalVIPStatus ? "VIP" : "Regular") + " to " + (newVIPStatus ? "VIP" : "Regular")) {
                @Override
                public void undo() {
                    passenger.setId(originalId);
                    passenger.setName(originalName);
                    passenger.setVipStatus(originalVIPStatus);
                }

                @Override
                public void redo() {
                    passenger.setId(newId);
                    passenger.setName(newName);
                    passenger.setVipStatus(originalVIPStatus);
                }
            });

            idF.clear();
            nameF.clear();
            vipStatusF.getSelectionModel().select("Regular");

            UIHandler.getInstance().open(new PassengersScreen(flight), 800, 500);
            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new PassengersScreen(flight), 800, 500);
        });

        actions.getChildren().addAll(insert, update, back);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(gp, actions);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private boolean allFilledAndCorrect(TextField id, TextField name) {
        if (id.getText().isEmpty() || name.getText().isEmpty())
            return false;

        return true;
    }
}
