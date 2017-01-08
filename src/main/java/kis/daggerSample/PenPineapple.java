/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.daggerSample;

import javax.inject.Inject;

/**
 *
 * @author naoki
 */
public class PenPineapple {
    Pen pen;
    Pineapple pineapple;

    @Inject
    public PenPineapple(Pen pen, Pineapple pineapple) {
        this.pen = pen;
        this.pineapple = pineapple;
    }

    @Override
    public String toString() {
        return "" + pen + pineapple;
    }
    
}
