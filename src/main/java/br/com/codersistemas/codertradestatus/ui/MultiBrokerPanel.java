package br.com.codersistemas.codertradestatus.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.codersistemas.codertradestatus.robot.CoderRobot;

public class MultiBrokerPanel extends JPanel {

	private static final long serialVersionUID = -3556068906690980070L;

	private CoderRobot robot;

	public MultiBrokerPanel() throws HeadlessException, AWTException {
		setLayout(new GridLayout(1,2));
		robot = new CoderRobot();
		
		painelAcessos();
		painelRoboGem();
		
	}

	private void painelRoboGem() {
		add(new RoboGemPanel(), BorderLayout.EAST);
	}

	private void painelAcessos() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7,2));
		panel.setSize(800, 500);
		panel.setBorder(BorderFactory.createTitledBorder("Acessos"));
		add(panel, BorderLayout.WEST);
		
		File[] listFiles = new File(".").listFiles();
		Arrays.sort(listFiles);
		Arrays.asList(listFiles).stream().filter(f->f.getName().toLowerCase().endsWith("-robot.properties")).forEach(f->criarBotao(f, panel));
	}

	private void criarBotao(File file, JPanel panel) {
		JButton btChangeLautenir = new JButton(file.getName().replace("-robot.properties", ""));
		btChangeLautenir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					executarRobo(file.getAbsolutePath());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		panel.add(btChangeLautenir);
	}

	private void executarRobo(String path)
			throws AWTException, FileNotFoundException, IOException, InterruptedException {
		
		System.out.println("Executar Robo");
		
		File file = new File(path);
		
		Properties prop = new Properties();
	    
	    prop.load(new FileInputStream(file));
	            
	    // get all keys
	    Set<Object> keySet = prop.keySet();
	    
	    file = new File(prop.getProperty("file"));
	    
	    if(!prop.contains("file")) {
	    	throw new RuntimeException("Propriedade file não encontada.");
	    }
	    
	    if(!file.exists()) {
	    	throw new RuntimeException("Arquivo "+prop.getProperty("file")+ " não encontrado");
	    }
	    
	    BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		while ((st = br.readLine()) != null) {
			
			for (Object key : keySet) {
				String value = prop.getProperty(key.toString());
				st = st.replace("${"+key.toString()+"}", value); 
			}
			
			if (st.startsWith("#") || "".equals(st)) {
				System.out.println(st);
				continue;
			}
			
			String[] split = st.split("\\s");
			if ("clicarEm".equals(split[0].trim())) {
				robot.clicarEm(new Integer(split[1]), new Integer(split[2]));
			} else if ("clicarEm2x".equals(split[0].trim())) {
				robot.clicarEm2x(new Integer(split[1]), new Integer(split[2]));
			} else if ("aguardar".equals(split[0].trim())) {
				robot.aguardar(new Integer(split[1]));
			} else if ("escrever".equals(split[0].trim())) {
				robot.escrever(st.substring(st.indexOf(" ")).trim());
			} else if ("enter".equals(split[0].trim())) {
				robot.executar(KeyEvent.VK_ENTER);
			} else if ("tab".equals(split[0].trim())) {
				robot.executar(KeyEvent.VK_TAB);
			} else if ("space".equals(split[0].trim())) {
				robot.executar(KeyEvent.VK_SPACE);
			} else if ("exibirMensagem".equals(split[0].trim())) {
				JOptionPane.showInputDialog("Copie este valor", st.replace(split[0], "").trim());
			} else if ("atalhos".equals(split[0].trim())) {
				//robot.atalho(KeyEvent.VK_CONTROL, KeyEvent.VK_ENTER);
			} else if ("pressionar".equals(split[0].trim())) {
				List<String> subList = Arrays.asList(split).subList(1, split.length);
				robot.atalho(subList.toArray(new String[subList.size()]));
			}else {
				JOptionPane.showMessageDialog(null, "COMANDO INVÁLIDO: "+split[0].trim());
				System.out.println("COMANDO INVÁLIDO: "+split[0].trim());
			}
		}
		System.out.println("Fim");
		JOptionPane.showMessageDialog(this, "Fim");
	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				try {
					MultiBrokerPanel x = new MultiBrokerPanel();
				} catch (HeadlessException | AWTException e) {
					e.printStackTrace();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (HeadlessException e) {
			e.printStackTrace();
		}
	}
}
