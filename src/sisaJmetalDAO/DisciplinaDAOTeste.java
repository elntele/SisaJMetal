package sisaJmetalDAO;

import java.util.List;

import sisaJmetalbeans.Disciplina;

public class DisciplinaDAOTeste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		
		List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
		
		for (Disciplina disciplina : disciplinas) {
			System.out.println("Período: " + disciplina.getPeriodo());
			System.out.println("Código: " + disciplina.getCodigo());
			System.out.println("Nome: " + disciplina.getNome());
			System.out.println("Área: " + disciplina.getArea().getNome());
		//	System.out.println("Pré-Requisitos: " + disciplina.getPreRequisitos());
			System.out.println("Semestre: " + disciplina.getSemestre());
			System.out.println("Média Geral: " + disciplina.getMédiageral());
			System.out.println("Grau de Dificuldade: " + disciplina.getGrauDificuldade());
		}
	}

}
