package br.com.codersistemas.codertradestatus.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NumberCellRender extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, 
			Object value, 
			boolean isSelected, 
			boolean hasFocus,
			int row, 
			int column) {
		
		setHorizontalAlignment(JLabel.RIGHT);
		
		JLabel label = this;
		if(value != null && !"".equals(value)) {
			setOpaque(true);
			label.setText(String.valueOf(value));
			Float floatValue = new Float(value
					.toString()
					.replace(".", "")
					.replace(",", "."));
			if(floatValue.floatValue() == 0f) {
				label.setForeground(Color.BLACK);
			}else if(floatValue.floatValue() < 0f) {
				label.setForeground(Color.RED);
			}else {
				label.setForeground(Color.BLUE);
			}
		}else {
			label.setText("");
		}
		
		return this;
	}

}
