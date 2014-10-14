package Visual;

import javax.swing.JFrame;

public class Tela {
	
	
	public Tela() {
		JFrame janela = new JFrame("Monitoramento Remoto");
		janela.setSize(Painel.LARGURA,Painel.ALTURA);
		janela.add(new Painel());
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Tela();
	}
	
}
