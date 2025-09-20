package mst.controller;

import mst.model.Station;
import mst.model.StationMap;
import mst.model.Graph;
import mst.view.MapGraphics;

import java.io.IOException;
import java.util.Iterator;

/**
 * The Controller tells the Model and View what to do and handles
 * communication between them.
 */
public class Controller implements Runnable {
    private static final int PAUSE_BETWEEN_EDGE_HIGHLIGHTING_MS = 1000;

    /**
     * Constructs the controller.
     */
    public Controller() {
    }

    @Override
    public void run() {
        try {
            // Create and draw the graph.
            MapGraphics mapGraphics = MapGraphics.getInstance();
            StationMap map = new StationMap();
            Graph<Station> graph = map.getGraph();
            mapGraphics.drawGraph(graph);

            // Calculate and display the minimum spanning tree.
            Iterator<Graph.Edge<Station>> iterator = graph.getKruskalIterator();
            int numEdges = 0;
            int totalDistance = 0;
            while (iterator.hasNext()) {
                Graph.Edge<Station> edge = iterator.next();
                mapGraphics.highlightEdge(edge);
                numEdges++;
                totalDistance += edge.getWeight();
                Thread.sleep(PAUSE_BETWEEN_EDGE_HIGHLIGHTING_MS);
            }
            System.out.printf(
                    "The minimum spanning tree has %d edges and is %d miles long.%n",
                    numEdges, totalDistance);
        } catch (InterruptedException e) {
            // While Thread.sleep() theoretically could throw this exception,
            // it is impossible based on how this program is structured.
            throw new RuntimeException("An exception occurred that Ellen said was not possible.");
        } catch (IOException e) {
            System.err.println("There was problem reading the data: " + e.getMessage());
        }
    }
}
