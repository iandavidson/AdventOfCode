package CustomIterator;

import java.util.List;

public class CarWithSpeedAbove65Iterator implements Iterator<Car> {
    private final List<Car> cars;
    private Integer currentIndex;

    public CarWithSpeedAbove65Iterator(List<Car> cars){
        this.cars = cars;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        int tempIndex = currentIndex;
        while(tempIndex  < cars.size()){
            if(cars.get(tempIndex).speed() > 65){
                return true;
            }
            tempIndex++;
        }

        return false;
    }

    @Override
    public Car next() {
        while(currentIndex < cars.size()){
            if(cars.get(currentIndex).speed() > 65){
                Car car =  cars.get(currentIndex);
                this.currentIndex++;
                return car;
            }
            this.currentIndex++;
        }

        return null;
    }
}
