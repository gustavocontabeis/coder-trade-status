package br.com.codersistemas.codertradestatus.ui;

import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import br.com.codersistemas.codertradestatus.builder.PanelBuilder;

public class RoboGemPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int x, y;
	
	private JTextField txtComentario = new JTextField();
	private JButton btComentario = new JButton("Comentário");
	
	private JSpinner txtX = new JSpinner();
	private JSpinner txtY = new JSpinner();
	private JButton btClicar = new JButton("Clicar");
	private JButton btClicar2x = new JButton("Clicar 2x");
	private JButton btCapturarPosicao = new JButton("Capturar Posição");
	
	private JTextField txtEscrever = new JTextField();
	private JButton btEscrever = new JButton("Escrever");
	private JButton btExibirMensagem = new JButton("Exibir Mensagem");
	
	private JTextField txtAguardar = new JTextField("1000");
	private JButton btAguardar = new JButton("Aguardar");
	
	private JComboBox<String> txtCtrlAtalho = new JComboBox<>();
	private JCheckBox chkCtrl = new JCheckBox("Ctrl");
	private JCheckBox chkShift = new JCheckBox("Shilf");
	private JCheckBox chkAlt = new JCheckBox("Alt");
	private JButton btCtrlAtalho = new JButton("Press");
	private JTextArea txtCodigo = new JTextArea(8, 26);
	
	public RoboGemPanel() {
		
		setLayout(null);
		//setSize(300, 600);
		
		NullLayoutHelper nlh = new NullLayoutHelper(5, 5, 5);
		
		JPanel panelRobo = new PanelBuilder()
				.row()
				.add(new JLabel("Comentário"))
				.row()
				.add(txtComentario)
				.row()
				.add(btComentario)
				.row()
				.add(new JLabel("X"))
				.add(new JLabel("Y"))
				.row()
				.add(txtX)
				.add(txtY)
				.row()
				.add(btClicar).add(btClicar2x).add(btCapturarPosicao)
				.row()
				.add(new JLabel("Escrever"))
				.row()
				.add(txtEscrever)
				.row()
				.add(btEscrever).add(btExibirMensagem)
				.row()
				.add(new JLabel("Aguardar"))
				.row()
				.add(txtAguardar)
				.row()
				.add(btAguardar)
				.row()
				.add(new JLabel("Atalho"))
				.row()
				.add(txtCtrlAtalho)
				.row()
				.add(chkCtrl).add(chkShift).add(chkAlt).add(btCtrlAtalho)
				.build();
		
		panelRobo.setBounds(nlh.getX(), nlh.getY(), nlh.setW(300), nlh.setH(350));
		
		add(panelRobo);
		
		Arrays.asList(new String[] {"ENTER", "ESC", "TAB", "A", "C", "V", "X"}).forEach(i->{txtCtrlAtalho.addItem(i);});
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBounds(nlh.getX(), nlh.getY(), nlh.setW(300), nlh.setH(160));
		p2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		p2.add(new JLabel("Código"), BorderLayout.NORTH);
		p2.add(new JScrollPane(txtCodigo), BorderLayout.CENTER);
		add(p2);
		
		btComentario.addActionListener(event->{comentario(event);});
		btClicar.addActionListener(event->{clicar(event);});
		btClicar2x.addActionListener(event->{clicar2x(event);});
		btCapturarPosicao.addActionListener(event->{capturarPosicao(event);});
		btEscrever.addActionListener(event->{escrever(event);});
		btExibirMensagem.addActionListener(event->{exibirMensagem(event);});
		btAguardar.addActionListener(event->{aguardar(event);});
		btCtrlAtalho.addActionListener(event->{atalho(event);});
	}

	private void atalho(ActionEvent event) {
		String code = "pressionar ";
		code += chkCtrl.isSelected()?"ctrl ":"";
		code += chkAlt.isSelected()?"alt ":"";
		code += chkShift.isSelected()?"shift ":"";
		code += this.txtCtrlAtalho.getSelectedItem().toString();
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void aguardar(ActionEvent event) {
		String code = "aguardar " + txtAguardar.getText();
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void escrever(ActionEvent event) {
		String code = "escrever " + txtEscrever.getText();
		System.out.println(code);
		adicionarAoCodigo(code); 
	}
	
	private void exibirMensagem(ActionEvent event) {
		String code = "exibirMensagem " + txtEscrever.getText();
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void clicar2x(ActionEvent event) {
		String code = "clicarEm2x " + x +" " + y;
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void clicar(ActionEvent event) {
		String code = "clicarEm " + x +" " + y;
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void comentario(ActionEvent event) {
		String code = "#" + txtComentario.getText();
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void capturarPosicao(ActionEvent event) {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		x = (int) b.getX();
		y = (int) b.getY();
		txtX.setValue(x);
		txtY.setValue(y);
		String code = "clicarEm " + x + " " + y;
		System.out.println(code);
		adicionarAoCodigo(code); 
	}

	private void adicionarAoCodigo(String code) {
		if(hasText(txtCodigo.getText()))
			txtCodigo.setText(txtCodigo.getText()+"\n"+code);
		else
			txtCodigo.setText(code);
	}

	private boolean hasText(String text) {
		return text != null && text.length() > 0;
	}

	public static void main(String args[]) {

		JFrame frame = new JFrame("Robot Coder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(325, 600);
		frame.getContentPane().add(new RoboGemPanel());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

}
