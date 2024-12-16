package me.yhamarsheh.airport.utilities;

import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Passenger;

public class FileUtils {

    public static Flight getFlightFromString(String s) {
        String[] data = s.split(",");
        if (data.length != 3) throw new IllegalArgumentException("Invalid flight string: " + s);

        int id;
        try {
            id = Integer.parseInt(data[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid flight string: " + s);
        }

        String destination = data[1];
        String activeStatusString = data[2];
        boolean activeStatus = activeStatusString.equalsIgnoreCase("Active");

        return new Flight(id, destination, activeStatus);
    }

    public static Passenger getPassengerFromString(String s) {
        String[] data = s.split(",");
        if (data.length != 4) throw new IllegalArgumentException("Invalid passenger string: " + s);

        int flightId;
        try {
            flightId = Integer.parseInt(data[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid passenger string: " + s);
        }

        String id = data[0];
        String name = data[1];
        String status = data[3];

        boolean isVip = status.equalsIgnoreCase("VIP");

        return new Passenger(id, name, flightId, isVip);
    }
}
