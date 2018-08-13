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
public class Lists {
    
    private ArrayList<Dataset> test;
    private ArrayList<Dataset> tranning_1;
    private ArrayList<Dataset> tranning_2;
    
    public Lists(){
        
    }
    
    public Lists(ArrayList<Dataset> test, ArrayList<Dataset> tranning_1, ArrayList<Dataset> tranning_2){
        this.test = test;
        this.tranning_1 = tranning_1;
        this.tranning_2 = tranning_2;
    }

    /**
     * @return the test
     */
    public ArrayList<Dataset> getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(ArrayList<Dataset> test) {
        this.test = test;
    }

    /**
     * @return the tranning_1
     */
    public ArrayList<Dataset> getTranning_1() {
        return tranning_1;
    }

    /**
     * @param tranning_1 the tranning_1 to set
     */
    public void setTranning_1(ArrayList<Dataset> tranning_1) {
        this.tranning_1 = tranning_1;
    }

    /**
     * @return the tranning_2
     */
    public ArrayList<Dataset> getTranning_2() {
        return tranning_2;
    }

    /**
     * @param tranning_2 the tranning_2 to set
     */
    public void setTranning_2(ArrayList<Dataset> tranning_2) {
        this.tranning_2 = tranning_2;
    }
    
    
    
}
