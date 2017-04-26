package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.MensagemEquipamento;
import br.ufc.Tv;

public class TrataClienteTv implements TrataCliente, Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;
	private MensagemEquipamento equipamento;
	private boolean terminar = false;

	public TrataClienteTv(Socket cliente, Servidor servidor, MensagemEquipamento equipamento) {
		this.cliente = cliente;
		this.servidor = servidor;
		//		this.objetoAtualizado = tipoObjeto;
		this.equipamento = equipamento;
	}

	public void run() {

		ObjectInputStream ois = null;

		while (!terminar ) {
			//Receber o tipo do equipamento que abriu a comunicação 
			try {
				ois = new ObjectInputStream(cliente.getInputStream());
				objetoAtualizado =  ois.readObject();

				System.out.println("Informacao recebida do cliente TV se a TV esta ligada:" + ((Tv)objetoAtualizado).isLigar());			
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

	boolean statusTv = false;

	public void enviarMensagemClienteTv(Object objEnviar) {
		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());

			statusTv = !statusTv;
			Tv tv = new Tv();
			tv.setLigar(statusTv);

			oos2.writeObject(tv);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void enviarMensagem(Object objEnviar) {
		enviarMensagemClienteTv(objEnviar);
	}
}