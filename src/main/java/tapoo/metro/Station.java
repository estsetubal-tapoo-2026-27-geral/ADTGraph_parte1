package tapoo.metro;

/**
 * Represents a metro station stored in the graph.
 *
 * <p>Name validation and uniqueness are enforced by {@link MetroNetworkImpl}.
 * This class only stores the already normalised station name.</p>
 */
public final class Station {
    private final String name;

    /**
     * Creates a station with the specified name.
     *
     * @param name station name
     */
    public Station(String name) {
        this.name = name;
    }

    /**
     * Returns this station's name.
     *
     * @return station name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the station name.
     *
     * @return station name
     */
    @Override
    public String toString() {
        return name;
    }
}
