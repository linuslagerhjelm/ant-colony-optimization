import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
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
    Tour bestTour;

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

    private Double newIteration() throws IOException {
        Set<City> cities = g.vertexSet();
        for (int i = 1; i < cities.size(); ++i) {
            for (Ant ant : ants) {
                ant.move(g.edgeSet());
            }
        }
        List<Tour> tours = ants.stream()
                .map(Ant::getTour)
                .sorted(Comparator.comparing(Tour::tourLength))
                .collect(Collectors.toList());

        if (bestTour == null) bestTour = tours.get(0);

        if (tours.get(0).tourLength() < bestTour.tourLength()) {
            bestTour = tours.get(0);
        }

        g.edgeSet().forEach(e -> e.updatePheromone(tours.get(0)));

        Optional<Double> min = ants.stream()
                .map(a -> a.getTour().tourLength())
                .min(Double::compare);

        File myFoo = new File("tspdata/bestTour");
        FileWriter fooWriter = new FileWriter(myFoo, false);
        fooWriter.write(bestTour.toString());
        fooWriter.close();
        ants.forEach(Ant::resetIteration);
        return min.get();
    }

    private Set<City> getCities() {
        return g.vertexSet();
    }

    private Set<SimpleEdge> getEdges() {
        return g.edgeSet();
    }

    private Tour getBestTour() {
        return bestTour;
    }

    public static void main(String[] args) throws IOException {
        MainWindow window = MainWindow.getInstance();
        Simulation s = new Simulation();
        for (int i = 0; i < StaticUtils.maxIter; ++i) {
            window.painGraph(s.getCities(), s.getEdges());
            System.out.println(s.newIteration() - StaticUtils.optimal);
        }

        System.exit(0);
    }
}
