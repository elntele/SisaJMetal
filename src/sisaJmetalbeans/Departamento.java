package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.List;



public class Departamento{
	private String id;
	private String nome;
	
	private List<Curso>  cursosDoDepartamento =  new ArrayList<Curso>();
	
	
	public Departamento() {
		super();
	}

	public Departamento(String nome) {
		super();
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Curso> getCursosDoDepartamento() {
		return cursosDoDepartamento;
	}

	public void setCursosDoDepartamento(List<Curso> cursosDoDepartamento) {
		this.cursosDoDepartamento = cursosDoDepartamento;
	}
	
	
	
}
