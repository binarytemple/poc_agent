package ie.hunt.poc_agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class FooProcessor {

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new PackagePrintingTransformer());
    }
}


