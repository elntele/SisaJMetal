package sisaJMetalMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sisaJmetalDAO.DisciplinaDAO;
import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class PreparacaoDoProblema {
	private Aluno aluno;
//	private List <Float> varianciaDoPeriodo=new ArrayList<Float>();
	private List<Disciplina> naoPagas=new  ArrayList <Disciplina>();
	private float variaQtdDiscPorPeriodo;
	DisciplinaDAO disciplinaDAO = new DisciplinaDAO();	
	List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
	private int[] sugestaoMat;
	private int AreaDePreferencia;
	private int numberOfObjetives=5;
	private int periodosRestantes;
	private int QtdDiscplinasParaConcluir;
	private int qtdDiscForaDaMinhaArea;
	private Float varianciaTotal;
	private int tempoDeFormatura;
	private float verificaAcompanhada;
	private float tempoExtraClasse;
	
		
	/**
	 *  seta a variavel periodosRestantes com o tempo que o aluno 
	 *  ainda tem ate completar 16 periodos que � o maximo de perodos
	 *  do curso.
	 */	
	public void tempoDeFimDecurso (){
	int periodosGastos = ((2016-this.aluno.getAnoIngresso())*2);	
		if (aluno.getSemestreIngresso()==2){
			periodosGastos=+1;
			}	
		this.periodosRestantes=16-periodosGastos;
	}
	

	/**
	 * simula a lista de displicinas que o aluno j� pagou mas sera
	 * abandonado porque isso ser� recuperado do cadastro do aluno no banco
	 */
	//disciplinas pagas
	public void montaListaDisciplinasPagas(){
		int i=1;
		List<Disciplina> transfer=new ArrayList();
		for (Disciplina d:disciplinas){
			if (d.getCodigo()!=14112){
				transfer.add(d);
				}
			i+=1;
			if (i==26) break;
			}
		aluno.setDiscPagas(transfer);
		}
	
	/**
	 * metodo simula a lista de disciplinas acompanhadas e reprovadas
	 *  para teste mas ser� abandonado porque isso ser� recuperado do 
	 *  cadastro do aluno no banco
	 */
	

	//disciplina acompanhada e reprovada
	public void montaListaDisciplinasReprovadas(){
		List<Disciplina> transfer=new ArrayList();
		for (Disciplina d:disciplinas){
			if (d.getCodigo()==14112){
				transfer.add(d);
				aluno.setDiscAcompanhada(transfer);
				aluno.setDiscRepro(transfer);
			}
		}
	}
	
	/**
	 * seta a variavel QtdDiscplinasParaConcluir com o numero de disciplinas
	 * que faltam para o aluno concluir o curso
	 */
	public void montaQtdDiscplinasParaConcluir(){
		this.QtdDiscplinasParaConcluir=45-aluno.getDiscPagas().size();
		
	}
	
	/**
	 * seta a lista nao pagas com a qtd de disciplina que 
	 * o aluno ainda n�o pagou, ou seja, sao as disciplinas
	 * do curso que ele ainda pode ou deve pagar 
	 */
	public void montaNaoPagas(){
		List<Disciplina> faltaPagar=new ArrayList();
		boolean presenca=false;
		for( Disciplina D: disciplinas){
			for (Disciplina Da:this.aluno.getDiscPagas()){
				if (D.getCodigo()==Da.getCodigo()) {
					presenca=true;
					break;
					} 
			}
			if (presenca==false) faltaPagar.add(D);
			presenca=false;
		}
		this.naoPagas=faltaPagar;
	}
	
	/**
	 * m�todo monta uma sugest�o para teste, mas ser� abandonado porque isso 
	 * ser� recebido dos metodos de mescla de cromossomos do jmetal
	 */
	
	public void montasugestao(){
		List<Disciplina> copia=new ArrayList();
		int temQuePagar=QtdDiscplinasParaConcluir;
		copia.addAll(naoPagas);
		int sugestao[]=new int [this.periodosRestantes*8];
		Random gerador = new Random();		 
        int numero; 
        int lugarPraZero=sugestao.length-QtdDiscplinasParaConcluir;
		for (int i=0;i<sugestao.length;i++){
			numero=gerador.nextInt(4);
			if (numero==0&&lugarPraZero!=0){	
				sugestao[i]=0;
				lugarPraZero-=1;
				}else if (copia.size()!=0&&temQuePagar!=0){
					numero=gerador.nextInt(copia.size());
					sugestao[i]=copia.get(numero).getId();
					copia.remove(numero);
					temQuePagar-=1;
					}else if (lugarPraZero!=0){
							sugestao[i]=0;
							lugarPraZero-=1;
							}else{
								continue;
							}
				}
		this.sugestaoMat=sugestao;
			}
		
	/**
	 * 
	 * @param id
	 * @return
	 * metodo auxiliar para o metodo varianciaDoPeriodo
	 * retorna o grau de dificuldade de uma cadeira
	 */
	public float RetornaGrauDificuldadeDaDisc(int id){
		Disciplina R=null;
		for (Disciplina D:this.disciplinas){
			if (D.getId()==id){
				R=D;
				break;
			}
		}
		return R.getGrauDificuldade();
	}
	/**
	 * metodo auxilar para qtdDeDisciplinasForaDeMinhaArea, 
	 * ele retorna o tipo de area da disciplina:
	 * 1 -Arq
	 * 2-Ensiso
	 * 3-Fc
	 * @param id
	 * @return
	 */
	public int RetornaArea(int id){
		Disciplina R=null;
		int retorno=0;
		for (Disciplina D:this.naoPagas){
			if (D.getId()==id){
				R=D;
				break;
			}
		}
	if (R!=null){	
		switch (R.getArea().getNome()){
		 case "ARQ": retorno=1;
		 			break;
		 case "Ensiso":  retorno=2;
			break;
		 case "FC":  retorno=3;
			break;
		
		 default: break;

		 }
	}

		return  retorno;
	}
	
	/**
	 * calcula a variancia de nivel de dificuldade dos periodos
	 * e seta a variavel varianciDoPeriodo 
	 */
	
	
	public void varianciaTotal(){
		float varia=0;
		float dificuldade=0;
		float mediaDoPeriodo=0;
		int divisorPorPeriodo=0;
		for(int i=0;i<this.tempoDeFormatura*8;i++) {// percorre o array de disciplinas para calcular a variancia por periodo			
			if (this.getSugestaoMat()[i]!=0){ 
				divisorPorPeriodo+=1;
				dificuldade+=RetornaGrauDificuldadeDaDisc(sugestaoMat[i]);
								
				}
			if (i%8==0 && i>0){
				if (divisorPorPeriodo!=0){					
					mediaDoPeriodo=dificuldade/divisorPorPeriodo;
					varia += (float) Math.pow( mediaDoPeriodo-2 ,2);
					divisorPorPeriodo=0;
					mediaDoPeriodo=0;
				}else{
					// valor colocado para quando a sugest�o vir com
					// periodos s� com 0 sem matricula, muito ruim...
					mediaDoPeriodo=100;									   	
					varia += (float) Math.pow( mediaDoPeriodo-2 ,2);
					divisorPorPeriodo=0;
					mediaDoPeriodo=0;
				}			 		   
				this.varianciaTotal=varia/this.tempoDeFormatura;
			}	
		}		
	}
	/**
	 * calcula a variancia de nivel de dificuldade total setando a variavel variancia total
	 */

//	public void varianciaTotal(){
//		float varia=0;
//		for(int i = 0; i<varianciaDoPeriodo.size();i++){
//			varia += (float) Math.pow( this.varianciaDoPeriodo.get(i) - 2,2);			
//		}
//		if (varianciaDoPeriodo.size()!=0){
//		this.varianciaTotal=(varia/varianciaDoPeriodo.size());
//		}else{
//			varianciaTotal=(float) 0;
//		}
//	}
 /**
  * verifica se a disciplina acompanhada esta no primeiro
  * periodo da sugest�o setando a variavel float verificaAcompanhada
  * como os valores entre 0 e 1 (quanto menor melhor).
  * OBS: ela s� a ssume o valor 0 se o aluno s� tiver uma disciplina acompanhada 
  * e ela estiver no primeiro periodo e s� assume o valor 1 em caso o cont�rio...
  * se o aluno tiver mais de uma disciplina acompanhada verificaAcompanhada 
  * vai assumir valores entre 0.9 e 0 (intervalo aberto)
  */
	public void verificaAcompNoPrimeDasug(){
		float retorno=0;
		float qtdAcomp=this.aluno.getDiscAcompanhada().size();
		int divisor=0;
		if(qtdAcomp==1){
			for (int i=0;i<8;i++){
				for (Disciplina D:this.aluno.getDiscAcompanhada()){
					if (this.sugestaoMat[i]==D.getId()){
						retorno=0;
					}else{
						retorno=1;
					}
				}		
			}			
		}else{
			for (int i=0;i<8;i++){
				for (Disciplina D:this.aluno.getDiscAcompanhada()){
					if (this.sugestaoMat[i]==D.getId()){
						divisor+=1;						
					}
					if (divisor >0){ 
						retorno= (float) (0.9/divisor);
						}else{ 
							retorno= 1;
						}
				}
			}
		}
		this.verificaAcompanhada= retorno;		
	}
	/**
	 * seta a variavel int tempoDeFormatura com a qtd de periodos que
	 * o aluno vai se forma
	 */
	
	public void contaTempoDeFormatura(){
		int fimDeCurso=0;
		int numeroDePeriodos=0;
		for (int i=0;i<sugestaoMat.length;i++){
			if (sugestaoMat[i]!=0){
				fimDeCurso=i;
			}
		} 
		this.tempoDeFormatura=fimDeCurso/8;
		if (fimDeCurso%8>0) this.tempoDeFormatura+=1;
	}
	/**
	 * seta o inteiro qtdDiscForaDaMinhaArea com a qtd de disciplina fora da area
	 * de prefer�ncia do aluno
	 */
	
	public void qtdDeDisciplinasForaDeMinhaArea(){
		int contador=0;
		for (int i=0;i<this.tempoDeFormatura*8;i++ ){
			if (sugestaoMat[i]!=0){
				for(Disciplina D:disciplinas){
					if (D.getId()==sugestaoMat[i]){
						if (RetornaArea(sugestaoMat[i])!=this.AreaDePreferencia){
							contador+=1;
						}
					}
				}
			}	
		}
		this.qtdDiscForaDaMinhaArea=contador;
	}
	
	public void montaAredePreferencia(){
		 switch (this.aluno.getAreaPref()){
		 case "ARQ": this.AreaDePreferencia=1;
		 			break;
		 case "Ensiso": this.AreaDePreferencia=2;
			break;
		 case "FC": this.AreaDePreferencia=3;
			break;
		 default: break;

		 }
			
		}
	
	/**
	 * seta a lista variaQtdDiscPorPeriodo com a variancia
	 * de quantidade de disciplina em cada periodo relativos
	 * a media de 5 disciplinas por periodo
	 * obs.: talvez seja necessario manipular este metodo para que
	 * no primeiro periodo ou seja i<8 a variancia seja 3...
	 * dica: Usar varia =(float) Math.pow(cont-3, 2)/8 para i<8
	 */
	public void varianciaDeQtdDeDiscPorPeriodo(){
		int cont=0;
		int totalDeDisc=0;
		float varia=0;
		float media=0;		
		for (int i=0; i<this.tempoDeFormatura*8;i++){
			if (sugestaoMat[i]!=0){
				totalDeDisc+=1;
			}
		}
		media=totalDeDisc/tempoDeFormatura;
		
		for (int i=0; i<this.tempoDeFormatura*8;i++){
			if (sugestaoMat[i]!=0){
				cont+=1;
			}
			if (i%8==0 && i!=0){
				if (cont!=0){
					varia +=(float) Math.pow(cont-media, 2);
					cont=0;
				}else{
					varia+=100;// caso o periodo n�o tenha disciplina
				}			
			}
		}
		
		this.variaQtdDiscPorPeriodo=varia/tempoDeFormatura;
	}
	
	/**
	 * Aluno estagia e mora longe 1.
	 * Aluno estagia e mora perto 2.
	 * Aluno n�o estagia e mora perto 3.
	 * Classe mede o tempo de estudo extra classe e seta a variavel tempoExtraClasse
	 * com 1,2,3 da seguinte forma:
	 * intervalo de estudo semanal 			categoria			qtdMaxDeDisciplinas
	 * 	 	 0-10								1				        3  
	 * 		11 -15								2				 Entre 4 e 5			
	 * 		 +15								3				 Entre 5 e 6
	 * 		obs: o algoritmo so alocar� mais que 6 disciplonas por periodo se
	 * o aluno n�o tiver tempo de curso para pagar menos que isso.  	
	 * 
	 */
	
	public void tempoDeEstudoExtraClasse(){
		float varia=0, varia1=0,varia2=0;
		int cont=0;
		int tempo=this.aluno.getTempDispExtrCla();			
				for (int i=0; i<this.tempoDeFormatura*8;i++){
					if (sugestaoMat[i]!=0){
						cont+=1;
					}
					if (i%8==0 && i!=0){
						if (cont!=0){
							varia +=(float) Math.pow(cont-3, 2);
							varia1+=(float) Math.pow(cont-4.5, 2);
							varia2+=(float) Math.pow(cont-5.5, 2);
							cont=0;
						}else{
							varia+=100;// caso o periodo n�o tenha disciplina
						}			
					}
				}				
				if (tempo<=10){
					this.tempoExtraClasse=varia/this.tempoDeFormatura;
				} else if(tempo>10 &&tempo<=15){
					this.tempoExtraClasse=varia1/this.tempoDeFormatura;
				} else if (tempo>15){
					this.tempoExtraClasse=varia2/this.tempoDeFormatura;
				}
				
			}
			
			
		
	
	
	
/**
 * getters and  any setters 	
 * @return
 */

	
	public Aluno getAluno() {
		return aluno;
	}

	public int[] getSugestaoMat() {
		return sugestaoMat;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public int getNumberOfObjetives() {
		return numberOfObjetives;
	}

	public int getPeriodosRestantes() {
		return periodosRestantes;
	}

	public void setPeriodosRestantes(int periodosRestantes) {
		this.periodosRestantes = periodosRestantes;
	}

	public int getQtdDiscplinasParaConcluir() {
		return QtdDiscplinasParaConcluir;
	}

	public void setQtdDiscplinasParaConcluir(int qtdDiscplinasParaConcluir) {
		QtdDiscplinasParaConcluir = qtdDiscplinasParaConcluir;
	}

	public List<Disciplina> getNaoPagas() {
		return naoPagas;
	}

	public void setNaoPagas(List<Disciplina> naoPagas) {
		this.naoPagas = naoPagas;
	}
	
	public int getAreaDePreferencia() {
		return AreaDePreferencia;
	}
	
	public void setSugestaoMat(int[] sugestaoMat) {
		this.sugestaoMat = sugestaoMat;
	}
		


	public float getVariaQtdDiscPorPeriodo() {
		return variaQtdDiscPorPeriodo;
	}


	public int getQtdDiscForaDaMinhaArea() {
		return qtdDiscForaDaMinhaArea;
	}


	public Float getVarianciaTotal() {
		return varianciaTotal;
	}


	public int getTempoDeFormatura() {
		return tempoDeFormatura;
	}


	public float getVerificaAcompanhada() {
		return verificaAcompanhada;
	}
	
	


	public float getTempoExtraClasse() {
		return tempoExtraClasse;
	}
/**
 * contrutor para testeComPrint
 * @param aluno
 */

	public PreparacaoDoProblema(Aluno aluno) {
		super();
		this.aluno = aluno;
		montaListaDisciplinasPagas();
		montaListaDisciplinasReprovadas();
		montaNaoPagas();
		montaAredePreferencia();
		montaQtdDiscplinasParaConcluir();
		tempoDeFimDecurso ();
		montasugestao();
		contaTempoDeFormatura();
		tempoDeEstudoExtraClasse();
		varianciaTotal();
		varianciaDeQtdDeDiscPorPeriodo();
		verificaAcompNoPrimeDasug();
		qtdDeDisciplinasForaDeMinhaArea();				
	}
	
/**
 * contrutor para o jmetal	
 */

//	public PreparacaoDoProblema(Aluno aluno) {
//		super();
//		this.aluno = aluno;
//		montaListaDisciplinasPagas();
//		montaListaDisciplinasReprovadas();
//		montaNaoPagas();
//		montaAredePreferencia();
//		montaQtdDiscplinasParaConcluir();
//		tempoDeFimDecurso ();
//			
//	}

	
}