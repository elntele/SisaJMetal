package sisaJMetalMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uma.jmetal.solution.IntegerSolution;

import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class PosProcessamento {
private List<IntegerSolution>  solution;
private PreparacaoDoProblema preparacao;
private List <Disciplina> DisciplinasObrigatorias= new ArrayList();
private List <Disciplina> Disciplinas= new ArrayList();
private HashMap <String, Disciplina> disciplinaMap = new HashMap <String, Disciplina>();


private Aluno aluno;


	public void removeForaDeOrdemDePreRequisito(){
		HashMap <Integer, Integer> ordemDeMatricula = new HashMap <Integer, Integer>();
		List<IntegerSolution>copia= new ArrayList<>();
		copia.addAll(this.solution);
		System.out.println("{tamanho de solution no fim do metodo remove Fora De Ordem De PreRequisito: }"+ this.solution.size());
		for (IntegerSolution S:this.solution){
			String[] L;
			String Buffer=S.toString();
			//System.out.println(S);
			L=Buffer.split(" ");			
			float temp=Float.parseFloat(L[S.getNumberOfVariables()+2]);
			int modulo8=8;
			//ordemDeMatricula.put(1, 1);
			int contador=0;
			for (Disciplina D: this.aluno.getDiscPagas()){
				ordemDeMatricula.put(D.getCodigo(),contador);
				contador+=1;
			}
			
			boolean pare=false;
			for (int i=0; i<this.preparacao.getTempoDeFormatura()*8;i+=8){
				try {
					if (!L[i].equals("0"))ordemDeMatricula.put(this.disciplinaMap.get(L[i]).getCodigo(), i+contador);
					for (Integer IT:this.disciplinaMap.get(L[i]).getPreRequisitos()){
						System.out.println("estou interanod dentro do array de prerequisitos");
						System.out.println("modulo8="+modulo8);
						System.out.println("lista de pre requisitos"+this.disciplinaMap.get(L[i]).getPreRequisitos());
						System.out.println("prerequisito"+IT);
						System.out.println(ordemDeMatricula.get(IT));
						int ordem=(int)ordemDeMatricula.get(IT);
						System.out.println("ordem="+ordem);
						
						if (ordem>modulo8){
							copia.remove(S);
							pare=true;
							System.out.println("removi pois não esta em orde");
							break;
						}
				}
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (i%8==0&&i!=0){
					modulo8=(i%8)*8;
				}
				if (pare) break;
			}
		}
	this.solution=copia;
	System.out.println("{tamanho de solution no fim do metodo remove Fora De Ordem De PreRequisito: }"+ this.solution.size());

		
		
	}
	public void removeMenorQueTres(){
		List<IntegerSolution>copia= new ArrayList();
		copia.addAll(this.solution);
		System.out.println("tamanho de solution no inicio do metodo remove menos que tres: }"+ this.solution.size());
		for (IntegerSolution S:this.solution){
			String[] L;
			String Buffer="";
			Buffer=S.toString();
			//System.out.println(S);
			L=Buffer.split(" ");			
			float temp=Float.parseFloat(L[S.getNumberOfVariables()+2]);
			int fim = (int)temp;
			int contaCadeira=0;
			int modulo8=0;
			for (int i=0; i<this.preparacao.getTempoDeFormatura()*8;i++){
				if (!L[i].equals("0")){
					//System.out.println("oh nos aqui de novo"+L[i]);
					contaCadeira+=1;
				}
				if (modulo8%8==0&&modulo8!=0){	
					if(contaCadeira<3) copia.remove(S);
					contaCadeira=0;
				}
				modulo8+=1;
			}
		}
	this.solution=copia;
	System.out.println("tamanho de solution no inicio do metodo remove menos que tres: }"+ this.solution.size());
	}


public void MontaHasMap(){
for (Disciplina D : this.Disciplinas ) {
   this.disciplinaMap.put(Integer.toString(D.getId()), D);
	System.out.println("["+D.getDiaHora()[0]+" "+ ""+" "+D.getDiaHora()[1]+" "+ ""+D.getDiaHora()[2]+""
											+ " "+ ""+D.getDiaHora()[3]+" "+ ""+D.getDiaHora()[4]+"]");
	}
}





	public void teste(){
			for (int i=0;i<this.solution.size();i++){
				System.out.println(solution.get(i));
				
			}
			
		} 
	
	public void montaDisciplinasObrigatorias(){
		for (Disciplina D:this.Disciplinas){			
			if(!D.getPeriodo().equals("0")){
				this.DisciplinasObrigatorias.add(D);
			}
		}
		
	}
	
	public void removeJaPagas(){
		List <Disciplina> copia= new ArrayList<>();
		copia.addAll(this.DisciplinasObrigatorias);
		for (Disciplina D:this.DisciplinasObrigatorias){
			for (Disciplina Da:this.aluno.getDiscPagas()){
				if (D.getId()==Da.getId()){
					copia.remove(Da);
				}
			}
		}
		this.DisciplinasObrigatorias=copia;
}
	
	public void removeNaoContemplaObrigatorias(){
		int qtdObrigatorias=this.DisciplinasObrigatorias.size();
		boolean encontrou =false;
		String[] L;
		String Buffer="";
		List<IntegerSolution>copia= new ArrayList();
		copia.addAll(this.solution);
		System.out.println("{método verifica todas as obrigatorias}tamanho inicial de solution: "+copia.size());
		for (Disciplina D:this.DisciplinasObrigatorias){
			for (IntegerSolution S:this.solution){
				Buffer=S.toString();
				L=Buffer.split(" ");
				for (int i=0;i<L.length;i++){
					//System.out.println(L[i]);
					if (L[i].equals("Objectives:")) break;
					if (L[i].equals(Integer.toString( D.getId())  )) {
						//System.out.println("encontrei em L["+i+"]");
						encontrou=true;
						break;
					}
			
				}
				if (!encontrou){
				copia.remove(S);
				}
				encontrou=false;
			}
		}
		this.solution=copia;
		System.out.println("{método verifica todas as obrigatorias}tamanho final de solution: "+solution.size());
	}
	
	public void removeChoqueDeHorario(){		
		boolean chocouHorario =false;
		String[] L;
		String Buffer="";
		List<IntegerSolution>copia= new ArrayList();
		copia.addAll(this.solution);
		System.out.println("{método verifica choque de horario}tamanho inicial de solution: "+copia.size());		
			for (IntegerSolution S:this.solution){
				Buffer=S.toString();
				System.out.println(S);
				L=Buffer.split(" ");			
				float temp=Float.parseFloat(L[S.getNumberOfVariables()+2]);
				int fim = (int)temp;
				int modulo8=0;
				int proximoModulo8=8;
				String[] horario1;
				String [] horario2;				
				for (int i=1;i<fim*8;i++){
					if (L[i]!="0"){		
						for (modulo8=i+1;modulo8<proximoModulo8;modulo8++){
							try {
								horario1=this.disciplinaMap.get(L[i]).getDiaHora();
								horario2=this.disciplinaMap.get(L[modulo8]).getDiaHora();
								 for (int k=0;k<5;k++){
									 
										if ((horario1[k].equals(horario2[k]))&&(!horario1[k].equals(""))&&(!horario2[k].equals(""))){
											chocouHorario =true;
											switch (k) {
											case 0: System.out.println("segunda");											
												break;											
											case 1: System.out.println("terça");										
												break;
											case 2: System.out.println("quarta");										
												break;
											case 4: System.out.println("quinta");
												break;
											case 5: System.out.println("sexta");
												break;
											default:
												break;
											}
									
											System.out.println("removi pois houve choque no horario: ");
											System.out.println("["+horario1[0]+" "+" "+horario1[1]+" "+horario1[2]+" "+horario1[3]+" "+horario1[4]+"]");
											System.out.println("["+horario2[0]+" "+" "+horario2[1]+" "+horario2[2]+" "+horario2[3]+" "+horario2[4]+"]");
											System.out.println(horario1[k]+" é igual a "+horario2[k]);
											System.out.println("id da discplina 1: "+L[i]);
											System.out.println("id da discplina 2: "+L[modulo8]);
											break;
										}//else {System.out.println("não houve choque não removi");}								
									
								 }
							} catch (Exception e) {
								// TODO: handle exception
							}
							if(chocouHorario) break;
						}
					}
					if (i%8==0) proximoModulo8+=8;
					if(chocouHorario) break;
				}
				if (chocouHorario){
					copia.remove(S);
				}
				chocouHorario=false;
			}
		
		this.solution=copia;
		System.out.println("{método verifica choque de horario}tamanho final de solution: "+solution.size());
		for (int x=0; x<this.solution.size();x++){
			System.out.println(this.solution.get(x));
		}

		
	}
	
	
	public PosProcessamento(List<IntegerSolution> solution, PreparacaoDoProblema preparacao) {
		super();
		this.solution = solution;
		this.preparacao=preparacao;
		//teste();
		this.Disciplinas=preparacao.getDisciplinas();
		this.aluno=preparacao.getAluno();
		montaDisciplinasObrigatorias();
		removeJaPagas();
		removeNaoContemplaObrigatorias();
		MontaHasMap();
		removeChoqueDeHorario();
		removeMenorQueTres();
		removeForaDeOrdemDePreRequisito();
		
	}


}
