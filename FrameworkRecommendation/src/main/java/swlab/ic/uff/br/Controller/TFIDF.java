/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author angelo
 */
public class TFIDF {
    
    private HashMap<Dataset, ArrayList<Double>> tf_idf_traning;
    private ArrayList<Double> test;

    /**
     * @return the tf_idf_traning
     */
    public HashMap<Dataset, ArrayList<Double>> getTf_idf_traning() {
        return tf_idf_traning;
    }

    /**
     * @param tf_idf_traning the tf_idf_traning to set
     */
    public void setTf_idf_traning(HashMap<Dataset, ArrayList<Double>> tf_idf_traning) {
        this.tf_idf_traning = tf_idf_traning;
    }

    /**
     * @return the test
     */
    public ArrayList<Double> getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(ArrayList<Double> test) {
        this.test = test;
    }

   

   
    
    
}
