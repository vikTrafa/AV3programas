/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Historico_emprestimo;
import conexao.Conexao;

public class Historico_emprestimoDAO {
    private Connection connection;

    public Historico_emprestimoDAO() {
        this.connection = Conexao.conectar(); 
    }

    public void inserir(Historico_emprestimo historico) {
        String sql = "INSERT INTO historico_emprestimos (id, ID_membro, ID_livro, data_acao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, historico.getID());
            stmt.setString(2, historico.getID_membro());
            stmt.setString(3, historico.getID_livro());
            stmt.setString(4, historico.getDataAcao().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Historico_emprestimo> listarTodos() {
        List<Historico_emprestimo> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico_emprestimos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Historico_emprestimo historico = new Historico_emprestimo(
                    rs.getInt("id"),
                    rs.getString("ID_membro"),
                    rs.getString("ID_livro"),
                    rs.getTimestamp("data_acao").toLocalDateTime()
                );
                historicos.add(historico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historicos;
    }

    public List<Historico_emprestimo> listarPorMembro(String idMembro) {
        List<Historico_emprestimo> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico_emprestimos WHERE ID_membro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, idMembro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Historico_emprestimo historico = new Historico_emprestimo(
                    rs.getInt("id"),
                    rs.getString("ID_membro"),
                    rs.getString("ID_livro"),
                    rs.getTimestamp("data_acao").toLocalDateTime()
                );
                historicos.add(historico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historicos;
    }
    // NÂO É NECESSARIO
    public boolean atualizar(Historico_emprestimo historico) {
    String sql = "UPDATE historico_emprestimos SET ID_membro = ?, ID_livro = ?, data_acao = ? WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, historico.getID_membro());
        stmt.setString(2, historico.getID_livro());
        stmt.setTimestamp(3, Timestamp.valueOf(historico.getDataAcao()));
        stmt.setInt(4, historico.getID());
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
 }
    public boolean deletar(int ID) {
    String sql = "DELETE FROM historico_emprestimos WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, ID); 
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}

