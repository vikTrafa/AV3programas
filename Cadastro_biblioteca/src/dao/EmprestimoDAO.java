/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Emprestimo;
import conexao.conexao;

public class EmprestimoDAO {
    private Connection connection;

    public EmprestimoDAO() {
        this.connection = conexao.conectar();
    }

    // CREATE
    public void inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (id, ID_livro, ID_membro, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getID());
            stmt.setString(2, emprestimo.getID_livro());
            stmt.setString(3, emprestimo.getID_membro());
            stmt.setDate(4, Date.valueOf(emprestimo.getData_emprestimo()));
            stmt.setDate(5, Date.valueOf(emprestimo.getData_devolucao()));
            stmt.setString(6, emprestimo.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - listar todos
    public List<Emprestimo> listarTodos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                    rs.getInt("id"),
                    rs.getString("ID_livro"),
                    rs.getString("ID_membro"),
                    rs.getDate("data_emprestimo").toLocalDate(),
                    rs.getDate("data_devolucao").toLocalDate(),
                    rs.getString("status")
                );
                lista.add(emprestimo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // READ - por ID
    public Emprestimo buscarPorId(int id) {
        String sql = "SELECT * FROM emprestimos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Emprestimo(
                    rs.getInt("id"),
                    rs.getString("ID_livro"),
                    rs.getString("ID_membro"),
                    rs.getDate("data_emprestimo").toLocalDate(),
                    rs.getDate("data_devolucao").toLocalDate(),
                    rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean atualizar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET data_devolucao = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(emprestimo.getData_devolucao()));
            stmt.setString(2, emprestimo.getStatus());
            stmt.setInt(3, emprestimo.getID());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE (opcional, só se for necessário)
    public boolean deletar(int id) {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
