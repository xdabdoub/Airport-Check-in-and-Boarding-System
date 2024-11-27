package me.yhamarsheh.airport.storage;

import java.io.File;

public class FileSystem {

    private File flightsFile;
    private File passengersFile;

    public FileSystem() {

    }

    public void readFlightsFile() {
        
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
