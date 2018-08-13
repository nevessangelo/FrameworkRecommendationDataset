/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class Dataset {
    private int id;
    private String nome;
    private Feature_Type type;
    private Double size;
    private ArrayList<Linkset> relevants;
    
    
    public Dataset (){
        
    }
    
    public Dataset(int id, String nome, Feature_Type type, Double size, ArrayList<Linkset> relevants){
        this.id = id;
        this.nome = nome;
        this.type = type;
        this.size = size;
        this.relevants = relevants;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    

    /**
     * @return the size
     */
    public Double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * @return the type
     */
    public Feature_Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Feature_Type type) {
        this.type = type;
    }

    /**
     * @return the relevants
     */
    public ArrayList<Linkset> getRelevants() {
        return relevants;
    }

    /**
     * @param relevants the relevants to set
     */
    public void setRelevants(ArrayList<Linkset> relevants) {
        this.relevants = relevants;
    }
    
    

    
    
}
