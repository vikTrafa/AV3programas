/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author 12825135945
 */
public class Historico_emprestimos extends ID {
    final String ID_membro; 
    final String ID_livro;
    final String data_acao;

        public Historico_emprestimos(int id, String ID_membro, String ID_livro, String data_acao) {
            super(id);
            this.ID_membro = ID_membro;
            this.ID_livro = ID_livro;
            this.data_acao = data_acao;
    }
        public String getID_membro() {
            return ID_membro;
        }
        //public void setID_membro(String ID_membro) {
        //    this.ID_membro = ID_membro;
        //}

        public String getID_livro() {
            return ID_livro;
        }
        //public void setID_livro(String ID_livro) {
        //    this.ID_livro = ID_livro;
        //}

        public String getData_acao() {
            return data_acao;
        }
        //public void setData_acao(String data_acao) {
        //    this.data_acao = data_acao;
        //}
}
