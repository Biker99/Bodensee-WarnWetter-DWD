package wetter.common;

//@SuppressWarnings("unused")
public class Warnung { 
    private String code;
    private String number;
    private String regionName;
//    private String description;
//    private String event;
//    private String end;
//    private String start;
//    private String type;
//    private String state;
//    private String level;
//    private String headline;
//    private String instruction;
    private String stateShort;
//    private String altitudeStart;
//    private String altitudeEnd;
    
    	////////////////////////////////////////////
    	// Constructors
    	//////////////////////////////////////////
    
//    public Warnung() {
//	code = "208440000";
//	number = "0";
//	regionName = "Bodensee - Ost";
////	description = "---";
////	event = "---";
////	end = "---";
////	start = "---";
////	type = "---";
////	state = "---";
////	level = "---";
////	headline = "---";
////	instruction = "---";
//	stateShort = "---";
////	altitudeStart = "---";
////	altitudeEnd = "---";
//    }
    
    public Warnung(
	    String xcode
	    ,int xnumber
	    ,String xregionName
//	    ,String xdescription 
//	    ,String xevent 
//	    ,String xend
//	    ,String xstart
//	    ,String xtype 
//	    ,String xstate 
//	    ,String xlevel 
//	    ,String xheadline 
//	    ,String xinstruction 
	    ,String xstateShort 
//	    ,String xaltitudeStart
//	    ,String xaltitudeEnd 
	    ) { 
	code = xcode;
	number = ""+xnumber;
	regionName = xregionName;
//	description = xdescription;
//	event = xevent;
//	end = xend;
//	start = xstart;
//	type = xtype;
//	state = xstate;
//	level = xlevel;
//	headline = xheadline;
//	instruction = xinstruction;
	stateShort = xstateShort;
//	altitudeStart = xaltitudeStart;
//	altitudeEnd = xaltitudeEnd;
    }
    
    ////////////////////
    // Getter / Setter
    ////////////////////
    
    
    public String getSelectedRegionName() {
	return "gewählte Region: "+regionName+ " in "+stateShort+" ("+number+")";
    } 
    
    public String getDisplayMessage() {
	return code +" ("+number+") - " +stateShort+ " / " +regionName; //+ " - " +event ;
    }
    
}
