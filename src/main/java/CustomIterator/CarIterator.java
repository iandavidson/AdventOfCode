package CustomIterator;

import java.util.List;

public class CarIterator {

    private final CarWithSpeedAbove65Iterator carWithSpeedAbove65Iterator;

    public CarIterator(List<Car> cars){
        this.carWithSpeedAbove65Iterator = new CarWithSpeedAbove65Iterator(cars);
    }

    public void initialize() {
        // initializes car iterator to first car in the system
    }

    public Car getNextCar() {
        // returns next car object available from system. If no car object available, return null.
        return carWithSpeedAbove65Iterator.next();
    }
}
