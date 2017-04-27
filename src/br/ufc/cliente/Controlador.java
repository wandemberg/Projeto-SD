package br.ufc.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import br.ufc.MensagemEquipamento;
import br.ufc.Lampada;
import br.ufc.MensagemControlador;

public class Controlador {

	private MensagemEquipamento equi;
	private String host = "127.0.0.1";
	private int porta = 12345;
	private Socket cliente;
	private Map<String, Object> listaDispositivos;

	public Controlador(MensagemEquipamento equi) {
		this.equi = equi;
				
		listaDispositivos = new HashMap<String, Object>();
		listaDispositivos.put("Arcondicionado", null);
		listaDispositivos.put("Cortina", null);
		listaDispositivos.put("Lampada", null);
		listaDispositivos.put("Tv", null);
		listaDispositivos.put("Radio", null);
		
		
		//Criar a primeira comunicação com o servidor para abrir o socket
		try {
			cliente = new Socket(this.host, this.porta);

			System.out.println("O controlador vai se conectar ao servidor!");

			// thread para receber mensagens do servidor
			RecebedorControlador r = new RecebedorControlador(cliente.getInputStream(), this);
			new Thread(r).start();

			//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());

			//Avisa que é um controlador
			oos.writeObject(this.equi);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Enviou o tipo de objeto");

	}

	public static void main(String[] args) {
		//controlador se cadastra no servidor
		MensagemEquipamento equipamento = new MensagemEquipamento();
		equipamento.setIndiceTipoSelecionado(5);
		equipamento.setNome("Controlador 1");

		Controlador contr1 = new Controlador(equipamento);

		MensagemControlador mc = new MensagemControlador();
		mc.setEnviar(true);

		Lampada la = new Lampada();
		la.setLigar(false);

		mc.setObj(la);
		
		//Manda atualizar o objeto lampada no servidor e no dispositivo
		contr1.enviarMensagem(mc);
		
		//se for para receber um objeto é 

	}

	public void enviarMensagem(MensagemControlador mc) {
		//Mandar um objeto que quer estabelecer a comunicacao, ou seja, o tipo do objeto
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(cliente.getOutputStream());
			//Avisa que é um controlador
			oos.writeObject(mc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getListaDispositivos() {
		return listaDispositivos;
	}

	public void setListaDispositivos(Map<String, Object> listaDispositivos) {
		this.listaDispositivos = listaDispositivos;
	}

}
