package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ufc.Lampada;

public class RecebedorLampada implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private ClienteLampada cliente;
	//Objeto que est√° no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorLampada(InputStream servidor, ClienteLampada cliente) {

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

			Lampada lampadaRecebida = ((Lampada)objetoAtualizado);

			cliente.setLampada(lampadaRecebida);//Atualiza o status da lampada recevida do servidor 

			System.out.println("Status Lampada recebida do Servidor e atualizada, est· ligada: " + lampadaRecebida.isLigar());

		}
	}

}
