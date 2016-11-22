package sisaJmetalDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sisaJmetalbeans.Area;
import sisaJmetalbeans.Conexao;
import sisaJmetalbeans.ConexaoPostgresql;
import sisaJmetalbeans.Disciplina;

/**
 * @author LENOVO
 *
 */
public class DisciplinaDAO {
	
/**
 * Este método é responsável por recuperar todos os dados no DB e armazenar em um List.
 *
 */		
	public List<Disciplina> getDisciplinas(){
		
		Connection con = ConexaoPostgresql.getConnection();
		
		//Conexão para o Mysql - Jorge
		//Connection con = Conexao.getConnection();
		
		try {
			
			List<Disciplina> disciplinas = new ArrayList<>();
			String sql = "SELECT * FROM disciplinas";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String[] diaHora= new String[5];
			while (rs.next()) {
//				/**
//				 *para msql 
//				 */
//				Disciplina disciplinaTemporaria = new Disciplina();
//				Area areaTemporaria = new Area();
//				disciplinaTemporaria.setPeriodo(rs.getString("periodo"));
//				disciplinaTemporaria.setId(rs.getInt("id"));
//				disciplinaTemporaria.setCodigo(rs.getInt("código"));
//				disciplinaTemporaria.setNome(rs.getString("nome"));
//				areaTemporaria.setNome(rs.getString("Área"));
//				disciplinaTemporaria.setArea(areaTemporaria);
//				disciplinaTemporaria.setPreRequisitos(rs.getString("Pré-requisitos"));
//				disciplinaTemporaria.setSemestre(rs.getInt("semestre"));
//				disciplinaTemporaria.setMédiageral(rs.getString("média geral"));
//				disciplinaTemporaria.setGrauDificuldade(rs.getString("dificudade"));
//				diaHora[0]=rs.getString("segunda");
//				diaHora[1]=rs.getString("terça");
//				diaHora[2]=rs.getString("quarta");
//				diaHora[3]=rs.getString("quinta");
//				diaHora[4]=rs.getString("sexta");
//				disciplinaTemporaria.setDiaHora(diaHora);
//				disciplinas.add(disciplinaTemporaria);
				
				/**
				 * para postgrees
				 */
				Disciplina disciplinaTemporaria = new Disciplina();
				Area areaTemporaria = new Area();
				disciplinaTemporaria.setPeriodo(rs.getString("periodo"));
				disciplinaTemporaria.setId(rs.getInt("id"));
				disciplinaTemporaria.setCodigo(rs.getInt("código"));
				disciplinaTemporaria.setNome(rs.getString("nome"));
				areaTemporaria.setNome(rs.getString("Área"));
				disciplinaTemporaria.setArea(areaTemporaria);
				disciplinaTemporaria.setPreRequisitos(rs.getString("prérequisitos"));
				disciplinaTemporaria.setSemestre(rs.getInt("semestre"));
				disciplinaTemporaria.setMédiageral(rs.getString("médiageral"));
				disciplinaTemporaria.setGrauDificuldade(rs.getString("dificudade"));
				diaHora[0]=rs.getString("segunda");
				diaHora[1]=rs.getString("terça");
				diaHora[2]=rs.getString("quarta");
				diaHora[3]=rs.getString("quinta");
				diaHora[4]=rs.getString("sexta");
				disciplinaTemporaria.setDiaHora(diaHora);
				disciplinas.add(disciplinaTemporaria);

				
				
			}
			rs.close();
			pstmt.close();
			return disciplinas;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
				
	}
	
}
