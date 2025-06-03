/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class Funcionario extends ID {
    String nome;
    String cargo;
    String login;
    char[] senha;

        public Funcionario(int id, String nome, String cargo, String login, char[] senha) {
            super(id);
            this.nome = nome;
            this.cargo = cargo;
            this.login = login;
            this.senha = senha;
    }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCargo() {
            return cargo;
        }
        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }

        public char[] getSenha() {
            return senha;
        }
        public void setSenha(char[] senha) {
            this.senha = senha;
        }
}
