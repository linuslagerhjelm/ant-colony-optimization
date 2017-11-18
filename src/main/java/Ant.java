
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Linus Lagerhjelm
 * File: Ant
 * Created: 2017-11-02
 * Description:
 */
public class Ant {
    private final City initialCity;
    private City currentCity;
    private Tour tour;
    private Random random;

    public Ant(City v) {
        initialCity = currentCity = v;
        tour = new Tour();
        random = new Random();
    }

    public void move(Set<SimpleEdge> simpleEdges) {
        // A feasible city is defined as a city that can be reached
        // from the current city that has not yet been visited.
        Set<SimpleEdge> feasibleCities = simpleEdges.stream()
                .filter(e -> e.getAdjacentCity(currentCity) != null)
                .filter(e -> !tour.containsCity(e.getAdjacentCity(currentCity)))
                .collect(Collectors.toSet());

        // Compute the denominator used when calculating probabilities
        double denom = feasibleCities.stream()
                .map(e -> Math.pow(e.getPheromone(), StaticUtils.alpha) *
                          Math.pow((1 / e.getWeight()), StaticUtils.beta))
                .reduce(Double::sum).get();

        // Compute the probability to choose a certain edge
        NavigableMap<Double, SimpleEdge> probabilities = new TreeMap<>();
        feasibleCities.forEach(e -> {
            double a = Math.pow(e.getPheromone(), StaticUtils.alpha);
            double b = Math.pow(e.getWeight(), StaticUtils.beta);
            Double prob = (a * b) / denom;
            probabilities.put(prob, e);
        });

        SimpleEdge nextEdge = null;
        if (probabilities.size() > 1) {
            nextEdge = chooseEdge(probabilities);
        } else {
            nextEdge = chooseEdge(feasibleCities);
        }
        tour.add(currentCity, nextEdge);
        currentCity = nextEdge.getAdjacentCity(currentCity);
    }

    private SimpleEdge chooseEdge(Collection<SimpleEdge> feasibleCities) {
        /*List<SimpleEdge> edges = new ArrayList<>();
        edges.addAll(feasibleCities);
        Integer idx = random.nextInt(edges.size());
        return edges.get(idx);*/
        List<SimpleEdge> sortedWeights = feasibleCities.stream()
                .sorted(Comparator.comparing(SimpleEdge::getWeight))
                .collect(Collectors.toList());
        return sortedWeights.get(0);
    }

    private SimpleEdge chooseEdge(NavigableMap<Double, SimpleEdge> probabilities) {
        Double rangeMin = probabilities.higherKey(0.0);
        Double rangeMax = probabilities.lastEntry().getKey();
        Double rnd = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        Double mix = random.nextDouble();
        Double value = rnd * (1 - mix) + rangeMax * mix;

        Map.Entry<Double, SimpleEdge> entry = probabilities.ceilingEntry(value);
        if (entry != null) {
            return entry.getValue();
        }

        // If no path were found (which is true for the first iteration)
        // every edge in the graph has equal probability of being chosen
        return chooseEdge(probabilities.values());
    }

    Tour getTour() {
        return tour;
    }

    void resetIteration() {
        tour.clear();
        currentCity = initialCity;
    }
}
