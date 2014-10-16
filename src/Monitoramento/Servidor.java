package Monitoramento;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor{
	public static void main(String[] args) {
		iniciarServidor();
	}
	
	private static void iniciarServidor() {
		try {
			Registry registro = LocateRegistry.createRegistry(5024);
			
			registro.rebind("rmi://localhost:5024/Servidor", new Mensageiro());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
}
