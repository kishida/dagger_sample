/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 *
 * @author naoki
 */
@Module
public class Piko {
    
    @Component(modules = Piko.class)
    interface Taro {
        PenPineappleApplePen ppap();
    }
    
    @Provides Pen iHaveAPen() {
        return new Pen();
    }
    @Provides Apple iHaveAnApple() {
        return new Apple();
    }
    
    @Provides ApplePen applePen(Apple apple, Pen pen) {
        return new ApplePen(apple, pen);
    }
}
