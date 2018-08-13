/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Strategy;

import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class Mean {
    
    private ArrayList<Double> recall;
    private ArrayList<Double> ndcg;
    
    public Mean(){
        
    }

    /**
     * @return the recall
     */
    public ArrayList<Double> getRecall() {
        return recall;
    }

    /**
     * @param recall the recall to set
     */
    public void setRecall(ArrayList<Double> recall) {
        this.recall = recall;
    }

    /**
     * @return the ndcg
     */
    public ArrayList<Double> getNdcg() {
        return ndcg;
    }

    /**
     * @param ndcg the ndcg to set
     */
    public void setNdcg(ArrayList<Double> ndcg) {
        this.ndcg = ndcg;
    }
    
    
    
}
