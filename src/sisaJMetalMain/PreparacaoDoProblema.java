package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;
import sisaJmetalDAO.DisciplinaDAO;
import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class PreparacaoDoProblema {
	private Aluno aluno;
	private int periodosRestantes;
	private int QtdDiscplinasParaConcluir;
	private List<Disciplina> naoPagas=new  ArrayList <Disciplina>();	
	DisciplinaDAO disciplinaDAO = new DisciplinaDAO();	
	List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
	
	//tempo que o aluno ainda tem ate completar 16 perios
	public void tempoDeFimDecurso (){
	int periodosGastos = (2016-this.aluno.getAnoIngresso())*2;	
		if (aluno.getSemestreIngresso()==2){
			periodosGastos=+1;
			this.periodosRestantes=16-periodosGastos;
		}		
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
			if (d.getCodigo()!=14112){
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
				if (D.getCodigo()==Da.getCodigo()) presenca=true; 
			}
			if (presenca==false) faltaPagar.add(D);
			presenca=false;
		}
		this.naoPagas=faltaPagar;
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

	public PreparacaoDoProblema(Aluno aluno) {
		super();
		this.aluno = aluno;
		tempoDeFimDecurso ();
		montaListaDisciplinasPagas();
		montaListaDisciplinasReprovadas();
		montaQtdDiscplinasParaConcluir();
		montaNaoPagas();
	}
	
}