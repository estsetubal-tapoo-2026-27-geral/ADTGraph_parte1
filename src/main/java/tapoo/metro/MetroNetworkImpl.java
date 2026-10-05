package tapoo.metro;


import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/** Domain operations implemented using the supplied Graph ADT. */
public final class MetroNetworkImpl implements MetroNetwork {
    // Station names are vertex elements; travel times are edge elements.
    private final Graph<String,Integer> graph = new GraphEdgeList<>();

    private String normaliseName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Station name must not be blank");
        return name.strip();
    }
    // The ADT works with references. Name lookup belongs to this application.
    private Vertex<String> findStation(String name) {
        String key = normaliseName(name);
        for (Vertex<String> vertex : graph.vertices())
            if (vertex.element().equals(key)) return vertex;
        return null;
    }
    private Vertex<String> requireStation(String name) {
        Vertex<String> vertex = findStation(name);
        if (vertex == null) throw new IllegalArgumentException("Unknown station: " + name);
        return vertex;
    }
    public void addStation(String name) {
        String key = normaliseName(name);
        if (findStation(key) != null)
            throw new IllegalArgumentException("Station already exists: " + key);
        graph.insertVertex(key);
    }
    public void connectStations(String origin, String destination, int travelMinutes) {
        if (travelMinutes <= 0)
            throw new IllegalArgumentException("Travel time must be positive");
        Vertex<String> first = requireStation(origin);
        Vertex<String> second = requireStation(destination);
        if (first == second) throw new IllegalArgumentException("Stations must be different");
        if (graph.areAdjacent(first, second))
            throw new IllegalArgumentException("Direct connection already exists");
        graph.insertEdge(first, second, Integer.valueOf(travelMinutes));
    }
    public boolean hasDirectConnection(String origin, String destination) {
        return graph.areAdjacent(requireStation(origin), requireStation(destination));
    }
    public Iterable<String> neighbouringStations(String station) {
        Vertex<String> vertex = requireStation(station);
        List<String> result = new ArrayList<>();
        for (Edge<Integer,String> edge : graph.incidentEdges(vertex)) {
            Vertex<String> neighbour = graph.opposite(vertex, edge);
            result.add(neighbour.element());
        }
        return result;
    }
    public int numberOfStations() { return graph.numVertices(); }
}
