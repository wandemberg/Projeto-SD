package br.ufc;

import java.io.Serializable;

public class Equipamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Nome do objeto
	private String nome = "Ar 1";
	//Nome da classe que define o objeto
	private String tipos []= {"Arcondicionado", "Lampada", "Cortina", "TV", "Radio"};
	//Indice do tipo selecionado
	private int indiceTipoSelecionado = 0;
	
	public String getTipo(){
		return tipos[indiceTipoSelecionado];
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIndiceTipoSelecionado() {
		return indiceTipoSelecionado;
	}
	public void setIndiceTipoSelecionado(int indiceTipoSelecionado) {
		this.indiceTipoSelecionado = indiceTipoSelecionado;
	}

}
