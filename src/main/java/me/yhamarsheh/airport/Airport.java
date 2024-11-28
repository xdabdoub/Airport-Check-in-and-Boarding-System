package me.yhamarsheh.airport;

import me.yhamarsheh.airport.managers.PrimaryManager;
import me.yhamarsheh.airport.ui.UIHandler;

public class Airport {

    public static final PrimaryManager PRIMARY_MANAGER = new PrimaryManager();
    private static UIHandler uiHandler;

    public static void main(String[] args) {
        uiHandler = new UIHandler();

        UIHandler.launchApp(args);
    }
}
