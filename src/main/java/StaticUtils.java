
import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: StaticUtils
 * Created: 2017-11-03
 * Description:
 */
class StaticUtils {
    static final double EVAP_RATE = 0.15;
    static final double Q = 10;
    static final double alpha = .2;
    static final double beta = .01;
    static final double initialPheromone = 0.0;

    static City getRandomValue(Set<City> cities) {
        List<City> cl = new ArrayList<>();
        cl.addAll(cities);
        Collections.shuffle(cl);
        return cl.get(0);
    }
}
