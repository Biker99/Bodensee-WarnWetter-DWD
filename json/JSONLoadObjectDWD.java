
package wetter.json;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import wetter.gui.Settings;
import wetter.gui.SimplifiedWetterGUI;


public class JSONLoadObjectDWD{ 
  
    private String JSONP;
    private String arrayAsString;
    private Scanner stream;
    private InputStream IOStream;


    ///////////////////////////////
    // constructors
    ///////////////////////////////

    public JSONLoadObjectDWD(URL URLPath) {
	try {
	    if (Settings.getChckbxOnlineUpdateDWD()== "true") {
		try {
		    IOStream = URLPath.openStream(); 		   
	     	}catch (IOException e) {
	     	    System.out.println("loadFromURL - IOE Lost Internet connection!"); 
	     	    SimplifiedWetterGUI.setInternetFaultLabel(true);
	     	    IOStream = URLPath.openStream();
	     	    SimplifiedWetterGUI.setInternetFaultLabel(false); 
	     	    System.out.println("loadFromURL - IOE passt wieder!"); 
	        }
		stream = new Scanner(IOStream);
		JSONP = stream.nextLine();
		stream.close();		
		arrayAsString = reduce(JSONP);		
	    };
	    SimplifiedWetterGUI.setInternetFaultLabel(false);
	}catch (Exception e) {
     	    System.out.println("loadFromURL - Lost Internet connection!"); 
     	    SimplifiedWetterGUI.setInternetFaultLabel(true);
     	    arrayAsString = loadMasterstring(); 
        }
    }
    
    public JSONLoadObjectDWD() throws IOException{
 	    arrayAsString = loadMasterstring(); 
    }
    

 
    
     
    ///////////////////////////////
    // methods
    ///////////////////////////////
        
    private String loadMasterstring() {
	String ms;
	System.out.println("loadMasterstring - Kein Zugriff auf gespeicherte Daten");
	ms = "{\"time\":1604651126000,\"warnings\":"
		+ "{\"208440000\":[{"
		+ "\"regionName\":\"Bodensee - Ost\","
		+ "\"end\":1608595113176,"
		+ "\"start\":1608595113176,"
		+ "\"type\":4,"
		+ "\"state\":\"BW\","
		+ "\"level\":2,"
		+ "\"description\":\"---\","
		+ "\"event\":\"STARKWIND\","
		+ "\"headline\":\"Amtliche WARNUNG vor STARKWIND\","
		+ "\"instruction\":\"\","
		+ "\"stateShort\":\"BW\","
		+ "\"altitudeStart\":null,"
		+ "\"altitudeEnd\":null}],"
		+ "\"208438000\":[{"
		+ "\"regionName\":\"Bodensee - Mitte\","
		+ "\"end\":1608597113176,"
		+ "\"start\":1608596113176,"
		+ "\"type\":4,"
		+ "\"state\":\"BW\","
		+ "\"level\":2,"
		+ "\"description\":\"---\","
		+ "\"event\":\"STURM\","
		+ "\"headline\":\"Amtliche WARNUNG vor STURM\","
		+ "\"instruction\":\"\","
		+ "\"stateShort\":\"BW\","
		+ "\"altitudeStart\":null,"
		+ "\"altitudeEnd\":null"
		+ "}]},\"vorabInformation\":{},\"copyright\":\"Copyright Deutscher Wetterdienst\"}";	
	return ms;
    }
    
    
    private String reduce(String JSONP) {
	StringBuilder arrayAsStringBuilder = new StringBuilder(JSONP);
	// Removing the first characters of a string: "WarnWetter.loadWarnings("
	arrayAsStringBuilder.delete(0,24);     	  
	// Removing the last two character of a string: ");"
	int stringlength = (arrayAsStringBuilder.length()); 
	arrayAsStringBuilder.delete(stringlength-2,stringlength);
	return arrayAsStringBuilder.toString();
    }
  
    ///////////////////////////////
    // getter
    ///////////////////////////////
       
    public JSONObject getJSObj() {
	if (Settings.getChckbxOnlineUpdateDWD() == "false") {
	    System.out.println("per settings selected: load DWD default Masterstring");
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
