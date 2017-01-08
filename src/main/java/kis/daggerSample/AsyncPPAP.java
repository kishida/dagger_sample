/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.ListenableFuture;

/**
 *
 * @author naoki
 */
public class AsyncPPAP {
    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        AsyncPiko.Taro pikotaro = DaggerAsyncPiko_Taro.builder()
                .asyncPiko(new AsyncPiko())
                .build();
        ListenableFuture<String> ppap = pikotaro.ppap();
        System.out.println("start ppap");
        System.out.println(ppap.get());
        System.out.println("finish ppap");

        ExecutorModule.service.shutdown();
    }
}
