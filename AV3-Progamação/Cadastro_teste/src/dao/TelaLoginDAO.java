package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaLoginDAO {

    private Connection connection;

    public TelaLoginDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cadastro", "root", ""); // ajuste conforme seu banco
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean autenticar(String username, String senha) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            boolean autenticado = rs.next();
            rs.close();
            stmt.close();

            return autenticado;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
