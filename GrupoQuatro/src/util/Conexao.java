package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	String URL = "jdbc:postgresql://127.0.0.1:5432/grupo4";
	String USER = "postgres";
	String PASS = "postgres";

	private static Connection conexao;

	public boolean conecta() {

		try {
			if (conexao != null && !conexao.isClosed()) {
				return true;
			}
			Class.forName("org.postgresql.Driver");


			conexao = DriverManager.getConnection(URL, USER, PASS);



		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Connection getConexao() {
		return this.conexao;
	}

	public void fechar() {
		try {
			if (conexao == null || conexao.isClosed())
				return;
					conexao.close();

		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}