import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Linus Lagerhjelm
 * File: Simulation
 * Created: 2017-10-31
 * Description:
 */
public class Simulation {
    SimpleWeightedGraph<City, SimpleEdge> g;
    Set<Ant> ants;

    Simulation() throws IOException {
        TSPFileParser parser = new TSPFileParser();
        g = parser.parseFile("tspdata/berlin52.tsp");
        createAnts();
    }

    private void createAnts() {
        // Create n ants and distribute them randomly in the graph
        ants = new HashSet<>();
        for (int i = 0; i < g.vertexSet().size(); i++) {
            ants.add(new Ant(StaticUtils.getRandomValue(g.vertexSet())));
        }
    }

    private Double newIteration() {
        Set<City> cities = g.vertexSet();
        for (int i = 1; i < cities.size(); ++i) {
            for (Ant ant : ants) {
                ant.move(g.edgeSet());
            }
        }
        Set<Tour> tours = ants.stream().map(Ant::getTour).collect(Collectors.toSet());
        g.edgeSet().forEach(e -> e.updatePheromone(tours));
        Optional<Double> min = ants.stream()
                .map(a -> a.getTour().tourLength())
                .min(Double::compare);
        ants.forEach(Ant::resetIteration);
        return min.get();
    }

    Set<City> getCities() {
        return g.vertexSet();
    }

    Set<SimpleEdge> getEdges() {
        return g.edgeSet();
    }

    public static void main(String[] args) throws IOException {
        MainWindow window = MainWindow.getInstance();
        Simulation s = new Simulation();
        while (true) {
            window.painGraph(s.getCities(), s.getEdges());
            System.out.println(s.newIteration());
        }
    }

}