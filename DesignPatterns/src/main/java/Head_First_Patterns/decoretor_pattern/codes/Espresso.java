package Head_First_Patterns.decoretor_pattern.codes;

public class Espresso extends Beverage {

    public Espresso() {
        decription = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
