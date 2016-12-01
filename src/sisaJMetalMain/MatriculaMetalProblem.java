package sisaJMetalMain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import com.mysql.fabric.xmlrpc.base.Array;

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
		//String[] Ret= new  String[8];
		//int[] fatia=new int[(10)+1];
		int indiceIni=i;
		int indiceFim=i;
		for (int k=i;k<100;k++){
			if (indiceIni%8!=0 &&indiceIni!=0 ){
				indiceIni-=1;
			}
			if (indiceFim==0){
				indiceFim+=1;
				}else if(indiceFim%8!=0){
					indiceFim+=1;
				}
			if (indiceIni%8==0&&indiceFim%8==0) break;
		}
		int indice=0;
		for (int w=indiceIni;w<indiceFim;w++){
			Ret.add(L[w]);
		}
		
		return Ret;
	}
	
	public boolean verificaSeJaexiste(IntegerSolution sol, int id, int i){
		boolean retornando=false;
		String Buffer="";
		Buffer=sol.toString();
		//System.out.println("buffer "+Buffer);
		String []L=Buffer.split(" ");
		List <String> fatia=new ArrayList<>();
		System.arraycopy(L, 1, L, 0, this.problemaPreparado.getPeriodosRestantes()*8);
		fatia=achaFatia(i,L);
		for (String S:fatia){
			if (S.equals(Integer.toString(id))){
				retornando=true;
			}
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
				//int[] fatia;
				System.out.println("buffer "+Buffer);
				String []L=Buffer.split(" ");
				List <String> fatia=new ArrayList<>();
				
				//System.out.println("tempo de formatora *8= " + this.problemaPreparado.getPeriodosRestantes()*8);
				System.arraycopy(L, 1, L, 0, this.problemaPreparado.getPeriodosRestantes()*8);
				fatia=achaFatia(i,L);
				String[] horario1;
				String [] horario2;				
				System.out.println(fatia);
				for (String S: fatia){
					System.out.println("ponto 2");
					if (!S.equals("0")){
						System.out.println("ponto 3");
						System.out.println("id: "+id);
						horario1=this.disciplinaMap.get(Integer.parseInt(S)).getDiaHora();
						horario2=this.disciplinaMap.get(id).getDiaHora();				
						if (comparaDiahora(horario1, horario2)){
							//System.out.println(this.disciplinaMap.get(Integer.parseInt(S)).getDiaHora());
							//System.out.println(this.disciplinaMap.get(id).getDiaHora());
							retornado =true;
							System.out.println("ponto 4");
							break;
						}
					}
				}

				return retornado;
			}
	
	
	public IntegerSolution removeDisciplinasJacursadasDaSugest(IntegerSolution sol,int tamanhoDaSugestao){
		boolean t=true;
		List<Disciplina>copiaJaCursadas=new ArrayList<>();
		copiaJaCursadas.addAll(this.problemaPreparado.getAluno().getDiscPagas());
		//while(t)System.out.println(copiaJaCursadas);
		for (Disciplina D:copiaJaCursadas){
			for (int i=0;i<tamanhoDaSugestao;i++){
				if (D.getId()==sol.getVariableValue(i)){
					sol.setVariableValue(i, 0);
					//while(t)System.out.println("troquei");
				}
			}
			
		}
		//while(t)System.out.println(sol);
		return sol;
	}
	
	
	@Override
	public IntegerSolution createSolution() {
		IntegerSolution retorno = new DefaultIntegerSolution(this); 
		List<Disciplina> copia0=new ArrayList<>();
		List<Disciplina> copia1=new ArrayList<>();
		List<Disciplina> copia2=new ArrayList<>();
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
        retorno=removeDisciplinasJacursadasDaSugest(retorno,tamanhoDaSugestao);
        for (int i=0;i<tamanhoDaSugestao;i++){
			numero=gerador.nextDouble()*100;
			int indexDeCopia =0;//inteiro que guarda o randâmico entre 0 e o tamanho do arraylist de disciplinas não pagas 
			
			if (modulo8>=4&&contaCadeira<3){// se o contador modulo8 estiver entre a posição 5 e 8 
				numero=38.00;				// e contador de disciplinas for menor que 3 ele força
				}							// o alg. a colocar mais disciplinas no periodo	
			
			if (numero<=37.5&&lugarPraZero!=0){
				if (i%2==0){
					troca1=i;
				}else {
					troca2=i;
				}
				retorno.setVariableValue(i, 0);
				lugarPraZero-=1;
				
				} 
				if ((copia0.size()!=0||copia1.size()!=0||copia2.size()!=0)&&temQuePagar!=0){
				boolean verificaChoque=true;
				numero=gerador.nextDouble()*100;
				if (copia0.size()!=0&&numero>20){
					int contador0=0;
					while(verificaChoque&&numero>20){
						indexDeCopia=gerador.nextInt(copia0.size());
						if (verificaSeJaexiste(retorno, copia0.get(indexDeCopia).getId(),i)){
							retorno=trocaPosicao(retorno, copia0.get(indexDeCopia).getId(),i);
							break;
						}
						verificaChoque=choqueDeHorario(retorno, copia0.get(indexDeCopia).getId(),i);
						System.out.println("ponto1-0");
						contador0+=1;
						if(contador0==20)break;
					}
					if(contador0==20){
						numero=1;
					}else {
						retorno.setVariableValue(i, copia0.get(indexDeCopia).getId());
						copia0.remove(indexDeCopia);
						temQuePagar-=1;
					}
					

				}
				if (semestreDaVez==1&&copia1.size()!=0&&numero<=20){
					int contador1=0;
					while(verificaChoque){
						indexDeCopia=gerador.nextInt(copia1.size());
						if (verificaSeJaexiste(retorno, copia1.get(indexDeCopia).getId(),i)){
							retorno=trocaPosicao(retorno, copia1.get(indexDeCopia).getId(),i);
							break;
						}
						verificaChoque=choqueDeHorario(retorno, copia1.get(indexDeCopia).getId(),i);
						System.out.println("ponto1-1");
						contador1+=1;
						if(contador1==20)break;
					}
					if(contador1==20){
						retorno.setVariableValue(i, 0);
						lugarPraZero-=1;
					}else {
						retorno.setVariableValue(i, copia1.get(indexDeCopia).getId());
						copia1.remove(indexDeCopia);
						temQuePagar-=1;
					}

				}
					else if (semestreDaVez==2&&copia2.size()!=0&&numero<=20){
						int contador2=0;
						while(verificaChoque){
							indexDeCopia=gerador.nextInt(copia2.size());
							if (verificaSeJaexiste(retorno, copia2.get(indexDeCopia).getId(),i)){
								retorno=trocaPosicao(retorno, copia2.get(indexDeCopia).getId(),i);
								break;
							}
							verificaChoque=choqueDeHorario(retorno, copia2.get(indexDeCopia).getId(),i);							
							System.out.println("ponto1-2");
							contador2+=1;
							if(contador2==20)break;
						}
						if(contador2==20){
							retorno.setVariableValue(i, 0);
							lugarPraZero-=1;
						}else {
							retorno.setVariableValue(i, copia2.get(indexDeCopia).getId());
							copia2.remove(indexDeCopia);
							temQuePagar-=1;
						}
					}
						
					if(modulo8%8!=0){// aqui ver se modulo 8 entra quando é igual a 0
						contaCadeira+=1;
						}else {
								contaCadeira=0;
								modulo8=0;
								if(semestreDaVez==1){
									if ((copia1.size())==2){
										retorno.setVariableValue(troca1, copia1.get(0).getId());
										retorno.setVariableValue(troca2, copia1.get(1).getId());
											}
									if(copia1.size()==1)retorno.setVariableValue(troca2, copia1.get(1).getId());
								}else{
									if ((copia2.size())==2){
										retorno.setVariableValue(troca1, copia2.get(0).getId());
										retorno.setVariableValue(troca2, copia2.get(1).getId());
											}
									if(copia2.size()==1)retorno.setVariableValue(troca2, copia2.get(1).getId());
									
								}
								semestreDaVez=mudaSemestre (semestreDaVez);
						}
				}else if (lugarPraZero!=0){
						retorno.setVariableValue(i, 0);
						lugarPraZero-=1;
						}else{
							continue;
						}
	modulo8+=1; 
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
		this.Disciplinas=preparacao.getDisciplinas();
		MontaHasMap();
		montaNaoPagas();
	
	}


}
