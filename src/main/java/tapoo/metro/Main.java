package tapoo.metro;

/**
 * Demonstrates the metro network with a small fictitious data set.
 *
 * <p>The travel times used here are illustrative and are intended to exercise
 * the operations defined by {@link MetroNetwork}.</p>
 */
public final class Main {
    private static final String[] STATIONS = {"Alameda", "Arroios", "Saldanha", "Campo Grande", "Aeroporto"};


    /**
     * Builds a sample network, performs basic queries and reports expected
     * domain errors.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        MetroNetwork network = new MetroNetworkImpl();
        for (String name : STATIONS)
            network.addStation(name);

        network.connectStations("Alameda", "Arroios", 2);
        network.connectStations("Alameda", "Saldanha", 3);
        network.connectStations("Arroios", "Campo Grande", 3);
        network.connectStations("Saldanha", "Campo Grande", 2);
        // Aeroporto is isolated. Distinct edges have repeated travel times.
        System.out.println("Stations: " + network.numberOfStations());
        System.out.println("Alameda neighbours: " + network.neighbouringStations(" alameda "));
        System.out.println("Aeroporto neighbours: " + network.neighbouringStations("AEROPORTO"));

        // Step 1: activate these calls after completing hasDirectConnection.
        // System.out.println("Alameda - Arroios: " + network.hasDirectConnection("alameda", "ARROIOS"));
        // System.out.println("Arroios - Alameda: " + network.hasDirectConnection("Arroios", "Alameda"));
        // System.out.println("Alameda - Campo Grande: " + network.hasDirectConnection("Alameda", "Campo Grande"));

        try {
            network.addStation(" alameda ");
        } catch (InvalidMetroException exception) {
            System.out.println("Cannot add station: " + exception.getMessage());
        }
        try {
            network.connectStations("Alameda", "Unknown", 2);
        } catch (InvalidMetroException exception) {
            System.out.println("Cannot create connection: " + exception.getMessage());
        }

        // Step 2: add directTravelMinutes to the interface and implementation,
        // activate these calls and add the error cases from README.md.
        // System.out.println("Alameda - Arroios (min): " + network.directTravelMinutes("Alameda", "Arroios"));
        // System.out.println("Arroios - Alameda (min): " + network.directTravelMinutes("Arroios", "Alameda"));
    }
}
