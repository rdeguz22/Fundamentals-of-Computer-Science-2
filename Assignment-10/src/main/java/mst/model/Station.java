package mst.model;

/**
 * A station, including its name and location.
 */
public class Station {
    private String name;
    private double latitude;
    private double longitude;

    /**
     * Constructs a new station.
     *
     * @param name      the name of the station
     * @param latitude  the latitude of the station
     * @param longitude the longitude of the station
     */
    public Station(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the name of this station.
     *
     * @return the name of this station
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the latitude of this station.
     *
     * @return the latitude of this station
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude of this station.
     *
     * @return the longitude of this station
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("%s (lat. %f, long. %f)", name, latitude, longitude);
    }
}
