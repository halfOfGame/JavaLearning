package other.factorymethod;

public abstract class VehicleFactory {
    protected abstract Vehicle creatVehicle (String item);

    public Vehicle orderVehicle (String size, String color) {
        Vehicle vehicle = creatVehicle(size);
        return vehicle;
    }
}
