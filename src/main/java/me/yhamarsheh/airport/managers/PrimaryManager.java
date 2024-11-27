package me.yhamarsheh.airport.managers;

import me.yhamarsheh.airport.managers.sub.FlightsManager;
import me.yhamarsheh.airport.storage.FileSystem;

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
}
