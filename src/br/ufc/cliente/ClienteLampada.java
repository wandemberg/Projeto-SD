package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.ufc.Arcondicionado;
import br.ufc.Equipamento;
import br.ufc.Lampada;

public class ClienteLampada {

	private String host;
	private int porta;

	private boolean terminar = false;
	private Equipamento equipamento;
	private Lampada lampada;
	private int tempoTransmissaoEstadoSensor = 5500;

	public ClienteLampada (String host, int porta) {
		this.host = host;
		this.porta = porta;
		lampada = new Lampada();
	}

	public void executa() throws UnknownHostException, IOException {

		Socket cliente = new Socket(this.host, this.porta);

		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor
		RecebedorLampada r = new RecebedorLampada(cliente.getInputStream(), this);
		new Thread(r).start();

		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
		equipamento = new Equipamento();
		equipamento.setNome("Lampada 1");
		equipamento.setIndiceTipoSelecionado(1);
		oos.writeObject(equipamento);

		System.out.println("Enviou o tipo de objeto");

		while (!terminar) {

			//Mandar o status do objeto inteligente atual
			ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());
			//Ficar variando o resultado para o cliente
			lampada.setLigar(!lampada.isLigar());
			oos2.writeObject(lampada);

			try {
				Thread.sleep(tempoTransmissaoEstadoSensor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Enviou ao servidor o status atual do objeto " + equipamento.getTipo() 
			+ " com valor: " + lampada.isLigar());
		}


		//		saida.close();
		//
		//		teclado.close();

		cliente.close();    

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// dispara cliente
		new ClienteLampada("127.0.0.1", 12345).executa();

	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Lampada getLampada() {
		return lampada;
	}

	public void setLampada(Lampada lampada) {
		this.lampada = lampada;
	}

}
