package br.ufc;

import java.io.Serializable;

public class Cortina implements Serializable{
	private static final long serialVersionUID = 1L;

	private boolean levantar = false;

	public boolean isLevantar() {
		return levantar;
	}

	public void setLevantar(boolean levantar) {
		this.levantar = levantar;
	}
	



}
