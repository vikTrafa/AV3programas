package DAO;

import Conexao.Conexao;
import entity.Funcionarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionariosDao {

    public void cadastrarFuncionario(Funcionarios f) throws SQLException {
        String sql = "INSERT INTO funcionarios (nome, cargo, login, senha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getLogin());
            ps.setString(4, f.getSenha());
            ps.executeUpdate();
        }
    }
     public boolean verificarLogin(String login, String senha) {
        String sql = "SELECT * FROM funcionarios WHERE login = ? AND senha = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Se houver resultado, login é válido

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
