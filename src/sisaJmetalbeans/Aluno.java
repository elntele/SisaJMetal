package sisaJmetalbeans;

import java.util.ArrayList;
import java.util.List;


public class Aluno{
	
	private Long id;
	private String nome;
	private String cpf;
	private boolean identificador=false;
	private String email; 
	private int anoIngresso;// ano de ingresso 
	private int semestreIngresso;// semestre de ingresso
	private int tempDispExtrCla;//tempo disponível para estudar fora da faculdade
	private int cargaHorariaDoPeriodo; // tem que ser entre 150 e 480
	
//	private List<SugestaoMatricula> sugestMat;//objeto sugestão de matricula
	
	/**
	 * Disciplinas pagas.
	 */
	private List<Disciplina> discPagas = new ArrayList<Disciplina>();
	
	private List<Disciplina> discRepro = new ArrayList<Disciplina>();
	
	private List<Disciplina> discAcompanhada = new ArrayList<Disciplina>();
	
	private String areaPref;// area de preferência (modificar)
	private int qtdPeriTranc;// quantidade de periodos que o curso esteve trancado
	
	
	public Aluno(){
		
	}
	
	public Aluno(String nome, String cpf, boolean identificador, String email, int anoIngresso, int semestreIngresso,
			int tempDispExtrCla, String areaPref, int qtdPeriTranc, int cargaHorariaDoPeriodo) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.identificador = identificador;
		this.email = email;
		this.anoIngresso = anoIngresso;
		this.semestreIngresso = semestreIngresso;
		this.tempDispExtrCla = tempDispExtrCla;
		this.areaPref = areaPref;
		this.qtdPeriTranc = qtdPeriTranc;
		this.cargaHorariaDoPeriodo = cargaHorariaDoPeriodo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAnoIngresso() {
		return anoIngresso;
	}

	public void setAnoIngresso(int anoIngresso) {
		this.anoIngresso = anoIngresso;
	}

	public int getSemestreIngresso() {
		return semestreIngresso;
	}

	public void setSemestreIngresso(int semestreIngresso) {
		this.semestreIngresso = semestreIngresso;
	}

	public int getTempDispExtrCla() {
		return tempDispExtrCla;
	}

	public void setTempDispExtrCla(int tempDispExtrCla) {
		this.tempDispExtrCla = tempDispExtrCla;
	}

	public String getAreaPref() {
		return areaPref;
	}

	public void setAreaPref(String areaPref) {
		this.areaPref = areaPref;
	}

	public int getQtdPeriTranc() {
		return qtdPeriTranc;
	}

	public void setQtdPeriTranc(int qtdPeriTranc) {
		this.qtdPeriTranc = qtdPeriTranc;
	}

	public int getCargaHorariaDoPeriodo() {
		return cargaHorariaDoPeriodo;
	}

	public void setCargaHorariaDoPeriodo(int cargaHorariaDoPeriodo) {
		this.cargaHorariaDoPeriodo = cargaHorariaDoPeriodo;
	}

	public List<Disciplina> getDiscPagas() {
		return discPagas;
	}

	public void setDiscPagas(List<Disciplina> discPagas) {
		this.discPagas = discPagas;
	}

	public List<Disciplina> getDiscRepro() {
		return discRepro;
	}

	public void setDiscRepro(List<Disciplina> discRepro) {
		this.discRepro = discRepro;
	}

	public List<Disciplina> getDiscAcompanhada() {
		return discAcompanhada;
	}

	public void setDiscAcompanhada(List<Disciplina> discAcompanhada) {
		this.discAcompanhada = discAcompanhada;
	}

	
}