package sisaJMetalMain;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class Main {
	public static void main(String args[] ) throws FileNotFoundException {
		Problem<IntegerSolution> problem;
		Algorithm<List<IntegerSolution>> algorithm;
		CrossoverOperator<IntegerSolution> crossover;
		MutationOperator<IntegerSolution> mutation;
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
		//String referenceParetoFront = "";
		//Aluno alunoReal = aluno;
		//Disciplina disciplinaReal;
		//List <Disciplina> listaDisciplinasDoCurso = listaGeral;
		
		
//	********************	Alunos de jorge**************************
//		Aluno aluno = new Aluno("abc", "123", false, "a@a", 2013, 1, 5, "FC", 0, 300);//aluno1
//		Aluno aluno = new Aluno("abc", "123", false, "a@a", 2013, 1, 17, "FC", 0, 300);//aluno2
//		Aluno aluno = new Aluno("abc", "123", false, "a@a", 2014, 2, 19, "FC", 0, 300);//aluno3
//		Aluno aluno = new Aluno("abc", "1245", false, "a@a", 2012, 1, 11, "FC", 1, 300);//aluno4
		Aluno aluno = new Aluno("abc", "1245", false, "a@a", 2012, 2, 11, "ARQ", 0, 300);//aluno5	
//		***************************alunos de italo******************************
//		Aluno aluno = new Aluno("Sofia", "1234", false, "sofia@urfpe.br", 2015, 2, 6, "FC", 0, 300);//aluno1
//		Aluno aluno = new Aluno("Joabson", "4239", false, "joabson.alves@urfpe.br", 2012, 1, 6, "FC", 0, 300);//aluno2
//		Aluno aluno = new Aluno("Tulio", "4578", false, "tulio.melo@urfpe.br", 2010, 1, 4, "Ensiso", 4, 300);//aluno3
		
//*******************		//alunos de jefferson*******************************
		/**
		*
		* Este caso comtempla um aluno que foi da 1ª turma do curso e ainda não se formou pois não pagou Introdução a Programação 2.
		*/
		
//		Aluno aluno = new Aluno("Roberta", "03028911", false, "betinha@gmail.com", 2010, 1, 3, "ARQ", 0, 300);//aluno1
		
		/**
		*
		* Este caso comtempla um aluno que falta pagar apenas uma disciplina que não é pré-requisito para nenhuma outra disciplina.
		*/
		
//		Aluno aluno = new Aluno("Anderson", "2345678", false, "anderson90@gmail.com", 2013, 2, 6, "Ensiso", 0, 300);//aluno2

		/**
		*
		* Este caso comtempla um aluno que reprovou todas as disciplinas obrigatórias da área que ele escolheu.
		*/
		
//		Aluno aluno = new Aluno("Amanda", "7890213", false, "mandysouza@gmail.com", 2013, 1, 2, "ARQ", 0, 300);//aluno3
		
	//*************************************alunos de leo*****************************
//		Aluno aluno = new Aluno("debora", "123", false, "deborahhh@email.com", 2014, 1, 20, "FC", 0, 300);//aluno1

//		Aluno aluno = new Aluno("poly", "456", false, "polyana@email.com", 2013, 2, 15, "ARQ", 4, 300);//aluno2
		
//		Aluno aluno = new Aluno("olga", "789", false, "olguinha@email.com", 2015, 1, 10, "ENSISO", 2, 300);//aluno3
				
// *************************************alunos de pedro***************************************
		
		//Primeiro aluno que ja está a muito tempo no curso, possui 2 disciplinas obrigatorias 
		//para finalizar e está acompanhado em compiladores
//		Aluno aluno = new Aluno("FHC", "07820329420", false, "a@gmail.com", 2010, 2, 4, "FC", 0, 300);//aluno1
		//Segundo aluno que ta em cerca da metade do curso, ta acompanhado em algoritmos e quase 
	   //acompanhado em circuitos

//		Aluno aluno = new Aluno("Lula", "07820333320", false, "aa@gmail.com", 2014, 2, 6, "ARQ", 0, 300);//aluno2

//		acompanhado em programacao 2 faz um bom tempo e ficou acompanhado em arquitetura de computadores no semestre passado.




//		Aluno aluno = new Aluno("Temer", "07820329420", false, "aaa@gmail.com", 2013, 2, 5, "Ensiso", 0, 300);//aluno3





		
		
		PreparacaoDoProblema preparacao =new PreparacaoDoProblema(aluno);
		problem = new MatriculaMetalProblem(preparacao);

		//****************************
		double crossoverProbability = 1.0 ;
	    double crossoverDistributionIndex = 20.0;
	    crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex) ;
	    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
	    double mutationDistributionIndex = 20.0 ;
	    mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex) ;
	    selection = new BinaryTournamentSelection<IntegerSolution>() ;
		

	    
	    
		//		algorithm = new NSGAIIBuilder<>(problem, crossover, mutation)
		//				.setSelectionOperator(selection).setPopulationSize(1000).setMaxEvaluations(1000).build();
		//		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
	    
	    /**
	     * modificação para fazer o algorimo rodar 10 vezes caso o retorno 
	     * de sugestões seja 0. A variavel pos  é declarada 
	     * fora do loop Do While e é estanciada dentro.
	     * A variavel population é declarada fora e instanciada apos o loop.
	     * a variavel qtdDeInteracoes conta quantas vezes o algoritmo vai rodar 
	     */
	    algorithm = new NSGAIIIBuilder<>(problem,preparacao).setCrossoverOperator(crossover).setMutationOperator(mutation)
					.setSelectionOperator(selection).setPopulationSize(1000).setMaxIterations(30).build();
	    
	    PosProcessamento pos;
	    int qtdDeInteracoes=1;
	    List<IntegerSolution> population;
	    do{
			AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
			population = algorithm.getResult();
			long computingTime = algorithmRunner.getComputingTime();
			JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
			pos=new PosProcessamento(population,preparacao);
			if (pos.getSolution().size()==0) 
				System.out.println("não foi encontrado uma sugestão aplicada ao seu caso, aguarde mais alguns momentos "
						+ "que estamos fazendo a tentativa "+(qtdDeInteracoes+1) + " de 5" );
			if (qtdDeInteracoes==5) break;
			qtdDeInteracoes+=1;
	    }while(pos.getSolution().size()==0 );
		
		population=pos.getSolution();
		
		printFinalSolutionSet(population);
		
		if (pos.getSolution().size()==0) 
			System.out.println("por algum motivo o aplicativo não encontrou "
					+ "uma solução aplicavel para o seu caso, por favor procure o coordenador ");
	}

	/**
	 * Write the population into two files and prints some data on screen
	 * 
	 * @param population
	 */
	public static void printFinalSolutionSet(List<? extends Solution<?>> population) {

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv")).print();

		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
		JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
		JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
	}

	/**
	 * Print all the available quality indicators
	 * 
	 * @param population
	 * @param paretoFrontFile
	 * @throws FileNotFoundException
	 */
	
	
	
}