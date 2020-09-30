package Head_First_Patterns.decoretor_pattern;

import Head_First_Patterns.decoretor_pattern.codes.Beverage;
import Head_First_Patterns.decoretor_pattern.codes.Espresso;
import Head_First_Patterns.decoretor_pattern.codes.HouseBlend;
import Head_First_Patterns.decoretor_pattern.codes.Mocha;

public class StarbuzzCoffee {

    public static void main(String[] args) {

        Beverage beverage_1 = new Espresso();
        beverage_1 = new Mocha(beverage_1);
        System.out.println(beverage_1.getDescription() + " $" + beverage_1.cost());

        Beverage beverage_2 = new Espresso();
        beverage_2 = new Mocha(beverage_2);
        beverage_2 = new Mocha(beverage_2);
        System.out.println(beverage_2.getDescription() + " $" + beverage_2.cost());

        Beverage beverage_3 = new HouseBlend();
        beverage_3 = new Mocha(beverage_3);
        System.out.println(beverage_3.getDescription() + " $" + beverage_3.cost());

    }
}
