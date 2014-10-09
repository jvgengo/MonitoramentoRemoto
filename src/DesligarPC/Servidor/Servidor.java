package DesligarPC.Servidor;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class Servidor extends UnicastRemoteObject implements InterfaceDesligar{
	
	protected Servidor() throws RemoteException {
		super();
	}

	@Override
	public void desligar() throws IOException {
		Runtime.getRuntime().exec("cmd /c c:\\windows\\system32\\shutdown -s -f -t 0");
	}
}
