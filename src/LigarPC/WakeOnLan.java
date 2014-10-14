package LigarPC;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WakeOnLan {

	public void WOL() {
		final int PORT = 9;

		String ipStr = "";

		InetAddress inet;

		try {
			inet = InetAddress.getLocalHost();
			ipStr = inet.getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		String macStr = "";

		
		try {

			byte[] macBytes = getMacBytes(macStr);

			byte[] bytes = new byte[6 + 16 * macBytes.length];

			for (int i = 0; i < 6; i++) {

				bytes[i] = (byte) 0xff;

			}

			for (int i = 6; i < bytes.length; i += macBytes.length) {

				System.arraycopy(macBytes, 0, bytes, i, macBytes.length);

			}

			InetAddress address = InetAddress.getByName(ipStr);

			DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
					address, PORT);
			DatagramSocket socket = new DatagramSocket();

			socket.send(packet);

			socket.close();

			System.out.println("Comando WOL enviado");

		} catch (Exception e) {
			System.out.println("Falha ao enviar comando " + e);

			System.exit(1);
		}

	}

	private static byte[] getMacBytes(String macStr)
			throws IllegalArgumentException {
		byte[] bytes = new byte[6];

		String[] hex = macStr.split("(\\:|\\-)");

		if (hex.length != 6) {

			throw new IllegalArgumentException("Endereço MAC inválido");

		}

		for (int i = 0; i < 6; ++i) {

			bytes[i] = (byte) Integer.parseInt(hex[i], 16);

			throw new IllegalArgumentException(
					"Endereço Hexadecimal MAC inválido");

		}

		return bytes;
	}
}
