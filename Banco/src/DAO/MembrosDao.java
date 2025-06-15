package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import entity.Membros;

public class MembrosDao {

    // Seu método já existente
    public void CadastrarMembros(entity.Membros Membros) {
        String sql = "INSERT INTO membros (nome, CPF, telefone, email) VALUES (?, ?, ?, ? )";

        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, Membros.getNome());
            ps.setString(2, Membros.getCpf());
            ps.setString(3, Membros.getTelefone());
            ps.setString(4, Membros.getEmail());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Novo método para exclusão
    public void excluirEmprestimosPorMembroId(int idMembro) throws SQLException {
    String sql = "DELETE FROM emprestimos WHERE id_membro = ?";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setInt(1, idMembro);
        ps.executeUpdate();
    }
}

public void excluirHistoricoPorMembroId(int idMembro) throws SQLException {
    String sql = "DELETE FROM historico_de_emprestimos WHERE id_membro = ?";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setInt(1, idMembro);
        ps.executeUpdate();
    }
}

/*public boolean excluirMembroPorId(int id) throws SQLException {
    try {
        // Excluir dependentes primeiro
        excluirEmprestimosPorMembroId(id);
        excluirHistoricoPorMembroId(id);

        // Depois excluir o membro
        String sql = "DELETE FROM membros WHERE id = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}*/
public Membros buscarMembroPorCpf(String cpf) {
    String sql = "SELECT * FROM membros WHERE cpf = ?";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Membros m = new Membros();
            m.setCodigo(rs.getInt("id"));
            m.setNome(rs.getString("nome"));
            m.setCpf(rs.getString("cpf"));
            m.setTelefone(rs.getString("telefone"));
            m.setEmail(rs.getString("email"));
            return m;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public List<Membros> listarTodos() throws SQLException {
        List<Membros> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email FROM membros";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Membros m = new Membros();
                m.setCodigo(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCpf(rs.getString("cpf"));
                m.setTelefone(rs.getString("telefone"));
                m.setEmail(rs.getString("email"));
                lista.add(m);
            }
        }
        return lista;
    }
   public boolean excluirMembroPorId(int id) {
    Connection conn = null;
    PreparedStatement insertLog = null;
    PreparedStatement deleteMembro = null;
    ResultSet rs = null;

    try {
        conn = Conexao.getConexao();

        // Buscar o membro a ser excluído
        String selectSQL = "SELECT * FROM membros WHERE id = ?";
        PreparedStatement select = conn.prepareStatement(selectSQL);
        select.setInt(1, id);
        rs = select.executeQuery();

        if (rs.next()) {
            // Inserir no log, incluindo a data de exclusão (deixe o banco preencher automaticamente se configurado)
            String insertSQL = "INSERT INTO membros_excluidos (nome, cpf, telefone, email, data_exclusao) VALUES (?, ?, ?, ?, NOW())";
            insertLog = conn.prepareStatement(insertSQL);
            insertLog.setString(1, rs.getString("nome"));
            insertLog.setString(2, rs.getString("cpf"));
            insertLog.setString(3, rs.getString("telefone"));
            insertLog.setString(4, rs.getString("email"));
            insertLog.executeUpdate();

            // Excluir da tabela principal
            String deleteSQL = "DELETE FROM membros WHERE id = ?";
            deleteMembro = conn.prepareStatement(deleteSQL);
            deleteMembro.setInt(1, id);
            return deleteMembro.executeUpdate() > 0;
        }

        return false;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        Conexao.fecharConexao(conn, insertLog, rs);
        Conexao.fecharConexao(null, deleteMembro, null);
    }
}
public List<Membros> listarExcluidos() {
    List<Membros> lista = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = Conexao.getConexao();
        String sql = "SELECT * FROM membros_excluidos";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Membros m = new Membros();
            m.setCodigo(rs.getInt("id"));
            m.setNome(rs.getString("nome"));
            m.setCpf(rs.getString("cpf"));
            m.setTelefone(rs.getString("telefone"));
            m.setEmail("Excluído em: " + rs.getString("data_exclusao")); // usamos o campo `email` só para exibir a data
            lista.add(m);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        Conexao.fecharConexao(conn, stmt, rs);
    }

    return lista;
}

}
