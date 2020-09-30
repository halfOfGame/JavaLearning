package other.simplefactory;

public class Test {
    public static void main(String[] args) {
        Vehicle v = VehicleFactory.creat(VehicleFactory.VehicleType.Car);
        v.move();
    }
}
