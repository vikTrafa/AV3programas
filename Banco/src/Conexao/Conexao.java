package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String user = "root";
    private static final String password = "1917";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public static void fecharConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
        }

        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar Statement: " + e.getMessage());
        }

        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar Connection: " + e.getMessage());
        }
    }

    // Sobrecarga para casos com apenas Connection e Statement
    public static void fecharConexao(Connection conn, PreparedStatement stmt) {
        fecharConexao(conn, stmt, null);
    }

    // Sobrecarga para fechar apenas o ResultSet
    public static void fecharConexao(ResultSet rs) {
        fecharConexao(null, null, rs);
    }
}
