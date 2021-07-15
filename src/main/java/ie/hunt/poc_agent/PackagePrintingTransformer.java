package ie.hunt.poc_agent;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

import static java.lang.System.exit;


public class PackagePrintingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String fullyQualifiedClassName, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        String className = fullyQualifiedClassName.split(":")[0];
        String packageName
                = fullyQualifiedClassName.replaceAll("/[a-zA-Z$0-9_]*$", "");

        byte[] byteCode = classfileBuffer;
        String packagePrintingTransformer = this.getClass().getName().replaceAll("\\.", "/");

        if (maybeInstrument(className)) {

            System.out.printf("Class: %s in: %s\n", className, packageName);
            System.out.printf("fullyQualifiedClassName %s", fullyQualifiedClassName);
            Logger.getGlobal().info("[Agent] Transforming class :" + className);
            System.out.printf("Compare: finalTargetClassName %s | className %s | packageName %s ", packagePrintingTransformer, className, packageName);
            try {
                ClassPool classPool = new ClassPool();
                classPool.appendClassPath(new LoaderClassPath(loader));
                CtClass cc = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                for (CtMethod m : cc.getDeclaredMethods()) {
                    String mname = m.getLongName();
                    m.addLocalVariable(
                            "startTime", CtClass.longType);
                    m.insertBefore(
                            "startTime = System.currentTimeMillis();");
                    m.addLocalVariable("endTime", CtClass.longType);
                    m.addLocalVariable("opTime", CtClass.longType);
                    StringBuilder endBlock = new StringBuilder();
                    endBlock.append(
                            "endTime = System.currentTimeMillis();");
                    endBlock.append(
                            "opTime = (endTime-startTime);");
                    endBlock.append(
                            "System.err.println(\"Method :" + mname + " completed in: \" + opTime + \" \");");
                    m.insertAfter(endBlock.toString());
                }
                byteCode = cc.toBytecode();
                cc.detach();
            } catch (Exception e) {
                System.err.printf("Exception: %s", e.toString());
                e.printStackTrace();
                exit(1);
            }
            return byteCode;
        } else {
            return byteCode;
        }
    }

    private boolean maybeInstrument(String className) {
        if(className.startsWith("jdk") || className.startsWith("java") ||  className.startsWith("sun") ) {
            return false;
        }

        if (!className.equals("ie/hunt/poc_agent/PackagePrintingTransformer")) {
            //System.err.println("instrument:" + className);
            return true;
        } else {
            //System.err.println("don't instrument:" + className);
            return false;
        }
    }
}