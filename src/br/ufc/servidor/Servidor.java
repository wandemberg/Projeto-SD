package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.MensagemEquipamento;
import br.ufc.MensagemControlador;

public class Servidor {

	private int porta;

	private Map<String, TrataCliente> clientes;

	private boolean terminar = false;


	public Servidor (int porta) {

		this.porta = porta;
		this.clientes = new HashMap<String,TrataCliente>();

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

			MensagemEquipamento obj;

			try {
				obj = (MensagemEquipamento) ois.readObject();
				System.out.println("Tipo do equipamento " + obj.getTipo());

				//Selecionar o tipo de objeto a ser tratado
				if (obj.getIndiceTipoSelecionado() == 0) {
					TrataClienteArcondicionado tc = new TrataClienteArcondicionado(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Arcondicionado",tc);
				} else if (obj.getIndiceTipoSelecionado() == 1){
					TrataClienteLampada tc = new TrataClienteLampada(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Lampada",tc);
				} else if (obj.getIndiceTipoSelecionado() == 2){
					TrataClienteCortina tc = new TrataClienteCortina(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Cortina",tc);
				} else if (obj.getIndiceTipoSelecionado() == 3){
					TrataClienteTv tc = new TrataClienteTv(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Tv",tc);
				} else if (obj.getIndiceTipoSelecionado() == 4){
					TrataClienteRadio tc = new TrataClienteRadio(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Radio",tc);
				} else if (obj.getIndiceTipoSelecionado() == 5){
					TrataClienteControlador tc = new TrataClienteControlador(cliente, this, obj);
					// cria tratador de cliente numa nova thread
					new Thread(tc).start();
					// adiciona saida do cliente à lista
					this.clientes.put("Controlador",tc);
				}										


				//				// adiciona saida do cliente à lista
				//				this.clientes.add(cliente);


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}



		}
	}

	public void enviarMensagemControlador(MensagemControlador msgControlador){
		if (clientes.get("Controlador") != null)
			clientes.get("Controlador").enviarMensagem(msgControlador);;
	}

	public static void main(String[] args) throws IOException {

		// inicia o servidor
		new Servidor(12345).executa();

	}

	public Map<String, TrataCliente> getClientes() {
		return clientes;
	}
}