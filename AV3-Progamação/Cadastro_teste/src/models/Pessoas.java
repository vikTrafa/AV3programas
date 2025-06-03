package models;

public class Pessoas{
//declarar os atributos
public String nome;
public String data_nascimento;
public String email;
//declaração dos métodos set e get
public void setNome(String nome){
    //variável nome recebe o nome que foi digitado
    this.nome = nome;
}
public String getNome(){
    return nome;
}
public void setEmail(String email){
    //variável nome recebe o nome que foi digitado
    this.email = email;
}
public String getEmail(){
    return email;
}
public void setData_nascimento(String data_nascimento){
    //variável nome recebe o nome que foi digitado
    this.data_nascimento = data_nascimento;
}
public String getData_nascimento(){
    return data_nascimento;
}

}
