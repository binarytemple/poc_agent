package ie.hunt.poc_agent;

import java.util.stream.IntStream;

public class FooMain {

    public FooMain() {

        System.out.println("Hello World");
    }


    public static void main(String[] args) throws InterruptedException {


        IntStream.range(0, 10).forEach(x ->
                loadClass()
        );

    }


    private static void loadClass() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new FooMain();
    }
}
