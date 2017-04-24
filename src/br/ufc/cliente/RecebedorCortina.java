package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ufc.Cortina;

public class RecebedorCortina implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private ClienteCortina cliente;
	//Objeto que est√° no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorCortina(InputStream servidor, ClienteCortina cliente) {

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

			Cortina cortinaRecebida = ((Cortina)objetoAtualizado);

			cliente.setCortina(cortinaRecebida);//Atualiza o status da lampada recevida do servidor 

			System.out.println("Status Cortina recebida do Servidor e atualizada, est· ligada: " + cortinaRecebida.isLevantar());

		}
	}

}
