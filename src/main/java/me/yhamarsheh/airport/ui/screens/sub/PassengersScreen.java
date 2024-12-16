package me.yhamarsheh.airport.ui.screens.sub;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.util.Optional;

public class PassengersScreen extends YazanScreen {

    private Flight currentFlight;
    public PassengersScreen(Flight currentFlight) {
        super("Al Aqsa International Airport (AQIA)", "Passengers Screen", false);

        this.currentFlight = currentFlight != null ? currentFlight : Airport.PRIMARY_MANAGER.getFlightsManager().getFirstFlight();
        setCenter(setup());
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);

        TableView<Passenger> tableView = new TableView<>();
        setupTableView(tableView);

        HBox searchBox = new HBox(10);
        TextField searchBar = new TextField();
        searchBar.setPromptText("\uD83D\uDD0D Enter search");
        searchBar.setPrefWidth(970);

        searchBar.setOnKeyTyped(e -> {
            if (searchBar.getText().isEmpty()) {
                initializePassengers(tableView);
                return;
            }

            search(tableView, searchBar.getText());
        });

        searchBox.getChildren().addAll(searchBar);
        searchBox.setAlignment(Pos.CENTER);

        VBox actions = new VBox(5);

        HBox editorButtons = new HBox(20);
        Button insert = new Button("Insert");
        insert.setPrefWidth(120);
        insert.setPrefHeight(20);

        insert.setOnAction(e -> {
            UIHandler.getInstance().open(new PassengerEditorScreen(null, currentFlight), 800, 500);
        });

        Button update = new Button("Update");
        update.setPrefWidth(120);
        update.setPrefHeight(20);

        update.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A flight must be selected to update!", Alert.AlertType.ERROR).show();
                return;
            }

            UIHandler.getInstance().open(new PassengerEditorScreen(tableView.getSelectionModel().getSelectedItem(), currentFlight), 800, 500);
        });

        Button delete = new Button("Delete");
        delete.setPrefWidth(120);
        delete.setPrefHeight(20);

        delete.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A passenger must be selected to delete!", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to PERMANENTLY delete this passenger?",
                    Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.CANCEL) return;

            Passenger passenger = tableView.getSelectionModel().getSelectedItem();

            currentFlight.removePassenger(passenger);
            tableView.getItems().remove(passenger);

            tableView.refresh();

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 500);
        });

        HBox otherButtons = new HBox(20);
        Button printAll = new Button("Print All Passengers");
        printAll.setPrefWidth(120);
        printAll.setPrefHeight(20);

        printAll.setOnAction(e -> {
            int i = 1;
            for (Passenger passenger : tableView.getItems()) {
                System.out.println(i++ + ". " + passenger.toString());
            }
        });

        Button printSelected = new Button("Print Selected Passenger");
        printSelected.setPrefWidth(120);
        printSelected.setPrefHeight(20);

        printSelected.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A passenger must be selected to print its data!", Alert.AlertType.ERROR).show();
                return;
            }

            System.out.println(tableView.getSelectionModel().getSelectedItem().toString());
        });

        otherButtons.getChildren().addAll(printAll, printSelected);
        otherButtons.setAlignment(Pos.CENTER);

        HBox navigation = new HBox(20);
        Button navigateNext = new Button("Next Flight");
        navigateNext.setPrefWidth(120);
        navigateNext.setPrefHeight(20);

        Label cFlight = UIUtils.label("Flight " + currentFlight.getId(), null, 0, null);

        Button navigatePrevious = new Button("Previous Flight");
        navigatePrevious.setPrefWidth(120);
        navigatePrevious.setPrefHeight(20);

        navigateNext.setOnAction(e -> {
            Flight flight = Airport.PRIMARY_MANAGER.getFlightsManager().getFlights().next(currentFlight);
            if (flight == null) return;

            currentFlight = flight;
            cFlight.setText("Flight " + currentFlight.getId());
            initializePassengers(tableView);
        });

        navigatePrevious.setOnAction(e -> {
            Flight flight = Airport.PRIMARY_MANAGER.getFlightsManager().getFlights().previous(currentFlight);
            if (flight == null) return;

            currentFlight = flight;
            cFlight.setText("Flight " + currentFlight.getId());
            initializePassengers(tableView);
        });

        navigation.getChildren().addAll(navigateNext, cFlight, navigatePrevious);
        navigation.setAlignment(Pos.CENTER);

        editorButtons.getChildren().addAll(insert, update, delete, back);
        editorButtons.setAlignment(Pos.CENTER);

        actions.getChildren().addAll(editorButtons);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(searchBox, tableView, actions, otherButtons, navigation);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private void setupTableView(TableView<Passenger> tableView) {
        TableColumn<Passenger, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));

        TableColumn<Passenger, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Passenger, Integer> flightIdCol = new TableColumn<>("Flight Id");
        flightIdCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getFlightId()).asObject());

        TableColumn<Passenger, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isVipMember() ? "VIP" : "Regular"));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(flightIdCol);
        tableView.getColumns().add(statusCol);

        initializePassengers(tableView);
    }

    private void initializePassengers(TableView<Passenger> tableView) {
        tableView.getItems().clear();

        for (Passenger passenger : currentFlight.getVipQueue()) {
            tableView.getItems().add(passenger);
        }

        for (Passenger passenger : currentFlight.getBoardedPassengers()) {
            tableView.getItems().add(passenger);
        }

        for (Passenger passenger : currentFlight.getRegularQueue()) {
            tableView.getItems().add(passenger);
        }
    }

    private void search(TableView<Passenger> tableView, String input) {
        ObservableList<Passenger> temp = FXCollections.observableArrayList(tableView.getItems());
        tableView.getItems().clear();

        for (Passenger passenger : temp) {
            if (String.valueOf(passenger.getId()).startsWith(input)) {
                tableView.getItems().add(passenger);
            }
        }

        tableView.refresh();
    }
}
