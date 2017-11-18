
import org.jgrapht.graph.DefaultEdge;

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

    void updatePheromone(Tour tour) {
        Double dT = tour.containsEdge(this) ? tour.tourLength() : 0;
        Double x = (1 - StaticUtils.EVAP_RATE) * pheromone + dT;
        if (x > StaticUtils.tMax) {
            pheromone = StaticUtils.tMax;
        } else if (x < StaticUtils.tMin) {
            pheromone = StaticUtils.tMin;
        } else {
            pheromone = x;
        }
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
