package Head_First_Patterns.strategy_pattern.codes.behaviors.impl;

import Head_First_Patterns.strategy_pattern.codes.behaviors.FlyBehavior;

/**
 * @author halfOfGame
 * @create 2020-05-22,22:20
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I'm flying!!");
    }
}
