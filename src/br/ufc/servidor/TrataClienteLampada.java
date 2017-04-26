package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.MensagemEquipamento;
import br.ufc.Lampada;
import br.ufc.MensagemControlador;

public class TrataClienteLampada implements TrataCliente, Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Object objetoAtualizado;
	private MensagemEquipamento equipamento;
	private boolean terminar = false;

	public TrataClienteLampada(Socket cliente, Servidor servidor, MensagemEquipamento equipamento) {
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

				System.out.println("Informacao recebida do cliente Lampada se a lampada esta ligada:" + ((Lampada)objetoAtualizado).isLigar());			
//				//Tratar esse indice esta preso ao numero do cliente
//				enviarMensagemClienteLampada(objetoAtualizado);
				
				MensagemControlador msgControlador = new MensagemControlador();
				msgControlador.setEnviar(false);
				msgControlador.setObj((Lampada)objetoAtualizado);
				msgControlador.setTipoObjeto("Lampada");
				//Enviar ao controlador o objeto atual
				servidor.enviarMensagemControlador(msgControlador );
				
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

//	boolean statusLampada = false;

	public void enviarMensagemClienteLampada(Object objEnviar) {
		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());

//			statusLampada = !statusLampada;
//			Lampada lamp = new Lampada();
//			lamp.setLigar(objEnviar);

			oos2.writeObject(objEnviar);


		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void enviarMensagem(Object objEnviar) {
		enviarMensagemClienteLampada(objEnviar);		
	}
	
}