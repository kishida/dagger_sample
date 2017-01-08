/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.inject.Qualifier;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientTracer;
import com.github.kristofa.brave.ServerTracer;
import com.github.kristofa.brave.SpanId;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import dagger.producers.ProducerModule;
import dagger.producers.Produces;
import dagger.producers.ProductionComponent;
import zipkin.reporter.Reporter;

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

    SpanId spanId;
    Map<String, Brave> braves = new HashMap<>();

    AsyncPiko(Reporter reporter, SpanId spanId) {
        this.spanId = spanId;
        Stream.of("pen", "apple", "applepen", "penPineapple", "penPineappleApplePen")
                .forEach(name -> braves.put(name, 
                                            new Brave.Builder(name + " task")
                                                     .reporter(reporter).build()));
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

            Brave brave = braves.get(name);//new Brave.Builder(name + " task").reporter(reporter).build();
            ServerTracer serverTracer = brave.serverTracer();
            serverTracer.setStateCurrentTrace(spanId, "ppap span");
            //serverTracer.setServerReceived();
            ClientTracer clientTracer = brave.clientTracer();
            clientTracer.startNewSpan(name);
            clientTracer.setClientSent();
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            }
            T result = supplier.get();
            clientTracer.setClientReceived();
            //serverTracer.setServerSend();
            System.out.println("finish " + name);

            return result;
        });
        task.run();
        return task;
    }
}
