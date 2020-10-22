package br.com.codersistemas.codertradestatus.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultEditorKit;

import br.com.codersistemas.codertradestatus.model.ItemCarteira;
import br.com.codersistemas.codertradestatus.renderers.NumberCellRender;
import br.com.codersistemas.codertradestatus.ui.models.PosicaoTableModel;
import br.com.codersistemas.codertradestatus.utils.FileUtils;

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
	private JButton btColar;
	private JButton btLimpar;

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
		
		btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		painelBotoes.add(btLimpar);
		
		btColar = new JButton(new DefaultEditorKit.PasteAction());
		btColar.setText("Colar");
		painelBotoes.add(btColar);
		
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
		tabela.getColumnModel().getColumn(5).setCellRenderer(new NumberCellRender());
		
		tabbedPane.setSelectedIndex(0);
	}

	private void limpar() {
		tabbedPane.setSelectedIndex(1);
		textAreaValoresAtivos.setText("");
		textAreaValoresAtivos.grabFocus();
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
        new PosicaoContasUI();
    }
}

