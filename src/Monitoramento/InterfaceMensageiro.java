package Monitoramento;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceMensageiro extends Remote {
	public boolean desligar() throws IOException;

	public byte[] getMacAdrress() throws RemoteException;

	public byte[] getScreenShot() throws RemoteException;

	public byte[] getPreviewShot() throws RemoteException;

}
