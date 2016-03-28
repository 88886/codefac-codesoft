/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author carlo
 */
public abstract class TiempoUtil {

    /**
     * Devuelve los dias entre 2 fechas
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return
     */
    public static int diferenciaEnDias(Date fechaMayor, Date fechaMenor) {
        long diferencia = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferencia / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    /**
     * Devuelve lass horas entre 2 fechas
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return
     */
    public static int diferenciaEnHoras(Date fechaMayor, Date fechaMenor) {
        long diferencia = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferencia / (1000 * 60 * 60);
        return (int) dias;
    }

    public static int diferenciaEnMinutos(Date fechaMayor, Date fechaMenor) {
        long diferencia = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferencia / (1000 * 60);
        return (int) dias;
    }
    
    

    public static Date diferenciaFechas(Date cal1, Date cal2) {
        // conseguir la representacion de la fecha en milisegundos
        long milis1 = cal1.getTime();
        long milis2 = cal2.getTime();

        // calcular la diferencia en milisengundos
        long diff = milis2 - milis1;

        // calcular la diferencia en segundos
        long diffSeconds = diff / 1000;

        // calcular la diferencia en minutos
        long diffMinutes = diff / (60 * 1000);

        // calcular la diferencia en horas
        long diffHours = diff / (60 * 60 * 1000);

        // calcular la diferencia en dias
        long diffDays = diff / (24 * 60 * 60 * 1000);
        
         // calcular la diferencia en meses
        long diffMonth = diff / (12*24 * 60 * 60 * 1000);

        // calcular la diferencia en años
        long diffYear = diff / (24 * 60 * 60 * 1000);
        
        Calendar FechaDiferencia = new GregorianCalendar();
        
        //FechaDiferencia.set(diffYear,diff, date, hourOfDa,diffMinutes);
        

        Date fechaNueva = new Date(diff);

//        System.out.println("En milisegundos: " + diff + " milisegundos.");
//        System.out.println("En segundos: " + diffSeconds + " segundos.");
//        System.out.println("En minutos: " + diffMinutes + " minutos.");
//        System.out.println("En horas: " + diffHours + " horas.");
//        System.out.println("En dias: " + diffDays + " dias.");
        return fechaNueva;
    }
}
