package tapoo.metro;

/** Station names are unique, non-blank and case-sensitive (outer spaces ignored).
 * Connections are undirected, have positive travel times, and cannot be repeated.
 * Invalid arguments, unknown stations and duplicates cause IllegalArgumentException.
 */
public interface MetroNetwork {
    void addStation(String name);
    void connectStations(String origin, String destination, int travelMinutes);
    boolean hasDirectConnection(String origin, String destination);
    Iterable<String> neighbouringStations(String station);
    int numberOfStations();
}
