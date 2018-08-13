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
public class MeanRetrivieal {
    
    private ArrayList<Double> ndcg_mean;
    private ArrayList<Double> recall_mean;
    
    public MeanRetrivieal(){
        
        
    }

    /**
     * @return the ndcg_mean
     */
    public ArrayList<Double> getNdcg_mean() {
        return ndcg_mean;
    }

    /**
     * @param ndcg_mean the ndcg_mean to set
     */
    public void setNdcg_mean(ArrayList<Double> ndcg_mean) {
        this.ndcg_mean = ndcg_mean;
    }

    /**
     * @return the recall_mean
     */
    public ArrayList<Double> getRecall_mean() {
        return recall_mean;
    }

    /**
     * @param recall_mean the recall_mean to set
     */
    public void setRecall_mean(ArrayList<Double> recall_mean) {
        this.recall_mean = recall_mean;
    }
    
    
    
}
