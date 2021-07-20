package wetter.common;

import wetter.gui.Settings;
import wetter.gui.SimplifiedWetterGUI;

public class AktualisierenVOWIS extends Thread {
    
    private static int countdown; // countdown in Seconds 
    private static int i=0;
    
    public void run() {
	while (true) {		//endlosschleife
	    countdown = Settings.getUpdateRateVOWISms()/1000;    // countdown in Seconds
	    System.out.println("VOWIS download counter: "+i);
	    if (AktualisierenDWD.isNight() == false) { //stunde>=6 && stunde <22){
		countdown = Settings.getUpdateRateVOWISms()/1000;    // countdown in Seconds
		try {
		    SimplifiedWetterGUI.setDisplayVOWIS();
		    i++;
		} catch (Exception e1) {} 
		while (countdown >0) {  
		    try {
			Thread.sleep(1000); // Zählen in Sekunden Schritten
		    } catch (InterruptedException e) {}
		    countdown--;
		} 
	    } 
//		--- Unterbrechen des automatischen downloads bei Nacht.(zw. 22Uhr und 6Uhr)
//	    kein VOWIS Zugriff um Datenrate zu sparen!!!
	    else {
//		AktualisierenDWD.isNight() == true); //(zw. 22Uhr und 6Uhr)	
		countdown = 3600; // = 1h       		
		while (countdown >0) {  
		    try {
			Thread.sleep(1000); // Zählen in Sekunden Schritten
		    } catch (InterruptedException e) {}
		    countdown--;
		} 
	    }
	}
    }
      public static void setVOWISReload() {
		countdown =0;
	}
}


