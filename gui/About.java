package wetter.gui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;


import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class About extends JDialog {

    private static final long serialVersionUID = 8182802011841659090L;
    private static final JPanel contentPanel = new JPanel();

    public About() {
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	setModal(true);
    	setTitle("Bodensee Wettermelder - About");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/wetter/ressources/favicon.png")));
	setBounds(100, 100, 663, 551);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	
	JButton btnDispose = new JButton("close");
	btnDispose.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    dispose();
		}
	});
	
	JTextArea txtWetterdaten = new JTextArea();
	txtWetterdaten.setBackground(SystemColor.menu);
	txtWetterdaten.setText("Quelle:\r\n\r\nCopyright:");
	
	JLabel lblAuthor = new JLabel("About");
	
	JLabel lblWetterdatenDWD = new JLabel("Wetterdaten - DWD");
	
	JTextArea txtAbout = new JTextArea();
	txtAbout.setBackground(SystemColor.menu);
	txtAbout.setText("Author:\r\nVersion:\r\nDate:");
	
	JTextArea txtAutorDaten = new JTextArea();
	txtAutorDaten.setText("Stefan Baiker\r\n2.2\r\nApril. 2021");
	txtAutorDaten.setBackground(SystemColor.menu);
	
	JTextArea txtDWD_Daten = new JTextArea();
	txtDWD_Daten.setText("https://www.dwd.de/DWD/warnungen/warnapp/json/warnings.json\r\n\r\nCopyright Deutscher Wetterdienst");
	txtDWD_Daten.setBackground(SystemColor.menu);
	
	JLabel lblWetterdatenVOWIS = new JLabel("Wetterdaten - VOWIS");
	
	JTextArea txtrQuelleCopyright = new JTextArea();
	txtrQuelleCopyright.setText("Quelle:\r\n\r\n\r\nCopyright:");
	txtrQuelleCopyright.setBackground(SystemColor.menu);
	
	JTextArea txtDWD_Daten_1 = new JTextArea();
	txtDWD_Daten_1.setText("https://vowis.vorarlberg.at/api/see \r\nhttps://vowis.vorarlberg.at/api/see/v1/jahresganglinie\r\n\r\n\"Amt der Vorarlberger Landesregierung, \r\nAbt. VIId – Wasserwirtschaft\" (VOWIS)\r\nhttps://www.vorarlberg.at/seewasserstand");
	txtDWD_Daten_1.setBackground(SystemColor.menu);
	GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
	gl_contentPanel.setHorizontalGroup(
		gl_contentPanel.createParallelGroup(Alignment.TRAILING)
			.addGroup(gl_contentPanel.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblAuthor, GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
									.addGap(9))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(txtAbout, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtAutorDaten, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(13))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtrQuelleCopyright, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(530, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblWetterdatenVOWIS, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtWetterdaten, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtDWD_Daten, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblWetterdatenDWD, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addComponent(txtDWD_Daten_1, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE)
							.addContainerGap()))
					.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
						.addComponent(btnDispose)
						.addContainerGap())))
	);
	gl_contentPanel.setVerticalGroup(
		gl_contentPanel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPanel.createSequentialGroup()
				.addContainerGap()
				.addComponent(lblAuthor)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(txtAutorDaten, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addComponent(txtAbout, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addComponent(lblWetterdatenDWD)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addComponent(txtWetterdaten, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addComponent(txtDWD_Daten, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
				.addGap(29)
				.addComponent(lblWetterdatenVOWIS)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
					.addComponent(txtrQuelleCopyright, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addComponent(txtDWD_Daten_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
				.addComponent(btnDispose)
				.addContainerGap())
	);
	contentPanel.setLayout(gl_contentPanel);
    }
}

