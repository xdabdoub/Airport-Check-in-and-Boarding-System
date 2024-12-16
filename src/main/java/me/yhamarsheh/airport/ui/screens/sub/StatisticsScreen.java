package me.yhamarsheh.airport.ui.screens.sub;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

public class StatisticsScreen extends YazanScreen {

    public StatisticsScreen() {
        super("Al Aqsa International Airport (AQIA)", "System Statistics Screen", true);
    }

    @Override
    public Node setup() {
        VBox details = new VBox(10);
        Label cancelledVIP = UIUtils.label("Total Number of Cancelled VIP Passengers: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getVIPCancellation(), null, 0, null);
        Label cancelledRegular = UIUtils.label("Total Number of Cancelled Regular Passengers: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getRegularCancellation(), null, 0, null);
        Label vipsInQueue = UIUtils.label("Total Number of VIP Passengers in the Queue: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getVIPInQueue(), null, 0, null);
        Label regularsInQueue = UIUtils.label("Total Number of Regular Passengers in the Queue: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getRegularInQueue(), null, 0, null);
        Label vipBoarded = UIUtils.label("Total Number of VIP Passengers Boarded: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getVIPBoarded(), null, 0, null);
        Label regularBoarded = UIUtils.label("Total Number of Regular Passengers Boarded: " +
                Airport.PRIMARY_MANAGER.getFlightsManager().getRegularBoarded(), null, 0, null);

        Button back = new Button("Back");
        back.setPrefWidth(220);
        back.setPrefHeight(30);
        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 800);
        });

        details.getChildren().addAll(cancelledVIP, cancelledRegular, vipsInQueue,
                regularsInQueue, vipBoarded, regularBoarded, back);
        details.setAlignment(Pos.CENTER);
        return details;
    }
}
