/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.test;

import ec.com.codesoft.util.backupMB;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    
    //@EJB
    //private backupMB backup;

    
    @Schedule(minute = tiempo, hour = "*")
    public void runEveryMinute() 
    {
        System.out.println("imprimiendo...");
        //backup.backup();
    }
}
