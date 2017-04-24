package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import br.ufc.Arcondicionado;

public class RecebedorArcondicionado implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private ClienteArcondicionado cliente;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorArcondicionado(InputStream servidor, ClienteArcondicionado cliente) {

		this.servidor = servidor;
		this.cliente = cliente;
	}



	public void run() {

		
		ObjectInputStream ois = null;

		// recebe msgs do servidor e imprime na tela

		while (!terminar) {
			//Receber o tipo dados do sensor 
			try {
				ois = new ObjectInputStream(servidor);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				objetoAtualizado =  ois.readObject();
//				ois.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			int tempProgramada = ((Arcondicionado)objetoAtualizado).getTemperaturaProgramada();
			
			cliente.getArcondicionado().setTemperaturaProgramada(tempProgramada);
			
			System.out.println("Temperatura recebida do Servidor e atualizada:" + tempProgramada);
			
		}
		
//		Scanner s = new Scanner(this.servidor);
//
//		while (s.hasNextLine()) {
//
//			System.out.println(s.nextLine());
//
//		}

	}

}
