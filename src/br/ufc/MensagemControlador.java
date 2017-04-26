package br.ufc;

import java.io.Serializable;

public class MensagemControlador implements Serializable{
	private static final long serialVersionUID = 1L;

	//Caso contrário que rece
	private boolean enviar = true;
	private String tipoObjeto = "Lampada";
	private Object obj;
	
	public boolean isEnviar() {
		return enviar;
	}
	public void setEnviar(boolean enviar) {
		this.enviar = enviar;
	}
	public String getTipoObjeto() {
		return tipoObjeto;
	}
	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
