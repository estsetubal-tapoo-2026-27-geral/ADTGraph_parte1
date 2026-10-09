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
     * Validates the station, normalises its name, then retrieves its vertex.
     *
     * @param station the station, using its name and ignoring case and surrounding spaces
     * @return the vertex containing the existing station
     * @throws InvalidMetroException if the station is null, its name is empty after trimming,
     *                               or the station does not exist
     */
    private Vertex<Station> checkStation(Station station) {
        if (station == null) {
            throw new InvalidMetroException("Invalid station.");
        }
        String normalisedName = normaliseName(station.getName());
        Vertex<Station> vertex = findStation(normalisedName);
        if (vertex == null) {
            throw new InvalidMetroException("Unknown station: " + station.getName());
        }
        return vertex;
    }


    @Override
    public void addStation(Station station) throws InvalidMetroException {
        if (station == null) {
            throw new InvalidMetroException("Invalid station.");
        }
        String normalisedName = normaliseName(station.getName());
        if (findStation(normalisedName) != null) {
            throw new InvalidMetroException("Station already exists.");
        }
        graph.insertVertex(new Station(normalisedName));
    }


    @Override
    public void connectStations(Station origin, Station destination, int travelMinutes)
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
    public boolean hasDirectConnection(Station origin, Station destination)
            throws InvalidMetroException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public Iterable<String> neighbouringStations(Station station) throws InvalidMetroException {
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
