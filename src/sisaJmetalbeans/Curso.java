package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private Long id;
	private String nome;
	
	private List<Disciplina> gradeDeDisciplinas = new ArrayList<Disciplina>();

	public Curso() {
		super();
	}

	public Curso(String nome) {
		super();
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Disciplina> getGradeDeDisciplinas() {
		return gradeDeDisciplinas;
	}

	public void setGradeDeDisciplinas(List<Disciplina> gradeDeDisciplinas) {
		this.gradeDeDisciplinas = gradeDeDisciplinas;
	}
	
	
}
