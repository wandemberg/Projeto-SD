package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.Arcondicionado;
import br.ufc.MensagemEquipamento;

public class TrataClienteArcondicionado implements TrataCliente, Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//Referência do servidor
	private Servidor servidor;
	//Objeto que está no cliente atualizado
	private Arcondicionado objetoAtualizado;
	private MensagemEquipamento equipamento;
	private boolean terminar = false;

	public TrataClienteArcondicionado(Socket cliente, Servidor servidor, MensagemEquipamento equipamento) {
		this.cliente = cliente;
		this.servidor = servidor;
		//		this.objetoAtualizado = tipoObjeto;
		this.equipamento = equipamento;
	}

	public void run() {

		// quando chegar uma msg, distribui pra todos
		//
		//		Scanner s = new Scanner(this.cliente);
		//
		//		while (s.hasNextLine()) {
		//
		//			servidor.distribuiMensagem(s.nextLine());
		//
		//		}

		ObjectInputStream ois = null;



		while (!terminar) {
			//Receber o tipo do equipamento que abriu a comunicação 
			try {
				ois = new ObjectInputStream(cliente.getInputStream());
				objetoAtualizado =  (Arcondicionado)ois.readObject();

				System.out.println("Temperatura recebida do cliente :" + objetoAtualizado.getTemperaturaProgramada());				
//				enviarMensagemClienteArcondicionado(objetoAtualizado);

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


	int temp = 18;

	public void enviarMensagemClienteArcondicionado(Object objEnviar) {

		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());

			Arcondicionado arcondicionado = new Arcondicionado();
			arcondicionado.setTemperaturaProgramada(temp);
			oos2.writeObject(arcondicionado);

			temp++;
			if(temp >= arcondicionado.getTemperaturaMaxima())
				temp = arcondicionado.getTemperaturaMinima();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	public void enviarArcondicionado(Arcondicionado objEnviar) {

		// envia msg para todo mundo
		//Mandar um objeto com o tipo Arcondicionado
		ObjectOutputStream oos2;
		try {
			oos2 = new ObjectOutputStream(cliente.getOutputStream());
			oos2.writeObject(objEnviar);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public Arcondicionado getObjetoAtualizado() {
		return objetoAtualizado;
	}

	public void setObjetoAtualizado(Arcondicionado objetoAtualizado) {
		this.objetoAtualizado = objetoAtualizado;
	}

	@Override
	public void enviarMensagem(Object objEnviar) {
		enviarArcondicionado((Arcondicionado)objEnviar);
		
	}


}