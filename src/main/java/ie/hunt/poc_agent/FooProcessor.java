package ie.hunt.poc_agent;

import java.lang.instrument.Instrumentation;

public class FooProcessor {

    public static void premain(
            String agentArgs, Instrumentation inst) {
        inst.addTransformer(new PackagePrintingTransformer());
    }
    public static void agentmain(
            String agentArgs, Instrumentation inst) {
        inst.addTransformer(new PackagePrintingTransformer());
    }

}


