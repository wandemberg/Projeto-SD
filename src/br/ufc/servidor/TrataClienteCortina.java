package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.Arcondicionado;
import br.ufc.Cortina;
import br.ufc.MensagemControlador;
import br.ufc.MensagemEquipamento;

public class TrataClienteCortina implements TrataCliente, Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;
	private MensagemEquipamento equipamento;
	private boolean terminar = false;

	public TrataClienteCortina(Socket cliente, Servidor servidor, MensagemEquipamento equipamento) {
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

				System.out.println("Informacao recebida do cliente cortina se a cortina esta ligada:" + ((Cortina)objetoAtualizado).isLevantar());			
//				//Tratar esse indice esta preso ao numero do cliente
//				enviarMensagemClienteCortina(objetoAtualizado);
				
				MensagemControlador msgControlador = new MensagemControlador();
				msgControlador.setEnviar(false);
				msgControlador.setObj((Cortina)objetoAtualizado);
				msgControlador.setTipoObjeto("Cortina");
				servidor.enviarMensagemControlador(msgControlador);
				
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

	boolean statusCortina = false;
	
	public void enviarMensagemClienteCortina(Object objEnviar) {

		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());

//			statusCortina = !statusCortina;
			
//			Cortina cortina = new Cortina();
//			cortina.setLevantar(statusCortina);

			oos2.writeObject(objEnviar);


		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void enviarMensagem(Object objEnviar) {
		enviarMensagemClienteCortina(objEnviar);		
	}


}