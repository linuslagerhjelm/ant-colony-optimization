
/**
 * Author: Linus Lagerhjelm
 * File: City
 * Created: 2017-11-02
 * Description:
 */
public class City {
    private int index;
    private double lat;
    private double lon;

    City(int index, Double lat, Double lon) {
        this.index = index;
        this.lat = lat;
        this.lon = lon;
    }

    double distance(City target) {
        double dx = target.lat - lat;
        double dy = target.lon - lon;
        return Math.round(Math.sqrt((dx*dx) + (dy*dy)));
    }

    double getLat() {
        return lat;
    }

    double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return index == city.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    public int getIndex() {
        return index;
    }
}
