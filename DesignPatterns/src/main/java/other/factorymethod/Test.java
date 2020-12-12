package other.factorymethod;

public class Test {
    public static void main(String[] args) {
        VehicleFactory carFactory = new CarFactory();
        carFactory.orderVehicle("large", "blue");
        carFactory.creatVehicle("small").getInfo();
    }

}
