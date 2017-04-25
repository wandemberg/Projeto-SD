package br.ufc.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufc.Equipamento;
import br.ufc.Lampada;
import br.ufc.MensagemControlador;

public class Controlador {

	private Equipamento equi;
	private String host = "127.0.0.1";
	private int porta = 12345;
	private Socket cliente;

	public Controlador(Equipamento equi) {
		this.equi = equi;
		//Criar a primeira comunicação com o servidor para abrir o socket
		
		try {
			cliente = new Socket(this.host, this.porta);


			System.out.println("O controlador vai se conectar ao servidor!");

			// thread para receber mensagens do servidor
			RecebedorControlador r = new RecebedorControlador(cliente.getInputStream(), this);
			new Thread(r).start();

			//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());

			//Avisa que é um controlador
			oos.writeObject(this.equi);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enviou o tipo de objeto");

	}

	public static void main(String[] args) {
		Equipamento equipamento = new Equipamento();
		equipamento.setIndiceTipoSelecionado(5);
		equipamento.setNome("Controlador 1");

		Controlador contr1 = new Controlador(equipamento);

		MensagemControlador mc = new MensagemControlador();
		mc.setEnviar(true);

		Lampada la = new Lampada();
		la.setLigar(true);

		mc.setObj(la);

		contr1.enviarMensagem(mc);
		
		//se for para receber um objeto é 

	}

	public void enviarMensagem(MensagemControlador mc) {
		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(cliente.getOutputStream());
			//Avisa que é um controlador
			oos.writeObject(mc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
