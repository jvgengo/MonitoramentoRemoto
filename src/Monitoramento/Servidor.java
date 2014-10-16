package Monitoramento;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor{
	
	public static final int PORTA = 5128;
	public static void main(String[] args) {
		iniciarServidor();
	}
	
	private static void iniciarServidor() {
            try {

                InterfaceMensageiro m = new Mensageiro();
				java.rmi.registry.LocateRegistry.createRegistry(5128);
			    Naming.rebind("rmi://localhost:"+PORTA+"/Servidor", m);
	            System.out.println("Aguardando...");
	       
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

        
	}
	
}
