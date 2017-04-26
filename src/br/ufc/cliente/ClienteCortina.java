package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufc.Cortina;
import br.ufc.MensagemEquipamento;

public class ClienteCortina {

	private String host;
	private int porta;

	private boolean terminar = false;
	private MensagemEquipamento equipamento;
	private Cortina cortina;
	private int tempoTransmissaoEstadoSensor = 5500;

	public ClienteCortina (String host, int porta) {
		this.host = host;
		this.porta = porta;
		cortina = new Cortina();
	}

	public void executa() throws UnknownHostException, IOException {

		Socket cliente = new Socket(this.host, this.porta);

		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor
		RecebedorCortina r = new RecebedorCortina(cliente.getInputStream(), this);
		new Thread(r).start();

		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
		equipamento = new MensagemEquipamento();
		equipamento.setNome("Cortina 1");
		equipamento.setIndiceTipoSelecionado(2);
		oos.writeObject(equipamento);

		System.out.println("Enviou o tipo de objeto");

		while (!terminar) {

			//Mandar o status do objeto inteligente atual
			ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());
			//Ficar variando o resultado para o cliente
			cortina.setLevantar(!cortina.isLevantar());
			oos2.writeObject(cortina);

			try {
				Thread.sleep(tempoTransmissaoEstadoSensor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Enviou ao servidor o status atual do objeto " + equipamento.getTipo() 
			+ " com valor: " + cortina.isLevantar());
		}


		//		saida.close();
		//
		//		teclado.close();

		cliente.close();    

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// dispara cliente
		new ClienteCortina("127.0.0.1", 12345).executa();

	}

	public MensagemEquipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(MensagemEquipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Cortina getCortina() {
		return cortina;
	}

	public void setCortina(Cortina cortina) {
		this.cortina = cortina;
	}



}
