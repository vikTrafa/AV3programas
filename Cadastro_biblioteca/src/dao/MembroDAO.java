/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Membro;
import conexao.conexao;

public class MembroDAO {
    private Connection connection;

    public MembroDAO() {
        this.connection = conexao.conectar(); // Usando sua classe de conexão
    }

    public void inserir(Membro membro) {
        String sql = "INSERT INTO membros (id, nome, cpf, telefone, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, membro.getID());
            stmt.setString(2, membro.getNome());
            stmt.setString(3, membro.getCPF());
            stmt.setString(4, membro.getTelefone());
            stmt.setString(5, membro.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Membro buscarPorId(int id) {
        String sql = "SELECT * FROM membros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membro(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Membro> listarTodos() {
        List<Membro> membros = new ArrayList<>();
        String sql = "SELECT * FROM membros";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Membro membro = new Membro(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                membros.add(membro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membros;
    }

    public void atualizar(Membro membro) {
        String sql = "UPDATE membros SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getTelefone());
            stmt.setString(3, membro.getEmail());
            stmt.setInt(4, membro.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM membros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
