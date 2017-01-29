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
private HashMap <Integer,Disciplina> disciplinaMapByCod=new HashMap <Integer, Disciplina>();


private Aluno aluno;


	public void removeForaDeOrdemDePreRequisito(){
	//	HashMap <Integer, Integer> ordemDeMatricula = new HashMap <Integer, Integer>();
		List<IntegerSolution>copia= new ArrayList<>();
		copia.addAll(this.solution);
		System.out.println("{método remove Fora De Ordem De PreRequisito}tamanho tamanho inicialda solution : "+ this.solution.size());
		for (IntegerSolution S:this.solution){
			String[] L=S.toString().split(" ");
			String tempo="";
			// descobre qtd de periodos da sugestão
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

			//coloca todos os ids de disciplinas já pagas numa lista 
			List <Integer> listaPeriodosPassados = new ArrayList<> ();
			for (Disciplina D: this.preparacao.getAluno().getDiscPagas()){
				listaPeriodosPassados.add(D.getId());
				}
			
			// percorre a sugestao ate numero de periodos validos
			// contando de 8 em 8 disciplinas e comparando com as que estão na lista de 
			// ja pagas nos periodos passados
			boolean pare=false;
			List <Integer> listaPeriodo = new ArrayList<> ();
			for (int i=1; i<(fim*8)+1;i++){
				// percorrendo a lista de pre requisitos
				
				try {
					for (Integer pre:this.disciplinaMap.get(L[i]).getPreRequisitos()){
						if (!listaPeriodosPassados.contains(this.disciplinaMapByCod.get(pre).getId())){// se a lista de disciplinas do passado não 
							copia.remove(S);					 // contem o pre requisitos para e retorna falso	
							pare=true;
							break;
							
						}
					}
				} catch (NullPointerException e) {
					continue;
				}
				//adiciona a disciplina a lista de disciplinas do periodo
				if (pare) break;
				listaPeriodo.add(Integer.parseInt(L[i]));
				// quando conta 8 disciplinas coloca elas nas lista de periodos passados e zera a lista do periodos
				if (i%8==0) {
					listaPeriodosPassados.addAll(listaPeriodo);
					listaPeriodo.removeAll(listaPeriodo);
				}
		}
	}
	this.solution=copia;
	System.out.println("{método remove Fora De Ordem De PreRequisito}tamanho tamanho final da solution : "+ this.solution.size());
	}
	
	/**
	 * metodo para testar se a solucao tem periodos com menos de 3 
	 * disciplinas 
	 */

	public void removeMenorQueTres(){
		List<IntegerSolution>copia= new ArrayList();
		copia.addAll(this.solution);
		System.out.println("{método remove menos que tres}tamanho tamanho inicial da solution : "+ this.solution.size());
		for (IntegerSolution S:this.solution){
			ArrayList<Integer> qtd  = new ArrayList<Integer>();
			String entrada = S.toString();
			entrada = entrada.replace("Variables:", ""); //subtitui variables: por espaço vazio 
			entrada = entrada.trim(); //remove espaço vazio criado anteriormente
			String [] sugestao = entrada.split(" ");
			//System.out.println(sugestao.length);
			int fim = 0;
			for (int i=1; i<sugestao.length; i++){ //procura o fim da sequencia de cadeiras
				if (sugestao[i].equals("Objectives:")){
					fim = i-7;
					break;
				}
			}
			fim  = fim/8;
			int k = 0;
			int contar = 0;
			for (int i=0; i<=fim; i++){ // cada i corresponde a 1 semestre
				int livre = 0;
				for (int j=0; j<8; j++){ //verifica quantas cadeiras livres tem o semestre
					if (sugestao[j+k].equals("0")){
						livre ++;
					}
					//System.out.println(sugestao[j+k]);
					
				}
				qtd.add(livre);
				if (livre>5 && livre<8){ //verifica quantos semestres tem nais de tres cadeiras livres e disconsidera os semestres zerados
					contar++;
				
				}
				//System.out.println(contar);
				k = k+8;
				
			}
			
			for (int i=1; i<qtd.size()-1; i++){ //este metodo
			if (qtd.get(i)!=0 && qtd.get(i-1)==0){
				copia.remove(S);
			}
		}
			if (contar!=0){
				
				try {
					copia.remove(S);
				} catch (Exception e) {
					System.out.println("Ja removida");				}
				
			}
			//System.out.println(checar);
	}
			
	this.solution=copia;
	System.out.println("{método remove menos que tres}tamanho tamanho final da solution : "+ this.solution.size());
	}



public void MontaHasMap(){
for (Disciplina D : this.Disciplinas ) {
   this.disciplinaMap.put(Integer.toString(D.getId()), D);
//	System.out.println("["+D.getDiaHora()[0]+" "+ ""+" "+D.getDiaHora()[1]+" "+ ""+D.getDiaHora()[2]+""
//											+ " "+ ""+D.getDiaHora()[3]+" "+ ""+D.getDiaHora()[4]+"]");
	}
for (Disciplina D : this.Disciplinas ) {
    this.disciplinaMapByCod.put(D.getCodigo(), D);
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
		List<IntegerSolution>copia= new ArrayList<>();
		List<Disciplina>copiaObrigatorias= new ArrayList<>();
		copia.addAll(this.solution);
		System.out.println("{método verifica todas as obrigatorias}tamanho inicial de solution: "+copia.size());
			for (IntegerSolution S:this.solution){
				String[] L=S.toString().split(" ");
				copiaObrigatorias.addAll(this.DisciplinasObrigatorias);
				for (String Z:L){
					if (Z.equals("Objectives:")) break;
					for (Disciplina D:this.DisciplinasObrigatorias){
						if (Z.equals(Integer.toString( D.getId())  )) {
							copiaObrigatorias.remove(D);
							break;
						}
					}
				}
				
				if (copiaObrigatorias.size()>0){
					copia.remove(S);// copia da população	
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
//				System.out.println(S);
				L=Buffer.split(" ");			
				float temp=Float.parseFloat(L[S.getNumberOfVariables()+2]);
				int fim = (int)temp;
				int modulo8=0;
				int proximoModulo8=8;
				String[] horario1;
				String [] horario2;				
				for (int i=1;i<=fim*8;i++){
					if (L[i]!="0"){		
						for (modulo8=i+1;modulo8<=proximoModulo8;modulo8++){
							try {
								horario1=this.disciplinaMap.get(L[i]).getDiaHora();
								horario2=this.disciplinaMap.get(L[modulo8]).getDiaHora();
								 for (int k=0;k<5;k++){
									 
										if ((horario1[k].equals(horario2[k]))&&(!horario1[k].equals(""))&&(!horario2[k].equals(""))){
											chocouHorario =true;
//											switch (k) {
//											case 0: System.out.println("segunda");											
//												break;											
//											case 1: System.out.println("terça");										
//												break;
//											case 2: System.out.println("quarta");										
//												break;
//											case 4: System.out.println("quinta");
//												break;
//											case 5: System.out.println("sexta");
//												break;
//											default:
//												break;
//											}
									
//											System.out.println("removi pois houve choque no horario: ");
//											System.out.println("["+horario1[0]+" "+" "+horario1[1]+" "+horario1[2]+" "+horario1[3]+" "+horario1[4]+"]");
//											System.out.println("["+horario2[0]+" "+" "+horario2[1]+" "+horario2[2]+" "+horario2[3]+" "+horario2[4]+"]");
//											System.out.println(horario1[k]+" é igual a "+horario2[k]);
//											System.out.println("id da discplina 1: "+L[i]);
//											System.out.println("id da discplina 2: "+L[modulo8]);
											break;
										}//else {System.out.println("não houve choque não removi");}								
									
								 }
							} catch (NullPointerException e) {
								continue;
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
//		for (int x=0; x<this.solution.size();x++){
//			System.out.println(this.solution.get(x));
//		}

		
	}
	
	
	public void removeSugestaoComDisciplinasRepetidas(){
		List<IntegerSolution>copia= new ArrayList<>();
		copia.addAll(this.solution);
		System.out.println("{método remove sogestao com disciplina repetida}tamanho inicial de solution: "+copia.size());
			for (IntegerSolution S:this.solution){
				List<String>sugestao= new ArrayList<>();
				String[] L=S.toString().split(" ");
				for (String Z:L){
					if (Z.equals("Objectives:")) break;
					if (!Z.equals("0")){
//						System.out.println("z!=0 :"+Z);
						if (sugestao.contains(Z)){
							copia.remove(S);// copia da população
							break;
						}else {
							sugestao.add(Z);
						}
						
					}
				}
			}
		this.solution=copia;
		System.out.println("{método remove sogestao com disciplina repetida}tamanho final de solution: "+solution.size());
	}
	
	public void RemoveDuplicatas(){
		List<IntegerSolution>copia= new ArrayList<>();
		copia.addAll(this.solution);
		System.out.println("{método remove duplicatas}tamanho inicial de solution: "+copia.size());
//		array list de arrayList
		ArrayList<ArrayList<String>> solut = new ArrayList<ArrayList<String>>();
		for (IntegerSolution S:this.solution){
			ArrayList<String> solutInterno = new ArrayList<String>();
			solutInterno.add(S.toString());
			if(solut.contains(solutInterno)){
				copia.remove(S);
			}else{
				solut.add(solutInterno);
			}
		}
		this.solution=copia;
		System.out.println("{método remove duplicatas}tamanho inicial de solution: "+copia.size());
		
	}
	
	
	
	public List<IntegerSolution> getSolution() {
		return solution;
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
		removeSugestaoComDisciplinasRepetidas();
		RemoveDuplicatas();
		
	}


}
