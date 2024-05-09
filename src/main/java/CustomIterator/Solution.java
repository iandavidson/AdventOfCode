package CustomIterator;

import java.util.List;

public class Solution{
    public static void main(String [] args){

        List<Car> cars = List.of(new Car(66), new Car(77), new Car(12), new Car(100), new Car(50));
        CarWithSpeedAbove65Iterator carWithSpeedAbove65Iterator = new CarWithSpeedAbove65Iterator(cars);

        while(carWithSpeedAbove65Iterator.hasNext()){
            Car car = carWithSpeedAbove65Iterator.next();
            System.out.println(car.speed());
        }
    }
}