package tapoo.metro;

/**
 * Defines the operations supported by an undirected metro network.
 *
 * <p>The network has no loops and no parallel connections. Station names are
 * read from {@link Station#getName()}, trimmed and converted to upper case for
 * storage and lookup. Invalid stations, unknown stations and domain violations are reported with
 * {@link InvalidMetroException}.</p>
 */
public interface MetroNetwork {
    /**
     * Registers a new station in the network.
     *
     * @param station station to register
     * @throws InvalidMetroException if the station is {@code null}, has a blank name or is
     *                               already registered
     */
    void addStation(Station station) throws InvalidMetroException;

    /**
     * Creates a direct connection between two existing, distinct stations.
     *
     * @param origin origin station
     * @param destination destination station
     * @param travelMinutes positive estimated travel time in minutes
     * @throws InvalidMetroException if a station is invalid or unknown, both
     *                               stations identify the same station, the
     *                               travel time is not positive, or the direct
     *                               connection already exists
     */
    void connectStations(Station origin, Station destination, int travelMinutes)
            throws InvalidMetroException;

    /**
     * Checks whether two existing stations have a direct connection.
     *
     * @param origin origin station
     * @param destination destination station
     * @return {@code true} if the stations are directly connected; {@code false}
     *         otherwise, including when a station is queried against itself
     * @throws InvalidMetroException if a station is invalid or unknown
     */
    boolean hasDirectConnection(Station origin, Station destination)
            throws InvalidMetroException;

    /**
     * Returns the names of all stations directly connected to the given station.
     *
     * @param station station whose neighbours are requested
     * @return iterable with neighbouring station names in unspecified order, or
     *         an empty iterable for an isolated station
     * @throws InvalidMetroException if the station is invalid or unknown
     */
    Iterable<String> neighbouringStations(Station station) throws InvalidMetroException;

    /**
     * Returns the number of stations registered in the network.
     *
     * @return total number of stations
     */
    int numberOfStations();

    // Activity: add the directTravelMinutes signature described in README.md.
}
