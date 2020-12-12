//package ClassLoader;
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * @author halfOfGame
// * @create 2020-03-20,21:39
// */
//public class ClassLoaderTest_1 {
//    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        ClassLoader myLoader = new ClassLoader() {
//            @Override
//            public Class<?> loadClass(String name) throws ClassNotFoundException {
//                try {
//                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
//                    InputStream is = getClass().getResourceAsStream(fileName);
//                    if (is == null) {
//                        return super.loadClass(name);
//                    }
//                    byte[] b = new byte[is.available()];
//                    is.read(b);
//                    return defineClass(name, b, 0, b.length);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        Object o = myLoader.loadClass("ClassLoader.ClassLoaderTest_1").newInstance();
//
//        System.out.println(o.getClass());
//        System.out.println(o instanceof ClassLoaderTest_1);
//    }
//}
