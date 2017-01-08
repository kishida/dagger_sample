/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

/**
 *
 * @author naoki
 */
public class ApplePen {
    Apple apple;
    Pen pen;

    public ApplePen(Apple apple, Pen pen) {
        this.apple = apple;
        this.pen = pen;
    }

    @Override
    public String toString() {
        return "" + apple + pen;
    }
    
}
