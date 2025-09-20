package mst.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import mst.controller.Controller;
import mst.model.Station;
import mst.model.Graph;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The View portion of the application.
 */
public class MapGraphics extends Application {
    private static final double MAX_LATITUDE = 38.1;
    private static final double MIN_LATITUDE = 37.3;
    private static final double MAX_LONGITUDE = -121.7;
    private static final double MIN_LONGITUDE = -122.5;
    private static final int PIXELS_PER_DEGREE = 1000;
    private static final int CANVAS_HEIGHT = (int) Math.round((MAX_LATITUDE - MIN_LATITUDE) * PIXELS_PER_DEGREE);
    private static final int CANVAS_WIDTH = (int) Math.round((MAX_LONGITUDE - MIN_LONGITUDE) * PIXELS_PER_DEGREE);
    private static final int SCENE_WIDTH = CANVAS_WIDTH;
    private static final int SCENE_HEIGHT = CANVAS_HEIGHT;
    private static final Color NODE_COLOR = Color.BLUE;
    private static final Color EDGE_COLOR = Color.LIGHTGREEN;
    private static final Color NODE_HIGHLIGHT_COLOR = Color.PURPLE;
    private static final Color EDGE_HIGHLIGHT_COLOR = Color.PURPLE;
    private static final Color HOVER_BACKGROUND_COLOR = Color.YELLOW;
    private static final int NODE_RADIUS = 2;
    private static final double MIN_DISTANCE_FOR_LINE = 0.1;

    private static boolean isInitialized = false;
    private static MapGraphics mapGraphics;
    private final Group group = new Group();

    /**
     * Creates an instance of this class.
     */
    public MapGraphics() {
    }

    @Override
    public void init() {
        mapGraphics = this;
    }

    /**
     * Gets the singleton instance of this class.
     *
     * @return the singleton instance of this class
     */
    public static MapGraphics getInstance() {
        // Don't return this until initialization is complete.
        while (!isInitialized) {
            Thread.yield();
        }
        return mapGraphics;
    }

    @Override
    public void start(Stage primaryStage) {
        final Pane pane = new Pane(group);
        pane.setPrefWidth(CANVAS_WIDTH);
        pane.setPrefHeight(CANVAS_HEIGHT);

        Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("Stations");
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    Platform.exit();
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        isInitialized = true;
    }

    private void drawNode(Graph.Node<Station> node, int radius, Color color) {
        final int x = getX(node.getData());
        if (x < 0) {
            System.err.println("Could not display " + node.getData());
            return;
        }
        final int y = getY(node.getData());
        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        addHover(circle, x, y, node.getData().getName());
        Platform.runLater(() -> {
            group.getChildren().add(circle);
        });
    }

    private void drawNode(Graph.Node<Station> node) {
        drawNode(node, NODE_RADIUS, NODE_COLOR);
    }

    // https://stackoverflow.com/a/40446828/631051
    private static final class TextWithBackgroundColor extends StackPane {

        public static final int TEXT_PADDING = 5;
        public static final int RECTANGLE_HEIGHT = 15;

        private TextWithBackgroundColor(double x, double y, String s) {
            setLayoutX(x);
            setLayoutY(y);
            Text text = new Text(x, y, s);
            Rectangle background = new Rectangle(x, y,
                    text.getBoundsInLocal().getWidth() + TEXT_PADDING, RECTANGLE_HEIGHT);
            background.setFill(HOVER_BACKGROUND_COLOR);
            getChildren().addAll(background, text);
        }
    }

    private void addHover(Node node, int x, int y, String s) {
        final TextWithBackgroundColor text = new TextWithBackgroundColor(x, y, s);
        node.hoverProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                    if (show) {
                        group.getChildren().add(text);
                    } else {
                        group.getChildren().remove(text);
                    }
                });
    }

    private void drawEdge(Graph.Edge<Station> edge, Color color, boolean enableHover) {
        Station station1 = edge.getNode1().getData();
        Station station2 = edge.getNode2().getData();
        final Line line = new Line(getX(station1), getY(station1), getX(station2), getY(station2));
        if (enableHover) {
            addHover(
                    line,
                    (getX(station1) + getX(station2)) / 2,
                    (getY(station1) + getY(station2)) / 2,
                    String.format("%s - %s (%d)", station1.getName(), station2.getName(), edge.getWeight()));
        }
        line.setStroke(color);
        Platform.runLater(() -> {
            group.getChildren().add(line);
        });
    }

    private void drawEdge(Graph.Edge<Station> edge) {
        drawEdge(edge, EDGE_COLOR, false);
    }

    /**
     * Displays all nodes in the graph and all edges with a weight of at
     * least {@link #MIN_DISTANCE_FOR_LINE}.
     *
     * @param graph the graph to display
     */
    public void drawGraph(Graph<Station> graph) {
        for (Graph.Edge<Station> edge : graph.getEdges()) {
            if (edge.getWeight() >= MIN_DISTANCE_FOR_LINE) {
                drawEdge(edge);
            }
        }

        for (Graph.Node<Station> node : graph.getNodes()) {
            drawNode(node);
        }
    }

    private void highlightNode(Graph.Node<Station> node, Color color) {
        drawNode(node, NODE_RADIUS, color);
    }

    private void highlightNode(Graph.Node<Station> node) {
        highlightNode(node, NODE_HIGHLIGHT_COLOR);
    }

    private void highlightEdge(Graph.Edge<Station> edge, Color color) {
        drawEdge(edge, color, true);
    }

    /**
     * Highlights the specified edge and adds hover text.
     *
     * @param edge the edge to highlight
     */
    public void highlightEdge(Graph.Edge<Station> edge) {
        highlightEdge(edge, EDGE_HIGHLIGHT_COLOR);
    }

    private int getX(Station station) {
        return longToX(station.getLongitude());
    }

    private int getY(Station station) {
        return latToY(station.getLatitude());
    }

    private int longToX(double longitude) {
        return (int) Math.round((longitude - MIN_LONGITUDE) * PIXELS_PER_DEGREE);
    }

    private int latToY(double latitude) {
        return SCENE_HEIGHT - (int) Math.round((latitude - MIN_LATITUDE) * PIXELS_PER_DEGREE);
    }

    /**
     * Starts the application.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new Controller());
        thread.start();
        launch(args);
    }
}
