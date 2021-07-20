package wetter.common;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import wetter.gui.SimplifiedWetterGUI;

public class LocalTime extends Thread {
    ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm");

    public void run() {	
	while (true) {		//endlosschleife
	    long localTime = System.currentTimeMillis(); 
	    Instant localTimeUTC = Instant.ofEpochMilli(localTime);
	    String localTimeFormatted = localTimeUTC.atZone(europeBerlin).format(formatter);
	    SimplifiedWetterGUI.setlblLocalTime(localTimeFormatted);
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
	    }
	}
    }

}
