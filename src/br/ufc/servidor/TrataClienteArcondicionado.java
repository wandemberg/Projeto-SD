package br.ufc.servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

import br.ufc.Arcondicionado;
import br.ufc.Equipamento;

public class TrataClienteArcondicionado implements Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;
	private Equipamento equipamento;

	public TrataClienteArcondicionado(Socket cliente, Servidor servidor, Equipamento equipamento) {
		this.cliente = cliente;
		this.servidor = servidor;
//		this.objetoAtualizado = tipoObjeto;
		this.equipamento = equipamento;
	}

	public void run() {

		// quando chegar uma msg, distribui pra todos
		//
		//		Scanner s = new Scanner(this.cliente);
		//
		//		while (s.hasNextLine()) {
		//
		//			servidor.distribuiMensagem(s.nextLine());
		//
		//		}

		ObjectInputStream ois = null;
		


		while (true) {
			//Receber o tipo do equipamento que abriu a comunicação 
			try {
				ois = new ObjectInputStream(cliente.getInputStream());
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
			System.out.println("Temperatura recebida do cliente :" + ((Arcondicionado)objetoAtualizado).getTemperaturaProgramada());
			
			servidor.enviarMensagemClienteArcondicionado(0, objetoAtualizado);
		}



		//		s.close();

	}

}