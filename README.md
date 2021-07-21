# Bodensee-WarnWetter-DWD


 
Wir haben  ein Tool geschrieben, mit dem wir
1.	die DWD Warnungen komplett für gesamt Deutschland auslesen und textlich darstellen
2.	diese Daten verwenden, um ein Bodensee-Spezifika bildlich darzustellen:
3.	diese Daten verwenden, um in regelmäßigen Zeitabständen einen kleinen HTML code-schnipsel zu generieren, der lokal gespeicherte Bilder aufruft. Dieser Schnipsel wird bei einer lokalen Anwendung verwendet
  
Es ist im Tool konfigurierbar, welche Daten vom DWD dargestellt werden sollen. (-->Settings)
Außerdem werden Bodensee Daten (Wassertemperatur und Wasserstand) vom Vorarlberger Wetterdienst (VOWIS) geladen und dargestellt.


Die Ergebnisse werden auf drei Arten ausgegeben:
1.	vollständige Ausgabe der Warnmeldungen
2.	Darstellung der Sturmwarnung für die drei Bodensee Regionen Ost, Mitte und West. Zusätzlich wird Bodensee Wassertemperatur, gemessen in Bregenz, und der Wasserstand am Lindauer Kanu-Club angezeigt. --> automatisches update der Daten vom DWD und VOWIS
3.	Es wird ein konfigurierbarer HTML Schnipsel erzeugt

Code / Project

    Java 14 [JDK-14.0.2]
    Eclipse IDE Version: 2020-12 (4.18.0)
    Window-Builder implementiert in Eclipse
    Package Name: wetter
    Die main datei ist die wetter\gui\SimplifiedWetterGUI.java
	executable .jar file, "HTML" directory with related .png and "SETTINGS" directory is located in "RESSOURCES"
	mehr details zum Programm sind in .../RESSOURCES/description_Bodensee-WarnWetter-DWD.pdf zu finden

Projekt Struktur:

    wetter
	    common
		    AktualisierenDWD.java
		    AktualisierenVOWIS.java
		    LocalTime.java
		    VOWIS.java
		    Warnung.java
	    gui	
		    About.java
		    CreateHTML.java
		    Settings.java
		    SimplifiedWetterGUI.java  main
	    includes	
		    json-simple-1.1.1.jar
	    json	
		    JSON_DecodeDWD.java
		    JSON_DecodeVOWIS.java
		    JSONLoadObjectDWD.java
		    JSONLoadObjectVOWIS.java
	    ressources	
		    Die benötigten Bilder etc. sind unter ressources gespeichert 
		    (Pfad zu den Bildern ist hard-coded!)

mehr details sind in /ressources/description_Bodensee-WarnWetter-DWD.pdf zu finden
