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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultEditorKit;

import br.com.codersistemas.codertradestatus.builder.PanelBuilder;
import br.com.codersistemas.codertradestatus.model.ItemCarteira;
import br.com.codersistemas.codertradestatus.renderers.NumberCellRender;
import br.com.codersistemas.codertradestatus.service.ImportarAtivosClear;
import br.com.codersistemas.codertradestatus.ui.models.PosicaoTableModel;
import br.com.codersistemas.codertradestatus.utils.FileUtils;
import br.com.codersistemas.codertradestatus.utils.NumberUtils;

public class PosicaoContasPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private PanelBuilder panelBuilder = new PanelBuilder();
	
	private JPanel painelTabela;
	private JPanel painelCadastro;
	private JPanel painelValoresAtivosProfit;
	private JPanel painelValoresAtivosClear;
	private JScrollPane barraRolagem;
	private JTable tabela;
	private JPanel painelBotoes;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JTextArea textAreaValoresAtivosProfit;
	private JScrollPane scrollValoresAtivosProfit;
	private JTextArea textAreaValoresAtivosClear;
	private JScrollPane scrollValoresAtivosClear;

	private JButton btAtualizar;
	private JButton btColar;
	private JButton btLimpar;

	private List<ItemCarteira> itensCarteira;

	private JTextField txtNome;

	private JTextField txtAtivo;

	private JTextField txtQuantidade;

	private JTextField txtValorAquisicao;

	private JButton btImportarClear;

	private JComboBox<String> comboContaClear;

	public PosicaoContasPanel() {
		criaJanela();
	}

	public void criaJanela(){

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		tabela = new JTable();
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            System.out.println(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
	            ItemCarteira itemCarteira = itensCarteira.get(tabela.getSelectedRow());
	            txtNome.setText(itemCarteira.getNomeCliente());
	            txtAtivo.setText(itemCarteira.getNomeAtivo());
	            txtQuantidade.setText(itemCarteira.getQuantidade().toString());
	            txtValorAquisicao.setText(NumberUtils.formatBR(itemCarteira.getValorAquisicao()));
	        }
	    });
		barraRolagem = new JScrollPane(tabela);
		painelTabela.add(barraRolagem, BorderLayout.CENTER);
		
		painelCadastro = new JPanel(new GridLayout(6,2));
		painelCadastro.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		painelCadastro.add(new JLabel("Nome"));
		txtNome = new JTextField(20);
		painelCadastro.add(txtNome);
		painelCadastro.add(new JLabel("Ativo"));
		txtAtivo = new JTextField(20);
		painelCadastro.add(txtAtivo);
		painelCadastro.add(new JLabel("Quantidade"));
		txtQuantidade = new JTextField(20);
		painelCadastro.add(txtQuantidade);
		painelCadastro.add(new JLabel("Valor Aquisição"));
		txtValorAquisicao = new JTextField(20);
		painelCadastro.add(txtValorAquisicao);
		painelCadastro.add(new JButton("Novo"));
		painelCadastro.add(new JButton("Salvar"));
		painelCadastro.add(new JButton("Excluir"));
		painelCadastro.add(new JLabel(""));
		
		painelTabela.add(painelCadastro, BorderLayout.SOUTH);
		
		painelValoresAtivosClear = new JPanel();
		painelValoresAtivosClear.setLayout(new BorderLayout());
		textAreaValoresAtivosClear = new JTextArea();
		scrollValoresAtivosClear = new JScrollPane(textAreaValoresAtivosClear);
		painelValoresAtivosClear.add(scrollValoresAtivosClear, BorderLayout.CENTER);
			
		btImportarClear = new JButton("Importar");
		btImportarClear.addActionListener(l->importarClear(l));
		
		comboContaClear = new JComboBox<>(new String[] {"FLAVIANE VIEL ZONATTO", "GIANA DA SILVA", "GUSTAVO DA SILVA", "LAUTENIR JOSE DA SILVA"});
		JPanel painelValoresAtivosClearBotoes =	panelBuilder.row()
				.add(new JLabel("Conta"))
				.add(comboContaClear)
				.add(btImportarClear)
				.build();
			
		painelValoresAtivosClear.add(painelValoresAtivosClearBotoes, BorderLayout.SOUTH);
		
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout());
		painelBotoes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(e->limpar(e));
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
		
		painelValoresAtivosProfit = new JPanel();
		painelValoresAtivosProfit.setLayout(new BorderLayout());
		textAreaValoresAtivosProfit = new JTextArea();
		scrollValoresAtivosProfit = new JScrollPane(textAreaValoresAtivosProfit);
		painelValoresAtivosProfit.add(scrollValoresAtivosProfit, BorderLayout.CENTER);
		painelValoresAtivosProfit.add(painelBotoes, BorderLayout.SOUTH);
		
		tabbedPane.addTab("Resultados", painelTabela);
		tabbedPane.addTab("Importar dados Profit", painelValoresAtivosProfit);
		tabbedPane.addTab("Importar dados Clear", painelValoresAtivosClear);
        
		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
		

		atualizar();

	}
    
	private void importarClear(ActionEvent l) {
		ImportarAtivosClear iac = new ImportarAtivosClear();
		List<ItemCarteira> itens = iac.importar(textAreaValoresAtivosClear.getText());
		String nomeCliente = (String) comboContaClear.getSelectedItem();
		itens.forEach(i->i.setNomeCliente(nomeCliente));
		itens.forEach(i->{System.out.printf("%s\t%s\t%s\t%s\n", i.getNomeCliente(), i.getNomeAtivo(), i.getQuantidade(), i.getValorAquisicao());});
		
		StringBuilder sb = new StringBuilder();
		itens.forEach(i->{sb.append(String.format("%s\t%s\t%s\t%s\n", i.getNomeCliente(), i.getNomeAtivo(), i.getQuantidade(), i.getValorAquisicao()));});
		
		textAreaValoresAtivosClear.setText(textAreaValoresAtivosClear.getText()+"\n"+sb.toString());
	}

	private void atualizar() { 
		
		Map<String, Float> map = gerarMapaDeCotacoes();
		
		List<String[]> listCarteira = FileUtils.readFile(new File("./carteira.txt"), "\t");
		itensCarteira = new ArrayList<>();
		for (String[] strings : listCarteira) {
			ItemCarteira e = new ItemCarteira(strings[0], strings[1], new Integer(strings[2]), new Float(strings[3]), null, null);
			
			Float valorAtual = map.get(e.getNomeAtivo());
			if(valorAtual != null) {
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

	private void limpar(ActionEvent e) {
		tabbedPane.setSelectedIndex(1);
		textAreaValoresAtivosProfit.setText("");
		textAreaValoresAtivosProfit.grabFocus();
	}
	
	private Map<String, Float> gerarMapaDeCotacoes() {
		String[] split = textAreaValoresAtivosProfit.getText().split("\n");
		List<String[]> listCotacoes = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			listCotacoes.add(split[i].split("\t"));
		}
		
		Map<String, Float> map = new HashMap<String, Float>();
		int i = 0;
		for (String[] strings : listCotacoes) {
			if(i++ != 0) {
				if(strings.length>1) {
					map.put(strings[0], new Float(strings[1].replace(".", "").replace(",", ".")));
				}
			}
		}
		return map;
	}
	
}

