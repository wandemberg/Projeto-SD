package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ufc.Tv;

public class RecebedorTv implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private ClienteTv cliente;
	//Objeto que est√° no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorTv(InputStream servidor, ClienteTv cliente) {

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

			Tv tvRecebida = ((Tv)objetoAtualizado);

			cliente.setTv(tvRecebida);//Atualiza o status da lampada recevida do servidor 

			System.out.println("Status Tv recebida do Servidor e atualizada, est· ligada: " + tvRecebida.isLigar());

		}
	}

}
