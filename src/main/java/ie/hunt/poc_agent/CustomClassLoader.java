package ie.hunt.poc_agent;

public class CustomClassLoader extends ClassLoader{

    public CustomClassLoader(ClassLoader classLoader) {
        super(classLoader);

    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading class :" + name);

        return super.loadClass(name);
    }
}