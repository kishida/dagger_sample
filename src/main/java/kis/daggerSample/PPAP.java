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
public class PPAP {
    public static void main(String[] args) {
        Piko.Taro pikotaro = DaggerPiko_Taro.builder()
                .piko(new Piko())
                .build();
        PenPineappleApplePen ppap = pikotaro.ppap();
        System.out.println(ppap);
    }
}
