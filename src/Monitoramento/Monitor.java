package Monitoramento;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Monitor {
	
	private Mensageiro mensageiro;
	
	public Monitor() {
		try {
			mensageiro = new Mensageiro();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void desligar() {
		try {
			Registry registro = LocateRegistry.getRegistry("localhost",5024);
			
			InterfaceMensageiro iDesliga = (InterfaceMensageiro) registro.lookup("rmi://localhost:5024/Servidor");
			iDesliga.desligar();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  void ligarPc() {
		final int PORT = 9;
		try {

			byte[] macBytes = mensageiro.getMacAdrress();
//			String aux = "80:C1:6E:8C:BA:40";
//			byte[] macBytes = aux.getBytes();

			byte[] bytes = new byte[6 + 16 * macBytes.length];

			for (int i = 0; i < 6; i++) {

				bytes[i] = (byte) 0xff;

			}

			for (int i = 6; i < bytes.length; i += macBytes.length) {

				System.arraycopy(macBytes, 0, bytes, i, macBytes.length);

			}

			InetAddress address = InetAddress.getByName("255.255.255.255");

			DatagramPacket packet = new DatagramPacket(bytes, bytes.length,address, PORT);
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			socket.send(packet);

			socket.close();

			System.out.println("Comando WOL enviado");

		} catch (Exception e) {
			System.out.println("Falha ao enviar comando " + e);

			System.exit(1);
		}

	}
	
}
