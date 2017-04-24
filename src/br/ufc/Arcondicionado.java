package br.ufc;

import java.io.Serializable;

public class Arcondicionado implements Serializable{
	private static final long serialVersionUID = 1L;

	private int temperaturaMaxima = 27;
	private int temperaturaMinima = 16;
	private int temperaturaProgramada = 20;
	private boolean ligar = false;
	
	
	public int getTemperaturaMaxima() {
		return temperaturaMaxima;
	}

	public void setTemperaturaMaxima(int temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}

	public int getTemperaturaMinima() {
		return temperaturaMinima;
	}

	public void setTemperaturaMinima(int temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}

	public int getTemperaturaProgramada() {
		return temperaturaProgramada;
	}

	public void setTemperaturaProgramada(int temperaturaProgramada) {
		this.temperaturaProgramada = temperaturaProgramada;
	}

	public boolean isLigar() {
		return ligar;
	}

	public void setLigar(boolean ligar) {
		this.ligar = ligar;
	}

}
