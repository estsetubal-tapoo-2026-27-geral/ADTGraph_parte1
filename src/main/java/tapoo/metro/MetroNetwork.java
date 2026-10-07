package tapoo.metro;

/**
 * Defines the operations supported by an undirected metro network.
 *
 * <p>The network has no loops and no parallel connections. Station names are
 * trimmed and converted to upper case for storage and lookup. Invalid names,
 * unknown stations and domain violations are reported with
 * {@link InvalidMetroException}.</p>
 */
public interface MetroNetwork {
    /**
     * Registers a new station in the network.
     *
     * @param name station name to register
     * @throws InvalidMetroException if the name is {@code null}, blank or
     *                               already registered
     */
    void addStation(String name) throws InvalidMetroException;

    /**
     * Creates a direct connection between two existing, distinct stations.
     *
     * @param origin origin station name
     * @param destination destination station name
     * @param travelMinutes positive estimated travel time in minutes
     * @throws InvalidMetroException if a station name is invalid, a station is
     *                               unknown, both names identify the same
     *                               station, the travel time is not positive,
     *                               or the direct connection already exists
     */
    void connectStations(String origin, String destination, int travelMinutes)
            throws InvalidMetroException;

    /**
     * Checks whether two existing stations have a direct connection.
     *
     * @param origin origin station name
     * @param destination destination station name
     * @return {@code true} if the stations are directly connected; {@code false}
     *         otherwise, including when a station is queried against itself
     * @throws InvalidMetroException if a station name is invalid or a station is
     *                               unknown
     */
    boolean hasDirectConnection(String origin, String destination)
            throws InvalidMetroException;

    /**
     * Returns the names of all stations directly connected to the given station.
     *
     * @param station station name whose neighbours are requested
     * @return iterable with neighbouring station names in unspecified order, or
     *         an empty iterable for an isolated station
     * @throws InvalidMetroException if the station name is invalid or unknown
     */
    Iterable<String> neighbouringStations(String station) throws InvalidMetroException;

    /**
     * Returns the number of stations registered in the network.
     *
     * @return total number of stations
     */
    int numberOfStations();

    // Activity: add the directTravelMinutes signature described in README.md.
}
