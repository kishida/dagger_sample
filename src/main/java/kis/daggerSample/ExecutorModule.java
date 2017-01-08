/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.producers.Production;

/**
 *
 * @author naoki
 */
@Module
public class ExecutorModule {
    static ExecutorService service;
    
    @Provides
    @Production
    static Executor executor() {
        return service == null ? service = Executors.newCachedThreadPool() : service;
    }
}
