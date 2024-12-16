package me.yhamarsheh.airport.objects;

import java.time.LocalDate;
import java.util.Date;

public abstract class Operation {

    private final String type;
    private final String description;
    private final LocalDate localDate;

    public Operation(String type, String description) {
        this.description = description;
        this.localDate = LocalDate.now();
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getType() {
        return type;
    }

    public abstract void undo();
    public abstract void redo();

    @Override
    public String toString() {
        return localDate.toString() + " " + type + " " + description;
    }

}
