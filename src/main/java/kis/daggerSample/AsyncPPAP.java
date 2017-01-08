/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import java.util.concurrent.ExecutionException;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientTracer;
import com.github.kristofa.brave.SpanId;
import com.google.common.util.concurrent.ListenableFuture;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.urlconnection.URLConnectionSender;

/**
 *
 * @author naoki
 */
public class AsyncPPAP {
    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String dockerEndpoint = "http://126.0.56.103:9411/api/v1/spans";
        URLConnectionSender sender = URLConnectionSender.create(dockerEndpoint);
        try (AsyncReporter<Span> reporter = AsyncReporter.builder(sender).build()) {
            ClientTracer clientTracer = new Brave.Builder("ppap main").reporter(reporter).build().clientTracer();
            SpanId spanId = clientTracer.startNewSpan("async ppap");
            
            AsyncPiko.Taro pikotaro = DaggerAsyncPiko_Taro.builder()
                    .asyncPiko(new AsyncPiko(reporter, spanId))
                    .build();
            ListenableFuture<String> ppap = pikotaro.ppap();
            System.out.println("start ppap");
            clientTracer.setClientSent();
            System.out.println(ppap.get());
            clientTracer.setClientReceived();
            System.out.println("finish ppap");

            ExecutorModule.service.shutdown();
        }
    }
}
