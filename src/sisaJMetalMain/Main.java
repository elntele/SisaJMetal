package sisaJMetalMain;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	public static ArrayList<ArrayList<ArrayList<String>>> main(/*String args[]*/Aluno aluno, List <Disciplina> disciplinasGerais ) throws FileNotFoundException {
		Problem<IntegerSolution> problem;
		Algorithm<List<IntegerSolution>> algorithm;
		CrossoverOperator<IntegerSolution> crossover;
		MutationOperator<IntegerSolution> mutation;
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
		Aluno alunoReal = aluno;
		Disciplina disciplinaReal;
		List <Disciplina> listaDisciplinasDoCurso = disciplinasGerais;
//		List <Disciplina> listaDisciplinasDoCurso = new ArrayList<>();
//		listaDisciplinasDoCurso=disciplinasGerais;

		
		
		
//	********************	Alunos de jorge**************************
		Aluno alana = new Aluno("alana", "1245", false, "a@a", 2014, 2, 5, "FC", 0);//aluno5	
		PreparacaoDoProblema preparacao =new PreparacaoDoProblema(alunoReal,listaDisciplinasDoCurso);
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
					.setSelectionOperator(selection).setPopulationSize(500).setMaxIterations(30).build();
	    
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
		
		PreparaRetorno prepara=new PreparaRetorno(population);
		 
		
		
		printFinalSolutionSet(population);
		if (pos.getSolution().size()==0) 
			System.out.println("por algum motivo o aplicativo não encontrou "
					+ "uma solução aplicavel para o seu caso, por favor procure o coordenador ");
		
		return prepara.getRetorno();
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