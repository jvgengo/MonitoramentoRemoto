package Monitoramento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Monitor {

	private Mensageiro mensageiro;
	final int PORT = 9;

	public Monitor() {
		try {
			mensageiro = new Mensageiro();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void desligar() {
		try {
			InterfaceMensageiro iDesliga = (InterfaceMensageiro) Naming.lookup("rmi://localhost:5024/Servidor");
			iDesliga.desligar();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void desligarPcArquivo(String nomeArq) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(nomeArq);
			Scanner out = new Scanner(in);

			while (out.hasNextLine()) {
				String ip = out.nextLine();
				InterfaceMensageiro m;
				try {
					m = (InterfaceMensageiro) Naming.lookup("rmi://" + ip
							+ ":5024/Servidor");
					if (m.desligar()) {
						System.out.println("Computador " + ip
								+ " desligado com sucesso.");
					} else {
						System.out.println("Computador " + ip
								+ " não desligou.");
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				System.err.println("Impossível fechar o arquivo.");
			}
		}

	}

	public void ligarPc() {
		
		try {

			byte[] macBytes = mensageiro.getMacAdrress();
			// String aux = "80:C1:6E:8C:BA:40";
			// byte[] macBytes = aux.getBytes();

			ligar(macBytes);

		} catch (Exception e) {
			System.out.println("Falha ao enviar comando " + e);

			System.exit(1);
		}
	}

	public void ligarPcArquivo(String nomeArq) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(nomeArq);
			Scanner out = new Scanner(in);
			while (out.hasNextLine()) {
				String macAux = out.nextLine();
				StringTokenizer st = new StringTokenizer(macAux, ":");

				byte mac[] = new byte[6];
				for (int i = 0; i < 6; ++i) {
					mac[i] = (byte) Integer.parseInt(st.nextToken(), 16);
				}
				
				try {
					ligar(mac);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
            try {
                in.close();
            } catch (IOException ex) {
                System.err.println("Impossível fechar o arquivo.");
            }
        }

	}

	private void ligar(byte[] macBytes)
			throws UnknownHostException, SocketException, IOException {
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
	}

}
