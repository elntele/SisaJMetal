package sisaJmetalDAO;

import java.util.List;

import sisaJmetalbeans.Disciplina;

public class DisciplinaDAOMontaLista {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		
		List<Disciplina> disciplinas = disciplinaDAO.getDisciplinas();
		
		for (Disciplina disciplina : disciplinas) {
			System.out.println("Período: " + disciplina.getPeriodo());
			System.out.println("Código: " + disciplina.getCodigo());
			System.out.println("Nome: " + disciplina.getNome());
			System.out.println("Área: " + disciplina.getArea().getNome());
			System.out.println("Pré-Requisitos: " + disciplina.getPreRequisitos());
			System.out.println("Semestre: " + disciplina.getSemestre());
			System.out.println("Média Geral: " + disciplina.getMédiageral());
			System.out.println("Grau de Dificuldade: " + disciplina.getGrauDificuldade());
			System.out.println("segunda: " + disciplina.getDiaHora()[0]);
			System.out.println("terça: " + disciplina.getDiaHora()[1]);
			System.out.println("quarta: " + disciplina.getDiaHora()[2]);
			System.out.println("quinta: " + disciplina.getDiaHora()[3]);
			System.out.println("sexta: " + disciplina.getDiaHora()[4]);
		}
	}

}
