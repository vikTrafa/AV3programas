
import DAO.Livros3Dao;
import entity.Livros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InterfaceListaLivros extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public InterfaceListaLivros() {
        setTitle("Lista de Livros");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Categoria");
        modelo.addColumn("Disponibilidade");

        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarLivros());

        add(scrollPane, BorderLayout.CENTER);
        add(atualizarBtn, BorderLayout.SOUTH);

        carregarLivros(); // carrega ao abrir
        setVisible(true);
    }

    private void carregarLivros() {
        try {
            modelo.setRowCount(0); // limpa a tabela
            ArrayList<Livros> lista = new Livros3Dao().listarTodos();
            for (Livros l : lista) {
                modelo.addRow(new Object[]{
                    l.getId(), l.getTitulo(), l.getAutor(), l.getCategoria(), l.getDisponibilidade()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceListaLivros::new);
    }
}
