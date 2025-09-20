package mst.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Graph representation of a set of stations, which is built from data in
 * a provided CSV file.
 */
public class StationMap {
    /**
     * The path to the file containing station data.
     */
    public static final String STATION_DATA_FILE = "BART.csv";

    private static final int NUM_DATA_FIELDS = 4;
    private static final int STATION_NAME_FIELD = 0;
    private static final int STATION_ID = 1;

    private static final int LONGITUDE_FIELD = 2;
    private static final int LATITUDE_FIELD = 3;

    // When set to -1, all data is used
    private static final int NUM_STATIONS = 5; // file has 52

    private static final int EARTH_RADIUS_IN_MILES = 3963;

    private final Graph<Station> graph = new Graph<>();

    /**
     * Constructs a graph representation of a set of stations.
     *
     * @throws IOException if the data cannot be read
     */
    public StationMap() throws IOException {
        makeGraph();
    }

    /**
     * Gets the graph representation of the station, where each station is a
     * node, and there are edges between each pair of stations weighted by the
     * distance between them.
     *
     * @return a graph representation of stations and the distances between them
     */
    public final Graph<Station> getGraph() {
        return graph;
    }

    private static double distanceInMiles(Station station1, Station station2) {
        // Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
        return EARTH_RADIUS_IN_MILES
                * Math.acos(
                (Math.sin(Math.toRadians(station1.getLatitude())) * Math.sin(Math.toRadians(station2.getLatitude())))
                        + Math.cos(Math.toRadians(station1.getLatitude()))
                        * Math.cos(Math.toRadians(station2.getLatitude()))
                        * Math.cos(Math.toRadians(station2.getLongitude() - station1.getLongitude())));
    }

    private List<String> readDataFromResource() throws IOException {
        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream(STATION_DATA_FILE)) {
            if (resource == null) {
                throw new IOException("Unable to read resource file " + STATION_DATA_FILE);
            }
            return new BufferedReader(new InputStreamReader(resource,
                    StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        }
    }

    private void makeGraph() throws IOException {
        List<String> lines = readDataFromResource();
        int numStations = 0;
        List<Station> stations = new ArrayList<>();
        for (String line : lines) {
            if (NUM_STATIONS > 0 && numStations++ >= NUM_STATIONS) {
                break;
            }
            String[] fields = line.split(",");
            if (fields.length == NUM_DATA_FIELDS) {
                Station station = new Station(fields[STATION_NAME_FIELD],
                        Double.parseDouble(fields[LATITUDE_FIELD]),
                        Double.parseDouble(fields[LONGITUDE_FIELD]));
                graph.addNode(station);
                stations.add(station);
            } else {
                throw new IOException("Unable to parse line: " + line);
            }
        }

        // Complete this method by adding edges connecting each pair of nodes.
        // The weight of the edge should be the distance in miles between their
        // stations. Use the provided helper method. Because the edges are
        // undirected, you should add only one edge between each pair of nodes.
        // Do not make an edge between a node and itself.
        Map<Station, Graph.Node<Station>> stationNodes = new HashMap<>();
        for (Graph.Node<Station> node : graph.getNodes()) {
            stationNodes.put(node.getData(), node);
        }
        for (int i = 0; i < stations.size(); i++) {
            for (int j = i + 1; j < stations.size(); j++) {
                Station station1 = stations.get(i);
                Station station2 = stations.get(j);
                Graph.Node<Station> node1 = stationNodes.get(station1);
                Graph.Node<Station> node2 = stationNodes.get(station2);
                if (node1 != null && node2 != null) {
                    int distance = (int) Math.round(distanceInMiles(station1, station2));
                    graph.addEdge(node1, node2, distance);
                }
            }
        }
    }
}
