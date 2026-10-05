import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Collection;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Graph<Integer, String> g = new GraphEdgeList<>();

        System.out.printf("Num. Vertices = %d \n", g.numVertices());
        System.out.printf("Num. Edges = %d \n", g.numEdges());
        System.out.printf("Vertices = %s \n", g.vertices());
        System.out.printf("Edges = %s \n", g.edges());

        Vertex<Integer> v1 = g.insertVertex(1);
        Vertex<Integer> v5 = g.insertVertex(5);
        Vertex<Integer> v6 = g.insertVertex(6);
        Vertex<Integer> v2 = g.insertVertex(2);
        Vertex<Integer> v7 = g.insertVertex(7);
        Vertex<Integer> v3 = g.insertVertex(3);
        Vertex<Integer> v4 = g.insertVertex(4);
        Vertex<Integer> v8 = g.insertVertex(8);

        Edge<String, Integer> e7 = g.insertEdge(v1, v5, "e7");
        Edge<String, Integer> e8 = g.insertEdge(v6, v5, "e8");
        Edge<String, Integer> e10 = g.insertEdge(v1, v6, "e10");
        Edge<String, Integer> e1 = g.insertEdge(v1, v2, "e1");
        Edge<String, Integer> e4 = g.insertEdge(v2, v7, "e4");
        Edge<String, Integer> e9 = g.insertEdge(v5, v7, "e9");
        Edge<String, Integer> e2 = g.insertEdge(v2, v3, "e2");
        Edge<String, Integer> e3 = g.insertEdge(v3, v4, "e3");
        Edge<String, Integer> e5 = g.insertEdge(v7, v4, "e5");
        Edge<String, Integer> e6 = g.insertEdge(v5, v4, "e6");

        System.out.printf("Num. Vertices = %d \n", g.numVertices());
        System.out.printf("Num. Edges = %d \n", g.numEdges());
        System.out.printf("Vertices = %s \n", g.vertices());
        System.out.printf("Edges = %s \n", g.edges());

        Collection<Edge<String, Integer>> incident = g.incidentEdges(v5);
        System.out.printf("Incident edges of 5: (%d) - %s \n",
                incident.size(), // Degree of vertex 5
                incident);

        System.out.println("5 -- 6 ? " + g.areAdjacent(v5, v6));
        System.out.println("5 -- 2 ? " + g.areAdjacent(v5, v2));

        for(Edge<String, Integer> edge : incident) {
            Vertex<Integer> w = g.opposite(v5, edge);
            System.out.print(w.element() + " ");
        }
        System.out.println();

        Vertex<Integer>[] vertices = e7.vertices();
        System.out.println(vertices[0].element());
        System.out.println(vertices[1].element());


        ///////////////////////////////////////////////////////////////////////////////////////
        // NOTHING TO DO BELOW THIS LINE

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<Integer, String> graphView = new SmartGraphPanel<>(g, strategy);

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 800, 800);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Visualization");
        stage.setScene(scene);
        stage.show();

        graphView.init();
    }
}
