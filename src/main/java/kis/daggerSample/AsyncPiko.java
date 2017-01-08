/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Supplier;

import javax.inject.Qualifier;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import dagger.producers.ProducerModule;
import dagger.producers.Produces;
import dagger.producers.ProductionComponent;

/**
 *
 * @author naoki
 */
@ProducerModule
public class AsyncPiko {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface PenPineappleApplePen{}
    
    @ProductionComponent(modules = {AsyncPiko.class, ExecutorModule.class})
    interface Taro {
        ListenableFuture<ApplePen> applePen();
        @PenPineappleApplePen
        ListenableFuture<String> ppap();
    }

    @Produces
    public ListenableFuture<Pen> iHaveAPen() {
        return task("pen", Pen::new, 100);
    }
    
    @Produces
    public ListenableFuture<Apple> iHaveAnApple() {
        return task("apple", Apple::new, 200);
    }
    
    @Produces
    public ListenableFuture<ApplePen> applePen(Pen pen, Apple apple) {
        return task("applepen", () -> new ApplePen(apple, pen), 100);
    }
    
    @Produces
    public ListenableFuture<String> penPineapple(Pen pen) {
        return task("penPineapple", () -> pen + "パイナッポー", 200);
    }
    
    @Produces
    @PenPineappleApplePen
    public ListenableFuture<String> penPineappleApplePen(ApplePen applePen, String penPineapple) {
        return task("penPineappleApplePen", () -> penPineapple + applePen, 50);
    }
    
    private <T> ListenableFuture<T> task(String name, Supplier<T> supplier, long millis) {
        ListenableFutureTask<T> task = ListenableFutureTask.create(() -> {
            System.out.println("start " + name);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            }
            T result = supplier.get();
            System.out.println("finish " + name);

            return result;
        });
        task.run();
        return task;
    }
}
