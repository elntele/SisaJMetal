//package sisaJMetalMain;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//
//import org.uma.jmetal.algorithm.Algorithm;
//import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
//import org.uma.jmetal.operator.CrossoverOperator;
//import org.uma.jmetal.operator.MutationOperator;
//import org.uma.jmetal.operator.SelectionOperator;
//import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
//import org.uma.jmetal.problem.Problem;
//import org.uma.jmetal.qualityindicator.impl.Epsilon;
//import org.uma.jmetal.qualityindicator.impl.ErrorRatio;
//import org.uma.jmetal.qualityindicator.impl.GenerationalDistance;
//import org.uma.jmetal.qualityindicator.impl.Hypervolume;
//import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
//import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
//import org.uma.jmetal.qualityindicator.impl.R2;
//import org.uma.jmetal.qualityindicator.impl.Spread;
//import org.uma.jmetal.solution.DoubleSolution;
//import org.uma.jmetal.solution.IntegerSolution;
//import org.uma.jmetal.solution.Solution;
//import org.uma.jmetal.util.AlgorithmRunner;
//import org.uma.jmetal.util.JMetalLogger;
//import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
//import org.uma.jmetal.util.front.Front;
//import org.uma.jmetal.util.front.imp.ArrayFront;
//import org.uma.jmetal.util.front.util.FrontNormalizer;
//import org.uma.jmetal.util.front.util.FrontUtils;
//import org.uma.jmetal.util.pseudorandom.JMetalRandom;
//
//import sisaJmetalbeans.SugestaoMatricula;
//
//public class Main {
//	public static void main(String[] args) throws FileNotFoundException {
//		Problem<IntegerSolution> problem;
//		Algorithm<List<IntegerSolution>> algorithm;
//		CrossoverOperator<IntegerSolution> crossover;
//		MutationOperator<IntegerSolution> mutation;
//		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
//		String referenceParetoFront = "";
//		//SugestaoMatricula sugestao = new SugestaoMatricula();
//		
//		
//		
//		
//		
//		problem = new PTDJMetalProblem(100, "benchmarks/nsfnet.gml");
//
//		double crossoverProbability = 1.0;
//		//crossover = new OpticalNetworkCrossover(crossoverProbability);
//
//		double mutationProbability = 1.0 / problem.getNumberOfVariables();
//		mutation = new OpticalNetworkMutation(mutationProbability);
//
//		selection = new BinaryTournamentSelection<IntegerSolution>();
//
//		algorithm = new NSGAIIIBuilder<>(problem).setCrossoverOperator(crossover).setMutationOperator(mutation)
//				.setSelectionOperator(selection).setPopulationSize(100).setMaxIterations(5000).build();
//		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
//
//		List<IntegerSolution> population = algorithm.getResult();
//		long computingTime = algorithmRunner.getComputingTime();
//
//		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
//
//		printFinalSolutionSet(population);
//		if (!referenceParetoFront.equals("")) {
//			printQualityIndicators(population, referenceParetoFront);
//		}
//	}
//
//	/**
//	 * Write the population into two files and prints some data on screen
//	 * 
//	 * @param population
//	 */
//	public static void printFinalSolutionSet(List<? extends Solution<?>> population) {
//
//		new SolutionSetOutput.Printer(population).setSeparator("\t")
//				.setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
//				.setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv")).print();
//
//		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
//		JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
//		JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
//	}
//
//	/**
//	 * Print all the available quality indicators
//	 * 
//	 * @param population
//	 * @param paretoFrontFile
//	 * @throws FileNotFoundException
//	 */
//	public static void printQualityIndicators(List<? extends Solution<?>> population, String paretoFrontFile)
//			throws FileNotFoundException {
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
//}