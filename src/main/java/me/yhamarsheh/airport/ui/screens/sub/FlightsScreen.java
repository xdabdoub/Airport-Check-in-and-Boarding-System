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
import me.yhamarsheh.airport.ui.UIHandler;
import me.yhamarsheh.airport.ui.screens.YazanScreen;
import me.yhamarsheh.airport.utilities.UIUtils;

import java.util.Optional;

public class FlightsScreen extends YazanScreen {

    private boolean searchByDestination;
    private String searchHow = "All Flights";

    public FlightsScreen() {
        super("مــطــار اﻟــقــدس اﻟــدولــي", "Flights Screen", true);
    }

    @Override
    public Node setup() {
        VBox vBox = new VBox(20);

        TableView<Flight> tableView = new TableView<>();
        setupTableView(tableView);

        HBox searchBox = new HBox(10);
        TextField searchBar = new TextField();
        searchBar.setPromptText("\uD83D\uDD0D Enter search");
        searchBar.setPrefWidth(970);

        searchBar.setOnKeyPressed(e -> {
            if (searchBar.getText().isEmpty()) {
                initializeFlights(tableView, searchHow);
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
            UIHandler.getInstance().open(new FlightEditorScreen(null), 800, 500);
        });

        Button update = new Button("Update");
        update.setPrefWidth(120);
        update.setPrefHeight(20);

        update.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A flight must be selected to update!", Alert.AlertType.ERROR).show();
                return;
            }

            UIHandler.getInstance().open(new FlightEditorScreen(tableView.getSelectionModel().getSelectedItem()), 800, 500);
        });

        Button delete = new Button("Delete");
        delete.setPrefWidth(120);
        delete.setPrefHeight(20);

        delete.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A flight must be selected to delete!", Alert.AlertType.ERROR).show();
                return;
            }

            Optional<ButtonType> confirmation = UIUtils.alert("Are you sure you'd like to PERMANENTLY delete this Flight?",
                    Alert.AlertType.CONFIRMATION).showAndWait();

            if (confirmation.isEmpty()) return;
            if (confirmation.get() == ButtonType.CANCEL) return;

            Flight flight = tableView.getSelectionModel().getSelectedItem();

            Airport.PRIMARY_MANAGER.getFlightsManager().removeFlight(flight);
            tableView.getItems().remove(flight);

            tableView.refresh();

            UIUtils.alert("Success!", Alert.AlertType.INFORMATION).show();
        });

        Button viewPassengers = new Button("View Passengers");
        viewPassengers.setPrefWidth(120);
        viewPassengers.setPrefHeight(20);

        viewPassengers.setOnAction(e -> {
            UIHandler.getInstance().open(new PassengersScreen(tableView.getSelectionModel().getSelectedItem()), 800, 500);
        });

        Button back = new Button("Back");
        back.setPrefHeight(20);
        back.setPrefWidth(120);

        back.setOnAction(e -> {
            UIHandler.getInstance().open(new HomeScreen(), 800, 500);
        });

        HBox otherButtons = new HBox(20);
        Button printAll = new Button("Print All Flights");
        printAll.setPrefWidth(120);
        printAll.setPrefHeight(20);

        printAll.setOnAction(e -> {
            int i = 1;
            for (Flight flight : tableView.getItems()) {
                System.out.println(i++ + ". " + flight.toString());
            }
        });

        Button printSelected = new Button("Print Selected Flight");
        printSelected.setPrefWidth(120);
        printSelected.setPrefHeight(20);

        printSelected.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                UIUtils.alert("A flight must be selected to print its data!", Alert.AlertType.ERROR).show();
                return;
            }

            System.out.println(tableView.getSelectionModel().getSelectedItem().toString());
        });

        HBox filter = new HBox(20);
        Label filterLabel = UIUtils.label("Filter:", null, 0, null);
        ComboBox<String> filterCB = new ComboBox<>();
        filterCB.setValue("All Flights");
        filterCB.getItems().addAll("All Flights", "Active Flights", "Inactive Flights");

        filterCB.setOnAction(e -> {
            String how = filterCB.getValue();
            searchHow = how;

            initializeFlights(tableView, how);
        });

        HBox searchBy = new HBox(20);
        Label searchByLabel = UIUtils.label("Search By:", null, 0, null);
        RadioButton id = new RadioButton("ID");
        RadioButton destination = new RadioButton("Destination");
        ToggleGroup toggleGroup = new ToggleGroup();
        id.setToggleGroup(toggleGroup);
        destination.setToggleGroup(toggleGroup);

        id.setOnAction(e -> {
            searchByDestination = false;
        });

        destination.setOnAction(e -> {
            searchByDestination = true;
        });

        searchBy.getChildren().addAll(searchByLabel, id, destination);
        searchBar.setAlignment(Pos.CENTER);

        filter.getChildren().addAll(filterLabel, filterCB);
        filter.setAlignment(Pos.CENTER);

        editorButtons.getChildren().addAll(insert, update, delete, viewPassengers, back);
        editorButtons.setAlignment(Pos.CENTER);

        actions.getChildren().addAll(editorButtons);
        actions.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(searchBox, tableView, actions);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private void setupTableView(TableView<Flight> tableView) {
        TableColumn<Flight, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Flight, String> destinationCol = new TableColumn<>("Destination");
        destinationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestination()));

        TableColumn<Flight, String> activeSCol = new TableColumn<>("Active Status");
        activeSCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isActiveStatus() ? "Active" : "Inactive"));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(destinationCol);
        tableView.getColumns().add(activeSCol);

        initializeFlights(tableView, "All Flights");
    }

    private void initializeFlights(TableView<Flight> tableView, String how) {
        for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            if (how.equalsIgnoreCase("All Flights"))
                tableView.getItems().add(flight);
            else if (how.equalsIgnoreCase("Active Flights")) {
                if (flight.isActiveStatus()) tableView.getItems().add(flight);
            } else if (how.equalsIgnoreCase("Inactive Flights"))
                if (!flight.isActiveStatus()) tableView.getItems().add(flight);
        }
    }

    private void search(TableView<Flight> tableView, String input) {

        ObservableList<Flight> temp = FXCollections.observableArrayList(tableView.getItems());
        tableView.getItems().clear();

        for (Flight flight : temp) {
            if (String.valueOf((searchByDestination ? flight.getDestination() : flight.getId())).startsWith(input)) {
                tableView.getItems().add(flight);
            }
        }

        tableView.refresh();
    }
}
