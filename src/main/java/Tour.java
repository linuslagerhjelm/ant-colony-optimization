
import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: Tour
 * Created: 2017-11-09
 * Description:
 */
public class Tour {
    List<SimpleEdge> edgesOrder;
    Set<SimpleEdge> edges;
    Set<City> cities;

    Tour(City currentCity) {
        edgesOrder = new ArrayList<>();
        edges = new HashSet<>();
        cities = new HashSet<>();
        cities.add(currentCity);
    }

    void add(City from, SimpleEdge edge) {
        edgesOrder.add(edge);
        edges.add(edge);
        cities.add(from);
    }

    Double tourLength() {
        return edgesOrder.stream()
                .map(SimpleEdge::getWeight)
                .reduce(0.0, (acc, curr) -> acc + curr);
    }

    boolean containsEdge(SimpleEdge simpleEdge) {
        return edges.contains(simpleEdge);
    }

    boolean containsCity(City city) {
        return cities.contains(city);
    }

    int size() {
        return edges.size();
    }

    void clear() {
        edgesOrder.clear();
        edges.clear();
        cities.clear();
    }
}
