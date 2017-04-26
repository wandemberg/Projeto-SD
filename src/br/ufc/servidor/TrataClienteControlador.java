package br.ufc.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.ufc.Arcondicionado;
import br.ufc.MensagemEquipamento;
import br.ufc.MensagemControlador;

public class TrataClienteControlador implements TrataCliente, Runnable{

	//Stream para receber dados de um cliente
	private Socket cliente;
	//ReferÃªncia do servidor
	private Servidor servidor;
	//Objeto que estÃ¡ no cliente atualizado
	private MensagemControlador objetoAtualizado;
	private MensagemEquipamento equipamento;
	private boolean terminar = false;

	public TrataClienteControlador(Socket cliente, Servidor servidor, MensagemEquipamento equipamento) {
		this.cliente = cliente;
		this.servidor = servidor;
		//		this.objetoAtualizado = tipoObjeto;
		this.equipamento = equipamento;
	}

	public void run() {

		ObjectInputStream ois = null;

		while (!terminar) {
			//Receber o tipo do equipamento que abriu a comunicaÃ§Ã£o 
			try {
				ois = new ObjectInputStream(cliente.getInputStream());
				//Recebe um objeto MensagemControlador
				objetoAtualizado =  (MensagemControlador)ois.readObject();

				System.out.println("Mensagem recebida do controlada para enviar :" + objetoAtualizado.isEnviar() + 
						" e objeto :" + objetoAtualizado.getTipoObjeto());			

				//Verificar se é uma mensagem para atualizar o dispositivo 
				if (objetoAtualizado.isEnviar()) {//Quer dizer que ele quer atualizar um dispositivo
					//					Class.forName(objetoAtualizado.getTipoObjeto()).isInstance(objetoAtualizado.getObj());

					if (objetoAtualizado.getTipoObjeto().equals("Arcondicionado")) {
						//Obtém a referência do objeto que pode enviar a mensagem ao simulador
						TrataCliente cliente = servidor.getClientes().get("Arcondicionado");
						cliente.enviarMensagem(objetoAtualizado.getObj());						
					} else if (objetoAtualizado.getTipoObjeto().equals("Lampada")) {
						//Obtém a referência do objeto que pode enviar a mensagem ao simulador
						TrataCliente cliente = servidor.getClientes().get("Lampada");
						cliente.enviarMensagem(objetoAtualizado.getObj());						
					} else if (objetoAtualizado.getTipoObjeto().equals("Cortina")) {
						//Obtém a referência do objeto que pode enviar a mensagem ao simulador
						TrataCliente cliente = servidor.getClientes().get("Cortina");
						cliente.enviarMensagem(objetoAtualizado.getObj());						
					} else if (objetoAtualizado.getTipoObjeto().equals("Tv")) {
						//Obtém a referência do objeto que pode enviar a mensagem ao simulador
						TrataCliente cliente = servidor.getClientes().get("Tv");
						cliente.enviarMensagem(objetoAtualizado.getObj());						
					} else if (objetoAtualizado.getTipoObjeto().equals("Radio")) {
						//Obtém a referência do objeto que pode enviar a mensagem ao simulador
						TrataCliente cliente = servidor.getClientes().get("Radio");
						cliente.enviarMensagem(objetoAtualizado.getObj());						
					} 				

				}


				///ou obter a informação de um dispositivo?


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

	public void enviarMensagemAoControlador(Object objEnviar) {

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

	public MensagemControlador getObjetoAtualizado() {
		return objetoAtualizado;
	}

	public void setObjetoAtualizado(MensagemControlador objetoAtualizado) {
		this.objetoAtualizado = objetoAtualizado;
	}

	@Override
	public void enviarMensagem(Object objEnviar) {		
		enviarMensagemAoControlador(objEnviar);		
	}
	
}