package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.List;

public class Area {
	private Long id;  
	private String nome;
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	public Area() {
		super();
	}
	public Area(String nome) {
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
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	

}