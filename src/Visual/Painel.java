package Visual;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import DesligarPC.Cliente.Cliente;
import LigarPC.WakeOnLan;
import exeverificacabo.Verificadora;

@SuppressWarnings("serial")
public class Painel extends JPanel {

	public static final int LARGURA = 400;
	public static final int ALTURA = 80;

	private JButton btnDesligar, btnVerificaCabo, btnLigar;

	public Painel() {
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
				Cliente.desligar();
			}
			
		});
		
		this.add(btnDesligar);
		

		btnVerificaCabo = new JButton("Verificar cabo");
		btnVerificaCabo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Verificadora v = new Verificadora();
				v.iniciarVerificadora();
			}
			
		});
		
		this.add(btnVerificaCabo);
		
		btnLigar = new JButton("Ligar pc");
		btnLigar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WakeOnLan wol = new WakeOnLan();
				wol.WOL();
			}
			
		});
		
		this.add(btnLigar);
		
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	}

}
