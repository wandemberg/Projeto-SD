package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ufc.Arcondicionado;
import br.ufc.Cortina;
import br.ufc.Lampada;
import br.ufc.MensagemControlador;
import br.ufc.Radio;
import br.ufc.Tv;
import br.ufc.servidor.TrataCliente;

public class RecebedorControlador implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private Controlador cliente;
	//Objeto que est√° no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorControlador(InputStream servidor, Controlador cliente) {

		this.servidor = servidor;
		this.cliente = cliente;
	}

	public void run() {

		ObjectInputStream ois = null;

		// recebe msgs do servidor e imprime na tela
		while (!terminar) {
			//Espera receber informacao do servidor 
			try {
				ois = new ObjectInputStream(servidor);
				objetoAtualizado =  ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
						
			MensagemControlador msgControlador = (MensagemControlador)objetoAtualizado;
			
			//Atualiza o objeto recebido na msg
			if (msgControlador.getTipoObjeto().equals("Arcondicionado")) {			
				cliente.getListaDispositivos().replace("Arcondicionado", (Arcondicionado)msgControlador.getObj());
				
			} else if (msgControlador.getTipoObjeto().equals("Lampada")) {
				cliente.getListaDispositivos().replace("Lampada", (Lampada)msgControlador.getObj());
				System.out.println("Status Lampada recebida do Servidor e atualizada, est· ligada: " + ((Lampada)msgControlador.getObj()).isLigar());
				
			} else if (msgControlador.getTipoObjeto().equals("Cortina")) {
				cliente.getListaDispositivos().replace("Cortina", (Cortina)msgControlador.getObj());

			} else if (msgControlador.getTipoObjeto().equals("Tv")) {
				cliente.getListaDispositivos().replace("Tv", (Tv)msgControlador.getObj());

			} else if (msgControlador.getTipoObjeto().equals("Radio")) {
				cliente.getListaDispositivos().replace("Radio", (Radio)msgControlador.getObj());

			} 			
			
//			//Dependendo da escolha do objeto controlador
//
//			Lampada lampadaRecebida = ((Lampada)objetoAtualizado);
//
////			cliente.setLampada(lampadaRecebida);//Atualiza o status da lampada recevida do servidor 
//
//			System.out.println("Status Lampada recebida do Servidor e atualizada, est· ligada: " + lampadaRecebida.isLigar());

		}
	}

}
