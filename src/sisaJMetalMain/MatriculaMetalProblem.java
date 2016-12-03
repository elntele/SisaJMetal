package sisaJMetalMain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import sisaJmetalbeans.Disciplina;

public class MatriculaMetalProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private PreparacaoDoProblema problemaPreparado;
	private int lowerBound = 0;
	private int upperBound;	
	private int tempoDeFormatura;
	private List <Disciplina> Disciplinas= new ArrayList<>();
	private List <Disciplina> NaoPagas0=new ArrayList<>();
	private List <Disciplina> NaoPagas1=new ArrayList<>();
	private List <Disciplina> NaoPagas2=new ArrayList<>();
	private HashMap <Integer, Disciplina> disciplinaMap = new HashMap <Integer, Disciplina>();
	


	//problemaPreparado pode fornecer 3 informações:
	// lista com as disciplinas que faltam pagar
	//número de máximo períodos restante para concluir o curso
	// quantas disciplinas ainda falta pagar para cocluir o curso
	
	public  int mudaSemestre (int mes){
		int back=0;
		if (mes==1) back=2;
		else back=1;
		
		return back;	
	}
	
	public int calendario(){
		Calendar cal = Calendar.getInstance();
		int mes=0;
        int month = cal.get(Calendar.MONTH) + 1;
		if (month==1||month==2||month==3||month==4||month==5||month==6){
			mes=1;
		}else{
			mes=2;
		}
			
		return mes;
	}
	
	public void montaNaoPagas(){
		for (Disciplina D:this.problemaPreparado.getNaoPagas())
		if (D.getSemestre()==0){ 
			this.NaoPagas0.add(D);
		}
		else if (D.getSemestre()==1){
			this.NaoPagas1.add(D);
			}
		else if (D.getSemestre()==2){
			this.NaoPagas2.add(D);
			}
	}
	
	public void MontaHasMap(){
		for (Disciplina D : this.Disciplinas ) {
		   this.disciplinaMap.put(D.getId(), D);
		}
	}
	
	public List<String> achaFatia (int i, String []L){
		List<String> Ret = new ArrayList<>();
		int indiceIni=i;
//		int indiceFim=i;
		for (int k=i;k<100;k++){
			if (indiceIni%8!=0 &&indiceIni!=0 ){
				indiceIni-=1;
			}
//			if (indiceFim==0){
//				indiceFim+=1;
//				}else if(indiceFim%8!=0){
//					indiceFim+=1;
//				}
			//if (indiceIni%8==0&&indiceFim%8==0) break;
			if (indiceIni%8==0) break;
		}
//		int indice=0;
		for (int w=indiceIni;w<i;w++){
			Ret.add(L[w]);
		}
		
		return Ret;
	}
	
	public boolean verificaSeJaexiste(IntegerSolution sol, int id, int i){
		boolean retornando=false;
//		String Buffer="";
//		Buffer=sol.toString();
		//System.out.println("buffer "+Buffer);
//		String []L=Buffer.split(" ");
//		List <String> fatia=new ArrayList<>();
//		System.arraycopy(L, 1, L, 0, this.problemaPreparado.getPeriodosRestantes()*8);
//		fatia=achaFatia(i,L);
		//for (String S:fatia){
			//if (S.equals(Integer.toString(id))){
			if (sol.getVariableValue(i)==id){ // esse é que foi add
				retornando=true;
			//}
		}
		return retornando;
	}
	
	public IntegerSolution trocaPosicao(IntegerSolution sol, int id, int i){
		boolean t=true;
//		IntegerSolution um =sol;
		int w,z;
		for ( w=i;w<i+10;w++){
			if (w%8==0&&w!=0)break;
		}
		
		for ( z=i; z<w;z++){
				//System.out.println(sol.getVariableValue(z));
			if (sol.getVariableValue(z).toString().equals(Integer.toString(id))){
				sol.setVariableValue(z, 0);
				sol.setVariableValue(i, id);
				break;
			}
		}
		
//	IntegerSolution dois =sol;
//	while (t){
//		System.out.println("id: "+id);
//		System.out.println("i: "+i);
//		System.out.println("z: "+z);
//		System.out.println("w: "+w);
//		System.out.println(um);
//		System.out.println(dois);
//	}		
		return sol;
		
	}
	
	public boolean comparaDiahora(String[] horario1,String [] horario2){
		boolean chocouHorario=false;
		for (int k=0;k<5;k++){
			if ((horario1[k].equals(horario2[k]))&&(!horario1[k].equals(""))&&(!horario2[k].equals(""))){
				chocouHorario =true;
				System.out.println("houve choque no horario: ");
				System.out.println("["+horario1[0]+" "+" "+horario1[1]+" "+horario1[2]+" "+horario1[3]+" "+horario1[4]+"]");
				System.out.println("["+horario2[0]+" "+" "+horario2[1]+" "+horario2[2]+" "+horario2[3]+" "+horario2[4]+"]");
				System.out.println(horario1[k]+" é igual a "+horario2[k]);
	
				break;
			}
		}
		return chocouHorario;
	}
	
	
	public boolean choqueDeHorario(IntegerSolution sol, int id, int i){
				boolean retornado=false;
				String Buffer="";
				Buffer=sol.toString();
				System.out.println("buffer "+Buffer);
				String []L=Buffer.split(" ");
				List <String> fatia=new ArrayList<>();
				System.arraycopy(L, 1, L, 0, this.problemaPreparado.getPeriodosRestantes()*8);
				fatia=achaFatia(i,L);// de modulo 8 ate i
				String[] horario1;
				String [] horario2;
				//System.out.println("i="+i);
				System.out.println(fatia);
				System.out.println("id: "+id);
				for (String S: fatia){
					if (!S.equals("0")){
						horario1=this.disciplinaMap.get(Integer.parseInt(S)).getDiaHora();
						horario2=this.disciplinaMap.get(id).getDiaHora();				
						if (comparaDiahora(horario1, horario2)){
							retornado =true;
							System.out.println("id disciplina1: "+S);
							System.out.println("id disciplina2: "+id);
							break;
						}
					}
				}

				return retornado;
			}
	
	
	public IntegerSolution resetSugestao(IntegerSolution sol,int tamanhoDaSugestao){
		boolean t=true;
		List<Disciplina>copiaJaCursadas=new ArrayList<>();
		//copiaJaCursadas.addAll(this.problemaPreparado.getAluno().getDiscPagas());
//		for (Disciplina D:copiaJaCursadas){
//			for (int i=0;i<tamanhoDaSugestao;i++){
//				if (D.getId()==sol.getVariableValue(i)){
//					sol.setVariableValue(i, 0);
//				}
//			}
//		}
		for (int i=0;i<tamanhoDaSugestao;i++) sol.setVariableValue(i, 0);		
//		while(t)System.out.println(sol);
		return sol;
	}
	
	
	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this); 
		List<Disciplina> copia0=new ArrayList<>();
		List<Disciplina> copia1=new ArrayList<>();
		List<Disciplina> copia2=new ArrayList<>();
		boolean falhou=false;
		int tamanhoDaSugestao=problemaPreparado.getPeriodosRestantes()*8;
		int temQuePagar=problemaPreparado.getQtdDiscplinasParaConcluir();
		copia0.addAll(this.NaoPagas0);
		copia1.addAll(this.NaoPagas1);
		copia2.addAll(this.NaoPagas2);
		int semestreDaVez=calendario();
		Random gerador = new Random();		 
        double numero;// utilizado para guardar resultado de randômico entre 0 e 100 
        int modulo8=0; // utilizado para medir passagem de periodo de 8 em 8 posições no array
        int contaCadeira=0; //utilizado para contar quantas disciplinas etm em cada periodo
        int troca1=0; // guarda posição com zero antes do periodo final para uma possivel troca
        int troca2=0;// guarda posição com zero antes do periodo final para uma possivel troca
        int lugarPraZero=tamanhoDaSugestao-temQuePagar; //// guarda quantidade de posições que pode-se alocar 0
        
        //Psc.: 0 significa que não há disciplinas na posição do array 
//        retorno=resetSugestao(retorno,tamanhoDaSugestao);
//        boolean T=true;
//        while (T) System.out.println(temQuePagar+" "+lugarPraZero);
        for (int i=0;i<tamanhoDaSugestao;i++){
			if (temQuePagar!=0){
        	numero=gerador.nextDouble()*100;
			}else {
				numero=10;
					}
			int indexDeCopia =0;//inteiro que guarda o randâmico entre 0 e o tamanho do arraylist de disciplinas não pagas 
			
//			if (modulo8>=4&&contaCadeira<3){// se o contador modulo8 estiver entre a posição 5 e 8 
//				numero=38.00;				// e contador de disciplinas for menor que 3 ele força
//				}							// o alg. a colocar mais disciplinas no periodo	
//			
			if (numero<=37.5&&lugarPraZero!=0){
				retorno.setVariableValue(i, 0);
				lugarPraZero-=1;
				System.out.println("opa sortiei o 0");
				} 
				if ((copia0.size()!=0||copia1.size()!=0||copia2.size()!=0)&&temQuePagar!=0&&numero>37.5){
					System.out.println("entrei i:="+i);
					boolean verificaChoque=true;
					double numero2=gerador.nextDouble()*100;
					if (copia0.size()!=0&&numero2>20){
						int contador0=copia0.size();
						for(indexDeCopia=0;indexDeCopia<contador0;indexDeCopia++){
							System.out.println("diciplinas obrigatórias todo periodo");
							System.out.println("i="+i);
							verificaChoque=choqueDeHorario(retorno, copia0.get(indexDeCopia).getId(),i);
							if (!verificaChoque){
								retorno.setVariableValue(i, copia0.get(indexDeCopia).getId());
								System.out.println("não houve choque, coloquei o id: "+copia0.get(indexDeCopia).getId());
								copia0.remove(indexDeCopia);
								temQuePagar-=1;
								System.out.println("tem que pagar: "+temQuePagar);
								break;
							}
						}
					}
					if(verificaChoque){
						numero2=1;// força entrar em uma das optativas
					}
					if (semestreDaVez==1&&copia1.size()!=0&&numero2<=20){
						int contador1=copia1.size();
						for(indexDeCopia=0;indexDeCopia<contador1;indexDeCopia++){
							System.out.println("semetres impares");
							System.out.println("i="+i);
							verificaChoque=choqueDeHorario(retorno, copia1.get(indexDeCopia).getId(),i);
							if (!verificaChoque){
								retorno.setVariableValue(i, copia1.get(indexDeCopia).getId());
								System.out.println("não houve choque, coloquei o id: "+copia1.get(indexDeCopia).getId());
								copia1.remove(indexDeCopia);
								temQuePagar-=1;
								System.out.println("tem que pagar: "+temQuePagar);
								break;
								}
						}
				
						
	
					}
					if (semestreDaVez==2&&copia2.size()!=0&&numero2<=20){
							int contador2=copia2.size();
							for(indexDeCopia=0;indexDeCopia<contador2;indexDeCopia++){
								System.out.println("semestre pares");
								System.out.println("i="+i);
								verificaChoque=choqueDeHorario(retorno, copia2.get(indexDeCopia).getId(),i);
								if (!verificaChoque){
									retorno.setVariableValue(i, copia2.get(indexDeCopia).getId());
									System.out.println("não houve choque, coloquei o id: "+copia2.get(indexDeCopia).getId());
									copia2.remove(indexDeCopia);
									temQuePagar-=1;
									System.out.println("tem que pagar: "+temQuePagar);
									break;
								}
							}
					}
					if (verificaChoque) falhou=true;
					if (falhou&&lugarPraZero!=0){
						retorno.setVariableValue(i, 0);
						lugarPraZero-=1;
						System.out.println("coloquei 0");
						//System.out.println("i: "+i);
						System.out.println("lugar para 0: "+lugarPraZero);
						System.out.println("falta pagar: "+temQuePagar);
					}		
				}
	Collections.shuffle(copia0);
	Collections.shuffle(copia1);
	Collections.shuffle(copia2);

	modulo8+=1;
	if (modulo8%8==0&&modulo8!=0){
		semestreDaVez=mudaSemestre (semestreDaVez);
		}
	 }
		return retorno;
  }
	
	
	
	
	int contador=0;
	@Override
	/**
	 * solution E um cromossomo retornado pelo algoritmo na  
	 * Classe main... Procure por populationna na classe  
	 * Main e siga suas transformacoes 
	 */

	
	
	public void evaluate(IntegerSolution solution) {
		// qual o tamanho desse array ?
		// esse é o array sugestão de matricula
		//então ele tera que ser do tamanho de 
		//numero de periodos restantes *8
		
		int [] vars = new int[solution.getNumberOfVariables()];
		List <Integer> impr= new ArrayList();
		for (int i = 0; i < vars.length; i++) {
			vars[i] = solution.getVariableValue(i);
// daqui para baixo			
//			if (i%8==0 && i!=0){
//			
//				System.out.println(impr);
//					impr.clear();			
//			}
//			impr.add(solution.getVariableValue(i));
//			if (i%79==0 && i!=0){
//				System.out.println(impr);
//				System.out.println(problemaPreparado.getTempoDeFormatura());
//				System.out.println(problemaPreparado.getVarianciaTotal());
//				System.out.println(problemaPreparado.getQtdDiscForaDaMinhaArea());
//				System.out.println(problemaPreparado.getVariaQtdDiscPorPeriodo());
//				System.out.println(problemaPreparado.getTempoExtraClasse());
//				System.out.println("**************************************"
//						+ "*********************************");
//				contador+=1;
//				System.out.println(contador);
//			}
// daqui para cima
		}
		problemaPreparado.setSugestaoMat(vars);
		problemaPreparado.contaTempoDeFormatura();
		problemaPreparado.varianciaTotal();
		problemaPreparado.verificaAcompNoPrimeDasug();
		problemaPreparado.qtdDeDisciplinasForaDeMinhaArea();
		problemaPreparado.varianciaDeQtdDeDiscPorPeriodo();
		problemaPreparado.tempoDeEstudoExtraClasse();
		//setando a sugestão de matricula em problema preparado				
		solution.setObjective(0, problemaPreparado.getTempoDeFormatura());
		solution.setObjective(1, problemaPreparado.getVarianciaTotal());
		solution.setObjective(2, problemaPreparado.getQtdDiscForaDaMinhaArea());
		solution.setObjective(3, problemaPreparado.getVariaQtdDiscPorPeriodo());
		solution.setObjective(4, problemaPreparado.getTempoExtraClasse());
		
	}

	
//	@Override
//	public int getNumberOfObjectives() {
//		return problemaPreparado.getNumberOfObjetives();
//	}
	// number of variable será o numero de disciplinas que faltam
	// pagar para terminar o curso
	@Override
	public int getNumberOfVariables() {
		return problemaPreparado.getPeriodosRestantes()*8;// tamanho maximo do array
													// de sugestão de matricula	
	}

	
	@Override
	public Integer getLowerBound(int index) {
		return this.lowerBound;
	}

	//jorge
	// e o upper bound
	@Override
	public Integer getUpperBound(int index) {
		return this.upperBound;
	}


	public int getTempoDeFormatura() {
		return tempoDeFormatura;
	}
	
	
	public void removeDisc(){
		
	}


	public MatriculaMetalProblem(PreparacaoDoProblema preparacao) {
		super();
		this.setNumberOfObjectives(5);
		this.setNumberOfVariables(preparacao.getPeriodosRestantes()*8);
		this.problemaPreparado = preparacao;		
		this.upperBound=preparacao.getDisciplinas().size();
		//this.upperBound=0;
		this.Disciplinas=preparacao.getDisciplinas();
		MontaHasMap();
		montaNaoPagas();
	
	}


}
