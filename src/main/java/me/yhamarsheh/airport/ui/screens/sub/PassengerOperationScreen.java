package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.GeneralUtils;
import me.yhamarsheh.airport.utilities.UIUtils;

public class PassengerOperationScreen extends YazanScreen {

    public PassengerOperationScreen() {
        super("Al Aqsa International Airport (AQIA)", "Passenger Operation Screen", true);
    }

    @Override
    public Node setup() {
        VBox details = new VBox(20);

        VBox passId = new VBox(5);
        Label label = UIUtils.label("Passenger's ID", null, 0, null);
        TextField textField = new TextField();
        textField.setPromptText("(e.g) T12144XXX");

        passId.getChildren().addAll(label, textField);

        HBox actions = new HBox(10);
        Button checkInPassenger = new Button("Check In");
        checkInPassenger.setPrefWidth(200);
        checkInPassenger.setPrefHeight(40);
        checkInPassenger.setFont(new Font(18));

        checkInPassenger.setOnAction(e -> {
            if (!GeneralUtils.passengerExists(textField.getText(), 5)) {
                UIUtils.alert("The entered passenger's ID does not exist!", Alert.AlertType.ERROR).show();
                return;
            }

            UIUtils.alert("Successfully checked in the passenger!", Alert.AlertType.INFORMATION).show();
            textField.clear();
        });

        Button boardPassenger = new Button("Board Passenger");
        boardPassenger.setPrefWidth(200);
        boardPassenger.setPrefHeight(40);
        boardPassenger.setFont(new Font(18));

        boardPassenger.setOnAction(e -> {
            if (!GeneralUtils.passengerExists(textField.getText(), 5)) {
                UIUtils.alert("The entered passenger's ID does not exist!", Alert.AlertType.ERROR).show();
                return;
            }

            if (GeneralUtils.passengerExists(textField.getText(), 2)) {
                UIUtils.alert("This passenger has already boarded the aircraft!", Alert.AlertType.WARNING).show();
                return;
            }

            Object[] data = GeneralUtils.getPassengerAndFlightByPassengerId(textField.getText(), 5);
            Flight flight = (Flight) data[0];
            Passenger passenger = (Passenger) data[1];

            flight.boardPassenger(passenger);
            UIUtils.alert("Successfully boarded!", Alert.AlertType.INFORMATION).show();
            textField.clear();
        });

        Button cancel = new Button("Cancel Booking");
        cancel.setPrefWidth(200);
        cancel.setPrefHeight(40);
        cancel.setFont(new Font(18));

        cancel.setOnAction(e -> {
            if (GeneralUtils.passengerExists(textField.getText(), 2)) {
                UIUtils.alert("This passenger has already boarded the aircraft, thus, we are unable to cancel their booking at this time!", Alert.AlertType.WARNING).show();
                return;
            }

            if (!GeneralUtils.passengerExists(textField.getText(), 5)) {
                UIUtils.alert("The entered passenger's ID does not exist!", Alert.AlertType.ERROR).show();
                return;
            }

            Object[] data = GeneralUtils.getPassengerAndFlightByPassengerId(textField.getText(), 5);
            Flight flight = (Flight) data[0];
            Passenger passenger = (Passenger) data[1];

            flight.cancelPassenger(passenger);
            UIUtils.alert("Successfully cancelled this passenger's booking!", Alert.AlertType.INFORMATION).show();
            textField.clear();
        });

        Button back = new Button("Back");
        back.setPrefWidth(200);
        back.setPrefHeight(40);
        back.setFont(new Font(18));
        back.setOnAction(e -> {
            UIHandler.getInstance().open(new OperationScreen(), 800, 800);
        });

        actions.getChildren().addAll(checkInPassenger, boardPassenger, cancel);
        actions.setAlignment(Pos.CENTER);

        details.getChildren().addAll(passId, actions, back);
        details.setAlignment(Pos.CENTER);
        return details;
    }
}
