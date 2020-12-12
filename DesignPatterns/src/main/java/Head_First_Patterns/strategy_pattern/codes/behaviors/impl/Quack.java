package Head_First_Patterns.strategy_pattern.codes.behaviors.impl;

import Head_First_Patterns.strategy_pattern.codes.behaviors.QuackBehavior;

/**
 * @author halfOfGame
 * @create 2020-05-22,22:23
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
