package Head_First_Patterns.decoretor_pattern.codes;

public abstract class Beverage {
    String decription = "Unknown Beverage";

    public String getDescription() {
        return decription;
    }

    public abstract double cost();
}
