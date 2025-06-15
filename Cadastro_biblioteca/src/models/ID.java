/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author 12825135945
 */
public class ID {
    private static int contador = 0;
    protected int id;

    public ID(int id) {
        contador++;
        this.id = contador;
    }
    public int getID() {
        return id;
    }
}
