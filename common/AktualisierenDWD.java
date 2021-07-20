package wetter.common;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;

import wetter.gui.CreateHTML;
import wetter.gui.SimplifiedWetterGUI;
import wetter.gui.Settings;

public class AktualisierenDWD extends Thread {
    
    private static int updateRate; // updates in Seconds    
    private static int countdown; // countdown in Seconds 
    private static int progressBar;
    private static int stunde, minute;
    private static boolean night = false; 
    private static long systemTime; // = System.currentTimeMillis();
    private static int i=0;
 
    public void run() {
	SimplifiedWetterGUI.setInternetFaultLabel(false);
	ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
	DateTimeFormatter formatterStunde = DateTimeFormatter.ofPattern("HH");
	DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("mm");

	while (true) {		//endlosschleife
	    systemTime = System.currentTimeMillis();
	    Instant systemTimeUTC = Instant.ofEpochMilli(systemTime);
	    stunde = Integer.parseInt(systemTimeUTC.atZone(europeBerlin).format(formatterStunde));
	    minute = Integer.parseInt(systemTimeUTC.atZone(europeBerlin).format(formatterMinute));
 
	    
	    if (night == false) { //stunde>=6 && stunde <22){
		updateRate = Settings.getUpdateRateDWDms()/1000;    
		countdown = updateRate;
		System.out.println("DWD download counter: "+i);
		progressBar = 0;	
		SimplifiedWetterGUI.progressBar.setMaximum(countdown);//in Sekunden
		JSONObject jo = new JSONObject();
		try {
		    jo = SimplifiedWetterGUI.loadDWDData();
		    SimplifiedWetterGUI.processJSON(jo);
		    i++;;
		    SimplifiedWetterGUI.loadBodenseeBild();
		}catch (Exception e) {
		    System.out.println("Aktualisieren - lost internet?  Automatisches HTML-update failed");
		}
		CreateHTML.AutoCreateHTML();  
		while (countdown >0) {     
		    SimplifiedWetterGUI.progressBar.setValue(progressBar);
		    SimplifiedWetterGUI.lblCountDown.setText("countdown: "+countdown+" Sekunden");
		    try {
			Thread.sleep(1000);// Zählen in Sekunden Schritten
		    } catch (InterruptedException e) {e.printStackTrace(); }
		    countdown--;
		    progressBar = updateRate-countdown;
		}
	    }
//	--- Unterbrechen des automatischen downloads bei Nacht.(zw. 22Uhr und 6Uhr)
//	    kein DWD Zugriff um Datenrate zu sparen!!!
	    else {
		night = true;//(zw. 22Uhr und 6Uhr)
		SimplifiedWetterGUI.loadBodenseeBild();
		CreateHTML.AutoCreateHTML();
		updateRate = 3600; // = 1h       
		countdown = updateRate;
		progressBar = 0;		
		SimplifiedWetterGUI.progressBar.setMaximum(countdown/60); //in Minuten
		systemTime = System.currentTimeMillis();
		minute = Integer.parseInt(Instant.ofEpochMilli(systemTime).atZone(europeBerlin).format(formatterMinute));
		countdown=3600-minute*60; //aktuelle Minuten werden vom CountDown abgezogen. damit ist der Wechsel immer zur vollen Stunde.
		progressBar = updateRate-countdown;
		int i0=600; //im Nachtmodus: AutoCreateHTML() alle 10 Minuten
		int i1=i0;
		while (countdown >0) {
		    if (i1<=0) {
			CreateHTML.AutoCreateHTML();  
			i1=i0;
		    }
		    SimplifiedWetterGUI.progressBar.setValue(progressBar/60);				//Anzeige des counter in Minuten Schritten
		    SimplifiedWetterGUI.lblCountDown.setText("countdown: "+countdown/60+" Minuten"); 	//Anzeige des counter in Minuten Schritten
		    try {
			Thread.sleep(1000);// Zählen in  Sekunden Schritten
		    } catch (InterruptedException e) { e.printStackTrace();}	
		    countdown--;
		    progressBar = updateRate-countdown;
		    i1--;
		}	
	    }
	    if (stunde>=6 && stunde <22)  night = false;
	    else   night = true;  //(zw. 22Uhr und 6Uhr)
	}
    }
    
    public static int getCountDown() {
	return countdown; // in Millisekunden
    }
    
    public static boolean isNight() {
	return night; 
    }
    
    public static void setDWDReload() {
	countdown =0;
	
    }
}
