import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.HashMap;

public class AOP implements Aspect {

    private Class<?>[] classTargets;
    private HashMap<Method, Runnable> beforeRunnable = new HashMap<>();
    private HashMap<Method, Runnable> afterRunnable = new HashMap<>();
    private HashMap<Method, Runnable> aroundRunnable = new HashMap<>();

    public Class<?>[] getTargets(){
        return classTargets;
    }

    public Runnable beforeAdviceFor(Method method){
        if (beforeRunnable.get(method) == null)
            return null;

        return beforeRunnable.get(method);
    }

    public Runnable afterAdviceFor(Method method){
        if (afterRunnable.get(method) == null)
            return null;

        return afterRunnable.get(method);
    }

    public Runnable aroundAdviceFor(Method method){
        if (aroundRunnable.get(method) == null)
            return null;

        return aroundRunnable.get(method);
    }
    

    public static class AOPBuilder implements Builder{

        private Class<?>[] targs;
        private HashMap<Method, Runnable> befAdvice = new HashMap<>();
        private HashMap<Method, Runnable> afAdvice = new HashMap<>();
        private HashMap<Method, Runnable> arAdvice = new HashMap<>();

        private AOP aspect;

        public Builder withTargets(Class<?>[] targets){
            targs = targets;
            return this;
        }

        public Builder withBeforeAdviceFor(Runnable beforeAdvice, Method... methods){
            for (Method m : methods){
                befAdvice.put(m, beforeAdvice);
            }
            return this;
        }

        public Builder withAfterAdviceFor(Runnable afterAdvice, Method... methods){
            for (Method m : methods){
                afAdvice.put(m, afterAdvice);
            }
            return this;
        }

        public Builder withAroundAdviceFor(Runnable aroundAdvice, Method... methods){
            for (Method m : methods){
                arAdvice.put(m, aroundAdvice);
            }
            return this;
        }

        public Aspect build(){
            aspect = new AOP();
            aspect.classTargets = this.targs;
            aspect.beforeRunnable = befAdvice;
            aspect.afterRunnable = afAdvice;
            aspect.aroundRunnable = arAdvice;
            return aspect;
        }
    }


    public static class AOPWeaver implements Weaver{

        private Aspect aspect;

        public void setWeaver(Aspect aspect){
            this.aspect = aspect;
        }
        
        public Object weave(Object target){
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), aspect.getTargets(), new InvocHandler(target, aspect));
        }
        
    }

    public static class AOPFactory implements Factory{

        public Builder newBuilder(){
            return new AOPBuilder();
        }
        
        public Weaver newWeaver(){
            return new AOPWeaver();
        }
    }

    public static class InvocHandler implements InvocationHandler{

        private Object target;
        private Aspect aspect;

        public InvocHandler(Object target, Aspect aspect) {
            this.target = target;
            this.aspect = aspect;
        }
        
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object returnValue = null;

            if (aspect.beforeAdviceFor(method) != null){
                aspect.beforeAdviceFor(method).run();
            }

            if (aspect.aroundAdviceFor(method) != null){
                aspect.aroundAdviceFor(method).run();
            }
            else{
                try{
                    returnValue = method.invoke(target, args);
                } catch (Exception exc) { 
                    throw exc;
                }
            }
            
            if (aspect.afterAdviceFor(method) != null){
                aspect.afterAdviceFor(method).run();
            }

            return returnValue;
        }
    }
}