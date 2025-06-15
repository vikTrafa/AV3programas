/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Funcionario;
import conexao.conexao;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO() {
        this.connection = conexao.conectar();
    }

    // Inserir novo funcionário
    public void inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (id, nome, cargo, login, senha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getID());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getCargo());
            stmt.setString(4, funcionario.getLogin());
            stmt.setString(5, new String(funcionario.getSenha())); // converte char[] para String
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Funcionario f = new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cargo"),
                    rs.getString("login"),
                    rs.getString("senha").toCharArray()
                );
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }

    public Funcionario buscarPorLogin(String login) {
        String sql = "SELECT * FROM funcionarios WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cargo"),
                    rs.getString("login"),
                    rs.getString("senha").toCharArray()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletar(int id) {
    String sql = "DELETE FROM funcionarios WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean atualizar(Funcionario funcionario) {
    String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, login = ?, senha = ? WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, funcionario.getNome());
        stmt.setString(2, funcionario.getCargo());
        stmt.setString(3, funcionario.getLogin());
        stmt.setString(4, new String(funcionario.getSenha()));
        stmt.setInt(5, funcionario.getID());
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    //public boolean autenticar(String login, char[] senha) {
       //Funcionarios f = buscarPorLogin(login);
       //if (f != null) {
            //return String.valueOf(f.getSenha()).equals(String.valueOf(senha));
        //}
        //return false;
    //}
}
