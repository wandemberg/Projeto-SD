package br.ufc;

import java.io.Serializable;

public class Radio implements Serializable{
	private static final long serialVersionUID = 1L;

	private float estacaoSelecionada = 93.9f;
	private int volumeSelecionado = 15;
	private boolean ligar = false;
	

	public boolean isLigar() {
		return ligar;
	}

	public void setLigar(boolean ligar) {
		this.ligar = ligar;
	}

	public int getVolumeSelecionado() {
		return volumeSelecionado;
	}

	public void setVolumeSelecionado(int volumeSelecionado) {
		this.volumeSelecionado = volumeSelecionado;
	}

	public float getEstacaoSelecionada() {
		return estacaoSelecionada;
	}

	public void setEstacaoSelecionada(float estacaoSelecionada) {
		this.estacaoSelecionada = estacaoSelecionada;
	}

}
