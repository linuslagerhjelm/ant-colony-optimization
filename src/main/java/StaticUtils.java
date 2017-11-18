
import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: StaticUtils
 * Created: 2017-11-03
 * Description:
 */
class StaticUtils {
    static final double EVAP_RATE = 0.51; // 0.51
    static final double alpha = .2; //0.2
    static final double beta = .009; //0.009
    static final double initialPheromone = 0.0; //0.0
    static final double tMax = 1;
    static final double tMin = 0;
    static final int maxIter = 2500;
    static final int optimal = 7542;

    static City getRandomValue(Set<City> cities) {
        List<City> cl = new ArrayList<>();
        cl.addAll(cities);
        Collections.shuffle(cl);
        return cl.get(0);
    }
}
