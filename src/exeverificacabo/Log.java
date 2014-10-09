package exeverificacabo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	
	private Calendar c;
	private File f;
	private BufferedWriter out;
	
	private static final String NOME_ARQ = "VerificaCabo.log";
	
	public Log() {
		c = Calendar.getInstance();
		f = new File("C:/Temp/"+NOME_ARQ);
			
	}
	
	public void notificar(String usuario, String ip) throws IOException {
		
		//pegando a data-hora
		StringBuilder notificacao = new StringBuilder();
		notificacao.append(c.getTime().toString())
						.append(";"+usuario)
							.append("; "+ip)
								.append(" Evento: Cabo desconectado");
		 
		
		try {
			out = new BufferedWriter(new FileWriter(f,true));
		} catch (IOException e) {
			System.out.println("Erro ao criar o Buffered");
		}
		
		out.newLine();
		out.append(notificacao.toString());
		out.flush();
		out.close();
		
		System.out.println("Gravou");
	}
	
	
	
	

}
