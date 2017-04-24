package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.ufc.Arcondicionado;
import br.ufc.Equipamento;

public class ClienteArcondicionado {

	private String host;
	private int porta;

	private boolean terminar = false;
	private Equipamento equipamento;
	private Arcondicionado arcondicionado;

	public ClienteArcondicionado (String host, int porta) {

		this.host = host;
		this.porta = porta;
		arcondicionado = new Arcondicionado();

	}



	public void executa() throws UnknownHostException, IOException {

		Socket cliente = new Socket(this.host, this.porta);

		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor

		RecebedorArcondicionado r = new RecebedorArcondicionado(cliente, this);
		new Thread(r).start();


		//Mandar um objeto com o tipo do objeto
		ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
		equipamento = new Equipamento();
		oos.writeObject(equipamento);

		System.out.println("Enviou o tipo de objeto");
		
		// lê msgs do teclado e manda pro servidor

		//		Scanner teclado = new Scanner(System.in);
		//
		//		PrintStream saida = new PrintStream(cliente.getOutputStream());

		//		while (teclado.hasNextLine()) {
		//
		//			saida.println(teclado.nextLine());
		//
		//		}


//		int temp = 20;

		while (!terminar) {

			//Mandar um objeto com o tipo Arcondicionado
			ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());
//			arcondicionado = new Arcondicionado();
//			arcondicionado.setTemperaturaProgramada(temp);
			oos2.writeObject(arcondicionado);
			//Aumentar, ou seja, variar a temperatura
//			temp++;

			try {
				Thread.sleep(5500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Envou ao servidor um objeto Arcondicionado");
//			if(temp >= 30)
//				temp = arcondicionado.getTemperaturaMinima();
		}



		//		saida.close();
		//
		//		teclado.close();

		cliente.close();    

	}

	public static void main(String[] args) 

			throws UnknownHostException, IOException {

		// dispara cliente

		new ClienteArcondicionado("127.0.0.1", 12345).executa();

	}



	public Equipamento getEquipamento() {
		return equipamento;
	}



	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}



	public Arcondicionado getArcondicionado() {
		return arcondicionado;
	}



	public void setArcondicionado(Arcondicionado arcondicionado) {
		this.arcondicionado = arcondicionado;
	}



}
