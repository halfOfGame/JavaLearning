package Head_First_Patterns.strategy_pattern;

import Head_First_Patterns.strategy_pattern.codes.entity.Duck;
import Head_First_Patterns.strategy_pattern.codes.entity.MallardDuck;

/**
 * @author halfOfGame
 * @create 2020-05-22,22:25
 */
public class MiniDuckSimulator {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();
    }
}
