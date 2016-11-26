package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Disciplina {
	
	/**
	 * O periodo varia de 1 a 9 para as obrigatorias e 0 para optativas.
	 */
	private int id;	
	private int codigo;
	private String nome;
	private String periodo; 
	private float grauDificuldade;
	private Area area;
	private List <Integer> preRequisitos = new ArrayList<Integer>();
	private List<Disciplina> posRequisitos = new ArrayList<Disciplina>();
	private String[] diaHora= new String[5];
	private int semestre;
	private String médiageral;
	
	/**
	 * Disciplinas pagas.
	 */
	private List<Aluno> alunosPagaram = new ArrayList<Aluno>();
	
	private List<Aluno> alunosReprovados = new ArrayList<Aluno>();
	
	private List<Aluno> alunosAcompanhados = new ArrayList<Aluno>();

	public Disciplina() {
		super();
	}

	public Disciplina(int codigo, String nome, String periodo, float grauDificuldade, Area area, String[] diaHora,
			int semestre) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.periodo = periodo;
		this.grauDificuldade = grauDificuldade;
		this.area = area;
		this.diaHora = diaHora;
		this.semestre = semestre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMédiageral() {
		return médiageral;
	}

	public void setMédiageral(String médiageral) {
		this.médiageral = médiageral;
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

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
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

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public float getGrauDificuldade() {
		return grauDificuldade;
	}

	public void setGrauDificuldade(String grauDificuldade) {
		float grau= Float.parseFloat(grauDificuldade);

		this.grauDificuldade = grau;
	}
	
	public List<Disciplina> getPosRequisitos() {
		return posRequisitos;
	}

	public void setPosRequisitos(List<Disciplina> posRequisitos) {
		this.posRequisitos = posRequisitos;
	}


	public List<Integer> getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(String pre) {
		String[] parts = pre.split ("-");
		int buffer;
		if (!pre.equals("")){
		for (int i=0;i< parts.length;i++){	
			buffer=Integer.parseInt(parts[i]);
			this.preRequisitos.add(buffer);
		}		
		}
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
