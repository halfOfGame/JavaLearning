package other.factorymethod;

public class CarFactory extends VehicleFactory {

    @Override
    protected Vehicle creatVehicle(String size) {
        if ("small".equals(size)) {
            return new SportCar();
        } else if ("large".equals(size)) {
            return new SedanCar();
        } else {
            return null;
        }
    }
}
