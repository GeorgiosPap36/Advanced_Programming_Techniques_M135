import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws NoSuchMethodException {

        AOP.AOPFactory aFactory = new AOP.AOPFactory();

        Aspect.Builder bob = aFactory.newBuilder();

        Class<?>[] targets = new Class[]{MyInterface.class};
        bob.withTargets(targets);

        Method myMethod;

        try{
            myMethod = MyInterface.class.getMethod("myMethod");
            Runnable beforeAdvice = () -> System.out.println("Before advice");
            bob.withBeforeAdviceFor(beforeAdvice, myMethod);
        } catch (Exception e){ }

        try{
            myMethod = MyInterface.class.getMethod("myMethod");
            Runnable afterAdvice = () -> System.out.println("After advice dfg d");
            bob.withAfterAdviceFor(afterAdvice, myMethod);
        } catch (Exception e){ }

        try{
            myMethod = MyInterface.class.getMethod("myMethod");
            Runnable aroundAdvice = () -> System.out.println("Around advice 34235");
            bob.withAroundAdviceFor(aroundAdvice, myMethod);
        } catch (Exception e){ }

        try{
            myMethod = MyInterface.class.getMethod("myMethod2");
            Runnable beforeAdvice = () -> System.out.println("Before advice");
            bob.withBeforeAdviceFor(beforeAdvice, myMethod);
        } catch (Exception e){ }

        try{
            myMethod = MyInterface.class.getMethod("myMethod2");
            Runnable afterAdvice = () -> System.out.println("After advice");
            bob.withAfterAdviceFor(afterAdvice, myMethod);
        } catch (Exception e){ }

        try{
            myMethod = MyInterface.class.getMethod("myMethod2");
            Runnable aroundAdvice = () -> System.out.println("Around advice");
            bob.withAroundAdviceFor(aroundAdvice, myMethod);
        } catch (Exception e){ }

        Aspect aspect = bob.build();
        AOP.AOPWeaver weaver = (AOP.AOPWeaver) aFactory.newWeaver();
        weaver.setWeaver(aspect);

        MyInterface target = new MyImplementation();
        MyInterface wovenTarget = (MyInterface) weaver.weave(target);

        wovenTarget.myMethod();
        System.out.println("------------------------------------------");
        wovenTarget.myMethod2();
    }
    
}

interface MyInterface {
    void myMethod();

    int myMethod2();
}

class MyImplementation implements MyInterface {
    public void myMethod(){
        System.out.println("Method1: ");
    }

    public int myMethod2() {
        System.out.println("Method2: ");
        return 5;
    }
}

