package br.com.codersistemas.codertradestatus.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class PanelBuilder {

	private List<Row> rows;
	private Row row;

	private Row row() {
		addInRow();
		return row;
	}

	private void addInRow() {
		rows = rows == null ? new ArrayList<Row>() : rows;
		row = new Row();
		rows.add(row);
	}

	class Row {
		
		private List<JComponent> components;

		public Row add(JComponent component) {
			components = components == null ? new ArrayList<JComponent>() : components;
			components.add(component);
			return this;
		}

		public JPanel build() {
			JPanel panel = new JPanel(new GridLayout(rows.size(), 1));
			panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			for (Row row : rows) {
				JPanel panelRow = new JPanel(new GridLayout(1, row.components.size()));
				for (JComponent component : row.components) {
					panelRow.add(component);
				}
				panel.add(panelRow);
			}
			return panel;
		}

		public Row row() {
			addInRow();
			return row;
		}
	}
	
	public static void main(String[] args) {
		
		PanelBuilder ui = new PanelBuilder();
		
		JPanel panel1 = 
		ui.row().add(new JLabel("Nome"))
		.row().add(new JTextField(20))
		.row().add(new JLabel("Email")).add(new JLabel("Nascimento"))
		.row().add(new JTextField(20)).add(new JFormattedTextField(20))
		.row().add(new JCheckBox("ADM")).add(new JCheckBox("SYS")).add(new JCheckBox("CLR")).add(new JCheckBox("GER")).add(new JCheckBox("JUR"))
		.row().add(new JButton("Novo")).add(new JButton("Salvar")).add(new JButton("Excluir"))
		.build();
		
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panel2.setSize(480, 300);
		panel2.add(new JLabel("Descrição"));
		JTextArea textArea = new JTextArea(10, 20);
		panel2.add(textArea);
		
		JFrame frame = new JFrame("Robot Coder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(panel1);
		frame.getContentPane().add(panel2);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 600);
		//frame.pack();
		
	}

}

