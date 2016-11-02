package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Disciplina {
	
	/**
	 * O periodo varia de 1 a 9 para as obrigatórias e 0 para optativas.
	 */
	private Long id;	
	private int codigo;
	private String nome;
	private int periodo; 
	private float grauDificuldade;
	private Area area;
	private List<Disciplina> preRequisitos = new ArrayList<Disciplina>();
	private List<Disciplina> posRequisitos = new ArrayList<Disciplina>();
	private String[] diaHora= new String[4];
	
	/**
	 * Disciplinas pagas.
	 */
	private List<Aluno> alunosPagaram = new ArrayList<Aluno>();
	
	private List<Aluno> alunosReprovados = new ArrayList<Aluno>();
	
	private List<Aluno> alunosAcompanhados = new ArrayList<Aluno>();


	
	public Disciplina() {
		super();
	}

		



	public Disciplina(int codigo, String nome, int periodo, float grauDificuldade, String[] diaHora) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.periodo = periodo;
		this.grauDificuldade = grauDificuldade;
		this.diaHora = diaHora;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}



	public Area getArea() {
		return area;
	}





	public void setArea(Area area) {
		this.area = area;
	}





	public String[] getDiaHora() {
		return diaHora;
	}

	public void setDiaHora(String[] diaHora) {
		this.diaHora = diaHora;
	}





	public float getGrauDificuldade() {
		return grauDificuldade;
	}

	public void setGrauDificuldade(float grauDificuldade) {
		this.grauDificuldade = grauDificuldade;
	}

	public List<Disciplina> getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(List<Disciplina> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}

	public List<Disciplina> getPosRequisitos() {
		return posRequisitos;
	}

	public void setPosRequisitos(List<Disciplina> posRequisitos) {
		this.posRequisitos = posRequisitos;
	}

	public List<Aluno> getAlunosPagaram() {
		return alunosPagaram;
	}

	public void setAlunosPagaram(List<Aluno> alunosPagaram) {
		this.alunosPagaram = alunosPagaram;
	}

	public List<Aluno> getAlunosReprovados() {
		return alunosReprovados;
	}

	public void setAlunosReprovados(List<Aluno> alunosReprovados) {
		this.alunosReprovados = alunosReprovados;
	}

	public List<Aluno> getAlunosAcompanhados() {
		return alunosAcompanhados;
	}

	public void setAlunosAcompanhados(List<Aluno> alunosAcompanhados) {
		this.alunosAcompanhados = alunosAcompanhados;
	}

}
