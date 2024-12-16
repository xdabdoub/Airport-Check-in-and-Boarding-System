package me.yhamarsheh.airport.managers;

import me.yhamarsheh.airport.managers.sub.FlightsManager;
import me.yhamarsheh.airport.objects.Flight;
import me.yhamarsheh.airport.objects.Operation;
import me.yhamarsheh.airport.storage.FileSystem;
import me.yhamarsheh.airport.structure.Stack;

public class PrimaryManager {

    private final FlightsManager flightsManager;
    private final FileSystem fileSystem;
    public PrimaryManager() {
        this.flightsManager = new FlightsManager();
        this.fileSystem = new FileSystem();
    }

    public FlightsManager getFlightsManager() {
        return flightsManager;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public String getLogFileContents() {
        Stack<Operation> temp = new Stack<>(500);
        StringBuilder sb = new StringBuilder();

        for (Flight flight : getFlightsManager().getFlights()) {
            while (!flight.getUndoOperations().isEmpty()) {
                Operation op = flight.getUndoOperations().pop();
                sb.append(op.toString()).append("\n");
                temp.push(op);
            }

            while (!temp.isEmpty()) {
                Operation op = temp.pop();
                flight.getUndoOperations().push(op);
            }
        }

        return sb.toString();
    }
}
