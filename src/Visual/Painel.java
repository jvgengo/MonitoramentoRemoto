package Visual;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import Monitoramento.Monitor;
import exeverificacabo.Verificadora;

@SuppressWarnings("serial")
public class Painel extends JPanel {

	public static final int LARGURA = 400;
	public static final int ALTURA = 100;
	
	private Monitor monitor;

	private JButton btnDesligar, btnVerificaCabo, btnLigar, btnLigarArquivo,
			btnDesligarArquivo;

	public Painel() {
		monitor = new Monitor();
		setSize(LARGURA, ALTURA);
		setFocusable(true);
		requestFocus();
		iniciarComponentes();
		setVisible(true);
	}

	private void iniciarComponentes() {

		FlowLayout layout = new FlowLayout();
		this.setLayout(layout);

		btnDesligar = new JButton("Delisgar PC");
		btnDesligar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.desligar();
			}

		});

		this.add(btnDesligar);

		btnLigar = new JButton("Ligar pc");
		btnLigar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.ligarPc();
			}

		});

		this.add(btnLigar);

		btnLigarArquivo = new JButton("Ligar Arq");
		btnLigarArquivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				monitor.ligarPcArquivo(nomeArq);
			}

		});

		this.add(btnLigarArquivo);

		btnDesligarArquivo = new JButton("Desligar Arq");
		btnDesligarArquivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				monitor.desligarPcArquivo(nomeArq);
			}

		});

		this.add(btnDesligarArquivo);

		btnVerificaCabo = new JButton("Verificar cabo");
		btnVerificaCabo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Verificadora v = new Verificadora();
				v.iniciarVerificadora();
			}

		});

		this.add(btnVerificaCabo);

		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

	}

}
