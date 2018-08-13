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
public class Feature_Type {
    private ArrayList<Linkset> linkset;
    private ArrayList<Types> types;
    
   
    public Feature_Type(){
        
    }

    /**
     * @return the linkset
     */
    public ArrayList<Linkset> getLinkset() {
        return linkset;
    }

    /**
     * @param linkset the linkset to set
     */
    public void setLinkset(ArrayList<Linkset> linkset) {
        this.linkset = linkset;
    }

    /**
     * @return the types
     */
    public ArrayList<Types> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }

    
}
