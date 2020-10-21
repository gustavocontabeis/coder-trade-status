package br.com.codersistemas.codertradestatus.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import br.com.codersistemas.codertradestatus.model.ItemCarteira;
import br.com.codersistemas.codertradestatus.utils.FileUtils;
import br.com.codersistemas.codertradestatus.utils.NumberUtils;

public class PosicaoContasUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel painelTabela;
	private JPanel painelValoresAtivos;
	private JScrollPane barraRolagem;
	private JTable tabela;
	private JPanel painelBotoes;
	private JTabbedPane tabbedPane = new JTabbedPane();

	private JTextArea textAreaValoresAtivos;

	private JScrollPane scrollValoresAtivos;

	private JButton btAtualizar;

	public PosicaoContasUI() {
		super ("Posição de Contas");
		criaJanela();
	}

	public void criaJanela(){

		painelTabela = new JPanel();
		painelTabela.setLayout(new GridLayout(1, 1));
		tabela = new JTable();
		barraRolagem = new JScrollPane(tabela);
		painelTabela.add(barraRolagem);
		
		painelValoresAtivos = new JPanel();
		painelValoresAtivos.setLayout(new GridLayout(1, 1));
		textAreaValoresAtivos = new JTextArea();
		scrollValoresAtivos = new JScrollPane(textAreaValoresAtivos);
		painelValoresAtivos.add(scrollValoresAtivos);
		
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout());
		painelBotoes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		btAtualizar = new JButton("Atualizar");
		btAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}

		});
		painelBotoes.add(btAtualizar);
		
		tabbedPane.addTab("Valores", painelTabela);
		tabbedPane.addTab("Profit", painelValoresAtivos);
        
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(painelBotoes, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(650, 600);
		setVisible(true);
		
		atualizar();

	}
    
	private void atualizar() {
		
		Map<String, Float> map = gerarMapaDeCotacoes();
		
		List<String[]> listCarteira = FileUtils.readFile(new File("./carteira.txt"), "\t");
		List<ItemCarteira> itensCarteira = new ArrayList<>();
		for (String[] strings : listCarteira) {
			ItemCarteira e = new ItemCarteira(strings[0], strings[1], new Integer(strings[2]), new Float(strings[3]), null, null);
			
			Float valorAtual = map.get(e.getNomeAtivo());
			if(valorAtual == null) {
				System.out.println(e.getNomeAtivo());
			}else {
				float resultado = (valorAtual.floatValue() - e.getValorAquisicao().floatValue()) * e.getQuantidade().intValue();
				e.setCotacaoAtual(valorAtual);
				e.setResultado(resultado);
			}
			
			itensCarteira.add(e);
		}
		tabela.setModel(new PosicaoTableModel(itensCarteira));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tabela.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tabela.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tabela.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		tabbedPane.setSelectedIndex(0);
	}

	private Map<String, Float> gerarMapaDeCotacoes() {
		String[] split = textAreaValoresAtivos.getText().split("\n");
		List<String[]> listCotacoes = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			listCotacoes.add(split[i].split("\t"));
		}
		
		Map<String, Float> map = new HashMap<String, Float>();
		int i = 0;
		for (String[] strings : listCotacoes) {
			if(i++ != 0) {
				if(strings.length>1) {
					System.out.println(i+" "+strings[0]+" "+strings[1]);
					map.put(strings[0], new Float(strings[1].replace(".", "").replace(",", ".")));
				}
			}
		}
		return map;
	}
	
   public static void main(String[] args) {
        PosicaoContasUI lc = new PosicaoContasUI();
    }
}

class PosicaoTableModel extends AbstractTableModel{
	
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
		return null;
	}
	
}