package tapoo.metro;

/** Distinct connections may have identical travel times. */
public final class Connection {
    private final int travelMinutes;
    public Connection(int travelMinutes) { this.travelMinutes = travelMinutes; }
    public int getTravelMinutes() { return travelMinutes; }
    @Override public String toString() { return travelMinutes + " min"; }
}
