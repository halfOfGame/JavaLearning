package MyFirstAnnotation;

import java.lang.annotation.*;

/**
 * @author halfOfGame
 * @create 2020-03-21,9:00
 */

/**
 * 注解本身没有任何功能，仅仅起到一个标示性的作用。我们是通过反射区获取到注解，
 * 再根据是否有这个注解、注解中的一些属性来判断、执行哪种业务逻辑。
 * 注解本质就是个接口，且继承自Annotation接口
 * 属性就是接口中的抽象方法
 */

/**
 * Target:描述该注解的作用范围（TYPE：类。METHOD：作用与方法。FIELD：作用与字段）
 * Retention：描述被保留的阶段
 * Documented：描述注解是否会被抽取到API文档中
 * Inherited：描述注解是否可以被继承
 */


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyDescription {

    String desc();

    String author();

    int age() default 21;
}
