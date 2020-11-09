package br.com.codersistemas.codertradestatus.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TradeStatusUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	public TradeStatusUI() throws HeadlessException, AWTException {
		super("Trade Status");

		tabbedPane.addTab("Multibroker", new MultiBrokerPanel());
		tabbedPane.addTab("Resultados", new PosicaoContasPanel());

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			new TradeStatusUI();
		} catch (HeadlessException | AWTException e) {
			e.printStackTrace();
		}
	}

}
