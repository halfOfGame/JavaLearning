package Head_First_Patterns.strategy_pattern.codes.entity;

import Head_First_Patterns.strategy_pattern.codes.behaviors.FlyBehavior;
import Head_First_Patterns.strategy_pattern.codes.behaviors.QuackBehavior;

/**
 * @author halfOfGame
 * @create 2020-05-22,22:13
 */
public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck() {
    }

    public void setFlyBehavior(FlyBehavior fb) {
        flyBehavior = fb;
    }

    public void setQuackBehavior(QuackBehavior qb) {
        quackBehavior = qb;
    }

    protected abstract void display();

    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void swim() {
        System.out.println("All ducks float, even decoys!");
    }
}
