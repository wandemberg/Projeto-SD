package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.Equipamento;
import br.ufc.Radio;

public class TrataClienteRadio implements Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;
	private Equipamento equipamento;
	private boolean terminar = false;

	public TrataClienteRadio(Socket cliente, Servidor servidor, Equipamento equipamento) {
		this.cliente = cliente;
		this.servidor = servidor;
		this.equipamento = equipamento;
	}

	public void run() {

		ObjectInputStream ois = null;

		while (!terminar ) {
			//Receber o tipo do equipamento que abriu a comunicação 
			try {
				ois = new ObjectInputStream(cliente.getInputStream());
				objetoAtualizado =  ois.readObject();

				System.out.println("Informacao recebida do cliente Radio se a Radio esta ligada:" + ((Radio)objetoAtualizado).isLigar());			
				//Tratar esse indice esta preso ao numero do cliente
				enviarMensagemClienteTv(objetoAtualizado);
			} catch (IOException e) {
				e.printStackTrace();
				terminar = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				terminar = true;
			}
		}

		try {
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean statusRadio = false;

	public void enviarMensagemClienteTv(Object objEnviar) {
		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());

			statusRadio = !statusRadio;
			Radio radio = new Radio();
			radio.setLigar(statusRadio);

			oos2.writeObject(radio);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}