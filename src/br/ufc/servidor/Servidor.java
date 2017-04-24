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
import br.ufc.Lampada;

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

			try {
				obj = (Equipamento) ois.readObject();
				System.out.println("Tipo do equipamento é " + obj.getTipo());
				//Selecionar o tipo de objeto a ser tratado
				if (obj.getIndiceTipoSelecionado() == 0) {
					TrataClienteArcondicionado tc = new TrataClienteArcondicionado(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
				} else if (obj.getIndiceTipoSelecionado() == 1){
					TrataClienteLampada tc = new TrataClienteLampada(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
				}				

				// adiciona saida do cliente à lista
				this.clientes.add(cliente);


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}



		}
	}


	int temp = 18;

	public void enviarMensagemClienteArcondicionado(int indiceCliente, Object objEnviar) {

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
			e1.printStackTrace();
		}

	}

	boolean statusLampada = false;
	public void enviarMensagemClienteLampada(int indiceCliente, Object objEnviar) {

		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(clientes.get(indiceCliente).getOutputStream());

			statusLampada = !statusLampada;
			Lampada lamp = new Lampada();
			lamp.setLigar(statusLampada);
			
			oos2.writeObject(lamp);


		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {

		// inicia o servidor
		new Servidor(12345).executa();

	}
}