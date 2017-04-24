package br.ufc;

import java.io.Serializable;

public class Tv implements Serializable{
	private static final long serialVersionUID = 1L;

	private int canalSelecionado = 20;
	private int volumeSelecionado = 15;
	private boolean ligar = false;
	

	public boolean isLigar() {
		return ligar;
	}

	public void setLigar(boolean ligar) {
		this.ligar = ligar;
	}

	public int getCanalSelecionado() {
		return canalSelecionado;
	}

	public void setCanalSelecionado(int canalSelecionado) {
		this.canalSelecionado = canalSelecionado;
	}

	public int getVolumeSelecionado() {
		return volumeSelecionado;
	}

	public void setVolumeSelecionado(int volumeSelecionado) {
		this.volumeSelecionado = volumeSelecionado;
	}

}
