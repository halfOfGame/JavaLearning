package other.simplefactory;

public class VehicleFactory {

    public enum VehicleType {
        Bike, Car, Trunk
    }

    public static Vehicle creat(VehicleType type) {
        if (type.equals(VehicleType.Bike)) {
            return new Bike();
        }
        if (type.equals(VehicleType.Car)) {
            return new Car();
        }
        if (type.equals(VehicleType.Trunk)) {
            return new Truck();
        }
        return null;
    }
}
