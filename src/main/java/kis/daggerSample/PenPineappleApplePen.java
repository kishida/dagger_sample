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
public class PenPineappleApplePen {
    PenPineapple penPineapple;
    ApplePen applePen;

    @Inject
    public PenPineappleApplePen(PenPineapple penPineapple, ApplePen applePen) {
        this.penPineapple = penPineapple;
        this.applePen = applePen;
    }

    @Override
    public String toString() {
        return "" + penPineapple + applePen;
    }
    
}
