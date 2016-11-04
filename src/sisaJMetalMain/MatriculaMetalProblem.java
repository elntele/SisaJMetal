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

	//private OpticalNetworkProblem bonsProblem;
	//private SugestaoMatricula sugeSobAvaliacao;
	private PreparacaoDoProblema problemaPreparado;
	
	public MatriculaMetalProblem(PreparacaoDoProblema preparacao) {
		this.problemaPreparado = preparacao;
	}
	//problemaPreparado pode fornecer 3 informações:
	// lista com as disciplinas que faltam pagar
	//número de máximo períodos restante para concluir o curso
	// quantas disciplinas ainda falta pagar para cocluir o curso
	
	
	@Override
	public void evaluate(IntegerSolution solution) {
		Integer[] vars = new Integer[solution.getNumberOfVariables()];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
		}
		Double[] objectives = bonsProblem.evaluate(vars);
		
		solution.setObjective(0, objectives[0]);
		solution.setObjective(1, objectives[1]/93000);
		solution.setObjective(2, objectives[2]/1800000);
		solution.setObjective(3, 1.0 / (1.0 + objectives[3]));
		
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

	public Double[][] getTraffic() {
		return bonsProblem.getTraffic();
	}
	
	@Override
	public int getNumberOfObjectives() {
		return bonsProblem.getNumberOfObjectives();
	}

	@Override
	public int getNumberOfVariables() {
		return bonsProblem.getNumberOfVariables();
	}

	@Override
	public Integer getLowerBound(int index) {
		return bonsProblem.getLowerLimitVariableAt(index);
	}

	@Override
	public Integer getUpperBound(int index) {
		return bonsProblem.getUpperLimitVariableAt(index);
	}
	
	public Geolocation[] getLocations(){
		return bonsProblem.getLocations();
	}
}
