package DAO;

import Conexao.Conexao;
import entity.Livros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Livros3Dao {

    public ArrayList<Livros> listarTodos() throws Exception {
        ArrayList<Livros> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livros l = new Livros();
                l.setId(rs.getInt("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setCategoria(rs.getString("categoria"));
                l.setDisponibilidade(rs.getBoolean("disponibilidade") ? "Disponível" : "Indisponível");
                lista.add(l);
            }
        }

        return lista;
    }
    
}
