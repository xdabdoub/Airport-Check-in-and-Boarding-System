package me.yhamarsheh.airport.managers.sub;

import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;
import me.yhamarsheh.airport.structure.LinkedList;

public class FlightsManager {

    private LinkedList<Flight> flights;
    public FlightsManager() {
        this.flights = new LinkedList<>();
    }

    public void addFlight(Flight flight) {
        flights.insert(flight);
    }

    public void removeFlight(Flight flight) {
        flights.delete(flight);
    }

    public boolean doesFlightExist(Flight flight) {
        return flights.find(flight) != null;
    }

    public boolean doesFlightExistById(int id) {
        for (Flight flight : flights) {
            if (flight.getId() > id) return false;
            if (flight.getId() == id) return true;
        }

        return false;
    }

    public Flight getFlightById(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) return flight;
        }

        return null;
    }

    public int getVIPCancellation() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getCancelledPassengers()) {
                if (passenger.isVipMember()) count++;
            }
        }

        return count;
    }

    public int getRegularCancellation() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getCancelledPassengers()) {
                if (!passenger.isVipMember()) count++;
            }
        }

        return count;
    }

    public int getVIPInQueue() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getVipQueue()) {
                count++;
            }
        }

        return count;
    }

    public int getRegularInQueue() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getRegularQueue()) {
                count++;
            }
        }

        return count;
    }

    public int getVIPBoarded() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getBoardedPassengers()) {
                if (passenger.isVipMember()) count++;
            }
        }

        return count;
    }

    public int getRegularBoarded() {
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getBoardedPassengers()) {
                if (!passenger.isVipMember()) count++;
            }
        }

        return count;
    }

    public Flight getFirstFlight() {
        return flights.getFirst().getData();
    }

    public LinkedList<Flight> getFlights() {
        return flights;
    }

}
