package wetter.json;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import wetter.gui.Settings;



public class JSON_DecodeDWD {

    private static String regionCode = "208440000"; // =null;
    private String JSONString = "{\"208440000\":[{\"regionName\":\"Bodensee-Ost\"}]}";
    private String returnmessage = null;
//    private String defaultArray = "{\"regionName\":\"Bodensee-Ost\"}";

    private JSONObject jobjx = new JSONObject();
    
    
    private JSONArray jarx = new JSONArray();
    private int messageSize;
    private Object[] warnRegionsArray;
    private boolean warningAvailable = true;
    private int numberOfMessagesPerWarning;
    
    ///////////////////////////////
    // constructors
    ///////////////////////////////
	    
	//Konstruktor ohne übergeben Werte. Leerer String und Default Regional Code vorbelegt
	public JSON_DecodeDWD(){
//	    JSONString =  "{\"208440000\":[{\"regionName\":\"Bodensee-Ost\"}]}"; 
//	    regionCode = Settings.getDefaultRegionalCode(); //Bodensee - Ost
	//    System.out.println("JSON Object und Regionalcode fehlt. Default Regional Code gewählt");
	}
	
	// Konstruktor mit JSON als string. Regionalcode fehlt. Default Regional Code vorbelegt
	public JSON_DecodeDWD(String js){
	    JSONString = js;
	    regionCode = Settings.getDefaultRegionalCode();
	//    System.out.println("Regionalcode fehlt. Default Regional Code gewählt");
	}
	
	// Konstruktor mit JSON und regionalcode als string
	public JSON_DecodeDWD(String js, String rc) {
	    JSONString = js;
	    regionCode = rc;

	}
	

	    ///////////////////////////////
	    // methods
	    ///////////////////////////////
	
	
	public String decode(String arguments, int requestedNumberOfMessage) throws ParseException{
	    String messageForSelectedRegion;
	    Object warnung;
	    decode();
	    JSONParser parser = new JSONParser();
	    if (isWarningAvailable() && requestedNumberOfMessage<getNumberOfMessagesPerWarning()) {
		messageForSelectedRegion = getJSONArray().get(requestedNumberOfMessage).toString();
//		System.out.println("messageForSelectedRegion: "+messageForSelectedRegion);
		jobjx = (JSONObject)parser.parse(messageForSelectedRegion);				// JSON object aus reduziertem Regional string  
		if (arguments == "start_raw") {
		    warnung = jobjx.get("start");
		    if (warnung == null)  returnmessage = (String) "null";
		    else returnmessage = warnung.toString();		
		}else {
		    // für alles andere und Rückmeldung der Startzeit als formatierte Zeit!
		    warnung = jobjx.get(arguments);
		    if (warnung == null) returnmessage = (String) "null";
		    else if (arguments == "start" || arguments == "end") {
			long time = (long) warnung;   
			ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm");
			returnmessage = (String) Instant.ofEpochMilli(time).atZone(europeBerlin).format(formatter)+" Uhr";
		    }else if (arguments == "altitudeStart" ||arguments == "altitudeEnd") {
			returnmessage = warnung.toString()+" m";
		    }else returnmessage = warnung.toString();
		}
	    } else returnmessage = (String) "null";	
	    return returnmessage;
	}
	
	public void decode() throws ParseException{
	    Object[] a1  ;
	    JSONParser parser = new JSONParser();
	    JSONObject jObj1 = (JSONObject)parser.parse(JSONString);	// JSON object aus gesamten Warnstring	
	    messageSize = jObj1.size();					// gesamte Anzahl der Warnungen im String
	    warnRegionsArray = jObj1.keySet().toArray();		// Auflistung der Warnregionen als Array
	    Object s2 = jObj1.get(regionCode);				// decoded, Auswertung des gesamten Warnstrings nach Region 
	    if (s2==null) {						// Selektor: gibt eine Warnmeldung oder ist der returnwert für die Region "null"
		warningAvailable = false;				// es gibt keine Warnung für die Region
		numberOfMessagesPerWarning =0;				// Anzahl der Warnungen innerhalb der Region is 0
	    }
	    else {
		warningAvailable = true;				// Es gibt Warnungen in der gewählten Region
		jarx = (JSONArray)parser.parse(s2.toString());		// JSONArray aus decoded regional string
		a1 = jarx.toArray();					// JSONArray als String
		numberOfMessagesPerWarning = a1.length;			// Anzahl der Warnungen für die gewählte Region
	    }
	}
	
	    ///////////////////////////////
	    // getter
	    ///////////////////////////////
	
	public int getMessageSize() {
	    return messageSize;
	}
	
	public Object[] getWarnRegionsArray() {	    
	    return warnRegionsArray;
	}
	
	public boolean isWarningAvailable() {
	    return warningAvailable;
	}
	
	public int getNumberOfMessagesPerWarning() {
	    return numberOfMessagesPerWarning;
	}
	
	private JSONArray getJSONArray() {
	    return jarx;
	}
		

}
