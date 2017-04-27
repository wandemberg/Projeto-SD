package br.ufc.cliente;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufc.MensagemEquipamento;
import br.ufc.Lampada;

public class ClienteLampada extends Thread{

	private String host;
	private int porta;

	private boolean terminar = false;
	private MensagemEquipamento equipamento;
	private Lampada lampada;
	private int tempoTransmissaoEstadoSensor = 6000;

	public ClienteLampada (String host, int porta) {
		this.host = host;
		this.porta = porta;
		lampada = new Lampada();
	}

	@Override		
	public void run(){

		Socket cliente;
		try {
			cliente = new Socket(this.host, this.porta);

			System.out.println("O cliente se conectou ao servidor!");

			// thread para receber mensagens do servidor
			RecebedorLampada r = new RecebedorLampada(cliente.getInputStream(), this);
			new Thread(r).start();

			//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
			equipamento = new MensagemEquipamento();
			equipamento.setNome("Lampada 1");
			equipamento.setIndiceTipoSelecionado(1);
			oos.writeObject(equipamento);

			System.out.println("Enviou o tipo de objeto");

			while (!terminar) {

				//Mandar o status do objeto inteligente atual
				ObjectOutputStream oos2 = new ObjectOutputStream(cliente.getOutputStream());

				//Ficar variando o resultado para o cliente
				//			lampada.setLigar(!lampada.isLigar());

				//Só faz retransmitir o objeto atual

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
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		// dispara cliente
		//		new ClienteLampada("127.0.0.1", 12345).executa();

		//Implementação utilizando Thread
		new ClienteLampada("127.0.0.1", 12345).start();

	}

	public MensagemEquipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(MensagemEquipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Lampada getLampada() {
		return lampada;
	}

	public void setLampada(Lampada lampada) {
		this.lampada = lampada;
	}

}
