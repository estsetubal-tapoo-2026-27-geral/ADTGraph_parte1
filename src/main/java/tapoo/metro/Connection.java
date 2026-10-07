package tapoo.metro;

/**
 * Represents a direct connection between two metro stations.
 *
 * <p>The connection stores the estimated travel time in minutes. Distinct
 * connections may have identical travel times.</p>
 */
public final class Connection {
    private final int travelMinutes;

    /**
     * Creates a connection with the specified travel time.
     *
     * @param travelMinutes estimated travel time in minutes
     */
    public Connection(int travelMinutes) {
        this.travelMinutes = travelMinutes;
    }

    /**
     * Returns the estimated travel time for this connection.
     *
     * @return travel time in minutes
     */
    public int getTravelMinutes() {
        return travelMinutes;
    }

    /**
     * Returns a readable representation of the connection travel time.
     *
     * @return travel time followed by the {@code min} unit
     */
    @Override
    public String toString() {
        return travelMinutes + " min";
    }
}
