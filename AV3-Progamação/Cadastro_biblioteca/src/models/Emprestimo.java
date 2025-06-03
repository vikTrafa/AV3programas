/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

public class Emprestimo extends ID {
    final String ID_livro;
    final String ID_membro;
    final LocalDate data_emprestimo;
    LocalDate data_devolucao; 
    String status;
    
    public Emprestimo(int id, String ID_livro, String ID_membro, LocalDate data_emprestimo, LocalDate data_devolucao, String status) {
        super(id);
        this.ID_livro= ID_livro;
        this.ID_membro = ID_membro;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
        this.status = status;
    }

    public String getID_livro() {
        return ID_livro;
    }
    //public void setID_livro(String ID_livro) {
    //    this.ID_livro = ID_livro;
    //}

    public String getID_membro() {
        return ID_membro;
    }
    //public void setID_membro(String ID_membro) {
    //    this.ID_membro = ID_membro;
    //}

    public LocalDate getData_emprestimo() {
        return data_emprestimo;
    }
    //public void setData_emprestimo(LocalDate data_emprestimo) {
    //    this.data_emprestimo = data_emprestimo;
    //}

    public LocalDate getData_devolucao() {
        return data_devolucao;
    }
    public void setData_devolucao(LocalDate data_devolucao) {
        this.data_devolucao = data_devolucao;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
