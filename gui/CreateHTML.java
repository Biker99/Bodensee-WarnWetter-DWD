package wetter.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wetter.common.AktualisierenDWD;
import wetter.common.VOWIS;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.SystemColor;


//	Hinweis des Autors:
//	Die Erstellung dieses HTML Schnipsels sieht sehr einfach aus
//	Allerdings unterstützt das benutzte Fahrtenbuch weder CSS noch JavaScript
//	darum muss hier diese Umweg gemacht werden.
//	Texte positionieren via CSS wird nicht unterstützt
//	Bilder positionieren via CSS wird nicht unterstützt
//	Texte über Bilder positionieren schon gleich gar nicht.
//	Ebenso JavaScript im HTML. Bilder Blinken etc. wird nicht unterstützt.
//	darum habe ich mich für statische, vorgefertigte Bilder entschieden.
//	diese werden je nach bedarf selektiert und dann in einem HTML codeschnipsel aufgerufen.
	



public class CreateHTML extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JPanel contentPane;
    private static JTextArea txtArea_Text1;
    private static JTextArea txtArea_Text2;
    private static JTextArea txtArea_Text3;
    private static JTextArea txtArea_output;
    private static JLabel lblSaveTo;
    private static String defaultCode1 = 
	 ("<!DOCTYPE html>\r\n"
    	+ "<html>\r\n"
    	+ "<head>\r\n"
    	+ "  <title id =\"headline\">Warnwetter f&uuml;r den Bodensee</title>\r\n"
    	+ "  <meta name=\"description\"content=\"Warnwetter Lindauer Kanu Club\"  />\r\n"
    	+ "  <meta name=\"author\"content=\"Stefan Baiker\" />\r\n"
    	+ "  <meta name=\"copyright\" content=\"Deutscher Wetterdienst - www.dwd.de\"/>\r\n"
    	+ "  <meta name=\"copyright\" content=\"Amt der Vorarlberger Landesregierung, Abt. VIId – Wasserwirtschaft - www.vorarlberg.at/seewasserstand\"/>\r\n" 
    	+ "  <meta http-equiv=\"refresh\" content=\"10\">\r\n"
    	+ "  <style type=\"text/css\">\r\n"
    	+ "    <!--\r\n"
    	+ "    body{\r\n"
    	+ "      font-family: Arial,VERDANA,HELVETICA,\"COURIER NEW\",MONOSPACE;\r\n"
    	+ "      background-color: #ffffff;\r\n"
    	+ "      font-size: small;\r\n"
    	+ "      text-align: left;}\r\n"
    	+ "    #ueberschrift{\r\n"
    	+ "      font-weight:bold;\r\n"
    	+ "      text-decoration:underline;}\r\n"
    	+ "    #datum{}\r\n"
    	+ "    #meldung{}\r\n"
    	+ "    #meldungT_L{}\r\n"
    	+ "    #meldungT_W{font-weight:bold;}\r\n"
    	+ "    #meldungW_B{}\r\n"
    	+ "    #meldungW_LKC{font-weight:bold;}\r\n"
    	+ "    -->\r\n"
    	+ "  </style>\r\n"
    	+ "</head>\r\n"
    	+ "<body id = \"body\">");
    private static String defaultCode3 = ("</body>\r\n</html>");
    
    public CreateHTML() {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 987, 744);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	
	JLabel lblNewLabel = new JLabel("code input - editable");	
	JScrollPane scrollPane_output = new JScrollPane();	
	JScrollPane scrollPane_Text2 = new JScrollPane();	
	scrollPane_Text2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	JScrollPane scrollPane_Text3 = new JScrollPane();	
	JScrollPane scrollPane_Text1 = new JScrollPane();
	
	txtArea_Text1 = new JTextArea();
	txtArea_Text1.setText(defaultCode1);
	scrollPane_Text1.setColumnHeaderView(txtArea_Text1);
	
	
	txtArea_Text2 = new JTextArea();
	txtArea_Text2.setBackground(SystemColor.menu);
	setTxtArea_Text2();
	scrollPane_Text2.setViewportView(txtArea_Text2);
	txtArea_Text2.setColumns(10);
	
	txtArea_Text3 = new JTextArea();
	txtArea_Text3.setText(defaultCode3);
	scrollPane_Text3.setViewportView(txtArea_Text3);
	
	txtArea_output = new JTextArea();
	txtArea_output.setEditable(false);
	scrollPane_output.setViewportView(txtArea_output);
	
	JButton btnGenerate = new JButton("save to HTML file");
	btnGenerate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String pathHTML = Settings.getPathHTML();
		File fileNameHTML = Settings.getNameHTML();
		setTxtArea_Text2();
		StringBuilder completeCode = new StringBuilder();
		completeCode.append(txtArea_Text1.getText()) ;
		completeCode.append(txtArea_Text2.getText()) ;
		completeCode.append(txtArea_Text3.getText()) ;
	    	txtArea_output.setText(completeCode.toString());   
	    	saveCode2File(pathHTML, fileNameHTML, completeCode);
	    	lblSaveTo.setText("HTML code is saved to "+pathHTML+ fileNameHTML);
	    }
	});
	
	JLabel lblNewLabel_1 = new JLabel("code output - wird vom Script erzeugt");
	
	lblSaveTo = new JLabel("saved to: directory");
	
	JButton btnRead = new JButton("read from HTML  file");
	btnRead.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    try {
			txtArea_Text1.setText(openCodefromFile1().toString());
			txtArea_Text3.setText(openCodefromFile3().toString());
		    } catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,"HTML-File not found");
		    }
		}
	});
	
	JButton btnClear = new JButton("clear \"code input\"");
	btnClear.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    txtArea_Text1.setText(null);
		    txtArea_Text3.setText(null);
		}
	});
	
	JButton btnClose = new JButton("close");
	btnClose.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    dispose();
		}
	});
	
	JButton btnDefault1 = new JButton("set \"code input\" to default");
	btnDefault1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    txtArea_Text1.setText(defaultCode1);
		    setTxtArea_Text2();
		    txtArea_Text3.setText(defaultCode3);
		}
	});
	
	
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(5)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(scrollPane_Text2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addComponent(scrollPane_Text1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addComponent(scrollPane_Text3, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
							.addComponent(btnGenerate, GroupLayout.PREFERRED_SIZE, 112, Short.MAX_VALUE)
							.addComponent(btnRead, GroupLayout.PREFERRED_SIZE, 112, Short.MAX_VALUE)
							.addComponent(btnDefault1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 112, Short.MAX_VALUE)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addComponent(lblSaveTo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(4)
						.addComponent(scrollPane_output, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)))
				.addContainerGap())
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(2)
						.addComponent(lblNewLabel))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel_1)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblSaveTo)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane_Text1, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addGap(40)
								.addComponent(btnDefault1)
								.addPreferredGap(ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
								.addComponent(btnRead)
								.addGap(142)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(scrollPane_Text2, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(scrollPane_Text3, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnGenerate)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnClose)
								.addGap(70))))
					.addComponent(scrollPane_output, GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE))
				.addGap(83))
	);
	contentPane.setLayout(gl_contentPane);
    }

    
   public static void AutoCreateHTML() {
        String pathHTML = Settings.getPathHTML();
        File fileNameHTML = Settings.getNameHTML();
	
	StringBuilder completeCode = new StringBuilder ();
	try {
	    StringBuilder import1 = new StringBuilder (openCodefromFile1());
	    completeCode.append(import1) ;
	    completeCode.append("\r\n<!--Auto generated-->");
	    StringBuilder autoCode1 = new StringBuilder(setAutoGeneratedCodeVOWIS());
	    completeCode.append(autoCode1) ;
	    StringBuilder autoCode2 = new StringBuilder(setAutoGeneratedCodeDWD());
	    completeCode.append(autoCode2) ;
	    completeCode.append("\r\n<!--Auto generated ende-->\r\n");  
	    StringBuilder import3 = new StringBuilder (openCodefromFile3()); 
	    completeCode.append(import3) ;
	    
    	    txtArea_output.setText(completeCode.toString());
	    saveCode2File(pathHTML, fileNameHTML, completeCode); 
	} catch (Exception e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,"HTML-File speichern fehlgeschlagen");
	}
    }

   private static String openCodefromFile1()  {
	String pathHTML = Settings.getPathHTML();
	File fileNameHTML = Settings.getNameHTML();
	File fullPath = new File(pathHTML+fileNameHTML);

//	File  erstellen, falls es nicht existiert
        File files = new File(pathHTML); 
        if (!files.exists()) {
            if (files.mkdirs()) 
                System.out.println("Multiple directories are created!");
            else {
                System.out.println("Failed to create multiple directories!");
            	JOptionPane.showMessageDialog(null,"HTML Verzeichnis erstellen fehlgeschlagen");
            }
        }
        
//	zurücklesen des HTML code aus der Datei und bereitstellen zum bearbeiten

	String zeile;
	StringBuilder import1 = new StringBuilder();
	BufferedReader in1 = null;
	if (!fullPath.exists()) {
	    try {
		fullPath.createNewFile();
		System.out.println("createNewFile"); 
	    } catch (IOException ioe) {
		ioe.printStackTrace();
		JOptionPane.showMessageDialog(null,"IO Fehler. \nNeues HTML File konnte nicht erstellt werden");
	    }		
	}
//	Import des ersten Blocks, bis zum Text "<!--Auto generated -->"
	try {
	    FileReader xxx = new FileReader(fullPath);
	    in1  = new BufferedReader(xxx);
	    while ((zeile = in1.readLine())!=null) {
		if (!zeile.contains("<!--Auto generated-->")) {
		    import1.append(zeile);
		    import1.append("\n");
		} else {
		    int x = import1.length();
		    import1.delete(x-1, x);
		    break;
		}
	    }	
	}catch (Exception ex){
	    System.out.println("leeres HTML file");
	    JOptionPane.showMessageDialog(null,"HTML File enthält keine Daten\ndefault wird geladen");
	}finally {
	    if (in1 != null) {
		try {
		    in1.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null,"HTML File konnte nicht geöffnet werden");
		}
	    }			
	}
	String xx1 = import1.toString();
	if (xx1.length() == 0) xx1=defaultCode1;
	return xx1;
}
   
//	hier steht der automatisch erstellte code :
//   		<!--Auto generated-->
//		    <!--Auto generated VOWIS -->
//	  	        setAutoGeneratedCodeVOWIS()
//		    <!--Auto generated VOWIS ende-->
//		    <!--Auto generated DWD -->
//		        setAautoGeneratedCodeDWD()
//		    <!--Auto generated DWD ende -->
//   		<!--Auto generated ende-->		
//	dieser Teil wird NICHT aus dem HTML zurückgelesen!!!
	
   private static String setAutoGeneratedCodeVOWIS() {
	StringBuilder autoGeneratedCodeVOWIS = new StringBuilder();
	autoGeneratedCodeVOWIS.append("\r\n<!--Auto generated VOWIS -->\r\n");
	if (AktualisierenDWD.isNight() || SimplifiedWetterGUI.getInternetFaultLabel())	autoGeneratedCodeVOWIS.append("");
	else {
	    autoGeneratedCodeVOWIS.append("<span id =\"ueberschrift\">Daten vom Pegel Bregenz und DWD, </span>"+SimplifiedWetterGUI.getWarnTime()+" Uhr<br>\r\n");
	    if ((int)VOWIS.getWT()<Settings.getWasserTemperaturSchaltschwelle()){
		autoGeneratedCodeVOWIS.append("&emsp;<font color=\"red\"><b>Wassertemperatur:&emsp;"+VOWIS.getWassertemperatur()+"</b></font><br>\r\n");
	    }else {
		autoGeneratedCodeVOWIS.append("&emsp;Wassertemperatur:&emsp;"+VOWIS.getWassertemperatur()+"</font><br>\r\n");
	    }
//	    autoGeneratedCodeVOWIS.append("&emsp;<span id =\"meldungW_B\">Wasserstand am Pegel Bregenz: "+VOWIS.getWasserstandBregenz()+"</span><br>\r\n");
	    autoGeneratedCodeVOWIS.append("&emsp;<span id =\"meldungW_B\">Wasserstand unter der LKC Rasenkante: "+VOWIS.getWasserstandRasenkante()+"</span><br>\r\n");
	}	  
	autoGeneratedCodeVOWIS.append("<!--Auto generated VOWIS ende -->");
	return autoGeneratedCodeVOWIS.toString();
   }
   
   private static String setAutoGeneratedCodeDWD() {
	StringBuilder autoGeneratedCodeDWD = new StringBuilder();
	StringBuilder WarnBildSeletor = new StringBuilder();
	
//	Übergabe der "Bilderauswahl vom Main" via SimplifiedWetterGUI.getWarnBildSeletor()
	WarnBildSeletor.delete(0, WarnBildSeletor.length());
	WarnBildSeletor.append(SimplifiedWetterGUI.getWarnBildSeletor());
	if (WarnBildSeletor.length() <8)  {
	    WarnBildSeletor.replace(0, WarnBildSeletor.length(), "00_00_00"); //übergebener Wert enthält den String "null"
	}
	WarnBildSeletor.append("_280.png");
	
//	Auto code für DWD erstellen
	autoGeneratedCodeDWD.append("\r\n<!--Auto generated DWD -->\r\n");
	if (AktualisierenDWD.isNight()){
	    autoGeneratedCodeDWD.append("<span id =\"ueberschrift\">Nachts kein Daten Download </span><br><br>\r\n");
	}else if (SimplifiedWetterGUI.getInternetFaultLabel()) { 
	    autoGeneratedCodeDWD.append("<span id =\"ueberschrift\">Internet Update Fehler</span><br><br>\r\n");
	} else {
	    autoGeneratedCodeDWD.append("<br>\r\n");    	
	} 
	autoGeneratedCodeDWD.append("<img src=\""+WarnBildSeletor+"\">\r\n");
	autoGeneratedCodeDWD.append("<!--Auto generated DWD ende -->");
	return autoGeneratedCodeDWD.toString();
    }

    
    private void setTxtArea_Text2() {
	txtArea_Text2.setText("\r\n<!--Auto generated-->");
	txtArea_Text2.append(setAutoGeneratedCodeVOWIS());
	txtArea_Text2.append(setAutoGeneratedCodeDWD());
	txtArea_Text2.append("\r\n<!--Auto generated ende-->\r\n");
    }
       
    
//	Import des zweiten Blocks, ab dem Text "<!--Auto generated ende-->"
    
    public static String openCodefromFile3() {
	String pathHTML = Settings.getPathHTML();
	File fileNameHTML = Settings.getNameHTML();
	File fullPath = new File(pathHTML+fileNameHTML);

//	zurücklesen des HTML code aus der Datei und bereitstellen zum bearbeiten
	String zeile;
	StringBuilder import3 = new StringBuilder();
	BufferedReader in3 = null;
	try {
	    in3  = new BufferedReader(new FileReader(fullPath));
	    boolean marker = false;
	    while ((zeile = in3.readLine())!=null) {
		if (zeile.contains("<!--Auto generated ende-->")) {
		    marker = true;
		} else if (marker == true){
			import3.append(zeile);
			import3.append("\n");
		}			
	    }
	    int x = import3.length();
	    import3.delete(x-1, x);
	} catch (Exception ex){
	    System.out.println("leeres HTML file");	
	    JOptionPane.showMessageDialog(null,"HTML File enthält keine Daten\ndefault wird geladen");
	}
	finally {
	    if (in3 != null) {
		try {
		    in3.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null,"HTML File konnte nicht geöffnet werden");
		}
	    }
	}
	String xx3 = import3.toString();
	if (xx3.length() == 0) xx3=defaultCode3;
	return xx3;
    }


    
    public static void saveCode2File(String pathHTML, File fileNameHTML, StringBuilder code) {
// 	Speichern des HTML code in einer Datei.
//	zuvor wurde die Variable "txtArea_output" aus den drei Teilen "txtArea_Text1", "txtArea_Text2" und "txtArea_Text3" generiert
	File fullPath = new File(pathHTML+fileNameHTML);

//	File  erstellen, falls es nicht existiert
        File files = new File(pathHTML); 
        if (!files.exists()) {
            if (files.mkdirs()) 
                System.out.println("Multiple directories are created!");
            else {
                System.out.println("Failed to create multiple directories!");
            	JOptionPane.showMessageDialog(null,"HTML Verzeichnis erstellen fehlgeschlagen");
            }
        }
        
//      file schreiben      
	BufferedWriter out = null;
        try {
	    FileWriter fw=new FileWriter(fullPath);
	    out = new BufferedWriter(fw);
	    out.write(code.toString());
	    out.newLine();
	} catch (IOException ex) {
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(null,"speichern der HTML Datei fehlgeschlagen");
	} finally {
	    if (out != null) {
		try {
		    out.close();
//		    System.out.println("out-closed");
		} catch (IOException ex) {
		    System.out.println("exception2");	
		    JOptionPane.showMessageDialog(null,"speichern der HTML Datei fehlgeschlagen");
		    ex.printStackTrace();
		}
	    }
	}
    }
 }



