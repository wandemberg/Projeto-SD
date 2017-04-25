package br.ufc;

public class MensagemControlador {
	//Caso contrário que rece
	private boolean enviar = true;
	private String tipoObjeto = "Arcondicionado";
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
