package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import entity.Livros;

public class LivrosDao {
    public void CadastrarLivros(Livros livros){
        String sql = "INSERT INTO livros (titulo, autor, categoria, disponibilidade) VALUES (?, ?, ?, ? )";
        
        
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, livros.getTitulo());
            ps.setString(2, livros.getAutor());
            ps.setString(3, livros.getCategoria());
            ps.setString(4, livros.getDisponibilidade());
            

             ps.execute();
             ps.close();
        } catch (SQLException e) {
          
            e.printStackTrace();
        }


    }
     public void excluirEmprestimosPorLivroId(int idLivro) throws SQLException {
    String sql = "DELETE FROM emprestimos WHERE id_livro = ?";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setInt(1, idLivro);
        ps.executeUpdate();
    }
}

/*public boolean excluirLivroPorId(int id) throws SQLException {
    try {
        // Excluir registros dependentes primeiro
        excluirEmprestimosPorLivroId(id);
        excluirHistoricoPorLivroId(id);

        // Depois excluir o livro
        String sql = "DELETE FROM livros WHERE id = ?";
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
public void excluirHistoricoPorLivroId(int idLivro) throws SQLException {
    String sql = "DELETE FROM historico_de_emprestimos WHERE id_livro = ?";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setInt(1, idLivro);
        ps.executeUpdate();
    }
}

 public List<Livros> listarTodos() {
        List<Livros> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();
            String sql = "SELECT * FROM livros";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Livros l = new Livros();
                l.setId(rs.getInt("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setCategoria(rs.getString("categoria"));
                l.setDisponibilidade(rs.getString("disponibilidade"));
                lista.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, stmt, rs);
        }

        return lista;
    }
    public List<Livros> listarLivrosAtivos() throws SQLException {
    List<Livros> lista = new ArrayList<>();
    String sql = "SELECT * FROM livros";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Livros l = new Livros();
            l.setId(rs.getInt("id"));
            l.setTitulo(rs.getString("titulo"));
            l.setAutor(rs.getString("autor"));
            l.setCategoria(rs.getString("categoria"));
            l.setDisponibilidade(rs.getString("disponibilidade"));
            lista.add(l);
        }
    }
    return lista;
}
public boolean excluirLivroPorId(int id) throws SQLException {
    try {
        // Excluir registros dependentes primeiro
        excluirEmprestimosPorLivroId(id);
        excluirHistoricoPorLivroId(id);

        // Registrar o livro na tabela de excluídos
        registrarLivroExcluido(id);

        // Depois excluir o livro
        String sql = "DELETE FROM livros WHERE id = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
private void registrarLivroExcluido(int idLivro) throws SQLException {
    String sql = "INSERT INTO livros_excluidos (id, titulo, autor, categoria, data_exclusao) " +
                 "SELECT id, titulo, autor, categoria, NOW() FROM livros WHERE id = ?";

    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setInt(1, idLivro);
        ps.executeUpdate();
    }
}

public List<Livros> listarLivrosExcluidos() throws SQLException {
    List<Livros> lista = new ArrayList<>();
    String sql = "SELECT * FROM livros_excluidos";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Livros l = new Livros();
            l.setId(rs.getInt("id"));
            l.setTitulo(rs.getString("titulo"));
            l.setAutor(rs.getString("autor"));
            l.setCategoria(rs.getString("categoria"));
            // Aqui usamos a disponibilidade para mostrar a data de exclusão, se quiser
            l.setDisponibilidade("Excluído em: " + rs.getString("data_exclusao"));
            lista.add(l);
        }
    }
    return lista;
}


    
}

    

