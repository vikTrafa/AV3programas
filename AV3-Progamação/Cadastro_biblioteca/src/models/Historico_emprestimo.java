/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

public class Historico_emprestimo extends ID {
    final String ID_membro; 
    final String ID_livro;
    final LocalDateTime dataAcao;

        public Historico_emprestimo(int id, String ID_membro, String ID_livro, LocalDateTime dataAcao) {
            super(id);
            this.ID_membro = ID_membro;
            this.ID_livro = ID_livro;
            this.dataAcao = dataAcao;
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

        public LocalDateTime getDataAcao() {
            return dataAcao;
        }
        //public void setData_acao(String data_acao) {
        //    this.data_acao = data_acao;
        //}
}
