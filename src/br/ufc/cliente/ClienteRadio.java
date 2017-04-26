package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufc.MensagemEquipamento;
import br.ufc.Radio;

public class ClienteRadio {

	private String host;
	private int porta;

	private boolean terminar = false;
	private MensagemEquipamento equipamento;
	private Radio radio;
	private int tempoTransmissaoEstadoSensor = 5500;

	public ClienteRadio (String host, int porta) {
		this.host = host;
		this.porta = porta;
		radio = new Radio();
	}

	public void executa() throws UnknownHostException, IOException {

		Socket cliente = new Socket(this.host, this.porta);

		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor
		RecebedorRadio r = new RecebedorRadio(cliente.getInputStream(), this);		
		new Thread(r).start();

		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
		equipamento = new MensagemEquipamento();
		equipamento.setNome("Radio 1");
		equipamento.setIndiceTipoSelecionado(4);
		oos.writeObject(equipamento);

		System.out.println("Enviou o tipo de objeto");

		while (!terminar) {

			//Mandar o status do objeto inteligente atual
			ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());
			//Ficar variando o resultado para o cliente
			radio.setLigar(!radio.isLigar());
			oos2.writeObject(radio);

			try {
				Thread.sleep(tempoTransmissaoEstadoSensor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Enviou ao servidor o status atual do objeto " + equipamento.getTipo() 
			+ " com valor: " + radio.isLigar());
		}


		//		saida.close();
		//
		//		teclado.close();

		cliente.close();    

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// dispara cliente
		new ClienteRadio("127.0.0.1", 12345).executa();

	}

	public MensagemEquipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(MensagemEquipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Radio getRadio() {
		return radio;
	}

	public void setRadio(Radio radio) {
		this.radio = radio;
	}
}
