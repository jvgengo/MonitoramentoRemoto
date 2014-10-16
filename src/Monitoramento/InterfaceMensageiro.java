package Monitoramento;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceMensageiro extends Remote{
	public void desligar() throws IOException;
	
	public byte[] getMacAdrress() throws RemoteException;

}
