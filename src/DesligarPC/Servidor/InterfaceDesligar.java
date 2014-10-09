package DesligarPC.Servidor;

import java.io.IOException;
import java.rmi.Remote;

public interface InterfaceDesligar extends Remote{
	public void desligar() throws IOException;

}
