package Monitoramento;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
			InterfaceMensageiro iDesliga = (InterfaceMensageiro) Naming.lookup("rmi://localhost:" + Servidor.PORTA + "/Servidor");
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
					m = (InterfaceMensageiro) Naming.lookup("rmi://" + ip + ":"
							+ Servidor.PORTA + "/Servidor");
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

	public void ligarPc(String mac) {

		try {

			byte[] macBytes = mac.getBytes();

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
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				System.err.println("Impossível fechar o arquivo.");
			}
		}

	}

	private void ligar(byte[] macBytes) throws UnknownHostException,
			SocketException, IOException {
		byte[] bytes = new byte[6 + 16 * macBytes.length];

		for (int i = 0; i < 6; i++) {

			bytes[i] = (byte) 0xff;

		}

		for (int i = 6; i < bytes.length; i += macBytes.length) {

			System.arraycopy(macBytes, 0, bytes, i, macBytes.length);

		}

		InetAddress address = InetAddress.getByName("255.255.255.255");

		DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
				address, PORT);
		DatagramSocket socket = new DatagramSocket();
		socket.setBroadcast(true);
		socket.send(packet);

		socket.close();

		System.out.println("Comando WOL enviado");
	}

	public void visualizarTela(String ip) {
		InterfaceMensageiro m;
		BufferedImage img = null;
		try {
			System.out.println("Enviado para ... rmi://" + ip + ":"
					+ Servidor.PORTA + "/Servidor");

			m = (InterfaceMensageiro) Naming.lookup("rmi://" + ip + ":" + Servidor.PORTA
					+ "/Servidor");
			img = ImageIO.read(new ByteArrayInputStream(m.getScreenShot()));

			class Painel extends JPanel {

				BufferedImage imagem;

				public Painel(BufferedImage imagem) {
					this.imagem = imagem;
				}

				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D) g;
					g2d.drawImage(imagem, null, 0, 0);
				}
			}
			JFrame frame = new JFrame();
			frame.setAlwaysOnTop(true);
			frame.setSize(img.getWidth(), img.getHeight());
			frame.setTitle("Telando " + ip);
			Container pane = frame.getContentPane();

			Painel painel = new Painel(img);

			pane.add(painel);

			frame.setVisible(true);

		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
