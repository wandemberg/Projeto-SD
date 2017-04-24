package br.ufc;

import java.io.Serializable;

public class Lampada implements Serializable{
	private static final long serialVersionUID = 1L;

	private boolean ligar = false;
	

	public boolean isLigar() {
		return ligar;
	}

	public void setLigar(boolean ligar) {
		this.ligar = ligar;
	}


}
