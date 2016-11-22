package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import sisaJmetalbeans.Disciplina;

public class MatriculaMetalProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private PreparacaoDoProblema problemaPreparado;
	private int lowerBound = 0;
	private int upperBound;	
	private int tempoDeFormatura;
	//problemaPreparado pode fornecer 3 informações:
	// lista com as disciplinas que faltam pagar
	//número de máximo períodos restante para concluir o curso
	// quantas disciplinas ainda falta pagar para cocluir o curso
	
	
	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this); 
		List<Disciplina> copia=new ArrayList();
		int tamanhoDaSugestao=problemaPreparado.getPeriodosRestantes()*8;
		int temQuePagar=problemaPreparado.getQtdDiscplinasParaConcluir();
		copia.addAll(problemaPreparado.getNaoPagas());
		Random gerador = new Random();		 
        double numero; 
  ///      int modulo8=0;
//        int contaCadeira=0;
        int lugarPraZero=tamanhoDaSugestao-temQuePagar;
		for (int i=0;i<tamanhoDaSugestao;i++){
			numero=gerador.nextDouble()*100;
			int indexDeCopia;
			//if (modulo8>=4&&contaCadeira<3)continue;
			if (numero<=37.5&&lugarPraZero!=0){
				retorno.setVariableValue(i, 0);
				lugarPraZero-=1;
				}else if (copia.size()!=0&&temQuePagar!=0){
					indexDeCopia=gerador.nextInt(copia.size());
					retorno.setVariableValue(i, copia.get(indexDeCopia).getId());
					copia.remove(indexDeCopia);
					temQuePagar-=1;
				//	contaCadeira+=1;
					}else if (lugarPraZero!=0){
							retorno.setVariableValue(i, 0);
							lugarPraZero-=1;
							}else{
								continue;
							}
//			modulo8+=1; // jorge	
				}
//		int l=0;
//	
//		for (int i=0; i<retorno.getNumberOfVariables();i++){
//			if (retorno.getVariableValue(i)!=0) l+=1;
//		}
//		while (l%8!=0)l+=1;
//		for (int k=0;k<l;k++){
//			
//		}
//	
		return retorno;
	}

	int contador=0;
	@Override
	/**
	 * solution E um cromossomo retornado pelo algoritmo na  
	 * Classe main... Procure por populationna na classe  
	 * Main e siga suas transformacoes 
	 */

	
	
	public void evaluate(IntegerSolution solution) {
		// qual o tamanho desse array ?
		// esse é o array sugestão de matricula
		//então ele tera que ser do tamanho de 
		//numero de periodos restantes *8
		
		int [] vars = new int[solution.getNumberOfVariables()];
		List <Integer> impr= new ArrayList();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
			
			if (i%8==0 && i!=0){
			
				System.out.println(impr);
					impr.clear();			
			}
			impr.add(solution.getVariableValue(i));
			if (i%79==0 && i!=0){
//				System.out.println(impr);
//				System.out.println(problemaPreparado.getTempoDeFormatura());
//				System.out.println(problemaPreparado.getVarianciaTotal());
//				System.out.println(problemaPreparado.getQtdDiscForaDaMinhaArea());
//				System.out.println(problemaPreparado.getVariaQtdDiscPorPeriodo());
//				System.out.println(problemaPreparado.getTempoExtraClasse());
				System.out.println("**************************************"
						+ "*********************************");
				contador+=1;
				System.out.println(contador);
			}
						
		}
		problemaPreparado.setSugestaoMat(vars);
		problemaPreparado.contaTempoDeFormatura();
		problemaPreparado.varianciaTotal();
		problemaPreparado.verificaAcompNoPrimeDasug();
		problemaPreparado.qtdDeDisciplinasForaDeMinhaArea();
		problemaPreparado.varianciaDeQtdDeDiscPorPeriodo();
		problemaPreparado.tempoDeEstudoExtraClasse();
		//setando a sugestão de matricula em problema preparado				
		solution.setObjective(0, problemaPreparado.getTempoDeFormatura());
		solution.setObjective(1, problemaPreparado.getVarianciaTotal());
		solution.setObjective(2, problemaPreparado.getQtdDiscForaDaMinhaArea());
		solution.setObjective(3, problemaPreparado.getVariaQtdDiscPorPeriodo());
		solution.setObjective(4, problemaPreparado.getTempoExtraClasse());
		
	}

	
//	@Override
//	public int getNumberOfObjectives() {
//		return problemaPreparado.getNumberOfObjetives();
//	}
	// number of variable será o numero de disciplinas que faltam
	// pagar para terminar o curso
	@Override
	public int getNumberOfVariables() {
		return problemaPreparado.getPeriodosRestantes()*8;// tamanho maximo do array
													// de sugestão de matricula	
	}

	
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


	public int getTempoDeFormatura() {
		return tempoDeFormatura;
	}


	public MatriculaMetalProblem(PreparacaoDoProblema preparacao) {
		super();
		this.setNumberOfObjectives(5);
		this.setNumberOfVariables(preparacao.getPeriodosRestantes()*8);
		this.problemaPreparado = preparacao;		
		this.upperBound=preparacao.getDisciplinas().size();
	
	}


}
