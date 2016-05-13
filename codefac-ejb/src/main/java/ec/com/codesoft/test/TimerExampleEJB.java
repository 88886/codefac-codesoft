/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.test;

import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author carlo
 */
@Stateless
public class TimerExampleEJB 
{
    
    private final String tiempo="*/1";
    
    private final Logger log = Logger
            .getLogger(TimerExampleEJB.class.getName());

    
    @Schedule(minute = tiempo, hour = "*")
    public void runEveryMinute() 
    {
        System.out.println("imprimiendo...");
    }
}
