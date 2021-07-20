
package wetter.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import wetter.gui.Settings;
import wetter.gui.SimplifiedWetterGUI;



public class JSONLoadObjectVOWIS{ 
  
    private String JSONP;
    private String arrayAsString;
    private Scanner stream;
    private InputStream IOStream;



    ///////////////////////////////
    // constructors
    ///////////////////////////////

    public JSONLoadObjectVOWIS(URL URLPath) throws IOException{
	try {
	    if (Settings.getChckbxOnlineUpdateVOWIS()== "true") {
		try {
		    IOStream = URLPath.openStream();
	     	}catch (Exception e) {
	     	    System.out.println("loadFromURL - IOE Lost Internet connection!"); 
	     	    SimplifiedWetterGUI.setInternetFaultLabel(true);
	     	    IOStream = URLPath.openStream(); 
	     	    SimplifiedWetterGUI.setInternetFaultLabel(false);
		    System.out.println("loadFromURL - IOE passt wieder!");
	        }
		stream = new Scanner(IOStream).useDelimiter("\\A");
		while (stream.hasNextLine()) {
		    JSONP = stream.nextLine();
		}
		stream.close();
		arrayAsString = reduce(JSONP);
		SimplifiedWetterGUI.setInternetFaultLabel(false);
	    };
     	}catch (Exception e) {
     	    System.out.println("loadFromURL - Lost Internet connection!"); 
     	    SimplifiedWetterGUI.setInternetFaultLabel(true);
     	    arrayAsString = loadMasterstring(); 
        }
    }
    


    public JSONLoadObjectVOWIS() throws IOException{
     	    arrayAsString = loadMasterstring(); 
    }
    
    
    ///////////////////////////////
    // methods
    ///////////////////////////////
    
    private String loadMasterstring() { 
	System.out.println("loadMasterstring - Kein Zugriff auf gespeicherte Daten");
	String ms = "{"
		+ "\"pegelnullpunkt\":392.14,"
		+ "\"pnpGueltigSeit\":\"2007-01-01T00:00:00Z\","
		+ "\"hW2\":460,\"hW10\":512,\"hW20\":531,\"hW30\":540,\"hW50\":553,\"hW100\":568,\"hW2abs\":396.74,\"hW10abs\":397.26,\"hW20abs\":397.45,\"hW30abs\":397.53999999999996,\"hW50abs\":397.66999999999996,"
		+ "\"hW100abs\":397.82,\"wMessungSeit\":\"1855-01-01T00:00:00Z\",\"wtMessungSeit\":\"1895-01-01T00:00:00Z\","
		+ "\"luftfeuchte\":{\"datum\":\"2021-02-13T20:45:00Z\",\"wert\":60.9},"
		+ "\"lufttemperatur\":{\"datum\":\"2021-02-13T20:45:00Z\",\"wert\":-4.8},"
		+ "\"wasserstand\":{\"datum\":\"2021-02-13T21:30:00Z\",\"wert\":380.9},"
		+ "\"wTemperatur\":{\"datum\":\"2021-02-13T21:30:00Z\",\"wert\":3.0},"
		+ "\"wtMilli05\":{\"datum\":\"2021-02-13T21:25:00Z\",\"wert\":4.7},"
		+ "\"wtMilli25\":{\"datum\":\"2021-02-13T21:25:00Z\",\"wert\":4.7},"
		+ "\"windgeschwindigkeit\":{\"datum\":\"2021-02-13T20:45:00Z\",\"wert\":0.8},"
		+ "\"windrichtung\":{\"datum\":\"2021-02-13T20:45:00Z\",\"wert\":170.0},"
		+ "\"windboe\":{\"datum\":\"2021-02-13T20:45:00Z\",\"wert\":145.0},"
		+ "\"hhw\":{"
		+ "\"datum\":\"1890-09-03T00:00:00Z\",\"wert\":581.0},"
		+ "\"betreiber\":\"Hydrographischer Dienst\","
		+ "\"beobachter\":null,"
		+ "\"errichtetAm\":\"0001-01-01T00:00:00Z\","
		+ "\"gewaesser\":null,"
		+ "\"flussgebiet\":null,"
		+ "\"gemeinde\":null,"
		+ "\"rechtswert\":-44116.0,"
		+ "\"hochwert\":263409.0,"
		+ "\"hoehe\":0.0,"
		+ "\"jahrbuchveroeffentlichung\":\"ja\","
		+ "\"datenfernuebertragung\":\"ja\","
		+ "\"flaechennR_1_BIS_8_ORDNG\":\"1-028-000-000-000-00-00-00\","
		+ "\"flaechennR_1_BIS_8_ORDNG_TXT\":\"Bodensee\","
		+ "\"hzbnr\":\"200337\","
		+ "\"name\":\"Bregenz (Hafen), 200337\"}";	
	return ms;
    }
    
    private String reduce(String JSONP) {
	StringBuilder arrayAsStringBuilder = new StringBuilder(JSONP);
	// Removing the first character of a string
	arrayAsStringBuilder.deleteCharAt(0);
	// Removing the last  character of a string
	arrayAsStringBuilder.deleteCharAt(arrayAsStringBuilder.length()-1);
	return arrayAsStringBuilder.toString();
    }
    
  
    ///////////////////////////////
    // getter
    ///////////////////////////////
    

    
    public JSONObject getJSObj() {
	if (Settings.getChckbxOnlineUpdateVOWIS() == "false") {
	    System.out.println("per settings selected: load VOWIS default Masterstring");
	    arrayAsString = loadMasterstring();
	}
	JSONObject jo;
	JSONParser parser = new JSONParser();
	try {
	    jo = (JSONObject) parser.parse(arrayAsString);  
	}catch(Exception p){
	    System.out.println("getJSObj - parserfehler"); 
	    return jo=null;
	}
	return jo;
    }  
}
