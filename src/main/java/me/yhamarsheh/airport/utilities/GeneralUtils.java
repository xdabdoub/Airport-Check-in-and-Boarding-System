package me.yhamarsheh.airport.utilities;

import me.yhamarsheh.airport.Airport;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;

public class GeneralUtils {

    public static boolean flightExists(Flight flight) {
        for (Flight f : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            if (f.getId() < flight.getId()) return false; // No need to go over all the flights because they're sorted DESC. based on their ID, so when we reach a flight's Id that is less than the flight we're looking for, that means it does not exist.
            if (f.getId() == flight.getId()) return true;
        }

        return false;
    }

    public static boolean passengerExists(Flight flight, String id) {
        for (Passenger vip : flight.getVipQueue()) {
            if (vip.getId().equalsIgnoreCase(id)) return true;
        }

        for (Passenger regular : flight.getRegularQueue()) {
            if (regular.getId().equalsIgnoreCase(id)) return true;
        }

        return false;
    }

    public static boolean passengerExists(String id) {
        for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            for (Passenger vip : flight.getVipQueue()) {
                if (vip.getId().equalsIgnoreCase(id)) return true;
            }

            for (Passenger regular : flight.getRegularQueue()) {
                if (regular.getId().equalsIgnoreCase(id)) return true;
            }

            for (Passenger passenger : flight.getBoardedPassengers()) {
                if (passenger.getId().equalsIgnoreCase(id)) return true;
            }
        }

        return false;
    }

    public static boolean passengerExists(String id, int i) {
        for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            if (i == 0 || i == 5) {
                for (Passenger vip : flight.getVipQueue()) {
                    if (vip.getId().equalsIgnoreCase(id)) return true;
                }
            }

            if (i == 1 || i == 5) {
                for (Passenger regular : flight.getRegularQueue()) {
                    if (regular.getId().equalsIgnoreCase(id)) return true;
                }
            }

            if (i == 2) {
                for (Passenger passenger : flight.getBoardedPassengers()) {
                    if (passenger.getId().equalsIgnoreCase(id)) return true;
                }
            }
        }
        return false;
    }

    public static Object[] getPassengerAndFlightByPassengerId(String id, int i) {
        for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            if (i == 0 || i == 5) {
                for (Passenger vip : flight.getVipQueue()) {
                    if (vip.getId().equalsIgnoreCase(id)) return new Object[] {flight, vip};
                }
            }

            if (i == 1 || i == 5) {
                for (Passenger regular : flight.getRegularQueue()) {
                    if (regular.getId().equalsIgnoreCase(id)) return new Object[] {flight, regular};
                }
            }

            if (i == 2) {
                for (Passenger passenger : flight.getBoardedPassengers()) {
                    if (passenger.getId().equalsIgnoreCase(id)) return new Object[] {flight, passenger};
                }
            }
        }

        return null;
    }

    public static Flight getFlightById(int id) {
        for (Flight flight : Airport.PRIMARY_MANAGER.getFlightsManager().getFlights()) {
            if (flight.getId() == id) return flight;
        }

        return null;
    }
}
