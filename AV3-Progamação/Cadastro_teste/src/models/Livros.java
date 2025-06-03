/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author 12825135945
 */
public class Livros extends ID {
    final String titulo;
    final String autor;
    final String categoria;
    int disponibilidade;
    
        public Livros(int id, String titulo, String autor, String categoria, int disponibilidade) {
            super(id);
            this.titulo = titulo;
            this.autor = autor;
            this.categoria = categoria;
            this.disponibilidade = disponibilidade;
    }
        public String getTitulo() {
            return titulo;
        }
        //public void setTitulo(String titulo) {
        //    this.titulo = titulo;
        //}

        public String getAutor() {
            return autor;
        }
        //public void setAutor(String autor) {
        //    this.autor = autor;
        //}

        public String getCategoria() {
            return categoria;
        }
        //public void setCategoria(String categoria) {
        //    this.categoria = categoria;
        //}

        public int getDisponibilidade() {
            return disponibilidade;
        }
        public void setDisponibilidade(int disponibilidade) {
            this.disponibilidade = disponibilidade;
        }
}
