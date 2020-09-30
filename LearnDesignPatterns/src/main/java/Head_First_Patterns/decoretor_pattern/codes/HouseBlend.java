package Head_First_Patterns.decoretor_pattern.codes;

public class HouseBlend extends Beverage{

    public HouseBlend() {
        decription = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
