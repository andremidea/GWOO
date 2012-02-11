package br.com.gwoo.main;

import static br.com.gwoo.infra.Constants.*;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	private final JLabel sniperStatus = createLabel(STATUS_JOINING);

	public MainWindow(){
		super("Auction Sniper");
		setName(Main.MAIN_WINDOW_NAME);
		add(sniperStatus);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JLabel createLabel(String initialText) {
		JLabel result = new JLabel(initialText);
		result.setName(Main.SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}

	public void showStatus(String status) {
		sniperStatus.setText(status);	
	}
}
