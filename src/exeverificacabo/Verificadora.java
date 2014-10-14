package exeverificacabo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Verificadora implements Runnable{

	private Thread tVerifica;
	private NetworkInterface ni;
	private boolean rodando;
	private Log log;
	private String usuario;
	private String ip;
	
	private boolean desconectou = false;
	private boolean desconectadoAntes = false;
	
	public Verificadora() {
		
		tVerifica = new Thread(this);
		rodando = false;
		log = new Log();
		
		try { 
			ni = NetworkInterface.getByName("eth3");
		} catch (SocketException e) {
			System.err.println("Erro na NetworkInterface");
		}
		
		usuario = System.getProperty("user.name");
		
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Erro ao pegar o Ip");
		}
		
	}
	
	public void iniciarVerificadora() {
		rodando = true;
		tVerifica.start();
	}
	
	private boolean verificaCabo() throws SocketException, InterruptedException {
		desconectadoAntes = desconectou;//guarda estado anterior 
		desconectou = !ni.isUp();		//fica true se n�o estiver conectado
		Thread.sleep(1000);
		return ni.isUp();
	}
	
	public void pararVerificadora() {
		rodando = false;
	}
	
	public void run(){
		while(rodando) {
			try {
				if (!verificaCabo())
					if (desconectadoAntes!=desconectou)	//se o estado atual diferente do anterior e se o estado atual = desconectado
							log.notificar(usuario,ip);
			} catch (IOException e) {
				e.getStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
		
}
