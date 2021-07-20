package wetter.common;


import java.net.URL;
import org.json.simple.JSONObject;

import wetter.gui.Settings;
import wetter.json.JSONLoadObjectVOWIS;
import wetter.json.JSON_DecodeVOWIS;


public class VOWIS {

  private static URL urlVOWIS;
  private static JSONObject joVOWIS;
  private static int wGulli, wRasen, wBregenz;
  private static String datum2, datum3, datum4;
  private static String time2, time3, time4;
  private static double lufttemperatur;
  private static double wassertemperatur;
  
  private static final int w0Bregenz = 366; 	//Wasserstand in Bregenz Hafen  am 19.2.2021
  private static final int w0Gulli = 46; 	//Wasserstand über dem Gulli beim LKC am 19.2.
  private static final int w0Rasen = 165; 	//Wasserstand unter der Rasenkanteam beim LKC 19.2.
  

    
	///////////////////////////////////////////////////////
	// This method is used to load and display VOWIS Data. Output is a JSON Object
	// Laden der Daten vom vowis.vorarlberg.at und als JSONObject zurückzugeben
	//////////////////////////////////////////////////////
    
    
    public static String displayVOWIS() throws Exception {
//	urlVOWIS = new URL("https://vowis.vorarlberg.at/api/see");
	urlVOWIS = Settings.getURLVOWIS();
	
	joVOWIS = new JSONLoadObjectVOWIS(urlVOWIS).getJSObj();	//Laden der Daten vom vowis.vorarlberg.at und als JSONObject ablegen

	JSON_DecodeVOWIS message = new JSON_DecodeVOWIS(joVOWIS.toString());
	StringBuilder ausgabeVOWISSeedaten = new StringBuilder();
	
//	String arg1 = "Luftfeuchte";
	String arg2 = "Lufttemperatur";
	String arg3 = "Wasserstand";
	String arg4 = "wTemperatur";
//	String arg5 = "wtMilli05";
//	String arg6 = "wtMilli25";
//	String arg7 = "windgeschwindigkeit";
//	String arg8 = "windrichtung";
//	String arg9 = "windboe";
	
//	message.decodeVOWIS(arg1.toLowerCase());
//	double luftfeuchte = message.getWertDouble();
	
	message.decodeVOWIS(arg2.toLowerCase());
	datum2 = message.getDate(); //Auslesen als String vom JSON file
	datum2=datum2.subSequence(11, 16).toString(); // Abschneiden von Datum und Sekundenangabe
	time2= message.getTime();
	lufttemperatur = message.getWertDouble(); //Auslesen als double vom JSON file
	

	message.decodeVOWIS(arg3.toLowerCase());
	datum3 = message.getDate();//Auslesen als String vom JSON file
	datum3=datum3.subSequence(11, 16).toString();// Abschneiden von Datum und Sekundenangabe
	time3= message.getTime();
	wBregenz = message.getWertInt(); //Auslesen als int vom JSON file
	
	message.decodeVOWIS(arg4);
	datum4 = message.getDate();//Auslesen als String vom JSON file
	datum4=datum4.subSequence(11, 16).toString(); // Abschneiden von Datum und Sekundenangabe
	time4= message.getTime();
	wassertemperatur = message.getWertDouble(); //Auslesen als double vom JSON file
	
//	message.decodeVOWIS(arg5);
//	double wtMilli05 = message.getWertDouble();
//	
//	message.decodeVOWIS(arg6);
//	double wtMilli25 = message.getWertDouble();
//	
//	message.decodeVOWIS(arg7);
//	double windgeschwindigkeit = message.getWertDouble();
//	
//	message.decodeVOWIS(arg8);
//	double windrichtung = message.getWertDouble();
//	
//	message.decodeVOWIS(arg9);
//	double windboe = message.getWertDouble();
	
//	Berechnung Wasserstand über LKC Landmarken
	wGulli = w0Gulli-w0Bregenz+wBregenz; //über dem Gullideckel vor dem LKC Steg
	wRasen = wBregenz-w0Bregenz-w0Rasen; //Unterhalb der LKC Rasenkante
	
//	Erweiterte Ausgabe auf der Konsole
	System.out.println("");
	System.out.println("VOWIS Daten von "+time2+" Uhr");
	System.out.println("--------------------");
	System.out.println("Lufttemperatur von "+time2+" Uhr: "+lufttemperatur+" °C");
//	System.out.println("VOWIS Daten von "+datum2+" Uhr");
	System.out.println("Wassertemperatur von "+time4+" Uhr: "+wassertemperatur+" °C");
//	System.out.println("VOWIS Daten vom "+datum4+" Uhr");
	System.out.println("--------------------");
	System.out.println("VOWIS Daten vom "+time3+" Uhr");
	System.out.println("Pegel Bregenz Hafen: "+wBregenz+" cm");
	System.out.println("Wasserstand über dem Gulli: "+wGulli+" cm");
	System.out.println("Wasserstand unter der Rasenkante: "+wRasen+" cm");
	System.out.println("====================");
//	System.out.println("Luftfeuchte: "+luftfeuchte+" %");	
//	System.out.println("wtMilli05: "+wtMilli05+" °C");	
//	System.out.println("wtMilli25: "+wtMilli25+" °C");
//	System.out.println("windgeschwindigkeit: "+windgeschwindigkeit+" m/s");
//	System.out.println("windrichtung: "+windrichtung+" Grad");
//	System.out.println("windboe: "+windboe+"Grad");
//	System.out.println("====================");
	System.out.println("");	
	
//	Ausgabe auf der Oberfläche
	ausgabeVOWISSeedaten.delete(0, ausgabeVOWISSeedaten.length()); // String leeren
	ausgabeVOWISSeedaten.append("Messdaten vom Pegel Bregenz Hafen\n");
	ausgabeVOWISSeedaten.append("--------------------\n");
	ausgabeVOWISSeedaten.append("Lufttemperatur von "+time2+" Uhr: "+lufttemperatur+" °C\n");
	ausgabeVOWISSeedaten.append("Wassertemperatur von "+time4+" Uhr: "+wassertemperatur+" °C\n");
	ausgabeVOWISSeedaten.append("--------------------\n");
	ausgabeVOWISSeedaten.append("Wasserstand von "+time3+" Uhr:\n");
	ausgabeVOWISSeedaten.append("- Pegel Bregenz Hafen: "+wBregenz+" cm\n");
	ausgabeVOWISSeedaten.append("- LKC, über dem Gulli: "+wGulli+" cm\n");
	ausgabeVOWISSeedaten.append("- LKC, unter der Rasenkante: "+wRasen+" cm\n");

	
	return ausgabeVOWISSeedaten.toString();

}
    public static String getLufttemperatur() {
	return lufttemperatur+" °C"; // entspricht arg2 = "Lufttemperatur";
    }
    public static String getTimeLufttemperatur() {
	return time2; // entspricht arg2 = "date";
    }
    
    public static String getWassertemperatur() {
	return wassertemperatur+" °C"; // entspricht arg4 = "wTemperatur";
    }
    public static double getWT() {
	return wassertemperatur; // entspricht arg4 = "wTemperatur";
    }
    public static String getTimeWassertemperatur() {
	return time4; // entspricht arg4 = "date";
    }
    
    public static String getWasserstandBregenz() {
	return wBregenz+" cm"; // entspricht arg3 = "Wasserstand";
    }
    public static String getTimeWasserstandBregenz() {
	return time3; // entspricht arg3 = "date";
    }
    
    public static String getWasserstandGulli() {
	return wGulli+" cm"; // entspricht berechnetem Wert aus arg3;
    }
    
    public static String getWasserstandRasenkante() {
	return -wRasen+" cm"; // entspricht berechnetem Wert aus arg3;
    }
    
    public static String getTime() {
	return time3; // entspricht arg3 = "date", übersetzt in Berlin...;
    }
    
    

}
