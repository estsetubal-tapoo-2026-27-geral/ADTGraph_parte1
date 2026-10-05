package tapoo.metro;

/** A fictitious network: names and travel times are illustrative. */
public final class Main {
    public static void main(String[] args) {
        MetroNetwork network = new MetroNetworkImpl();
        for (String station : new String[]{"Central", "Museum", "Garden", "University", "Hospital", "Airport"})
            network.addStation(station);
        network.connectStations("Central", "Museum", 3);
        network.connectStations("Central", "Garden", 4);
        network.connectStations("Museum", "University", 5);
        network.connectStations("Garden", "University", 2);
        network.connectStations("University", "Hospital", 3);
        // Airport is initially isolated.
        System.out.println("Stations: " + network.numberOfStations());
        System.out.println("Central neighbours: " + network.neighbouringStations("Central"));
        System.out.println("Airport neighbours: " + network.neighbouringStations("Airport"));
        System.out.println("Central - Museum: " + network.hasDirectConnection("Central", "Museum"));
        System.out.println("Museum - Central: " + network.hasDirectConnection("Museum", "Central"));
        System.out.println("Central - University: " + network.hasDirectConnection("Central", "University"));
        try { network.connectStations("Central", "Unknown", 2); }
        catch (IllegalArgumentException exception) {
            System.out.println("Cannot create connection: " + exception.getMessage());
        }
    }
}
