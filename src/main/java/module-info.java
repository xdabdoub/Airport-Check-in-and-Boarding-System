module me.yhamarsheh.airport {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.yhamarsheh.airport to javafx.fxml;
    exports me.yhamarsheh.airport.ui;
    exports me.yhamarsheh.airport.utilities;
    exports me.yhamarsheh.airport.managers;
    exports me.yhamarsheh.airport.managers.sub;
    exports me.yhamarsheh.airport.structure;
    exports me.yhamarsheh.airport.storage;
    exports me.yhamarsheh.airport.objects;
    exports me.yhamarsheh.airport.ui.screens;
    exports me.yhamarsheh.airport.ui.screens.sub;
    exports me.yhamarsheh.airport.structure.nodes;
}