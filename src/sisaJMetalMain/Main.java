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

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Problem<IntegerSolution> problem;
		Algorithm<List<IntegerSolution>> algorithm;
		CrossoverOperator<IntegerSolution> crossover;
		MutationOperator<IntegerSolution> mutation;
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
		String referenceParetoFront = "";
		
		
		Aluno aluno = new Aluno("abc", "123", false, "a@a", 2013, 1, 20, "FC", 0, 300);
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
		
		//***************************
//	
		algorithm = new NSGAIIIBuilder<>(problem).setCrossoverOperator(crossover).setMutationOperator(mutation)
				.setSelectionOperator(selection).setPopulationSize(1).setMaxIterations(1).build();
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

//		algorithm = new NSGAIIBuilder<>(problem, crossover, mutation)
//				.setSelectionOperator(selection).setPopulationSize(1000).setMaxEvaluations(1000).build();
//		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

		
		
		List<IntegerSolution> population = algorithm.getResult();
		long computingTime = algorithmRunner.getComputingTime();

		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
		PosProcessamento pos=new PosProcessamento(population,preparacao);
		population=pos.getSolution();
		printFinalSolutionSet(population);
//		if (!referenceParetoFront.equals("")) {
//			printQualityIndicators(population, referenceParetoFront);
//		}
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
	
	
	
//	public static void printQualityIndicators(List<? extends Solution<?>> population, String paretoFrontFile)
//			throws FileNotFoundException {
//		//jorge e esse paretofrontfile, o que é de onde vem e eu vou precisar dele?
//		Front referenceFront = new ArrayFront(paretoFrontFile);
//		FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront);
//
//		Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront);
//		Front normalizedFront = frontNormalizer.normalize(new ArrayFront(population));
//		List<DoubleSolution> normalizedPopulation = FrontUtils.convertFrontToSolutionList(normalizedFront);
//
//		String outputString = "\n";
//		outputString += "Hypervolume (N) : "
//				+ new Hypervolume<List<? extends Solution<?>>>(normalizedReferenceFront).evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "Hypervolume     : "
//				+ new Hypervolume<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//		outputString += "Epsilon (N)     : "
//				+ new Epsilon<List<? extends Solution<?>>>(normalizedReferenceFront).evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "Epsilon         : "
//				+ new Epsilon<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//		outputString += "GD (N)          : "
//				+ new GenerationalDistance<List<? extends Solution<?>>>(normalizedReferenceFront)
//						.evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "GD              : "
//				+ new GenerationalDistance<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//		outputString += "IGD (N)         : "
//				+ new InvertedGenerationalDistance<List<? extends Solution<?>>>(normalizedReferenceFront)
//						.evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "IGD             : "
//				+ new InvertedGenerationalDistance<List<? extends Solution<?>>>(referenceFront).evaluate(population)
//				+ "\n";
//		outputString += "IGD+ (N)        : "
//				+ new InvertedGenerationalDistancePlus<List<? extends Solution<?>>>(normalizedReferenceFront)
//						.evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "IGD+            : "
//				+ new InvertedGenerationalDistancePlus<List<? extends Solution<?>>>(referenceFront).evaluate(population)
//				+ "\n";
//		outputString += "Spread (N)      : "
//				+ new Spread<List<? extends Solution<?>>>(normalizedReferenceFront).evaluate(normalizedPopulation)
//				+ "\n";
//		outputString += "Spread          : "
//				+ new Spread<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//		try {
//			outputString += "R2 (N)          : "
//					+ new R2<List<DoubleSolution>>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//			outputString += "R2              : "
//					+ new R2<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		outputString += "Error ratio     : "
//				+ new ErrorRatio<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
//
//		JMetalLogger.logger.info(outputString);
//	}
}