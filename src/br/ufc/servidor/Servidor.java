package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.ufc.Arcondicionado;
import br.ufc.Equipamento;

public class Servidor {

	private int porta;

	private List<Socket> clientes;


	public Servidor (int porta) {

		this.porta = porta;
		this.clientes = new ArrayList<Socket>();

	}

	public void executa () throws IOException {

		ServerSocket servidor = new ServerSocket(this.porta);

		System.out.println("Porta 12345 aberta!");

		while (true) {

			// aceita um cliente

			Socket cliente = servidor.accept();

			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

			//Receber o tipo do equipamento que abriu a comunicação 
			ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
			
			Equipamento obj;
			TrataCliente tc = null;
			
			try {
				obj = (Equipamento) ois.readObject();
				System.out.println("Tipo do equipamento é " + obj.getTipo());
				tc = new TrataCliente(cliente, this, obj);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			

			// adiciona saida do cliente à lista

			PrintStream ps = new PrintStream(cliente.getOutputStream());

			this.clientes.add(cliente);


			// cria tratador de cliente numa nova thread


			
			new Thread(tc).start();

		}
	}

	public void distribuiMensagem(String msg) {

//		// envia msg para todo mundo
//		for (PrintStream cliente : this.clientes) {
//			cliente.println(msg);
//		}

	}
	
	int temp = 18;
	
	public void enviarMensagemCliente(int indiceCliente, Object objEnviar) {

		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(clientes.get(indiceCliente).getOutputStream());
			
			Arcondicionado arcondicionado = new Arcondicionado();
			arcondicionado.setTemperaturaProgramada(temp);
			oos2.writeObject(arcondicionado);
			
			temp++;
			if(temp >= arcondicionado.getTemperaturaMaxima())
				temp = arcondicionado.getTemperaturaMinima();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
		
	}
	

	public static void main(String[] args) throws IOException {

		// inicia o servidor
		new Servidor(12345).executa();

	}
}