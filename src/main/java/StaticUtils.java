
import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: StaticUtils
 * Created: 2017-11-03
 * Description:
 */
class StaticUtils {
    static final double EVAP_RATE = 0.3; // 0.3
    static final double alpha = .4; //0.4
    static final double beta = .0096; //0.0096
    static final double initialPheromone = 0.0; //0.0
    static final double tMax = 1;
    static final double tMin = 0;
    static final int maxIter = 3000;
    static final int optimal = 7542;

    static City getRandomValue(Set<City> cities) {
        List<City> cl = new ArrayList<>();
        cl.addAll(cities);
        Collections.shuffle(cl);
        return cl.get(0);
    }
}
