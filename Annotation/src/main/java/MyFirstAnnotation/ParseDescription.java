package MyFirstAnnotation;

import java.lang.reflect.Method;

/**
 * @author halfOfGame
 * @create 2020-03-21,9:03
 */


/**
 * 功能：通过class文件，查看属性和方法中是否存在注解。然后进行逻辑处理。
 */

public class ParseDescription {

    public static void main(String[] args) {

        try {
            Class c = Class.forName("MyFirstAnnotation.Test");
            boolean IsExist = c.isAnnotationPresent(MyDescription.class);
            if (IsExist) {
                MyDescription description = (MyDescription)c.getAnnotation(MyDescription.class);
                System.out.println(description.desc());
            }

            Method[] methods = c.getMethods();
            for (Method method : methods) {
                IsExist = method.isAnnotationPresent(MyDescription.class);
                if (IsExist) {
                    MyDescription description = method.getAnnotation(MyDescription.class);
                    System.out.println(description.desc());
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
