package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.lang3.tuple.Pair;
import org.uma.jmetal.solution.IntegerSolution;

public class PreparaRetorno {
	private List<IntegerSolution>  solution;
	ArrayList<ArrayList<ArrayList<String>>> retorno = new ArrayList<ArrayList<ArrayList<String>>>();
	
	public PreparaRetorno(List<IntegerSolution> solution) {
		super();
		this.solution = solution;
		montaRetorno();
	}
	
	public void montaRetorno(){
		List<IntegerSolution>copia= new ArrayList<>();
		copia.addAll(this.solution);
//		arraylist de arrayList
		
		for (IntegerSolution S:this.solution){
			ArrayList<ArrayList<String>> solucao = new ArrayList<ArrayList<String>>();// 
			ArrayList<String> Sugestao = new ArrayList<>();
			ArrayList<String> objetivos = new ArrayList<>();
			
			String[] L=S.toString().split(" ");
			String tempo="";
			int w=0;
			for (String sug: L){			
				if (sug.equals("Objectives:")){
					tempo=L[w+1];
					break;
				}
				w+=1;
			}
			float temp=Float.parseFloat(tempo);
			int fim = (int)temp;
			
			for(int i=1;i<=fim*8;i++){
				Sugestao.add(L[i]);
				
			}
			
			for (int i=w+1;i<w+6;i++){
				objetivos.add(L[i]);
			}
			
			solucao.add(Sugestao);
			solucao.add(objetivos);
			this.retorno.add(solucao);
				
		}
	}

	public ArrayList<ArrayList<ArrayList<String>>> getRetorno() {
		return retorno;
	}		
	
	
		
}
 

