package br.ufc.cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import br.ufc.Radio;
import br.ufc.Tv;

public class RecebedorRadio implements Runnable{


	private InputStream servidor;
	private boolean terminar = false;
	private ClienteRadio cliente;
	//Objeto que est√° no cliente atualizado
	private Object objetoAtualizado;

	public RecebedorRadio(InputStream servidor, ClienteRadio cliente) {
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

			Radio radioRecebida = ((Radio)objetoAtualizado);

			cliente.setRadio(radioRecebida);//Atualiza o status da lampada recevida do servidor 

			System.out.println("Status Radio recebida do Servidor e atualizada, est· ligada: " + radioRecebida.isLigar());

		}
	}

}
