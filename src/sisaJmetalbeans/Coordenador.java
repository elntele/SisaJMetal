package sisaJmetalbeans;


public class Coordenador {
	private Long id;
	private String nome;
	private String cpf;
	private boolean identificador = true;// habilita ao professor funcionalidades extras
	
	
	public Coordenador() {
		super();
	}
	public Coordenador(String nome, String cpf, boolean identificador) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.identificador = identificador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public boolean isIdentificador() {
		return identificador;
	}
	public void setIdentificador(boolean identificador) {
		this.identificador = identificador;
	}
	
	
	
}
