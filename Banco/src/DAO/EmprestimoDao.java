package DAO;

import Conexao.Conexao;
import entity.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDao {

    public void registrarEmprestimo(Emprestimo e) throws Exception {
        Connection conn = Conexao.getConexao();
        if (conn == null) throw new Exception("Falha na conexão com o banco de dados.");

        // Verifica se o livro está disponível
        if (!livroDisponivel(e.getIdLivro(), conn)) {
            throw new Exception("Livro não está disponível para empréstimo.");
        }

        // Registra empréstimo
        String sql = "INSERT INTO emprestimos (id_livro, id_membro, data_emprestimo, data_devolucao, status_livro) " +
                     "VALUES (?, ?, ?, ?, 'emprestado')";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, e.getIdLivro());
        stmt.setInt(2, e.getIdMembro());
        stmt.setString(3, e.getDataEmprestimo());
        stmt.setString(4, e.getDataDevolucao());
        stmt.executeUpdate();
        stmt.close();

        // Atualiza disponibilidade na tabela livros
        atualizarDisponibilidadeLivro(e.getIdLivro(), "0", conn);

        // Registra no histórico
        registrarHistorico(e.getIdLivro(), e.getIdMembro(), e.getDataEmprestimo(), conn);
    }

    private boolean livroDisponivel(int idLivro, Connection conn) throws Exception {
        String sql = "SELECT disponibilidade FROM livros WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idLivro);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            boolean disponivel = rs.getBoolean("disponibilidade");
            rs.close();
            stmt.close();
            return disponivel;
        }
        rs.close();
        stmt.close();
        throw new Exception("Livro não encontrado.");
    }

    private void atualizarDisponibilidadeLivro(int idLivro, String novaDisponibilidade, Connection conn) throws Exception {
        String sql = "UPDATE livros SET disponibilidade = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, novaDisponibilidade);
        stmt.setInt(2, idLivro);
        stmt.executeUpdate();
        stmt.close();
    }

    private void registrarHistorico(int idLivro, int idMembro, String data, Connection conn) throws Exception {
        String sql = "INSERT INTO historico_de_emprestimos (id_livro, id_membro, data_acao) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idLivro);
        stmt.setInt(2, idMembro);
        stmt.setString(3, data);
        stmt.executeUpdate();
        stmt.close();
    }
    /*  public void registrarDevolucao(int idEmprestimo, String dataDevolucao) throws Exception {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dataDevolucao); // data em formato yyyy-MM-dd
            stmt.setInt(2, idEmprestimo);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new Exception("Nenhum empréstimo encontrado com o ID informado.");
            }
        }
    }*/
     public int buscarIdLivroPorEmprestimo(int idEmprestimo) throws Exception {
        String sql = "SELECT id_livro FROM emprestimos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmprestimo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_livro");
                } else {
                    throw new Exception("Empréstimo não encontrado para ID: " + idEmprestimo);
                }
            }
        }
    }
    public List<Emprestimo> buscarTodosEmprestimos() throws Exception {
    List<Emprestimo> lista = new ArrayList<>();
    String sql = "SELECT * FROM emprestimos";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Emprestimo e = new Emprestimo();
            e.setIdEmprestimo(rs.getInt("id")); // ajuste conforme nome da coluna
            e.setIdLivro(rs.getInt("id_livro"));
            e.setIdMembro(rs.getInt("id_membro"));
            e.setDataEmprestimo(rs.getString("data_emprestimo"));
            e.setDataDevolucao(rs.getString("data_devolucao"));
            lista.add(e);
        }
    }
    return lista;

}
public List<Emprestimo> buscarEmprestimosAtivos() throws Exception {
    List<Emprestimo> lista = new ArrayList<>();
    String sql = "SELECT * FROM emprestimos WHERE data_devolucao IS NULL";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Emprestimo e = new Emprestimo();
            e.setIdEmprestimo(rs.getInt("id_emprestimo"));
            e.setIdLivro(rs.getInt("id_livro"));
            e.setIdMembro(rs.getInt("id_membro"));
            e.setDataEmprestimo(rs.getString("data_emprestimo"));
            lista.add(e);
        }
    }

    return lista;
}

public void registrarDevolucao(int idEmprestimo, String dataDevolucao) throws Exception {
    String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id_emprestimo = ?";
    String sqlLivro = "UPDATE livros SET disponibilidade = true WHERE id = (SELECT id_livro FROM emprestimos WHERE id_emprestimo = ?)";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         PreparedStatement stmtLivro = conn.prepareStatement(sqlLivro)) {

        stmt.setString(1, dataDevolucao);
        stmt.setInt(2, idEmprestimo);
        stmt.executeUpdate();

        stmtLivro.setInt(1, idEmprestimo);
        stmtLivro.executeUpdate();
    }
}
public List<String> listarEmprestimosPorMembro(int idMembro) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT e.id, l.titulo FROM emprestimos e " +
                     "JOIN livros l ON e.id_livro = l.id " +
                     "WHERE e.id_membro = ? AND e.status_livro = 'emprestado'";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idMembro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idEmprestimo = rs.getInt("id");
                String titulo = rs.getString("titulo");
                lista.add(idEmprestimo + " - " + titulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void devolverLivro(int idEmprestimo) throws SQLException {
        Connection conn = Conexao.getConexao();
        conn.setAutoCommit(false);

        try {
            // 1. Atualiza status do empréstimo
            String updateEmprestimo = "UPDATE emprestimos SET status_livro = 'disponivel' WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(updateEmprestimo);
            ps1.setInt(1, idEmprestimo);
            ps1.executeUpdate();

            // 2. Recupera id_livro e id_membro
            String select = "SELECT id_livro, id_membro FROM emprestimos WHERE id = ?";
            PreparedStatement ps2 = conn.prepareStatement(select);
            ps2.setInt(1, idEmprestimo);
            ResultSet rs = ps2.executeQuery();

            int idLivro = 0;
            int idMembro = 0;
            if (rs.next()) {
                idLivro = rs.getInt("id_livro");
                idMembro = rs.getInt("id_membro");
            }

            // 3. Atualiza disponibilidade do livro
            String updateLivro = "UPDATE livros SET disponibilidade = 1 WHERE id = ?";
            PreparedStatement ps3 = conn.prepareStatement(updateLivro);
            ps3.setInt(1, idLivro);
            ps3.executeUpdate();

            // 4. Insere no histórico
            String insertHistorico = "INSERT INTO historico_de_emprestimos (id_membro, id_livro, data_acao) VALUES (?, ?, CURDATE())";
            PreparedStatement ps4 = conn.prepareStatement(insertHistorico);
            ps4.setInt(1, idMembro);
            ps4.setInt(2, idLivro);
            ps4.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }



}