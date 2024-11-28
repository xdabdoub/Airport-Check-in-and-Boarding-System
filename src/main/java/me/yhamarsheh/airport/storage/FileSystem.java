package me.yhamarsheh.airport.storage;

import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;
import me.yhamarsheh.airport.utilities.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileSystem {

    private File flightsFile;
    private File passengersFile;

    public FileSystem() {

    }

    public void readFlightsFile() {
        if (flightsFile == null) return;
        try (Scanner scanner = new Scanner(flightsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                if (line.isEmpty()) continue;

                Flight flight;
                try {
                    flight = FileUtils.getFlightFromString(line);
                    System.out.println(flight.getId());
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }

                System.out.println(flight.getId());
                Airport.PRIMARY_MANAGER.getFlightsManager().getFlights().insert(flight); // Manual Insertion to avoid having undo/redo operations on data loading
                if (flight.getId() == 1051) System.out.println("added flight 1051");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find flights file.");
        }
    }

    public void readPassengersFile() {
        if (passengersFile == null) return;
        try (Scanner scanner = new Scanner(passengersFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;

                Passenger passenger;
                try {
                    passenger = FileUtils.getPassengerFromString(line);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }

                Flight flight = Airport.PRIMARY_MANAGER.getFlightsManager().getFlightById(passenger.getFlightId());
                if (flight == null) {
                    System.out.println("Passenger " + passenger.getName() + " flight " + passenger.getFlightId() + " could not be found.");
                    continue;
                }

                if (passenger.isVipMember())
                    flight.getVipQueue().enqueue(passenger);
                else flight.getRegularQueue().enqueue(passenger);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find passengers file.");
        }
    }

    public void update() throws FileNotFoundException {
        saveFlights();
        savePassengers();
    }

    public void saveFlights() throws FileNotFoundException {
        if (flightsFile == null) return;

        try (PrintWriter writer = new PrintWriter(flightsFile)) {
            for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
                writer.println(flight.getId() + "," + flight.getDestination() + "," + (flight.isActiveStatus() ? "Active" : "Inactive"));
            }
        }
    }

    public void savePassengers() throws FileNotFoundException {
        if (passengersFile == null) return;

        try (PrintWriter writer = new PrintWriter(passengersFile)) {
            for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
                for (Passenger passenger : flight.getVipQueue()) {
                    writer.println(passenger.getId() + "," + passenger.getName() + ",VIP");
                }

                for (Passenger passenger : flight.getRegularQueue()) {
                    writer.println(passenger.getId() + "," + passenger.getName() + ",Regular");
                }
            }
        }
    }

    public File getPassengersFile() {
        return passengersFile;
    }

    public void setPassengersFile(File passengersFile) {
        this.passengersFile = passengersFile;
    }

    public File getFlightsFile() {
        return flightsFile;
    }

    public void setFlightsFile(File flightsFile) {
        this.flightsFile = flightsFile;
    }
}
