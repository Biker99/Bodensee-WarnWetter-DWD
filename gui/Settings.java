package wetter.gui;


import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.ComponentOrientation;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;


public class Settings extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private final JPanel panel = new JPanel();
    
    private static JTextField tfPathDWD;
    private static JTextField tfUpdateRateDWD;
    private static JTextField tfPathVOWIS;
    private static JTextField tfUpdateRateVOWIS;
    private static JTextField tfPathSettings;
    private static JTextField tfSettings;
    private static JTextField tfPathHTML;
    private static JTextField tfHTML;
    private static JTextField tfWasserTemperaturSchaltschwelle;
    private static JTextField tfDefaultRegionalCode;
    
    private static JButton btnDefault;
    private static JButton btnClose;
    private static JButton btnReLoad;
    private static JButton btnSave;
    
    private static JCheckBox chckbxRegionName;
    private static JCheckBox chckbxDescription;
    private static JCheckBox chckbxEvent;
    private static JCheckBox chckbxEnd;
    private static JCheckBox chckbxStart;
    private static JCheckBox chckbxType;
    private static JCheckBox chckbxState;
    private static JCheckBox chckbxLevel;
    private static JCheckBox chckbxHeadline;
    private static JCheckBox chckbxInstruction;
    private static JCheckBox chckbxStateShort;
    private static JCheckBox chckbxAltitudeStart;
    private static JCheckBox chckbxAltitudeEnd;
    private static JCheckBox chckbxOnlineUpdateDWD;
    private static JCheckBox chckbxOnlineUpdateVOWIS;
    private static JCheckBox chckbxDefaultJsonDwd;
    private static JCheckBox chckbxDefaultJsonVowis;

    private static String[] argumente = new String [21]; 
    
    private final ButtonGroup buttonGroupDWD = new ButtonGroup();
    private final ButtonGroup buttonGroupVOWIS = new ButtonGroup();

     
//    @SuppressWarnings("unused")
//    private static Settings dialog = new Settings();

    public Settings() {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setModal(true);
	
    	setTitle("Bodensee Wettermelder - Settings");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/wetter/ressources/favicon.png")));
	setBounds(100, 100, 846, 651);
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	
	JLabel lblSettings = new JLabel("Settings");
	lblSettings.setFont(new Font("Tahoma", Font.BOLD, 12));
	

	JLabel lblPathDWD = new JLabel("Internet Adresse des DWD JSON file");	
	tfPathDWD = new JTextField();
	tfPathDWD.setEditable(false);
//	tfPathDWD.setText("https://www.fewo-baiker.de/warningsxx.json");
	tfPathDWD.setText("https://www.dwd.de/DWD/warnungen/warnapp/json/warnings.json");
	tfPathDWD.setColumns(10);
	
	JLabel lblUpdateRate = new JLabel("download rate - DWD: ");	
	tfUpdateRateDWD = new JTextField();
	tfUpdateRateDWD.setText("600");
	tfUpdateRateDWD.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	tfUpdateRateDWD.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfUpdateRateDWD.setBackground(Color.WHITE);
	tfUpdateRateDWD.setColumns(10);
	JLabel lblUnitSekunden_DWD = new JLabel("Sekunden (UpdateRate DWD: 600 Sek.)");
	
	chckbxOnlineUpdateDWD = new JCheckBox("soll das JSON File aktuell sein? (online update)");
	chckbxOnlineUpdateDWD.setSelected(true);
	chckbxDefaultJsonDwd = new JCheckBox("DefaultJSON");
	buttonGroupDWD.add(chckbxOnlineUpdateDWD);
	buttonGroupDWD.add(chckbxDefaultJsonDwd);
	
	JLabel lblPathVOWIS = new JLabel("Internet Adresse des VOWIS JSON file");	
	tfPathVOWIS = new JTextField();
	tfPathVOWIS.setEditable(false);
	tfPathVOWIS.setText("https://vowis.vorarlberg.at/api/see");
	tfPathVOWIS.setColumns(10);
	
	JLabel lblUpdateRateVOWIS = new JLabel("download rate - VOWIS");
	
	tfUpdateRateVOWIS = new JTextField();
	tfUpdateRateVOWIS.setText("3600");
	tfUpdateRateVOWIS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	tfUpdateRateVOWIS.setColumns(10);
	tfUpdateRateVOWIS.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfUpdateRateVOWIS.setBackground(Color.WHITE);
	JLabel lblSekundenupdaterateVowis = new JLabel("Sekunden (UpdateRate VOWIS: ~3600 Sek.)");
	
	chckbxOnlineUpdateVOWIS = new JCheckBox("soll das JSON File aktuell sein? (online update)");
	chckbxOnlineUpdateVOWIS.setSelected(true);
	chckbxDefaultJsonVowis = new JCheckBox("DefaultJSON");
	buttonGroupVOWIS.add(chckbxOnlineUpdateVOWIS);
	buttonGroupVOWIS.add(chckbxDefaultJsonVowis);
	
	
	JLabel lblEinstellungenSpeichern = new JLabel("Einstellungen speichern");
	tfPathSettings = new JTextField();
	tfPathSettings.setEnabled(true);
	tfPathSettings.setEditable(false);
	tfPathSettings.setText("settings");
	tfPathSettings.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfPathSettings.setBackground(Color.WHITE);
	tfPathSettings.setColumns(10);
	tfSettings = new JTextField();
	tfSettings.setEditable(false);
	tfSettings.setText("settings.dat");
	tfSettings.setColumns(10);
	
	JLabel lblSpeichernHTML = new JLabel("Pfad zum Speichern der Bilder und HTML");
	tfPathHTML = new JTextField();
	tfPathHTML.setEnabled(true);
	tfPathHTML.setEditable(false);
	tfPathHTML.setText("HTML");
	tfPathHTML.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfPathHTML.setBackground(Color.WHITE);
	tfPathHTML.setColumns(10);
	tfHTML = new JTextField();
	tfHTML.setText("BodenseeWetter.html");
	tfHTML.setEditable(false);
	tfHTML.setColumns(10);
	
	JLabel lblWasserTemperaturSchaltschwelle = new JLabel("Wassertemperatur Schaltschwelle");
	tfWasserTemperaturSchaltschwelle = new JTextField();
	tfWasserTemperaturSchaltschwelle.setText("15");
	tfWasserTemperaturSchaltschwelle.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	tfWasserTemperaturSchaltschwelle.setColumns(10);
	tfWasserTemperaturSchaltschwelle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfWasserTemperaturSchaltschwelle.setBackground(Color.WHITE);
	JLabel lblUnitC = new JLabel("°C");


	JLabel lblDefaultRegionalCode = new JLabel("Default Regional Code");	
//	lblDefaultRegionalCode.setVisible(false);
	tfDefaultRegionalCode = new JTextField();
	tfDefaultRegionalCode.setText("208440000");
	tfDefaultRegionalCode.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	tfDefaultRegionalCode.setColumns(10);
	tfDefaultRegionalCode.setBackground(Color.WHITE);
	JLabel lblNewLabel = new JLabel("z.B. 208440000 - Bodensee-Ost");

	

	
	btnReLoad = new JButton("Re-load saved settings");
	btnReLoad.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    setArguments(getPathSettings(), getNameSettings());
		} catch (IOException e1) {
		    e1.printStackTrace();
		    JOptionPane.showMessageDialog(null,"Fehler beim laden der Settings");
		}
	    }
	});
	
	btnDefault = new JButton("set to default");
	btnDefault.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		setDefaultArguments();
	    }
	});
	
	btnClose = new JButton("Close");
	btnClose.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		saveArguments(getPathSettings(), getNameSettings());
		dispose();
	    }
	});
	
	btnSave = new JButton("Save");
	btnSave.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		saveArguments(getPathSettings(), getNameSettings());
	    }
	});
	
	
	JLabel lblArgumente = new JLabel("Argumente ausw\u00E4hlen");	
	chckbxRegionName = new JCheckBox("regionName");
	chckbxDescription = new JCheckBox("description");
	chckbxEvent = new JCheckBox("event");
	chckbxEnd = new JCheckBox("end");
	chckbxStart = new JCheckBox("start");
	chckbxType = new JCheckBox("type");	
	chckbxState = new JCheckBox("state");
	chckbxLevel = new JCheckBox("level");	
	chckbxHeadline = new JCheckBox("headline");
	chckbxInstruction = new JCheckBox("instruction");
	chckbxStateShort = new JCheckBox("State Short");
	chckbxAltitudeStart = new JCheckBox("altitudeStart");
	chckbxAltitudeEnd = new JCheckBox("altitudeEnd");
	

	

	
		
	GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
	gl_contentPanel.setHorizontalGroup(
		gl_contentPanel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPanel.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblUpdateRateVOWIS)
								.addGap(95)
								.addComponent(tfUpdateRateVOWIS, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblSekundenupdaterateVowis, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(chckbxOnlineUpdateVOWIS, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(chckbxDefaultJsonVowis, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(chckbxOnlineUpdateDWD, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(chckbxDefaultJsonDwd, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblUpdateRate, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfUpdateRateDWD, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblUnitSekunden_DWD, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
							.addComponent(lblPathVOWIS, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblSettings, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 433, Short.MAX_VALUE))
							.addComponent(lblSpeichernHTML, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblDefaultRegionalCode)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(tfDefaultRegionalCode, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
							.addComponent(tfPathSettings, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(tfHTML, Alignment.LEADING, 501, 501, Short.MAX_VALUE)
							.addComponent(tfPathHTML, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(tfPathDWD, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(tfPathVOWIS, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(tfSettings, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(lblPathDWD, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addComponent(lblEinstellungenSpeichern, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
						.addGap(17))
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addComponent(lblWasserTemperaturSchaltschwelle, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tfWasserTemperaturSchaltschwelle, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblUnitC, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE))))
	);
	gl_contentPanel.setVerticalGroup(
		gl_contentPanel.createParallelGroup(Alignment.TRAILING)
			.addGroup(gl_contentPanel.createSequentialGroup()
				.addContainerGap()
				.addComponent(lblSettings)
				.addGap(11)
				.addComponent(lblPathDWD)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tfPathDWD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(chckbxOnlineUpdateDWD)
					.addComponent(chckbxDefaultJsonDwd))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblUpdateRate)
					.addComponent(tfUpdateRateDWD, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblUnitSekunden_DWD))
				.addGap(18)
				.addComponent(lblPathVOWIS)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tfPathVOWIS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(chckbxOnlineUpdateVOWIS)
					.addComponent(chckbxDefaultJsonVowis))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblUpdateRateVOWIS)
					.addComponent(tfUpdateRateVOWIS, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblSekundenupdaterateVowis))
				.addGap(38)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblWasserTemperaturSchaltschwelle)
					.addComponent(tfWasserTemperaturSchaltschwelle, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblUnitC))
				.addGap(54)
				.addComponent(lblEinstellungenSpeichern)
				.addGap(4)
				.addComponent(tfPathSettings, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tfSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(39)
				.addComponent(lblSpeichernHTML)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tfPathHTML, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(tfHTML, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
				.addComponent(lblDefaultRegionalCode)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(tfDefaultRegionalCode, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblNewLabel))
				.addContainerGap())
	);
	contentPanel.setLayout(gl_contentPanel);	
	
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 512, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(61)
				.addComponent(btnSave)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnClose)
				.addContainerGap())
			.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
	);
	

	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.TRAILING)
			.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
					.addComponent(chckbxInstruction, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxStateShort, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblArgumente, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnReLoad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnDefault, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addComponent(chckbxHeadline, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxLevel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxState, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxType, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxEnd, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxEvent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxDescription, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxRegionName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addComponent(chckbxAltitudeStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(chckbxAltitudeEnd, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(9))
	);
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addContainerGap()
				.addComponent(lblArgumente)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxRegionName)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxDescription)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxEvent)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxEnd)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxStart)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxType)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxState)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxLevel)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxHeadline)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxInstruction)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxStateShort)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxAltitudeStart)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(chckbxAltitudeEnd)
				.addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
				.addComponent(btnReLoad)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnDefault)
				.addGap(12))
	);
	panel.setLayout(gl_panel);
	getContentPane().setLayout(groupLayout);
    }
      
    
    ///////////////////////////////////////
    // getter 
    ///////////////////////////////////////


    public static  String getChckbxOnlineUpdateDWD() {
	if( chckbxOnlineUpdateDWD.isSelected()) return "true";
	else return "false";
    }
    
    public static int getUpdateRateDWDms() { // Integer wert zur Berechnung (in ms)
	int time;
	String rate = tfUpdateRateDWD.getText();
	if (rate.isEmpty()) {
	    time = 5000;
	}else {
	    time = Integer.parseInt(rate)*1000;
	    if (time<5000) time=5000;
	}
 	return time;
    }
    
    public static  String getChckbxOnlineUpdateVOWIS() {
	if( chckbxOnlineUpdateVOWIS.isSelected()) return "true";
	else return "false";
    } 
    
    public static int getWasserTemperaturSchaltschwelle() { // Wasser Temperatur Schaltschwelle in °C
	int temp=15;
	String x = tfWasserTemperaturSchaltschwelle.getText();
	if (x.isEmpty()) {
	    x = "15";
	}else {
	    temp = Integer.parseInt(x);
//	    if (time<5000) time=5000;
	}
 	return temp;
     }
    
    public static URL getURLDWD() {
	URL PathDWD = null;
	try { 
	    PathDWD = new URL(tfPathDWD.getText());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
//	    JOptionPane.showMessageDialog(null,"Fehler beim Zugriff auf den DWD Server");
	}
        return PathDWD;
    }
    
    public static URL getURLVOWIS() {
	URL PathVOWIS = null;
	try {
	    PathVOWIS = new URL(tfPathVOWIS.getText());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
//	    JOptionPane.showMessageDialog(null,"Fehler beim Zugriff auf den VOWIS Server");
	}
        return PathVOWIS;
    }
     
    public static String getPathSettings() {
	String path = "." + File.separator + tfPathSettings.getText() + File.separator;
 	return path;
     }
    
    
    public static File getNameSettings() {
	File file = new File (tfSettings.getText());
	return file;
    }
    
    
    public static String getPathHTML() {
	String path = "." + File.separator + tfPathHTML.getText() + File.separator;
 	return path;
     }
    
    public static File getNameHTML() {
 	File file = new File (tfHTML.getText());
	return file;
     }
    
    public static int getUpdateRateVOWISms() { // Integer wert zur Berechnung (in ms)
	int time;
	String rate = tfUpdateRateVOWIS.getText();
	if (rate.isEmpty()) {
	    time = 5000;
	}else {
	    time = Integer.parseInt(rate)*1000;
	    if (time<5000) time=5000;
	}
 	return time;
     }
    
    public static String getDefaultRegionalCode() {
        return tfDefaultRegionalCode.getText();
    }

    private static  String getChckbxRegionName() { 
	if(chckbxRegionName.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxDescription() {
	if(chckbxDescription.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxEvent() {
	if( chckbxEvent.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxEnd() {
	if( chckbxEnd.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxStart() {
	if( chckbxStart.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxType() {
	if( chckbxType.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxState() {
	if( chckbxState.isSelected()) return "true";
	else return "false";
    }
    
    private static String getChckbxLevel() {
	if( chckbxLevel.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxHeadline() {
	if( chckbxHeadline.isSelected()) return "true";
	else return "false";
    }
    
    private static String getchckbxInstruction() {
	if( chckbxInstruction.isSelected()) return "true";
	else return "false";
    }

    private static  String getChckbxStateShort() {
	if( chckbxStateShort.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxAltitudeStart() {
	if( chckbxAltitudeStart.isSelected()) return "true";
	else return "false";
    }

    private static String getChckbxAltitudeEnd() {
        if( chckbxAltitudeEnd.isSelected()) return "true";
	else return "false";
    }
    
    public static String[] getArguments() {
	argumente[0] = getChckbxRegionName();
	argumente[1] = getChckbxDescription();
	argumente[2] = getChckbxEvent();
	argumente[3] = getChckbxEnd();
	argumente[4] = getChckbxStart();
	argumente[5] = getChckbxType();
	argumente[6] = getChckbxState();
	argumente[7] = getChckbxLevel();
	argumente[8] = getChckbxHeadline();
	argumente[9] = getchckbxInstruction();
	argumente[10] = getChckbxStateShort();
	argumente[11] = getChckbxAltitudeStart();
	argumente[12] = getChckbxAltitudeEnd();
	argumente[13] = getChckbxOnlineUpdateDWD();
	argumente[14] = getChckbxOnlineUpdateVOWIS();
	argumente[15] = ""+getWasserTemperaturSchaltschwelle(); 
	argumente[16] = ""+(getUpdateRateDWDms()/1000);
	argumente[17] = getDefaultRegionalCode();
	argumente[18] = ""+(getUpdateRateVOWISms()/1000);
	argumente[19] = tfPathSettings.getText();
	argumente[20] = tfPathHTML.getText();

	return argumente;
    }
      
    
    ///////////////////////////////////////
    // Setter 
    ///////////////////////////////////////

    public static  void setDefaultArguments() {
	chckbxRegionName.setSelected(true);
	chckbxDescription.setSelected(true);
	chckbxEvent.setSelected(true);
	chckbxEnd.setSelected(true);
	chckbxStart.setSelected(true);
	chckbxType.setSelected(false);
	chckbxState.setSelected(false);
	chckbxLevel.setSelected(false); 
	chckbxHeadline.setSelected(false);
	chckbxInstruction.setSelected(false);
	chckbxStateShort.setSelected(false); 
	chckbxAltitudeStart.setSelected(false);  
	chckbxAltitudeEnd.setSelected(false);
	chckbxOnlineUpdateDWD.setSelected(true);
	chckbxDefaultJsonDwd.setSelected(false);
	chckbxOnlineUpdateVOWIS.setSelected(true);
	chckbxDefaultJsonVowis.setSelected(false);
	setWasserTemperaturSchaltschwelle("15");
	setUpdateRateDWD("300"); 
	setDefaultRegionalCode("208440000");
	setUpdateRateVOWIS("3600");
	tfPathSettings.setText("settings");
	tfPathHTML.setText("HTML");
    }
    

    
    public static void setDefaultRegionalCode(String code) {
        tfDefaultRegionalCode.setText(code);
    }
    
    public static void setUpdateRateDWD(String rate) {
        tfUpdateRateDWD.setText(rate);
    }
    
    public static void setUpdateRateVOWIS(String rate) {
        tfUpdateRateVOWIS.setText(rate);
    }
    
    public static void setWasserTemperaturSchaltschwelle(String rate) {
	tfWasserTemperaturSchaltschwelle.setText(rate);
    }
      
    public static void setArguments(String dir, File datei) throws IOException {
	File directory = new File (dir);
	BufferedReader in = null;
	if (!directory.exists()) {
	    if (directory.mkdirs()) 
		System.out.println("Settings datei - Multiple directories are created!");
	    else {
		System.out.println("Settings datei - Failed to create multiple directories!");
		JOptionPane.showMessageDialog(null,"Fehler beim erstellen des \"Settings\" Verzeichnis");
		}
	    setDefaultArguments();
//	    getArguments();
	    saveArguments( dir,  datei);
	    JOptionPane.showMessageDialog(null, "Default Einstellungen sind gespeichert \nProgram Re-start required");
	    System.exit(0);
	} else {
	    String adresszeile;
	    try {
		in = new BufferedReader(new FileReader(new File(dir+datei)));
		int i=0;		 
		while ((adresszeile = in.readLine()) != null) {
		    argumente[i] = adresszeile; i++;		
		}		
	    } catch (Exception e) {
		setDefaultArguments();
//		getArguments();
		saveArguments( dir,  datei);
		JOptionPane.showMessageDialog(null, "Default Einstellungen sind gespeichert \nProgram Re-start required");
		System.exit(0);	
	    }finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Fehler beim Lesen der Settings");
		    }
		}
	    }
	}
	chckbxRegionName.setSelected(Boolean.parseBoolean(argumente[0]));
	chckbxDescription.setSelected(Boolean.parseBoolean(argumente[1]));
	chckbxEvent.setSelected(Boolean.parseBoolean(argumente[2]));
	chckbxEnd.setSelected(Boolean.parseBoolean(argumente[3]));
	chckbxStart.setSelected(Boolean.parseBoolean(argumente[4]));
	chckbxType.setSelected(Boolean.parseBoolean(argumente[5]));
	chckbxState.setSelected(Boolean.parseBoolean(argumente[6]));
	chckbxLevel.setSelected(Boolean.parseBoolean(argumente[7])); 
	chckbxHeadline.setSelected(Boolean.parseBoolean(argumente[8]));
	chckbxInstruction.setSelected(Boolean.parseBoolean(argumente[9]));
	chckbxStateShort.setSelected(Boolean.parseBoolean(argumente[10]));  
	chckbxAltitudeStart.setSelected(Boolean.parseBoolean(argumente[11]));  
	chckbxAltitudeEnd.setSelected(Boolean.parseBoolean(argumente[12]));
	chckbxOnlineUpdateDWD.setSelected(Boolean.parseBoolean(argumente[13]));
	chckbxDefaultJsonDwd.setSelected(!Boolean.parseBoolean(argumente[13]));
	chckbxOnlineUpdateVOWIS.setSelected(Boolean.parseBoolean(argumente[14]));
	chckbxDefaultJsonVowis.setSelected(!Boolean.parseBoolean(argumente[14]));
	setWasserTemperaturSchaltschwelle(argumente[15]);
	setUpdateRateDWD(argumente[16]);
	setDefaultRegionalCode(argumente[17]);	
	setUpdateRateVOWIS(argumente[18]);
	tfPathSettings.setText(argumente[19]);
	tfPathHTML.setText(argumente[20]);	
    }
    
    
    public static void saveArguments(String dir, File datei) {
	getArguments();
	BufferedWriter out = null;
        File directory = new File(dir); 
        if (!directory.exists()) {
            if (directory.mkdirs()) 
                System.out.println("Multiple directories are created!");
            else 
                System.out.println("Failed to create multiple directories!");
        }
	try {
	    out = new BufferedWriter(new FileWriter(new File(dir + datei)));  
	    for (int i = 0; i < argumente.length; i++) {
		out.write(argumente[i]);
		out.newLine();
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(null,"Fehler beim Speichern der Settings");
	} finally {
	    if (out != null) {
		try {
		    out.close();
		    System.out.println("out-closed");
		} catch (IOException ex) {
		    ex.printStackTrace();
		    JOptionPane.showMessageDialog(null,"Fehler beim Speichern der Settings");
		}
	    }
	}
    }
}

