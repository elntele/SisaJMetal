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

public void MontaHasMap(){
for (Disciplina D : this.Disciplinas ) {
   this.disciplinaMap.put(Integer.toString(D.getId()), D);
      
   if (!this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[0].equals("")){
	   System.out.println("valor de 0:"+this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[0]);
   }else{
	   System.out.println("horario 0 vario");
   }
   if (!this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[1].equals("")){
	   System.out.println("valor de 1:"+this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[1]);
   }else{
	   System.out.println("horario 1 vario");
   }
   if (!this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[2].equals("")){
	   System.out.println("valor de 2:"+this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[2]);
   }else{
	   System.out.println("horario 2 vario");
   }
   if (!this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[3].equals("")){
	   System.out.println("valor de 3:"+this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[3]);
   }else{
	   System.out.println("horario vario");
   }
   if (!this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[4].equals("")){
	   System.out.println("valor de 4:"+this.disciplinaMap.get(Integer.toString(D.getId())).getDiaHora()[4]);
   }else{
	   System.out.println("horario vario");
   }
   
 }
}




private Aluno aluno;
	public void teste(){
			for (int i=0;i<this.solution.size();i++){
				System.out.println(solution.get(i));
				
			}
			
		} 
	
	public void montaDisciplinasObrigatorias(){
		for (Disciplina D:this.Disciplinas){
			
			if(!D.getPeriodo().equals("0")){
				this.DisciplinasObrigatorias.add(D);
				//System.out.println(D.getNome());
			}
		}
		
	}
	
	public void removeJaPagas(){
		List <Disciplina> copia= new ArrayList();
		copia.addAll(this.DisciplinasObrigatorias);
		for (Disciplina D:this.DisciplinasObrigatorias){
			//System.out.println(D.getNome());
			for (Disciplina Da:this.aluno.getDiscPagas()){
				
				if (D.getId()==Da.getId()){
					copia.remove(Da);
				}
			}
		}
		this.DisciplinasObrigatorias=copia;
//		System.out.println(copia.get(0).getNome());
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
		String separaFim="";
		String separaFim1="";
		List<IntegerSolution>copia= new ArrayList();
		copia.addAll(this.solution);
		System.out.println("{método verifica choque de horario}tamanho inicial de solution: "+copia.size());		
			for (IntegerSolution S:this.solution){
				Buffer=S.toString();
				L=Buffer.split(" ");
				//System.out.println("esse aqui"+L[S.getNumberOfVariables()+2]);
				int fim=0;
				float temp=Float.parseFloat(L[S.getNumberOfVariables()+2]);
				fim = (int)temp;
				int modulo8=0;
				int proximoModulo8=8;
				String[] horario1;
				String [] horario2;				
				for (int i=1;i<fim*8;i++){
					//System.out.println(L[i]);
					if (L[i]!="0"){		
						for (modulo8=i+1;modulo8<proximoModulo8;modulo8++){
							//System.out.println("entrei no for do modulo8 ");
							try {
								 
								horario1=this.disciplinaMap.get(L[i]).getDiaHora();
								horario2=this.disciplinaMap.get(L[modulo8]).getDiaHora();
								 for (int k=0;k<5;k++){
								   // if (!horario1[k].equals(""))System.out.println("horario1: "+ horario1[k]);
								    //if (!horario1[k].equals(""))System.out.println("horario2: "+ horario2[k]);
										if (horario1[k]==horario2[k]&&!horario1[k].equals("")){
											chocouHorario =true;	
								//			System.out.println("removi poi houve choque no horario: "+horario1[k]);
											break;
										}//else {System.out.println("não houve choque não removi");}								
									if(chocouHorario) break;
								 }
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
					if (i%8==0) proximoModulo8+=8;					
				}
				if (chocouHorario){
					copia.remove(S);
				}
				chocouHorario=false;
			}
		
		this.solution=copia;
		System.out.println("{método verifica choque de horario}tamanho final de solution: "+solution.size());

		
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
	}


}
