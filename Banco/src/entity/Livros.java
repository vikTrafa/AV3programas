package entity;

public class Livros {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private String disponibilidade;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitulo(){
        return titulo; 
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getAutor(){
        return autor;
    }
    public void setAutor(String autor){
        this.autor = autor;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    public String getDisponibilidade(){
        return disponibilidade;
    }
    public void setDisponibilidade(String disponibilidade){
        this.disponibilidade = disponibilidade;
    }
    
}
