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
		
		Connection conexao = ConexaoPostgresql.getConnection();
		
		//Conexão para o Mysql - Jorge
		Connection con = Conexao.getConnection();
		
		try {
			
			List<Disciplina> disciplinas = new ArrayList<>();
			String sql = "SELECT * FROM cadeiras";
			PreparedStatement pstmt = conexao.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
		
			while (rs.next()) {
				Disciplina disciplinaTemporaria = new Disciplina();
				Area areaTemporaria = new Area();
				disciplinaTemporaria.setPeriodo(rs.getString("periodo"));
				disciplinaTemporaria.setCodigo(rs.getInt("código"));
				disciplinaTemporaria.setNome(rs.getString("nome"));
				areaTemporaria.setNome(rs.getString("Área"));
				disciplinaTemporaria.setArea(areaTemporaria);
				//disciplinaTemporaria.setPreRequisitos(rs.getArray("prérequisitos"));
				disciplinaTemporaria.setSemestre(rs.getInt("semestre"));
				disciplinaTemporaria.setMédiageral(rs.getString("médiageral"));
				disciplinaTemporaria.setGrauDificuldade(rs.getString("dificudade"));
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
