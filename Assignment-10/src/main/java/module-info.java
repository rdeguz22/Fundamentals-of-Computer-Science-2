/**
 * This module provides support for a JavaFX application to display a map of
 * mst and to gradually add edges representing distance to produce a
 * minimum spanning tree.
 */
module mst {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens mst.model to javafx.fxml;
    exports mst.model;
    exports mst.view;
    opens mst.view to javafx.fxml;
    exports mst.controller;
    opens mst.controller to javafx.fxml;
}
