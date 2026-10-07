package tapoo.metro;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * {@link MetroNetwork} implementation backed by the SmartGraph graph ADT.
 *
 * <p>Vertices store {@link Station} objects and edges store {@link Connection}
 * objects. All validation and name normalisation for the metro domain is
 * centralised in this class.</p>
 */
public final class MetroNetworkImpl implements MetroNetwork {
    private final Graph<Station, Connection> graph = new GraphEdgeList<>();


    private String normaliseName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMetroException("Invalid station name.");
        }
        return name.trim().toUpperCase(Locale.ROOT);
    }


    private Vertex<Station> findStation(String name) {
        for (Vertex<Station> vertex : graph.vertices()) {
            if (vertex.element().getName().equalsIgnoreCase(name)) {
                return vertex;
            }
        }
        return null;
    }
    /**
     * Validates and normalises the station name, then retrieves its vertex.
     *
     * @param name the station name, ignoring case and surrounding spaces
     * @return the vertex containing the existing station
     * @throws InvalidMetroException if the name is null or empty after trimming,
     *                               or the station does not exist
     */
    private Vertex<Station> checkStation(String name) {
        String normalisedName = normaliseName(name);
        Vertex<Station> vertex = findStation(normalisedName);
        if (vertex == null) {
            throw new InvalidMetroException("Unknown station: " + name);
        }
        return vertex;
    }


    @Override
    public void addStation(String name) throws InvalidMetroException {
        String normalisedName = normaliseName(name);
        if (findStation(normalisedName) != null) {
            throw new InvalidMetroException("Station already exists.");
        }
        graph.insertVertex(new Station(normalisedName));
    }


    @Override
    public void connectStations(String origin, String destination, int travelMinutes)
            throws InvalidMetroException {
        Vertex<Station> first = checkStation(origin);
        Vertex<Station> second = checkStation(destination);
        if (first == second) {
            throw new InvalidMetroException("Stations must be different.");
        }
        if (travelMinutes <= 0) {
            throw new InvalidMetroException("Travel time must be positive.");
        }
        if (graph.areAdjacent(first, second)) {
            throw new InvalidMetroException("Direct connection already exists.");
        }
        // Create a distinct object for every edge, even when travel times coincide.
        graph.insertEdge(first, second, new Connection(travelMinutes));
    }


    @Override
    public boolean hasDirectConnection(String origin, String destination)
            throws InvalidMetroException {
        // TODO: resolve both names and call graph.areAdjacent(...).
        // Existing stations without an edge must produce false.
        throw new UnsupportedOperationException("TODO: implement hasDirectConnection.");
    }


    @Override
    public Iterable<String> neighbouringStations(String station) throws InvalidMetroException {
        Vertex<Station> vertex = checkStation(station);
        List<String> result = new ArrayList<>();
        for (Edge<Connection, Station> edge : graph.incidentEdges(vertex)) {
            Vertex<Station> neighbour = graph.opposite(vertex, edge);
            result.add(neighbour.element().getName());
        }
        return result;
    }


    @Override
    public int numberOfStations() {
        return graph.numVertices();
    }
}
