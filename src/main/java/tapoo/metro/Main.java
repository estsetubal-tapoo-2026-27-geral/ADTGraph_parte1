package tapoo.metro;

/**
 * Demonstrates the metro network with a small fictitious data set.
 *
 * <p>The travel times used here are illustrative and are intended to exercise
 * the operations defined by {@link MetroNetwork}.</p>
 */
public final class Main {
    private static final Station ALAMEDA = new Station("Alameda");
    private static final Station ARROIOS = new Station("Arroios");
    private static final Station SALDANHA = new Station("Saldanha");
    private static final Station CAMPO_GRANDE = new Station("Campo Grande");
    private static final Station AEROPORTO = new Station("Aeroporto");
    private static final Station[] STATIONS = {ALAMEDA, ARROIOS, SALDANHA, CAMPO_GRANDE, AEROPORTO};


    /**
     * Builds a sample network, performs basic queries and reports expected
     * domain errors.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        MetroNetwork network = new MetroNetworkImpl();
        for (Station station : STATIONS)
            network.addStation(station);

        network.connectStations(ALAMEDA, ARROIOS, 2);
        network.connectStations(ALAMEDA, SALDANHA, 3);
        network.connectStations(ARROIOS, CAMPO_GRANDE, 3);
        network.connectStations(SALDANHA, CAMPO_GRANDE, 2);
        // Aeroporto is isolated. Distinct edges have repeated travel times.
        System.out.println("Stations: " + network.numberOfStations());
        System.out.println("Alameda neighbours: " + network.neighbouringStations(new Station(" alameda ")));
        System.out.println("Aeroporto neighbours: " + network.neighbouringStations(new Station("AEROPORTO")));

        // Step 1: activate these calls after completing hasDirectConnection.
        System.out.println("Alameda - Arroios: " + network.hasDirectConnection(ALAMEDA, ARROIOS));
        System.out.println("Arroios - Alameda: " + network.hasDirectConnection(ARROIOS, ALAMEDA));
        System.out.println("Alameda - Campo Grande: " + network.hasDirectConnection(ALAMEDA, CAMPO_GRANDE));

        try {
            network.addStation(new Station(" alameda "));
        } catch (InvalidMetroException exception) {
            System.out.println("Cannot add station: " + exception.getMessage());
        }
        try {
            network.connectStations(ALAMEDA, new Station("Unknown"), 2);
        } catch (InvalidMetroException exception) {
            System.out.println("Cannot create connection: " + exception.getMessage());
        }

        // Step 2: add directTravelMinutes to the interface and implementation,
        // activate these calls and add the error cases from README.md.
        // System.out.println("Alameda - Arroios (min): " + network.directTravelMinutes("Alameda", "Arroios"));
        // System.out.println("Arroios - Alameda (min): " + network.directTravelMinutes("Arroios", "Alameda"));
    }
}
