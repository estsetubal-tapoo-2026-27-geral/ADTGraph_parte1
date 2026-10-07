package tapoo.metro;

/**
 * An undirected network without loops or parallel connections.
 * Names are trimmed and converted to upper case for storage and lookup.
 * Invalid names, unknown stations and domain violations cause InvalidMetroException.
 */
public interface MetroNetwork {
    /** Register a station; reject null, blank or duplicate names. */
    void addStation(String name) throws InvalidMetroException;
    /** Connect existing distinct stations; reject non-positive times and duplicate edges. */
    void connectStations(String origin, String destination, int travelMinutes)
        throws InvalidMetroException;
    /** Return false if no edge exists, including a station queried against itself. */
    boolean hasDirectConnection(String origin, String destination)
        throws InvalidMetroException;
    /** Return neighbour names in unspecified order; isolated stations produce an empty collection. */
    Iterable<String> neighbouringStations(String station) throws InvalidMetroException;
    int numberOfStations();
    // Activity: add the directTravelMinutes signature described in README.md.
}
