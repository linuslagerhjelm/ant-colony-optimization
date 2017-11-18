
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;

/**
 * Author: Linus Lagerhjelm
 * File: SimpleEdge
 * Created: 2017-11-02
 * Description:
 */
class SimpleEdge extends DefaultEdge {
    private final double weight;
    private double pheromone;
    private final City start;
    private final City end;

    SimpleEdge(double weight, City start, City end) {
        pheromone = StaticUtils.initialPheromone;
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    void updatePheromone(Set<Tour> tours) {
        double addedPherm = 0;
        for (Tour t : tours) {
            if (t.containsEdge(this)) {
                addedPherm += StaticUtils.Q / t.tourLength();
            }
        }
        pheromone = (1 - StaticUtils.EVAP_RATE) * pheromone + addedPherm;
    }

    Double getWeight() {
        return weight;
    }

    double getPheromone() {
        return pheromone;
    }

    City getAdjacentCity(City curr) {
        if (curr == start) return end;
        if (curr == end) return start;
        return null;
    }

    City getStart() {
        return start;
    }

    City getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleEdge edge = (SimpleEdge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (Double.compare(edge.pheromone, pheromone) != 0) return false;
        if (!start.equals(edge.start)) return false;
        return end.equals(edge.end);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pheromone);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}
