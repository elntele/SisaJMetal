package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import sisaJmetalbeans.Disciplina;
import sisaJmetalbeans.SugestaoMatricula;

public class PTDJMetalProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;

	//private OpticalNetworkProblem bonsProblem;
	private SugestaoMatricula sugeSobAvaliacao;
	public PTDJMetalProblem() {
		bonsProblem = new OpticalNetworkProblem(networkLoad, gmlFile);
	}
	
	// variacia do periodo
		public void VarianciaPeriodo(){
			float varia=0;
			ArrayList<Float>  minilist= new ArrayList<Float>();// um buffer pra lista
			//private List <Float> VarianciaPeriodo=new ArrayList<Float>();

			int cont=0, deivisorPorPeriodo=0;
			for(Disciplina D : this.sugeSobAvaliacao.getGradeDeHorarios()) {// percorre o array de disciplinas para calcular a variancia por periodo
				cont+=1;
				if (D.getNome()!="tampabrecha"){ // se não houver disciplina na posição
					deivisorPorPeriodo+=1;		// a estratégia é criar uma disciplina 
					}							// com esse nome	
				varia += (float) Math.pow( D.getGrauDificuldade()- this.sugeSobAvaliacao.getMedia(),2);
				if (cont%8==0){
					if (deivisorPorPeriodo!=0){
						
						minilist = (ArrayList<Float>) this.sugeSobAvaliacao.getVarianciaPeriodo();
						minilist.add(varia/deivisorPorPeriodo);
						this.sugeSobAvaliacao.setVarianciaPeriodo(minilist);
						cont=0;
					 }else{
						 minilist.add((float) 0);
						 this.sugeSobAvaliacao.setVarianciaPeriodo(minilist);
					}
				}
			}
			
		}

		
		//metodo pra contar quantas disciplinas de cada area
		public void contadorDeArea(){
			int buffer;
			for(Disciplina D : this.sugeSobAvaliacao.getGradeDeHorarios()){
				switch (D.getArea().getNome()){
					case "ensiso": buffer = this.sugeSobAvaliacao.getContadorAreaEnsiso();
								   buffer+=1;
								   this.sugeSobAvaliacao.setContadorAreaEnsiso(buffer);
									break;
					case "Arq":buffer = this.sugeSobAvaliacao.getContadorAreaArq();
					   		   buffer+=1;
					   		   this.sugeSobAvaliacao.setContadorAreaArq(buffer);
					   		   break;
					case "FC": buffer = this.sugeSobAvaliacao.getContadorAreaFC();
		   					   buffer+=1;
		   					   this.sugeSobAvaliacao.setContadorAreaFC(buffer);
		   					   break;
					default: break;					
				}
			}	
		}
		// compia da sugestão apenas uam lista com os códigos
	private void setaLista(){
		ArrayList<Integer>  minilist2= new ArrayList<Integer>();
		for(Disciplina D : this.sugeSobAvaliacao.getGradeDeHorarios()){
			minilist2=(ArrayList<Integer>) this.sugeSobAvaliacao.getGradeHorarioSoCodigoDaDisciplina();
			minilist2.add(D.getCodigo());
			this.sugeSobAvaliacao.setGradeHorarioSoCodigoDaDisciplina(minilist2);
		}

		
	}

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
