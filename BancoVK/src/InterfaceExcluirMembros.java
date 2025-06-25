import DAO.MembrosDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceExcluirMembros extends JFrame {
    private JTextField idField;
    private JTextArea resultadoArea;

    public InterfaceExcluirMembros() {
        setTitle("Excluir Membros");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        idField = new JTextField();

        formPanel.add(new JLabel("ID do membro a excluir:"));
        formPanel.add(idField);

        JButton excluirButton = new JButton("Excluir");
        JButton voltarButton = new JButton("Voltar ao Menu");

        formPanel.add(excluirButton);
        formPanel.add(voltarButton);

        resultadoArea = new JTextArea(4, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirMembro();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPrincipal();
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void excluirMembro() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            resultadoArea.setText("Por favor, insira o ID do membro para exclusão.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            boolean excluido = new MembrosDao().excluirMembroPorId(id);
            if (excluido) {
                resultadoArea.setText("Membro com ID " + id + " excluído com sucesso.");
                idField.setText("");
            } else {
                resultadoArea.setText("Membro com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException ex) {
            resultadoArea.setText("ID inválido. Por favor, insira um número inteiro.");
        } catch (Exception ex) {
            resultadoArea.setText("Erro ao excluir: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceExcluirMembros());
    }
}
