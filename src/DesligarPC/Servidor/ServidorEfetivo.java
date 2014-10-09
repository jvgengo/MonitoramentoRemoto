package DesligarPC.Servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorEfetivo{
	public static void main(String[] args) {
		iniciarServidor();
	}
	
	private static void iniciarServidor() {
		try {
			Registry registro = LocateRegistry.createRegistry(8080);
			
			registro.rebind("desliga", new Servidor());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
}
