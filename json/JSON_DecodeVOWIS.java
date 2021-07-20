package wetter.json;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSON_DecodeVOWIS {

//    private static String regionCode = null;
    private static String JSONString;
    private static Object wert, datum;
    private static Instant time;
    
    ///////////////////////////////
    // constructors
    ///////////////////////////////
	    
	
	// Konstruktor mit JSON als string. Regionalcode fehlt. Default Regional Code vorbelegt
	public JSON_DecodeVOWIS(String js){
	    JSONString = js;
	}

    ///////////////////////////////
    // methods
    ///////////////////////////////
	
	public void decodeVOWIS(String arg) throws ParseException{
	    JSONParser parser = new JSONParser();
	    JSONObject jObj1 = (JSONObject)parser.parse(JSONString);	// JSON object aus gesamten Warnstring	
	    Object obj1 = jObj1.get(arg);				// decoded, Auswertung des gesamten Warnstrings nach Region 
	    JSONObject jObj2 = (JSONObject)parser.parse(obj1.toString());	// JSON object aus gesamten Warnstring						// gesamte Anzahl der Warnungen im String
	    wert = jObj2.get("wert");
	    datum = jObj2.get("datum");
	    time = Instant.parse(datum.toString());
	}
	
    ///////////////////////////////
    // getter
    ///////////////////////////////
	
	public String getWertStrg() {
	    return wert.toString();
	}
	
	public String getWert2Strg(String arg) throws ParseException {
	    decodeVOWIS(arg);
	    return wert.toString();
	}
	
	public int getWertInt() {
	    String a=  String.valueOf(wert);
	    int i = (int)Double.parseDouble(a);
	    return i;
	}
	
	public double getWertDouble() {
	    String a=  String.valueOf(wert);
	    double i = Double.parseDouble(a);
	    return i;
	}
	
	public String getDate() {
	    return datum.toString();
	}
	
	public String getTime() {
	    ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	    String timeFormatted = time.atZone(europeBerlin).format(formatter);
	    return timeFormatted;
	}
}
