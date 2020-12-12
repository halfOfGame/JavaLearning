package Head_First_Patterns.strategy_pattern.codes.entity;

import Head_First_Patterns.strategy_pattern.codes.behaviors.impl.FlyWithWings;
import Head_First_Patterns.strategy_pattern.codes.behaviors.impl.Quack;

/**
 * @author halfOfGame
 * @create 2020-05-22,22:26
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    public void display() {
        System.out.println("I'm a real Mallard duck");
    }
}
