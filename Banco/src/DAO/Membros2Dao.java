package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;
import entity.Membros;

public class Membros2Dao {
    
    public void AtualizarMembros(Membros membros) {
        String sql = "UPDATE membros SET nome = ?, telefone = ?, email = ? WHERE CPF = ?";
        
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, membros.getNome());
            ps.setString(2, membros.getTelefone());
            ps.setString(3, membros.getEmail());
            ps.setString(4, membros.getCpf()); // CPF como identificador único para update

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
