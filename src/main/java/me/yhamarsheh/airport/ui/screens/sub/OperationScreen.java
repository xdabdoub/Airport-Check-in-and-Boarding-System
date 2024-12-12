package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;

public class OperationScreen extends YazanScreen {

    public OperationScreen() {
        super("Al Aqsa Internation Airport (AQIA)", "Operation Screen", true);
    }

    @Override
    public Node setup() {
        VBox details = new VBox(20);

        HBox actions = new HBox(10);
        Button passengersOperation = new Button("Passenger Operations");
        passengersOperation.setPrefWidth(200);
        passengersOperation.setPrefHeight(40);
        passengersOperation.setFont(new Font(18));

        passengersOperation.setOnAction(e -> {
            UIHandler.getInstance().open(new PassengerOperationScreen(), 800, 800);
        });

        Button flightOperation = new Button("Flight Operations");
        flightOperation.setPrefWidth(200);
        flightOperation.setPrefHeight(40);
        flightOperation.setFont(new Font(18));

        flightOperation.setOnAction(e -> {
            UIHandler.getInstance().open(new FlightOperationScreen(), 800, 800);
        });

        actions.getChildren().addAll(passengersOperation, flightOperation);
        actions.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setPrefWidth(200);
        back.setPrefHeight(40);
        back.setFont(new Font(18));

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 800);
        });

        details.getChildren().addAll(actions, back);
        details.setAlignment(Pos.CENTER);
        return details;
    }
}
