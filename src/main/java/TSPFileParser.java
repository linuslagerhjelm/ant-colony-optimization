
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: Linus Lagerhjelm
 * File: TSPFileParser
 * Created: 2017-11-02
 * Description:
 */
public class TSPFileParser {

    public SimpleWeightedGraph<City, SimpleEdge>
    parseFile(String fileName) throws IOException {

        SimpleWeightedGraph<City, SimpleEdge> g = new SimpleWeightedGraph<>(SimpleEdge.class);

        Stream<String> stream = Files.lines(Paths.get(fileName));
        stream.forEach(line -> addLine(line, g));

        Set<City> cities = g.vertexSet();
        for (City c1 : cities) {
            for (City c2 : cities) {
                if (c1.equals(c2)) continue;

                SimpleEdge e = new SimpleEdge(c1.distance(c2), c1, c2);
                g.addEdge(c1, c2, e);
            }
        }

        return g;
    }

    private void addLine(String line, SimpleWeightedGraph<City, SimpleEdge> g) {
        List<Double> values = Arrays.stream(line.split(" "))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        g.addVertex(new City(values.get(0).intValue(), values.get(1), values.get(2)));
    }
}
