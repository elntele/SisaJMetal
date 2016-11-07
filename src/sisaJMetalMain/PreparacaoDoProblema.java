package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sisaJmetalDAO.DisciplinaDAO;
import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class PreparacaoDoProblema {
	private Aluno aluno;
	private int periodosRestantes;
	private int QtdDiscplinasParaConcluir;
	private List<Disciplina> naoPagas=new  ArrayList <Disciplina>();
	private int AreaDePreferencia;
	private int numberOfObjetives=5;
	private int[] sugestaoMat;
	DisciplinaDAO disciplinaDAO = new DisciplinaDAO();	
	List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
	

	//tempo que o aluno ainda tem ate completar 16 periodos
	public void tempoDeFimDecurso (){
	int periodosGastos = ((2016-this.aluno.getAnoIngresso())*2);	
		if (aluno.getSemestreIngresso()==2){
			periodosGastos=+1;
			}	
		this.periodosRestantes=16-periodosGastos;
	}
	
	//disciplinas pagas
	public void montaListaDisciplinasPagas(){
		int i=1;
		List<Disciplina> transfer=new ArrayList();
		for (Disciplina d:disciplinas){
			if (d.getCodigo()!=14112){
				transfer.add(d);
				}
			i+=1;
			if (i==26) break;
			}
		aluno.setDiscPagas(transfer);
		}

	//disciplina acompanhada e reprovada
	public void montaListaDisciplinasReprovadas(){
		List<Disciplina> transfer=new ArrayList();
		for (Disciplina d:disciplinas){
			if (d.getCodigo()==14112){
				transfer.add(d);
				aluno.setDiscAcompanhada(transfer);
				aluno.setDiscRepro(transfer);
			}
		}
	}
	public void montaQtdDiscplinasParaConcluir(){
		this.QtdDiscplinasParaConcluir=45-aluno.getDiscPagas().size();
		
	}
	
	public void montaNaoPagas(){
		List<Disciplina> faltaPagar=new ArrayList();
		boolean presenca=false;
		for( Disciplina D: disciplinas){
			for (Disciplina Da:this.aluno.getDiscPagas()){
				if (D.getCodigo()==Da.getCodigo()) {
					presenca=true;
					break;
					} 
			}
			if (presenca==false) faltaPagar.add(D);
			presenca=false;
		}
		this.naoPagas=faltaPagar;
	}
	
	public void montasugestao(){
		List<Disciplina> copia=new ArrayList();
		int temQuePagar=QtdDiscplinasParaConcluir;
		copia.addAll(naoPagas);
		int sugestao[]=new int [this.periodosRestantes*8];
		Random gerador = new Random();		 
        int numero; 
        int lugarPraZero=sugestao.length-QtdDiscplinasParaConcluir;
		for (int i=0;i<sugestao.length;i++){
			numero=gerador.nextInt(4);
			if (numero==0&&lugarPraZero!=0){	
				sugestao[i]=0;
				lugarPraZero-=1;
				}else if (copia.size()!=0&&temQuePagar!=0){
					numero=gerador.nextInt(copia.size());
					sugestao[i]=copia.get(numero).getId();
					copia.remove(numero);
					temQuePagar-=1;
					}else if (lugarPraZero!=0){
							sugestao[i]=0;
							lugarPraZero-=1;
							}else{
								continue;
							}
				}
		this.sugestaoMat=sugestao;
			}
		
				 

	
	public Aluno getAluno() {
		return aluno;
	}

	public int[] getSugestaoMat() {
		return sugestaoMat;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public int getNumberOfObjetives() {
		return numberOfObjetives;
	}

	public int getPeriodosRestantes() {
		return periodosRestantes;
	}

	public void setPeriodosRestantes(int periodosRestantes) {
		this.periodosRestantes = periodosRestantes;
	}

	public int getQtdDiscplinasParaConcluir() {
		return QtdDiscplinasParaConcluir;
	}

	public void setQtdDiscplinasParaConcluir(int qtdDiscplinasParaConcluir) {
		QtdDiscplinasParaConcluir = qtdDiscplinasParaConcluir;
	}

	public List<Disciplina> getNaoPagas() {
		return naoPagas;
	}

	public void setNaoPagas(List<Disciplina> naoPagas) {
		this.naoPagas = naoPagas;
	}
	
	public int getAreaDePreferencia() {
		return AreaDePreferencia;
	}

	public void montaAredePreferencia(){
	 switch (this.aluno.getAreaPref()){
	 case "ARQ": this.AreaDePreferencia=1;
	 			break;
	 case "Ensiso": this.AreaDePreferencia=2;
		break;
	 case "FC": this.AreaDePreferencia=3;
		break;
	 default: break;

	 }
		
	}
	
	public PreparacaoDoProblema(Aluno aluno) {
		super();
		this.aluno = aluno;
		tempoDeFimDecurso ();
		montaListaDisciplinasPagas();
		montaListaDisciplinasReprovadas();
		montaQtdDiscplinasParaConcluir();
		montaNaoPagas();
		montaAredePreferencia();
		montasugestao();
	}
	
	
}