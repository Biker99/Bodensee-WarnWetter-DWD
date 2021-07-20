package wetter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import wetter.common.AktualisierenDWD;
import wetter.common.AktualisierenVOWIS;
import wetter.common.LocalTime;
import wetter.common.VOWIS;
import wetter.common.Warnung;
import wetter.json.JSONLoadObjectDWD;
import wetter.json.JSON_DecodeDWD;

import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SimplifiedWetterGUI extends JFrame {
    private static final long serialVersionUID = -8211865409113346472L;
//    components
    
    private static JMenuItem mntmSettings;
    private static JMenuItem mntmAbout;
    private static JMenuItem mntmBodenseeWarnings;
    private static JMenuItem mntmHTML;   
    private static Settings settings= new Settings();
    private static About about = new About();
    private static CreateHTML create_html = new CreateHTML();
  
//  Pfadangaben aus Settings laden
    private static String pathSettings = Settings.getPathSettings();  //Pfad und Name der HTML Datei
    private static File fileNameSettings = Settings.getNameSettings();//Pfad und Name der HTML Datei
    private static URL urlDWD ; //Link zum DWD
    
//    DWD decode
    private static Instant warnTimeUTC;
    private static String warnTimeFormatted ="0";
    private static String localTimeFormatted ="0";
    private static String copyright ="";
    
    private static final String regionName = "regionName";
    private static final String description = "description";
    private static final String event = "event";
    private static final String end = "end"; 
    private static final String start = "start";
    private static final String type = "type";
    private static final String state = "state";
    private static final String level = "level";
    private static final String headline = "headline";
    private static final String instruction = "headline";
    private static final String stateShort = "stateShort";
    private static final String altitudeStart = "altitudeStart";
    private static final String altitudeEnd =   "altitudeEnd";

    private static final String regionCode1 = "208440000"; //Bodensee - Ost
    private static final String regionCode2 = "208438000"; //Bodensee - Mitte
    private static final String regionCode3 = "208439000"; //Bodensee - West
    private static int sturmwarnung_Ost;
    private static int sturmwarnung_Mitte;
    private static int sturmwarnung_West;
    
    private static JSON_DecodeDWD warnings; 
    private static String string_warnings;

    private static StringBuilder messageBodenseeOst =   new StringBuilder();
    private static StringBuilder messageBodenseeMitte  = new StringBuilder();
    private static StringBuilder messageBodenseeWest =  new StringBuilder();
    private static StringBuilder xmessage = new StringBuilder();
    
//    private static long maxDifferenceSeconds ;
  	
    

    private static JPanel contentPane = new JPanel();
    private static JPanel panelBodensee = new JPanel();;  
    private static JPanel panelBodenseeWarnung = new JPanel();
    private static JPanel panelBodenseeBild = new JPanel();
    private static JPanel panelDWDWarnungen = new JPanel();
    
    private static JTextPane tpWarnungOst = new JTextPane();
    private static JTextPane tpWarnungMitte = new JTextPane();
    private static JTextPane tpWarnungWest = new JTextPane();
    private static JButton btnRefreshJSON  = new JButton("Aktualisieren (data re-load)");;
    private static JButton btnAllDWD_Reload = new JButton("All DWD - Aktualisieren (re-load vom DWD)");
    private static JButton btnExit;
    private static int downloadCounterDWD = 0;
    private static int fehlerCounter = 0;
    private static JLabel lblCopyright  = new JLabel("lblCopyright");
    private static JLabel lblUpdateCounter = new JLabel("lblUpdateCounter");
    private static JLabel lblFehlerCounter= new JLabel("lblFehlerCounter");
    private static JLabel lblWarntime = new JLabel("lblWarntime");
    private static JLabel lbl2Warntime = new JLabel("lbl2Warntime");
    private static JLabel lblInternetFault = new JLabel("Internet Fault");
    private static JLabel lblLocalTime1 = new JLabel("lblLocalTime1");
    private static JLabel lblLocalTime2 = new JLabel("lblLocalTime2");
    private static JLabel lblBodenseeBild = new JLabel();
    private static boolean internetFault = false;
    public static JLabel lblCountDown = new JLabel("lblCountDown");
    public static JProgressBar progressBar = new JProgressBar();
    private static JTextField tfRegionalCode;
    private static ButtonGroup buttonGroupRegion = new ButtonGroup();
    private static JRadioButton rdbtnOberAllgaeu;
    private static JRadioButton rdbtnKonstanz;
    private static JRadioButton rdbtnBodenseekreis;
    private static JRadioButton rdbtnRavensburg;
    private static JRadioButton rdbtnLindau;
    private static JRadioButton rdbtnBodenseeWest;
    private static JRadioButton rdbtnBodenseeMitte;
    private static JRadioButton rdbtnBodenseeOst;
    private static JTextPane tfWarnungSelectedRegion;
    
  //component models
    
    @SuppressWarnings("rawtypes")
    private static DefaultListModel warnListModel = new DefaultListModel();

    private static  Object[] regionCodeList;

    private static JList<String> lstWarnList;
    private static JScrollPane scrollPaneList;
    private static JMenuItem mntmAllWarnings;
    private static JLabel lblRegionalCode = new JLabel("gew\u00E4hlter Regional-Code");
    private static JLabel lblWarningSelectedRegion = new JLabel("Warnung im ausgew\u00E4hlten Gebiet");
    private static JLabel lblListHeadline = new JLabel("Es gibt 0 Warnungen in folgenden Regionen:");
    
    private static StringBuilder warnBildSeletor = new StringBuilder();
    private static JPanel panelVOWISSeedaten;
    private static JTextPane tpVOWISSeedaten;



	///////////////////////////////////////////////////////
	// Launch the application.
	////////////////////////////////////////////////////// 
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		setInternetFaultLabel(false);
		try {
		    SimplifiedWetterGUI frame = new SimplifiedWetterGUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null,"main-run - Fehler \n"+e.getMessage());
		}
		AktualisierenDWD aktualisierenDWD = new AktualisierenDWD();	//thread zum automatischen update und countdown anzeigen
		aktualisierenDWD.start();
		
	        LocalTime localTime = new LocalTime();			//thread zur Uhrzeit
	        localTime.start();
	        
		AktualisierenVOWIS aktualisierenVOWIS = new AktualisierenVOWIS();	//thread zum automatischen update und countdown anzeigen
		aktualisierenVOWIS.start();
	    }
	});
    }

	///////////////////////////////////////////////////////
	// Create the frame.
	////////////////////////////////////////////////////// 
    
    public SimplifiedWetterGUI() throws Exception {
	//  Einstellungen aus dem Init file in "Settings" laden
	Settings.setArguments(pathSettings, fileNameSettings); //Pfad und Name der HTML Datei
	//  Werte aus "Settings" laden und als Variable speichern.
	urlDWD = Settings.getURLDWD();	
        AktualisierenDWD.setDWDReload();	//re-set des counter, neues Laden vom DWD
        GUIHandling();
    }


	///////////////////////////////////////////////////////
	// This method is used to load and store DWD Data. Output is a JSON Object
	//////////////////////////////////////////////////////
	

    public static JSONObject loadDWDData() throws ParseException, IOException {
	JSONObject jo = new JSONObject();
	JSONLoadObjectDWD array;
	array = new JSONLoadObjectDWD(urlDWD);		//Laden der Daten vom DWD und als Array speichern
	jo = array.getJSObj();
	downloadCounterDWD++;
//	System.out.println("Download Counter: "+downloadCounterDWD);
	lblUpdateCounter.setText("Verbindungen zum DWD: "+downloadCounterDWD);
	lblFehlerCounter.setText("korrigierte Fehler: "+fehlerCounter);
	while (jo==null) {
	    try {
		fehlerCounter++;
		System.out.println("loadDWDData() - JO ist NULL!");
		Thread.sleep(5000);	//neuer versuch nach 5 Sekunden
	    } catch (InterruptedException e) {System.out.println("loadDWDData() - InterruptedException");}
	    array = new JSONLoadObjectDWD(urlDWD);;
	    jo = array.getJSObj();
	    System.out.println("Fehler Counter: "+fehlerCounter);
	}
	return jo;
    }
    

    
       
	///////////////////////////////////////////////////////
	// This method is used to process DWD JSON Date
	//////////////////////////////////////////////////////
    

    public static void processJSON(JSONObject jo) throws UnsupportedEncodingException {	
	try {	
// 	getting time 
	    ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	    long time = (long) jo.get("time"); 
	    localTimeFormatted = localTimeUTC();
	    warnTimeUTC = Instant.ofEpochMilli(time);
	    warnTimeFormatted = warnTimeUTC.atZone(europeBerlin).format(formatter);
	    lblWarntime.setText("Letztes Update der Daten beim DWD: "+warnTimeFormatted+" Uhr");

// 	getting copyright 
	    copyright = (String) jo.get("copyright"); 
	    lblCopyright.setText("Wetterwarnung: "+copyright);
       
// 	getting warn message 
	    string_warnings = jo.get("warnings").toString(); 
		
// 	Warnmeldung des DWD für den Bodensee Decodieren
	    sturmwarnung_Ost = bodenseeDecode(string_warnings,regionCode1);
	    messageBodenseeOst.delete(0, messageBodenseeOst.length());
	    messageBodenseeOst.append("Bodensee - Ost:   "+xmessage);
	    tpWarnungOst.setText(messageBodenseeOst.toString());
	    switch (sturmwarnung_Ost) {
		case 40: tpWarnungOst.setBackground(Color.ORANGE); break;
		case 90: tpWarnungOst.setBackground(Color.red); break;
		default: tpWarnungOst.setBackground(SystemColor.menu);
	    }
     
	    sturmwarnung_Mitte =bodenseeDecode(string_warnings,regionCode2);
	    messageBodenseeMitte.delete(0, messageBodenseeMitte.length());
	    messageBodenseeMitte.append("Bodensee - Mitte:   "+xmessage);
	    tpWarnungMitte.setText(messageBodenseeMitte.toString());
	    switch (sturmwarnung_Mitte) {
		case 40: tpWarnungMitte.setBackground(Color.ORANGE); break;
		case 90: tpWarnungMitte.setBackground(Color.red); break;
		default: tpWarnungMitte.setBackground(SystemColor.menu);
	    }
        
	    sturmwarnung_West = bodenseeDecode(string_warnings,regionCode3);;
	    messageBodenseeWest.delete(0, messageBodenseeWest.length()+1);
	    messageBodenseeWest.append("Bodensee - West:   "+xmessage); 
	    tpWarnungWest.setText(messageBodenseeWest.toString());
	    switch (sturmwarnung_West) {
		case 40: tpWarnungWest.setBackground(Color.ORANGE); break;
		case 90: tpWarnungWest.setBackground(Color.red); break;
		default: tpWarnungWest.setBackground(SystemColor.menu);   
	    }      
	}catch(ParseException pe2) {
		System.out.println("Parser Fehler");
//		JOptionPane.showMessageDialog(null,"Parser Fehler");
//		JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
		pe2.printStackTrace();
	}
	
    }
    
	/////////////////////////////////////////////////////////////////////////////
	// This method contains all the code for creating and initializing components
	/////////////////////////////////////////////////////////////////////////////

    public static String localTimeUTC() {
	ZoneId europeBerlin = ZoneId.of("Europe/Berlin");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm:ss");
	long localTime = System.currentTimeMillis(); 
	Instant localTimeUTC = Instant.ofEpochMilli(localTime);
	String localTimeFormatted = localTimeUTC.atZone(europeBerlin).format(formatter);
	return localTimeFormatted;
    }

    public static int bodenseeDecode(String string_warnings, String regionCode) throws ParseException {
	int sturmLevel = 0; 
	JSON_DecodeDWD warnings = new JSON_DecodeDWD(string_warnings,regionCode);  
	warnings.decode();
	boolean warnlabel = warnings.isWarningAvailable();
	
//	Warnlabel festlegen, in Abhängigkeit von Startzeit und aktueller Zeit
	String start_raw = "start_raw";
	start_raw = warnings.decode(start_raw,0); 
	
//	Betriebszeiten des Sturmwarnsystems:
//	Von Anfang April bis Ende Oktober von 6.00 bis 22.00 Uhr.
//	In der übrigen Zeit von 7.00 bis 20.00 Uhr.
//	ich habe einfachhiet halber immer von 6-22Uhr gefiltert.
	long xstart = 0;
	long systemTime = System.currentTimeMillis();
	if (start_raw != "null")  xstart = Long.parseLong(start_raw);
	if (xstart>systemTime) warnlabel= false;

	ZoneId europeBerlin = ZoneId.of("Europe/Berlin");  
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm");
	DateTimeFormatter formatterStunde = DateTimeFormatter.ofPattern("HH");
	Instant systemTimeUTC = Instant.ofEpochMilli(systemTime);
	localTimeFormatted = systemTimeUTC.atZone(europeBerlin).format(formatter1);
	int stunde = Integer.parseInt(systemTimeUTC.atZone(europeBerlin).format(formatterStunde));


	xmessage.delete(0, xmessage.length());
	if (warnlabel== true && (stunde<6 ||stunde >=22)) {
	    warnlabel= false;	    
	    xmessage.append("Es ist "+localTimeFormatted+"Uhr,\n --> Sturm, aber ausserhalb der Betriebszeiten des Sturmwarnsystems");
	}else{  
	//    int anzahlWarnungen = warnings.getNumberOfMessagesPerWarning(); 	//wird noch nicht ausgewertt. Ich habe aber beobachtet, dass der Wert größer 1 sein kann!
	    String xevent = warnings.decode(event,0);				//Es wird die erste Warnung im Warnstring ausgegeben (0). War bisher immer die aktuellste...
	    String xstring = xevent.toString().toUpperCase();
	    if (warnlabel) {
		if (xstring.contentEquals("STARKWIND")) {
		    xmessage.append(xstring+" --> Blinkfrequenz: 40 BPM");
		    sturmLevel = 40;
		}else if(xstring.contentEquals("STURM")) {
		    xmessage.append(xstring+" --> Blinkfrequenz: 90 BPM");
		    sturmLevel = 90;
		}
	    }else xmessage.append("\nEs liegen keine Warnungen vom DWD vor.");   
	}
	return sturmLevel;
}

    public static void loadBodenseeBild() {
	panelBodenseeBild.removeAll();
	int meldungOst = sturmwarnung_Ost;
	int meldungMitte = sturmwarnung_Mitte;
	int meldungWest = sturmwarnung_West;
	int wasserTemperatur=15;
	wasserTemperatur= (int)VOWIS.getWT();
	
	StringBuilder bild = new StringBuilder();
	boolean nachtbetrieb = AktualisierenDWD.isNight();
	
	tpWarnungWest.setVisible(!nachtbetrieb);
	tpWarnungMitte.setVisible(!nachtbetrieb);
	tpWarnungOst.setVisible(!nachtbetrieb);
	if (nachtbetrieb) {
	    bild.append("Nachtbetrieb");  
	    System.out.println("Es ist der "+localTimeUTC()+"Uhr, \nausserhalb der Betriebszeiten des Sturmwarnsystems am Bodensee");
	}else {
	    if (getInternetFaultLabel()) { 
		bild.append("IOException"); 
	    }else {
		
		switch(meldungWest)	     {
			case 40: bild.append("40"); break;
			case 90: bild.append("90"); break;
			default: bild.append("00");
		}switch(meldungMitte)	     {
			case 40:bild.append("_40");break;
			case 90:bild.append("_90");break;
			default:bild.append("_00");
		}switch(meldungOst)	     {
			case 40:bild.append("_40");break;
			case 90:bild.append("_90");break;
			default:bild.append("_00");
		}		
	    }
	}
	warnBildSeletor.replace(0, warnBildSeletor.length(), bild.toString());
//	System.out.println("wasserTemperatur: "+wasserTemperatur);
	if (wasserTemperatur<Settings.getWasserTemperaturSchaltschwelle()) {
//	    System.out.println("wasserTemperatur: "+wasserTemperatur);
	    warnBildSeletor.insert(0, "SW_");
	    bild.insert(0, "SW_");
	}
	bild.append("_350.png");
	 
//	System.out.println("warnBildSeletor: "+warnBildSeletor);
//	System.out.println("bild: "+bild);
	
	panelBodenseeBild.add(lblBodenseeBild);
	panelBodenseeBild.setBackground(Color.white); 

	lblBodenseeBild.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));;
	lblBodenseeBild.setIcon(new ImageIcon(SimplifiedWetterGUI.class.getResource("/wetter/ressources/SeeGross/"+bild))); //defaultIcon=file:.../bin/wetter/ressources/SeeGross/
 		
}
    
    private void initMessage(JSON_DecodeDWD message) throws Exception {
	StringBuilder textMessage2 = new StringBuilder();
	String[] selectedArguments = Settings.getArguments();
	int numberOfMessagesPerWarning= message.getNumberOfMessagesPerWarning();
	lblWarningSelectedRegion.setText("Warnmeldung für "+message.decode(regionName,0)+" - "+message.decode(stateShort,0)+" ("+numberOfMessagesPerWarning+" Warnungen)");
	int i=0;
	if (selectedArguments[0]=="true") textMessage2.append("-"+message.decode(regionName,i));
	if (selectedArguments[6]=="true") textMessage2.append(" - State: "+message.decode(state,i));	
	while (i<numberOfMessagesPerWarning) {
	    textMessage2.append("\n -----------\n");
	    if (selectedArguments[2]=="true") textMessage2.append("- Event: "+message.decode(event,i)+"\n");
	    if (selectedArguments[8]=="true") textMessage2.append("- Headline: "+message.decode(headline,i)+"\n");
	    if (selectedArguments[1]=="true") textMessage2.append("- Description: "+message.decode(description,i)+"\n");
	    if (selectedArguments[9]=="true") textMessage2.append("- Instruction: "+message.decode(instruction,i)+"\n");
	    if (selectedArguments[5]=="true") textMessage2.append("- Type: "+message.decode(type,i)+"\n");
	    if (selectedArguments[7]=="true") textMessage2.append("- Level: "+message.decode(level,i)+"\n");
	    if (selectedArguments[4]=="true") textMessage2.append("- Start: "+message.decode(start,i)+"\n");
	    if (selectedArguments[3]=="true") textMessage2.append("- Ende: "+message.decode(end,i)+"\n");
	    if (selectedArguments[10]=="true") textMessage2.append("- State Short: "+message.decode(stateShort,i)+"\n");
	    if (selectedArguments[11]=="true") textMessage2.append("- Altitude Start: "+message.decode(altitudeStart,i)+"\n");
	    if (selectedArguments[12]=="true") textMessage2.append("- Altitude End: "+message.decode(altitudeEnd,i)+"\n");
	    i++;
	    }
	tfWarnungSelectedRegion.setText(textMessage2.toString());
	}
	   
//@SuppressWarnings("unchecked")
    @SuppressWarnings("unchecked")
    public void initList(String string_warnings) throws ParseException {
	lbl2Warntime.setText("Letztes Update der Daten beim DWD: "+warnTimeFormatted);
	if (string_warnings!=null) {	
//		Größe und Inhalt des Warn Arrays
	    JSON_DecodeDWD warning_0 = new JSON_DecodeDWD(string_warnings); 
	    warning_0.decode();
	    int stringlaenge =  warning_0.getMessageSize();
	    int numberOfMessagesPerWarning;
	    regionCodeList = warning_0.getWarnRegionsArray();
	    lblListHeadline.setText("Es gibt "+stringlaenge+" Warnungen in folgenden Regionen:");
	    getWarnListModel().clear();	
	    int i=0;
	    for (i=0; i<stringlaenge; i++) {
		String regCode = regionCodeList[i].toString();       
		JSON_DecodeDWD warnings = new JSON_DecodeDWD(string_warnings,regCode);
		warnings.decode();   
		numberOfMessagesPerWarning = warnings.getNumberOfMessagesPerWarning();
		Warnung element = new Warnung(
			regCode
			,numberOfMessagesPerWarning // missing code, hier muss die laufende Nummer des array rein (mehrere Meldungen für einen Ort)
			,warnings.decode(regionName,0)
//    	    		,warnings.decode(description,0)
//    	    		,warnings.decode(event,0)
//    	    		,warnings.decode(end,0) 
//    	    		,warnings.decode(start,0)
//    	    		,warnings.decode(type,0)
//    	    		,warnings.decode(state,0)
//    	    		,warnings.decode(level,0)
//    	    		,warnings.decode(headline,0)
//    	    		,warnings.decode(instruction,0)
			,warnings.decode(stateShort,0)
//    	   		,warnings.decode(altitudeStart,0)  
//    	    		,warnings.decode(altitudeEnd,0)
			);         
		getWarnListModel().addElement(element);
	    }
	    listRenderer();
	}
    }

//@SuppressWarnings("serial")
//das ist ein kopierter teil ((c) Daniel Grissom), den ich nicht verstehe. 
//Braucht man aber, damit die Liste mit codes und Warnungen (warnListModel) richtig dargestellt wird. 
//Dazu gehört die Klasse:
//wetter.comon.Warnung
    private void listRenderer() {
	lstWarnList.setCellRenderer(new DefaultListCellRenderer() {
		/**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (renderer instanceof JLabel && value instanceof Warnung)
	                    ((JLabel) renderer).setText(((Warnung) value).getDisplayMessage());
	                return renderer;
	        }
	});
    }
    
	///////////////////////////////////////////////////////
	// This block contains all the code for the Menue
	//////////////////////////////////////////////////////

 //   @SuppressWarnings("unchecked")
    @SuppressWarnings("unchecked")
    public void GUIHandling() throws Exception {	
    	setTitle("Bodensee Wettermelder"); 	
    	setIconImage(Toolkit.getDefaultToolkit().getImage(SimplifiedWetterGUI.class.getResource("/wetter/ressources/favicon.png")));
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setBounds(100, 100, 1028, 645);
	
// 	menue bar 
	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);	
// 	menue "Settings"
	JMenu mnSettings = new JMenu("Settings");
	menuBar.add(mnSettings);
//	menue item "Settings"
	mntmSettings = new JMenuItem("Settings");
	mntmSettings.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {	
		    settings.setModal(true);
		    settings.setVisible(true);
		}
	});
	mnSettings.add(mntmSettings);
	
// 	menue "HTML"
	JMenu mnHTML = new JMenu("HTML");
	menuBar.add(mnHTML);
//	menue item "create HTML"	
	mntmHTML = new JMenuItem("create HTML");
	mntmHTML.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    create_html.setVisible(true);
		}
	});
	mnHTML.add(mntmHTML);
	
// 	menue "DWD"
	JMenu mnDWD = new JMenu("DWD");
	menuBar.add(mnDWD);
//	menue item "All DWD Warnings"
	mntmAllWarnings = new JMenuItem("All DWD Warnings");
	mntmAllWarnings.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {	
		    panelDWDWarnungen.setVisible(true);
		    panelDWDWarnungen.setBounds(0, 0, 1012, 539);
		    panelBodensee.setVisible(false);
//		    Aktualisieren.setDWDReload();	//re-set des counter, neues Laden vom DWD
		    try {
			initList(string_warnings);	//initialisieren der Liste mit allen Warnungen
		    } catch (ParseException e1) {
			// TODO Auto-generated catch block
			System.out.println("fehler \"initList(string_warnings)\"");
			e1.printStackTrace();
		    }
		    
// Anzeige der Warnung für den Default regional code
		    String regCode = Settings.getDefaultRegionalCode();	// default regional code aus den Settings
		    tfRegionalCode.setText(regCode);
		    if (tfRegionalCode.getText().equals("")) {
			regCode = "208440000";	//Bodensee - Ost;
			tfRegionalCode.setText(regCode);
			System.out.println("Pre-selected Region Code \"208440000\" (Bodensee - Ost) was used");
		    } 
		    try {
			warnings = new JSON_DecodeDWD(string_warnings,regCode);
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			tfRegionalCode.requestFocus();
			lblWarningSelectedRegion.setText(regCode);
			tfWarnungSelectedRegion.setText("Keine Warnung für "+regCode);
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
//	menue item "Bodensee Warnungen"
	mntmBodenseeWarnings = new JMenuItem("Bodensee Warnungen");
	mntmBodenseeWarnings.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    panelDWDWarnungen.setVisible(false);
		    panelBodensee.setVisible(true);
		    panelBodensee.setBounds(0, 0, 1012, 539);
		}
	});
	
	mnDWD.add(mntmBodenseeWarnings);	
	mnDWD.add(mntmAllWarnings);
	//	menue item "About"	
		mntmAbout = new JMenuItem("About");
		menuBar.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//    about.setModal(true);
			    about.setVisible(true);		    
			}
		});

	
	///////////////////////////////////////////////////////
	// This block contains all the code for the Main Area
	//////////////////////////////////////////////////////	
	
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	lblCopyright.setBounds(10, 550, 969, 23);
	lblCopyright.setFont(new Font("Tahoma", Font.ITALIC, 10));
	lblCopyright.setText("Wetterwarnung: "+copyright);
	
	btnExit = new JButton("EXIT");
	btnExit.setBounds(891, 11, 88, 23);
	btnExit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	});

	contentPane.setLayout(null);
	contentPane.add(btnExit);
	contentPane.add(lblCopyright);
	contentPane.add(panelBodensee);

	contentPane.add(panelDWDWarnungen);
	
	panelBodensee.setBounds(0, 0, 1012, 539);
	panelBodensee.setLayout(null);	
	panelBodensee.add(panelBodenseeWarnung);
	panelBodensee.add(panelBodenseeBild);
	panelBodensee.add(lblUpdateCounter);
	panelBodensee.add(progressBar);
	panelBodensee.add(lblCountDown);
	panelBodensee.add(lblInternetFault);
	panelBodensee.add(lblFehlerCounter);
	panelBodensee.add(lblWarntime);
	
	panelBodenseeWarnung.setBounds(10, 108, 300, 217);
	panelBodenseeWarnung.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Warnung f\u00FCr den Bodensee", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	
	panelBodenseeBild.setBounds(338, 108, 644, 418);
	panelBodenseeBild.setLayout(new BoxLayout(panelBodenseeBild, BoxLayout.X_AXIS));
	panelBodenseeBild.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Bodensee West/Mitte/Ost", TitledBorder.LEADING, TitledBorder.BELOW_BOTTOM, null, new Color(0, 0, 0)));
		
	progressBar.setBounds(10, 36, 300, 14);	
	progressBar.setOpaque(true);
	progressBar.setMinimum(0);
	progressBar.setStringPainted(true);
		
	lblUpdateCounter.setBounds(10, 11, 300, 14);
	lblCountDown.setBounds(10, 61, 300, 14);
	lblFehlerCounter.setBounds(10, 79, 164, 14);
	lblWarntime.setBounds(338, 83, 547, 14);

	lblInternetFault.setBounds(184, 86, 126, 23);
	lblInternetFault.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED, Color.PINK, Color.PINK));
	lblInternetFault.setFont(new Font("Tahoma", Font.BOLD, 12));
	lblInternetFault.setForeground(Color.RED);
		
	panelDWDWarnungen.setVisible(false);
	panelDWDWarnungen.setBounds(0, 0, 1012, 539);	
	panelDWDWarnungen.setLayout(null);
	
	tfRegionalCode = new JTextField();
	tfRegionalCode.setText("");
	tfRegionalCode.setEditable(false);
	tfRegionalCode.setBounds(389, 210, 88, 20);
		
	
	rdbtnBodenseeOst = new JRadioButton("208440000 - \tBodensee - Ost");
	rdbtnBodenseeOst.setBounds(58, 69, 275, 23);
	rdbtnBodenseeOst.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "208440000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Bodensee - Ost");
			    tfWarnungSelectedRegion.setText("Keine Warnung für Bodensee - Ost");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnBodenseeMitte = new JRadioButton("208438000 - \tBodensee - Mitte");
	rdbtnBodenseeMitte.setBounds(58, 89, 275, 23);
	rdbtnBodenseeMitte.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "208438000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Bodensee - Mitte");
				tfWarnungSelectedRegion.setText("Keine Warnung für Bodensee - Mitte");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}		
	});
	
	rdbtnBodenseeWest = new JRadioButton("208439000 - \tBodensee - West");
	rdbtnBodenseeWest.setBounds(58, 109, 275, 23);
	rdbtnBodenseeWest.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "208439000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Bodensee - West");
			    tfWarnungSelectedRegion.setText("Keine Warnung für Bodensee - West");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnLindau = new JRadioButton("109776000 - \tKreis Lindau (Bodensee)");
	rdbtnLindau.setBounds(58, 129, 275, 23);
	rdbtnLindau.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "109776000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Kreis Lindau (Bodensee)");
			    tfWarnungSelectedRegion.setText("Keine Warnung für Kreis Lindau (Bodensee)");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnRavensburg = new JRadioButton("108436000 - \tKreis Ravensburg");
	rdbtnRavensburg.setBounds(58, 149, 275, 23);
	rdbtnRavensburg.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "108436000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Kreis Ravensburg");
			    tfWarnungSelectedRegion.setText("Keine Warnung für Kreis Ravensburg");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnBodenseekreis = new JRadioButton("108435000 - \tBodenseekreis");
	rdbtnBodenseekreis.setBounds(58, 169, 275, 23);
	rdbtnBodenseekreis.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "108435000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Bodenseekreis");
			    tfWarnungSelectedRegion.setText("Keine Warnung für den Bodenseekreis");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnKonstanz = new JRadioButton("108335000 - \tKreis Konstanz");
	rdbtnKonstanz.setBounds(58, 189, 275, 23);
	rdbtnKonstanz.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "108335000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 
		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Konstanz");
			    tfWarnungSelectedRegion.setText("Keine Warnung für die Region Konstanz");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	rdbtnOberAllgaeu = new JRadioButton("109780000 - \tKreis Oberallg\u00E4u");
	rdbtnOberAllgaeu.setBounds(58, 209, 275, 23);
	rdbtnOberAllgaeu.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    String regCode = "109780000";
		    tfRegionalCode.setText(regCode);
		    warnings = new JSON_DecodeDWD(string_warnings,regCode); 

		    try {
			warnings.decode();
			if (warnings.isWarningAvailable()== true)
			    initMessage(warnings);
			else {
			    tfRegionalCode.requestFocus();
			    lblWarningSelectedRegion.setText("Region: Oberallgäu");
			    tfWarnungSelectedRegion.setText("Keine Warnung für die Region Oberallgäu");
			}
		    }catch (Exception e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});
	
	lblRegionalCode.setBounds(332, 193, 216, 14);
	
	buttonGroupRegion.add(rdbtnBodenseeOst);
	buttonGroupRegion.add(rdbtnBodenseeMitte);
	buttonGroupRegion.add(rdbtnBodenseeWest);
	buttonGroupRegion.add(rdbtnLindau);
	buttonGroupRegion.add(rdbtnRavensburg);
	buttonGroupRegion.add(rdbtnBodenseekreis);
	buttonGroupRegion.add(rdbtnKonstanz);
	buttonGroupRegion.add(rdbtnOberAllgaeu);


	panelDWDWarnungen.add(lblRegionalCode);
	panelDWDWarnungen.add(rdbtnBodenseeOst);
	panelDWDWarnungen.add(rdbtnBodenseeMitte);
	panelDWDWarnungen.add(rdbtnBodenseeWest);
	panelDWDWarnungen.add(rdbtnLindau);
	panelDWDWarnungen.add(rdbtnRavensburg);
	panelDWDWarnungen.add(rdbtnBodenseekreis);
	panelDWDWarnungen.add(rdbtnKonstanz);
	panelDWDWarnungen.add(rdbtnOberAllgaeu);
	panelDWDWarnungen.add(tfRegionalCode);
	

	lblWarningSelectedRegion.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblWarningSelectedRegion.setBounds(10, 239, 538, 14);
	panelDWDWarnungen.add(lblWarningSelectedRegion);
	
	JScrollPane scrollPaneWarnung = new JScrollPane();
	scrollPaneWarnung.setBounds(10, 264, 538, 275);
	panelDWDWarnungen.add(scrollPaneWarnung);
	
	tfWarnungSelectedRegion = new JTextPane();
	tfWarnungSelectedRegion.setText("Keine Warnung f\u00FCr die eingegebene Region");
	tfWarnungSelectedRegion.setEditable(false);
	scrollPaneWarnung.setViewportView(tfWarnungSelectedRegion);
	

	lblListHeadline.setBounds(379, 93, 599, 14);
	panelDWDWarnungen.add(lblListHeadline);
	
	scrollPaneList = new JScrollPane();
	scrollPaneList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPaneList.setBounds(558, 118, 420, 421);
	panelDWDWarnungen.add(scrollPaneList);
	
	lstWarnList = new JList<String>();
	lstWarnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPaneList.setViewportView(lstWarnList);
	lstWarnList.setModel(getWarnListModel());	
	

	btnAllDWD_Reload.setBounds(345, 50, 363, 23);
	panelDWDWarnungen.add(btnAllDWD_Reload);
	lbl2Warntime.setBounds(370, 78, 608, 14);
	
	panelDWDWarnungen.add(lbl2Warntime);
	
	lblLocalTime2.setBounds(344, 11, 534, 14);
	panelDWDWarnungen.add(lblLocalTime2);
	btnAllDWD_Reload.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    try {
			AktualisierenDWD.setDWDReload(); 	//re-set des counter, neues Laden vom DWD
			initList(string_warnings);
		    } catch (ParseException e1) {
			System.out.println("Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler");
//			JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
			e1.printStackTrace();
		    }
		}
	});

	btnRefreshJSON.setBounds(345, 50, 256, 23);
	panelBodensee.add(btnRefreshJSON);
	btnRefreshJSON.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    AktualisierenDWD.setDWDReload(); 	  //re-set des counter, neues Laden vom DWD
		    AktualisierenVOWIS.setVOWISReload();  //re-set des counter, neues Laden vom VOWIS
		}
	});

	lstWarnList.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    buttonGroupRegion.clearSelection();
		    int i=lstWarnList.getSelectedIndex();
			String regCode = (String) regionCodeList[i];
			tfRegionalCode.setText(regCode);
			warnings = new JSON_DecodeDWD(string_warnings,regCode); 
			try {
			    warnings.decode();
			    if (warnings.isWarningAvailable()== true)
				initMessage(warnings);
			    else 
				tfRegionalCode.requestFocus();
			}catch (Exception e1) {
				System.out.println("Parser Fehler");
//				JOptionPane.showMessageDialog(null,"Parser Fehler");
//				JOptionPane.showMessageDialog(null,"Parser Fehler: \n " + e1.getMessage());
				e1.printStackTrace();
			}
//		    }
		}
	});	
	
	GroupLayout gl_panelBodenseeWarnung = new GroupLayout(panelBodenseeWarnung);
	gl_panelBodenseeWarnung.setHorizontalGroup(
		gl_panelBodenseeWarnung.createParallelGroup(Alignment.TRAILING)
			.addGroup(gl_panelBodenseeWarnung.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_panelBodenseeWarnung.createParallelGroup(Alignment.TRAILING)
					.addComponent(tpWarnungWest, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
					.addComponent(tpWarnungOst, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
					.addComponent(tpWarnungMitte, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
				.addContainerGap())
	);
	gl_panelBodenseeWarnung.setVerticalGroup(
		gl_panelBodenseeWarnung.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panelBodenseeWarnung.createSequentialGroup()
				.addComponent(tpWarnungOst, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tpWarnungMitte, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tpWarnungWest, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				.addGap(198))
	);
	panelBodenseeWarnung.setLayout(gl_panelBodenseeWarnung);
	

	lblLocalTime1.setBounds(338, 11, 363, 14);
	panelBodensee.add(lblLocalTime1);
	
	panelVOWISSeedaten = new JPanel();
	panelVOWISSeedaten.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "VOWIS Seedaten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	panelVOWISSeedaten.setBounds(10, 336, 300, 192);
	panelBodensee.add(panelVOWISSeedaten);
	
	tpVOWISSeedaten = new JTextPane();
	GroupLayout gl_panelVOWISSeedaten = new GroupLayout(panelVOWISSeedaten);
	gl_panelVOWISSeedaten.setHorizontalGroup(
		gl_panelVOWISSeedaten.createParallelGroup(Alignment.TRAILING)
			.addGroup(Alignment.LEADING, gl_panelVOWISSeedaten.createSequentialGroup()
				.addContainerGap()
				.addComponent(tpVOWISSeedaten, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
				.addContainerGap())
	);
	gl_panelVOWISSeedaten.setVerticalGroup(
		gl_panelVOWISSeedaten.createParallelGroup(Alignment.LEADING)
			.addComponent(tpVOWISSeedaten, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
	);
	panelVOWISSeedaten.setLayout(gl_panelVOWISSeedaten);
    }


    
  

    //////////////////////////////////////////////
    // getter and setter
    //////////////////////////////////////////////   

    public static String getWarnTime() {
        return warnTimeFormatted;
    }
    
    public static Instant getWarnTimeUTC() {
	return warnTimeUTC;
    }
    
    public static int getSturmwarnung_Ost() {
        return sturmwarnung_Ost;
    }
    
    public static int getSturmwarnung_Mitte() {
        return sturmwarnung_Mitte;
    }

    public static int getSturmwarnung_West() {
        return sturmwarnung_West;
    }

    public static void setlblLocalTime(String zeit) {
	lblLocalTime1.setText("Aktuelle Urzeit: "+zeit);
	lblLocalTime2.setText("Aktuelle Urzeit: "+zeit);
    }
    
    public static void setInternetFaultLabel(boolean selector) {
	if(selector == true) {
	    lblInternetFault.setVisible(true);
	    internetFault=true;
	}else{
	    lblInternetFault.setVisible(false);
	    internetFault=false;
	}
    }
    
    public static boolean getInternetFaultLabel() {
	return internetFault;
    }

    @SuppressWarnings("rawtypes")
    public static DefaultListModel getWarnListModel() {
	return warnListModel;
    }

    @SuppressWarnings("rawtypes")
    public static void setWarnListModel(DefaultListModel warnListModel) {
	SimplifiedWetterGUI.warnListModel = warnListModel;
    }
    
    public static StringBuilder getWarnBildSeletor()    {
	return warnBildSeletor;
    }

    public static void setDisplayVOWIS() throws Exception {
//	///////////////////////////////////////////////////////
//	// This method is used to load and display VOWIS Data. Output is a JSON Object
//    	// Laden der Daten vom vowis.vorarlberg.at und als JSONObject ablegen
//	//////////////////////////////////////////////////////
	tpVOWISSeedaten.setText(VOWIS.displayVOWIS());

    }
}
