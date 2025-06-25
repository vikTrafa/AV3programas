package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;
import entity.Livros;

public class Livros2Dao {

    
    public void AtualizarDisponibilidade(Livros livros) {
    String sql = "UPDATE livros SET disponibilidade = ? WHERE id = ?"; 

    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setString(1, livros.getDisponibilidade());
        ps.setInt(2, livros.getId()); 

        int linhasAfetadas = ps.executeUpdate();
        System.out.println("Linhas afetadas: " + linhasAfetadas);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
