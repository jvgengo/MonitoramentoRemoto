package Visual;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Monitoramento.Monitor;

@SuppressWarnings("serial")
public class Painel extends JPanel {

	public static final int LARGURA = 400;
	public static final int ALTURA = 200;

	private Monitor monitor;

	private JButton btnDesligar, btnLigar, btnLigarArquivo, btnDesligarArquivo;
	private JTextField txtMac, txtNomeArq;

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
		BorderLayout bLayout = new BorderLayout();

		this.setLayout(bLayout);

		txtMac = new JTextField("Endereço mac:");
		txtMac.setEditable(true);
		add(BorderLayout.NORTH, txtMac);

		txtNomeArq = new JTextField("Nome arquivo:");
		txtNomeArq.setEditable(true);
		add(BorderLayout.CENTER, txtNomeArq);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(layout);

		btnDesligar = new JButton("Delisgar PC");
		btnDesligar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.desligar();
			}

		});

		painelBotoes.add(btnDesligar);

		btnDesligarArquivo = new JButton("Desligar Arq");
		btnDesligarArquivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.desligarPcArquivo(txtNomeArq.getText().toString());
			}

		});

		painelBotoes.add(btnDesligarArquivo);

		btnLigar = new JButton("Ligar pc");
		btnLigar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.ligarPc(txtMac.getText().toString());
			}

		});

		painelBotoes.add(btnLigar);

		btnLigarArquivo = new JButton("Ligar Arq");
		btnLigarArquivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor.ligarPcArquivo(txtNomeArq.getText().toString());
			}

		});

		painelBotoes.add(btnLigarArquivo);

		this.add(BorderLayout.SOUTH, painelBotoes);
	}

}
