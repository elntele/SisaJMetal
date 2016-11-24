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
	//problemaPreparado pode fornecer 3 informa��es:
	// lista com as disciplinas que faltam pagar
	//n�mero de m�ximo per�odos restante para concluir o curso
	// quantas disciplinas ainda falta pagar para cocluir o curso
	
	
	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this); 
		List<Disciplina> copia=new ArrayList();
		int tamanhoDaSugestao=problemaPreparado.getPeriodosRestantes()*8;
		int temQuePagar=problemaPreparado.getQtdDiscplinasParaConcluir();
		copia.addAll(problemaPreparado.getNaoPagas());
		Random gerador = new Random();		 
        double numero;// utilizado para guardar resultado de rand�mico entre 0 e 100 
        int modulo8=0; // utilizado para medir passagem de periodo de 8 em 8 posi��es no array
        int contaCadeira=0; //utilizado para contar quantas disciplinas etm em cada periodo
        int troca1=0; // guarda posi��o com zero antes do periodo final para uma possivel troca
        int troca2=0;// guarda posi��o com zero antes do periodo final para uma possivel troca
        int lugarPraZero=tamanhoDaSugestao-temQuePagar; //// guarda quantidade de posi��es que pode-se alocar 0
        //Ps.: 0 significa que n�o h� disciplinas na posi��o do array 
		for (int i=0;i<tamanhoDaSugestao;i++){
			numero=gerador.nextDouble()*100;
			int indexDeCopia;//inteiro que guarda o rand�mico entre 0 e o tamanho do arraylist de disciplinas n�o pagas 
			
			if (modulo8>=4&&contaCadeira<3){// se o contador modulo8 estiver entre a posi��o 5 e 8 
				numero=38.00;				// e contador de disciplinas for menor que 3 ele for�a
				}							// o alg. a colocar mais disciplinas no periodo	
			
			if (numero<=37.5&&lugarPraZero!=0){
				if (i%2==0){
					troca1=i;
				}else {
					troca2=i;
				}
				retorno.setVariableValue(i, 0);
				lugarPraZero-=1;
				
				}else if (copia.size()!=0&&temQuePagar!=0){
						indexDeCopia=gerador.nextInt(copia.size());
						retorno.setVariableValue(i, copia.get(indexDeCopia).getId());
						copia.remove(indexDeCopia);
						temQuePagar-=1;
							if(modulo8%8!=0){
								contaCadeira+=1;
								}else {
										contaCadeira=0;
										modulo8=0;
										if (copia.size()==2){
											retorno.setVariableValue(troca1, copia.get(0).getId());
											retorno.setVariableValue(troca2, copia.get(1).getId());
												}
										if(copia.size()==1)retorno.setVariableValue(troca2, copia.get(1).getId());
									
								}
						}else if (lugarPraZero!=0){
								retorno.setVariableValue(i, 0);
								lugarPraZero-=1;
								}else{
									continue;
								}
			modulo8+=1; 
				}
	
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
		// esse � o array sugest�o de matricula
		//ent�o ele tera que ser do tamanho de 
		//numero de periodos restantes *8
		
		int [] vars = new int[solution.getNumberOfVariables()];
		List <Integer> impr= new ArrayList();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
// daqui para baixo			
//			if (i%8==0 && i!=0){
//			
//				System.out.println(impr);
//					impr.clear();			
//			}
//			impr.add(solution.getVariableValue(i));
//			if (i%79==0 && i!=0){
//				System.out.println(impr);
//				System.out.println(problemaPreparado.getTempoDeFormatura());
//				System.out.println(problemaPreparado.getVarianciaTotal());
//				System.out.println(problemaPreparado.getQtdDiscForaDaMinhaArea());
//				System.out.println(problemaPreparado.getVariaQtdDiscPorPeriodo());
//				System.out.println(problemaPreparado.getTempoExtraClasse());
//				System.out.println("**************************************"
//						+ "*********************************");
//				contador+=1;
//				System.out.println(contador);
//			}
// daqui para cima
		}
		problemaPreparado.setSugestaoMat(vars);
		problemaPreparado.contaTempoDeFormatura();
		problemaPreparado.varianciaTotal();
		problemaPreparado.verificaAcompNoPrimeDasug();
		problemaPreparado.qtdDeDisciplinasForaDeMinhaArea();
		problemaPreparado.varianciaDeQtdDeDiscPorPeriodo();
		problemaPreparado.tempoDeEstudoExtraClasse();
		//setando a sugest�o de matricula em problema preparado				
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
	// number of variable ser� o numero de disciplinas que faltam
	// pagar para terminar o curso
	@Override
	public int getNumberOfVariables() {
		return problemaPreparado.getPeriodosRestantes()*8;// tamanho maximo do array
													// de sugest�o de matricula	
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
	
	
	public void removeDisc(){
		
	}


	public MatriculaMetalProblem(PreparacaoDoProblema preparacao) {
		super();
		this.setNumberOfObjectives(5);
		this.setNumberOfVariables(preparacao.getPeriodosRestantes()*8);
		this.problemaPreparado = preparacao;		
		this.upperBound=preparacao.getDisciplinas().size();
	
	}


}
