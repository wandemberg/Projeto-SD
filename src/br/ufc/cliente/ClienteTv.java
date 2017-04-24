package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufc.Equipamento;
import br.ufc.Tv;

public class ClienteTv {

	private String host;
	private int porta;

	private boolean terminar = false;
	private Equipamento equipamento;
	private Tv tv;
	private int tempoTransmissaoEstadoSensor = 5500;

	public ClienteTv (String host, int porta) {
		this.host = host;
		this.porta = porta;
		tv = new Tv();
	}

	public void executa() throws UnknownHostException, IOException {

		Socket cliente = new Socket(this.host, this.porta);

		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor
		RecebedorTv r = new RecebedorTv(cliente.getInputStream(), this);		
		new Thread(r).start();

		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
		equipamento = new Equipamento();
		equipamento.setNome("TV 1");
		equipamento.setIndiceTipoSelecionado(3);
		oos.writeObject(equipamento);

		System.out.println("Enviou o tipo de objeto");

		while (!terminar) {

			//Mandar o status do objeto inteligente atual
			ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());
			//Ficar variando o resultado para o cliente
			tv.setLigar(!tv.isLigar());
			oos2.writeObject(tv);

			try {
				Thread.sleep(tempoTransmissaoEstadoSensor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Enviou ao servidor o status atual do objeto " + equipamento.getTipo() 
			+ " com valor: " + tv.isLigar());
		}


		//		saida.close();
		//
		//		teclado.close();

		cliente.close();    

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// dispara cliente
		new ClienteTv("127.0.0.1", 12345).executa();

	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Tv getTv() {
		return tv;
	}

	public void setTv(Tv tv) {
		this.tv = tv;
	}

	
	

}
