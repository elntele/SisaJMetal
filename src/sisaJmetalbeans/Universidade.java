package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.List;
public class Universidade{
	private String id;
	private String nome;
	private String cnpj;
	
	public List<Departamento>  departamentoDaUniversidade = new ArrayList<Departamento>() ;

	public Universidade() {
		super();
	}

	public Universidade(String nome, String cnpj) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Departamento> getDepartamentoDaUniversidade() {
		return departamentoDaUniversidade;
	}

	public void setDepartamentoDaUniversidade(List<Departamento> departamentoDaUniversidade) {
		this.departamentoDaUniversidade = departamentoDaUniversidade;
	}
	
	
	
}
