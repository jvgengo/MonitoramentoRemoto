package Monitoramento;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class Mensageiro extends UnicastRemoteObject implements InterfaceMensageiro{
	
	protected Mensageiro() throws RemoteException {
		super();
	}
	
	@Override
	public void desligar() throws IOException {
		Runtime.getRuntime().exec("cmd /c c:\\windows\\system32\\shutdown -s -f -t 0");
	}

	@Override
	public byte[] getMacAdrress() throws RemoteException {
        try {

            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            byte[] mac = network.getHardwareAddress();
            return mac;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
