package br.com.codersistemas.codertradestatus.ui.models;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.codersistemas.codertradestatus.model.ItemCarteira;
import br.com.codersistemas.codertradestatus.utils.NumberUtils;

public class PosicaoTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private Object[] columnNames = new Object[] {"Nome", "Ativo", "Quantidade", "Valor Aquisição", "Cotacao Atual", "Resultado"};
	
	List<ItemCarteira> itensCarteira;
	
	public PosicaoTableModel(List<ItemCarteira> itensCarteira) {
		super();
		this.itensCarteira = itensCarteira;
	}

	@Override
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }

	@Override
	public int getRowCount() {
		return itensCarteira.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(!itensCarteira.isEmpty()) {
			
			ItemCarteira itemCarteira = itensCarteira.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return itemCarteira.getNomeCliente();
			case 1:
				return itemCarteira.getNomeAtivo();
			case 2:
				return itemCarteira.getQuantidade();
			case 3:
				return NumberUtils.formatBR(new BigDecimal(itemCarteira.getValorAquisicao()));
			case 4:
				return itemCarteira.getCotacaoAtual();
			case 5:
				return itemCarteira.getResultado() != null ? NumberUtils.formatBR(new BigDecimal(itemCarteira.getResultado())) : null;
			}
		}
		return null;
	}
	
}