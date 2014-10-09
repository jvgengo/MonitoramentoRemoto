package DesligarPC.Cliente;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import DesligarPC.Servidor.InterfaceDesligar;

public class Cliente {
	
	public static void main(String[] args) {
		desligar();
	}
	
	public static void desligar() {
		try {
			Registry registro = LocateRegistry.getRegistry("localhost",8080);
			
			InterfaceDesligar iDesliga = (InterfaceDesligar) registro.lookup("desliga");
			iDesliga.desligar();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
