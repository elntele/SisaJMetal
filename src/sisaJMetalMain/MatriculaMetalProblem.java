package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import sisaJmetalbeans.Disciplina;
import sisaJmetalbeans.SugestaoMatricula;

public class MatriculaMetalProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private PreparacaoDoProblema problemaPreparado;
	private int lowerBound = 0;
	private int upperBound;	
	private List <Float> varianciaDoPeriodo=new ArrayList();
	private Float varianciaTotal;
	//problemaPreparado pode fornecer 3 informações:
	// lista com as disciplinas que faltam pagar
	//número de máximo períodos restante para concluir o curso
	// quantas disciplinas ainda falta pagar para cocluir o curso
	
	public float RetornaGrauDificuldadeDaDisc(int id){
		Disciplina R=null;
		for (Disciplina D:this.problemaPreparado.getNaoPagas()){
			if (D.getId()==id){
				R=D;
				break;
			}
		}
		return R.getGrauDificuldade();
	}
	
	
	public void VarianciaPeriodo(){
		float varia=0;
		float dificuldade;
		ArrayList<Float>  variaPorPeriodo= new ArrayList<Float>();// um buffer pra lista
		ArrayList<Float>  variaPorTotal= new ArrayList<Float>();
		int divisorPorPeriodo=0;
		for(int i=0;i<this.problemaPreparado.getSugestaoMat().length;i++) {// percorre o array de disciplinas para calcular a variancia por periodo			
			if (this.problemaPreparado.getSugestaoMat()[i]!=0){ 
				divisorPorPeriodo+=1;
				dificuldade=RetornaGrauDificuldadeDaDisc(problemaPreparado.getSugestaoMat()[i]);
				varia += (float) Math.pow( dificuldade-2 ,2);				
				}
			if (i%8==0){
				varia =varia/divisorPorPeriodo;
				divisorPorPeriodo=0;
				this.varianciaDoPeriodo.add(varia);
			}
			
		}
		
	}

	public void varianciaTotal(){
		float varia=0;
		for(int i = 0; i<varianciaDoPeriodo.size();i++){
			varia += (float) Math.pow( this.varianciaDoPeriodo.get(i) - 2,2);			
		}
		if (varianciaDoPeriodo.size()!=0){
		this.varianciaTotal=(varia/varianciaDoPeriodo.size());
		}else{
			varianciaTotal=(float) 0;
		}
	}
	
	
	@Override
	public void evaluate(IntegerSolution solution) {
//		Integer[] vars = new Integer[solution.getNumberOfVariables()];
//		for (int i = 0; i < vars.length; i++) {
//			vars[i] = solution.getVariableValue(i);
//		}
//		//jorge
//		// essa classse bons tambem
//		//Double[] objectives = bonsProblem.evaluate(vars);
//		Double[] objectives = problemaPreparado.evaluate(vars);
//		solution.setObjective(0, objectives[0]);
//		solution.setObjective(1, objectives[1]/93000);
//		solution.setObjective(2, objectives[2]/1800000);
//		solution.setObjective(3, 1.0 / (1.0 + objectives[3]));
		
	}
	
	

	@Override
	public IntegerSolution createSolution() {
		IntegerSolution sol = new DefaultIntegerSolution(this);
		for (int i = 0; i < getNumberOfVariables() - 2; i++) {
			if (Math.random() > 0.25) {
				sol.setVariableValue(i, 0);
			} else {
				sol.setVariableValue(i, 1);
			}
		}
		sol.setVariableValue(getNumberOfVariables() - 1, (int) (Math.round(Math.random() * 36) + 4));
		sol.setVariableValue(getNumberOfVariables() - 2, (int) (Math.round(Math.random() * 5)));
		return sol;
	}

	
	@Override
	public int getNumberOfObjectives() {
		return problemaPreparado.getNumberOfObjetives();
	}
	// number of variable será o numero de disciplinas que faltam
	// pagar para terminar o curso
	@Override
	public int getNumberOfVariables() {
		return problemaPreparado.getQtdDiscplinasParaConcluir();
	}

	// jorge
	// qual seria o lowerbound em nosso contexto
	//@Override
	//	public Integer getLowerBound(int index) {
	//		return bonsProblem.getLowerLimitVariableAt(index);
	//	}
	
	@Override
	public Integer getLowerBound(int index) {
		return this.lowerBound;
	}

	//jorge
	// e o upper bound
	@Override
	public Integer getUpperBound(int index) {
		return this.upperBound;
	}

//	public Integer getUpperBound(int index) {
//		return bonsProblem.getUpperLimitVariableAt(index);
//	}

	public List<Float> getVarianciaDoPeriodo() {
		return varianciaDoPeriodo;
	}


	public Float getVarianciaTotal() {
		return varianciaTotal;
	}

	

	public MatriculaMetalProblem(PreparacaoDoProblema preparacao) {
		this.problemaPreparado = preparacao;		
		this.upperBound=preparacao.getDisciplinas().size();
		VarianciaPeriodo();
		varianciaTotal();
	}


}
