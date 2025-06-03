package views;

import dao.ProfessoresDAO;
import models.Professores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.List;

public class InterfaceProfessor extends JFrame {
    private JTextField nomeField, emailField, salarioField, disciplinaField;
    private JFormattedTextField data_nascimentoField;
    private JTextArea tela_resultado;
    private JTable tabelaProfessores;
    private DefaultTableModel tableModel;
    private List<Professores> listaProfessores = new ProfessoresDAO().listar();
    private int indiceSelecionado = -1;

    public InterfaceProfessor() {
        setTitle("Cadastro de Professores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        formPanel.setBackground(new Color(240, 248, 255));

        nomeField = new JTextField();
        emailField = new JTextField();
        salarioField = new JTextField();
        disciplinaField = new JTextField();

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            data_nascimentoField = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao aplicar máscara de data: " + e.getMessage());
            data_nascimentoField = new JFormattedTextField();
        }

        tela_resultado = new JTextArea(3, 30);
        tela_resultado.setEditable(false);
        tela_resultado.setLineWrap(true);
        tela_resultado.setWrapStyleWord(true);
        tela_resultado.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton cadastrar = new JButton("Cadastrar");
        JButton alterar = new JButton("Alterar");
        JButton excluir = new JButton("Excluir");
        JButton voltar = new JButton("Voltar");

        cadastrar.addActionListener(this::cadastrarProfessor);
        alterar.addActionListener(this::alterarProfessor);
        excluir.addActionListener(this::excluirProfessor);

        voltar.addActionListener(e -> {
            new TelaInicial();
            dispose();
        });

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("E-mail:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Data de Nascimento:"));
        formPanel.add(data_nascimentoField);
        formPanel.add(new JLabel("Salário:"));
        formPanel.add(salarioField);
        formPanel.add(new JLabel("Disciplina:"));
        formPanel.add(disciplinaField);
        formPanel.add(cadastrar);
        formPanel.add(alterar);

        JPanel botoesInferiores = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        botoesInferiores.setBackground(new Color(240, 248, 255));
        botoesInferiores.add(excluir);
        botoesInferiores.add(voltar);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(tela_resultado), BorderLayout.CENTER);

        String[] colunas = {"Nome", "E-mail", "Nascimento", "Salário", "Disciplina"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaProfessores = new JTable(tableModel);
        tabelaProfessores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProfessores.setRowHeight(24);

        tabelaProfessores.getSelectionModel().addListSelectionListener(e -> {
            int i = tabelaProfessores.getSelectedRow();
            if (i >= 0 && i < listaProfessores.size()) {
                Professores prof = listaProfessores.get(i);
                nomeField.setText(prof.getNome());
                emailField.setText(prof.getEmail());
                data_nascimentoField.setText(prof.getData_nascimento());
                salarioField.setText(prof.getSalario());
                disciplinaField.setText(prof.getDisciplina());
                indiceSelecionado = i;
                tela_resultado.setText("Professor selecionado para edição.");
            }
        });

        JPanel tabelaPanel = new JPanel(new BorderLayout());
        tabelaPanel.setBorder(BorderFactory.createTitledBorder("Professores Cadastrados"));
        tabelaPanel.add(new JScrollPane(tabelaProfessores), BorderLayout.CENTER);
        tabelaPanel.setPreferredSize(new Dimension(0, 180));

        JPanel sulPanel = new JPanel(new BorderLayout());
        sulPanel.add(tabelaPanel, BorderLayout.CENTER);
        sulPanel.add(botoesInferiores, BorderLayout.SOUTH);

        add(sulPanel, BorderLayout.SOUTH);

        atualizarTabela();
        setVisible(true);
    }

    private void cadastrarProfessor(ActionEvent e) {
        Professores prof = new Professores();
        prof.setNome(nomeField.getText());
        prof.setEmail(emailField.getText());
        prof.setData_nascimento(data_nascimentoField.getText());
        prof.setSalario(salarioField.getText());
        prof.setDisciplina(disciplinaField.getText());

        ProfessoresDAO dao = new ProfessoresDAO();
        dao.adicionar(prof);

        listaProfessores = dao.listar();
        atualizarTabela();
        tela_resultado.setText("Professor cadastrado com sucesso no banco de dados.");
        limparFormulario();
    }

    private void alterarProfessor(ActionEvent e) {
        if (indiceSelecionado >= 0 && indiceSelecionado < listaProfessores.size()) {
            Professores prof = listaProfessores.get(indiceSelecionado);
            prof.setNome(nomeField.getText());
            prof.setEmail(emailField.getText());
            prof.setData_nascimento(data_nascimentoField.getText());
            prof.setSalario(salarioField.getText());
            prof.setDisciplina(disciplinaField.getText());

            new ProfessoresDAO().alterar(prof);
            listaProfessores = new ProfessoresDAO().listar();
            atualizarTabela();
            tela_resultado.setText("Professor alterado no banco.");
            limparFormulario();
        } else {
            tela_resultado.setText("Selecione um professor para alterar.");
        }
    }

    private void excluirProfessor(ActionEvent e) {
        if (indiceSelecionado >= 0 && indiceSelecionado < listaProfessores.size()) {
            Professores prof = listaProfessores.get(indiceSelecionado);
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este professor?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                new ProfessoresDAO().excluir(prof.getId());
                listaProfessores = new ProfessoresDAO().listar();
                atualizarTabela();
                limparFormulario();
                tela_resultado.setText("Professor excluído com sucesso.");
                indiceSelecionado = -1;
            }
        } else {
            tela_resultado.setText("Selecione um professor para excluir.");
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Professores prof : listaProfessores) {
            tableModel.addRow(new Object[]{
                    prof.getNome(), prof.getEmail(), prof.getData_nascimento(),
                    prof.getSalario(), prof.getDisciplina()
            });
        }
    }

    private void limparFormulario() {
        nomeField.setText("");
        emailField.setText("");
        data_nascimentoField.setText("");
        salarioField.setText("");
        disciplinaField.setText("");
    }
}
