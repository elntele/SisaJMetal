package sisaJmetalbeans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgresql {
	
			private static String usuario="postgres";
		    private static String senha="postgres";
		    private static String banco="sisa";
		    private static String ip="localhost";
		    private static String driver="org.postgresql.Driver";
		    private static Connection conexao = null;

		    //padrao singleton
		    public static Connection getConnection() {
		        System.out.println(">>Conectando ao banco");
		        try {
		            Class.forName(driver);
		            if(conexao==null || conexao.isClosed()){
		                conexao=DriverManager.getConnection("jdbc:postgresql://"+ip+"/"+banco+"",usuario, senha);
		            }
		            return conexao; 
		        }catch (ClassNotFoundException e) {    
		            throw new RuntimeException(e);    
		        }catch (SQLException e) {

		            closeConnection();
		            throw new RuntimeException(e);
		        }

		    }

		    public static void closeConnection(){
		        try{
		            if(conexao!=null && !conexao.isClosed()){
		                conexao.close();
		                System.out.println(">>Conexao encerrada com sucesso");
		            }
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
		    }



		    public static void main(String[] args) {
		        System.out.println("conexao: "+getConnection());
		        System.out.println("conexao: "+getConnection());
		        System.out.println("conexao: "+getConnection());
		    }

}

