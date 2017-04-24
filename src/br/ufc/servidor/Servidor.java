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

	private boolean terminar = false;


	public Servidor (int porta) {

		this.porta = porta;
		this.clientes = new ArrayList<Socket>();

	}

	public void executa () throws IOException {

		ServerSocket servidor = new ServerSocket(this.porta);

		System.out.println("Porta 12345 aberta!");

		while (!terminar ) {

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
				} else if (obj.getIndiceTipoSelecionado() == 2){
					TrataClienteCortina tc = new TrataClienteCortina(cliente, this, obj);
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


	public static void main(String[] args) throws IOException {

		// inicia o servidor
		new Servidor(12345).executa();

	}
}