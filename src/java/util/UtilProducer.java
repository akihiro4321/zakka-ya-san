/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

/**
 *
 * @author okuya
 */
@Dependent
public class UtilProducer {
    @Produces
    private FileUtil ftool() {
        FileUtil fu = new FileUtil();
        return fu;
    }
}
