package sisaJmetalDAO;

import java.util.List;

import sisaJMetalMain.MatriculaMetalProblem;
import sisaJMetalMain.PreparacaoDoProblema;
import sisaJmetalbeans.Aluno;
import sisaJmetalbeans.Disciplina;

public class TesteComPrint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		Aluno aluno = new Aluno("abc", "123", false, "a@a", 2013, 1, 9, "FC", 0, 300);
		 
		PreparacaoDoProblema problema = new PreparacaoDoProblema(aluno);
		//List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
		 List<Disciplina> disciplinas = problema.getDisciplinas();
		 MatriculaMetalProblem matriculaMetal = new MatriculaMetalProblem(problema); 
		int l=1;
		 for (float V:matriculaMetal.getVarianciaDoPeriodo()){
		 System.out.println("variancia periodo: "+l+": "+V);
		 }
		 
		 System.out.println("numero de periodos restantes : "+problema.getPeriodosRestantes());
		 System.out.println("quantidade de disciplinas para concluir n�: "+problema.getQtdDiscplinasParaConcluir());
		 /**
		  * dentro de aluno
		  */
		 for (Disciplina D:problema.getAluno().getDiscPagas()){
			
			 System.out.println("disciplina paga id n� : "+ D.getId());
		 }
	
		 for (Disciplina D:problema.getAluno().getDiscRepro()){
			 System.out.println("disciplina reprovada id n� : "+D.getId());
		 }
		 
		 for (Disciplina D:problema.getAluno().getDiscAcompanhada()){
			 System.out.println("disciplina acompanhada id n� : "+D.getId());
		 }
		 
		 /**
		  * de dentro do problema
		  */
		 
		 for (Disciplina D:problema.getNaoPagas()){
			 System.out.println("da lista de diciplina n�o pagas id n� : "+D.getId());
		 }
		 
		 
		 System.out.println("are de preferencia do aluno n�: "+problema.getAreaDePreferencia());
		 
		 
		 

		 System.out.println(problema.getSugestaoMat().length);
		 
		for (int i=0; i<problema.getSugestaoMat().length;i++){
			System.out.println("disciplina id "+ problema.getSugestaoMat()[i]);
		}
		
		
		
		
//		for (Disciplina disciplina : disciplinas) {
//			System.out.println("id: " + disciplina.getId());
//			System.out.println("Per�odo: " + disciplina.getPeriodo());
//			System.out.println("C�digo: " + disciplina.getCodigo());
//			System.out.println("Nome: " + disciplina.getNome());
//			System.out.println("�rea: " + disciplina.getArea().getNome());
//			System.out.println("Pr�-Requisitos: " + disciplina.getPreRequisitos());
//			System.out.println("Semestre: " + disciplina.getSemestre());
//			System.out.println("M�dia Geral: " + disciplina.getM�diageral());
//			System.out.println("Grau de Dificuldade: " + disciplina.getGrauDificuldade());
//			System.out.println("segunda: " + disciplina.getDiaHora()[0]);
//			System.out.println("ter�a: " + disciplina.getDiaHora()[1]);
//			System.out.println("quarta: " + disciplina.getDiaHora()[2]);
//			System.out.println("quinta: " + disciplina.getDiaHora()[3]);
//			System.out.println("sexta: " + disciplina.getDiaHora()[4]);
//		}
		
		
	}
	
	

}
