package gr.uoa.di.promise;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * > "What I cannot create, I do not understand"
 * > Richard Feynman
 * > https://en.wikipedia.org/wiki/Richard_Feynman
 * 
 * This is an incomplete implementation of the Javascript Promise machinery in Java.
 * You should expand and ultimately complete it according to the following:
 * 
 * (1) You should only use the low-level Java concurrency primitives (like 
 * java.lang.Thread/Runnable, wait/notify, synchronized, volatile, etc)
 * in your implementation. 
 * 
 * (2) The members of the java.util.concurrent package 
 * (such as Executor, Future, CompletableFuture, etc.) cannot be used.
 * 
 * (3) No other library should be used.
 * 
 * (4) Create as many threads as you think appropriate and don't worry about
 * recycling them or implementing a Thread Pool.
 * 
 * (5) I may have missed something from the spec, so please report any issues
 * in the course's e-class.
 * 
 * The Javascript Promise reference is here:
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
 * 
 * A helpful guide to help you understand Promises is available here:
 * https://javascript.info/async
 */

public class Promise<V> {

    public static enum Status {
        PENDING,
        FULFILLED,
        REJECTED
    }

    volatile Status state = Status.PENDING;
    volatile ValueOrError<V> v_e;

    public Promise(PromiseExecutor<V> executor) {
        try {
            executor.execute(Success(), Fail());
        }
        catch (Error e){
            Fail().accept(e);
        }
    }

    private Consumer<V> Success() {
        Consumer<V> success = new Consumer<>() {
            public void accept(V v) {

                if (state != Status.PENDING) 
                    return;

                synchronized(state){
                    v_e = new ValueOrError<>(){
                        public boolean hasError(){
                            return false;
                        }
                        public V value(){
                            return v;
                        }
                        public Throwable error(){
                            return null;
                        }
                    };

                    state = Status.FULFILLED;
                }
            }
        };

        return success;
    }

    private Consumer<Throwable> Fail() {
        Consumer<Throwable> fail = new Consumer<>() {
            public void accept(Throwable e) {

                if (state != Status.PENDING) 
                    return;
                    
                synchronized(state){
                    v_e = new ValueOrError<>(){
                        public boolean hasError(){
                            return true;
                        }
                        public V value(){
                            return null;
                        }
                        public Throwable error(){
                            return e;
                        }
                    };

                    state = Status.REJECTED;
                }
            }
        };

        return fail;
    }

    public <T> Promise<T> then(Function<V, T> onResolve, Consumer<Throwable> onReject) {
        return new Promise<T>( (Consumer<T> resolve, Consumer<Throwable> reject) -> {
            new Thread(() -> {
                while(true){
                    if (state == Status.FULFILLED){
                        T temp = onResolve.apply(v_e.value());
                        resolve.accept(temp);
                        break;
                    }
                    else if (state == Status.REJECTED){
                        onReject.accept(v_e.error());
                        reject.accept(v_e.error());
                        break;
                    }
                }
            }).start();
        });
    }

    public <T> Promise<T> then(Function<V, T> onResolve) {
        return then(onResolve, null);        
    }

    // catch is a reserved word in Java.
    public Promise<?> catchError(Consumer<Throwable> onReject) {
        return then(null, onReject);         
    }

    // finally is a reserved word in Java.
    public Promise<V> andFinally(Consumer<ValueOrError<V>> onSettle) {
        return new Promise<V>((Consumer<V> onResolve, Consumer<Throwable> onReject) -> {
            then((wildcardVal) -> {
                onSettle.accept(v_e);
                return null;
            }, (Throwable error) -> {
                onSettle.accept(v_e);
            });

        });
    }

    public static <T> Promise<T> resolve(T value) {
        return new Promise<T>((Consumer<T> onResolve, Consumer<Throwable> onReject) -> {
            onResolve.accept(value);
        });
    }

    public static Promise<Void> reject(Throwable error) {
        return new Promise<Void>((Consumer<Void> onResolve, Consumer<Throwable> onReject) -> {
            onReject.accept(error);
        });
    }

    public static Promise<ValueOrError<?>> race(List<Promise<?>> promises) {
        return new Promise<ValueOrError<?>>((Consumer<ValueOrError<?>> resolve, Consumer<Throwable> reject) -> {
            for (Promise<?> promise : promises){
                promise.then((wildcardVar) -> {
                    resolve.accept(promise.v_e);
                    return null;
                }, (Throwable error) -> {
                    reject.accept(promise.v_e.error());
                });
            }
        });
    }

    public static Promise<?> any(List<Promise<?>> promises) {
        return new Promise<Object>((Consumer<Object> resolve, Consumer<Throwable> reject) -> {
            List<Throwable> errors = new ArrayList<>();
            for (Promise<?> promise : promises){
                promise.then((wildcardVar) -> {
                    resolve.accept(promise.v_e.value());
                    return null;
                }, (Throwable error) -> {
                    errors.add(promise.v_e.error());
                    if (errors.size() == promises.size())
                        reject.accept(errors.get(0)); //it should reject with an array of all the errors.
                });
            }
        });                                         
    }

    public static Promise<List<?>> all(List<Promise<?>> promises) {
        return new Promise<List<?>>((Consumer<List<?>> resolve, Consumer<Throwable> reject) -> {
            List<Object> values = new ArrayList<>();
            for (Promise<?> promise : promises){
                promise.then((wildcardVar) -> {
                    values.add(promise.v_e.value());
                    if (values.size() == promises.size()){
                        values.clear();
                        for (Promise<?> p : promises){
                            values.add(p.v_e.value());
                        }
                        resolve.accept(values);
                    }
                    return null;
                }, (Throwable error) -> {
                    reject.accept(promise.v_e.error());
                });   
            }
        });
    }

    public static Promise<List<ValueOrError<?>>> allSettled(List<Promise<?>> promises) {
        return new Promise<List<ValueOrError<?>>>((Consumer<List<ValueOrError<?>>> resolve, Consumer<Throwable> reject) -> {
            List<ValueOrError<?>> values_errors = new ArrayList<>();
            for (Promise<?> promise : promises){
                promise.andFinally((vE) -> {
                    values_errors.add(promise.v_e);
                    if (values_errors.size() == promises.size())
                        resolve.accept(values_errors);
                });
            }
        });
    }
}