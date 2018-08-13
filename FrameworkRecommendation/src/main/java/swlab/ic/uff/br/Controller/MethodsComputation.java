/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import java.lang.reflect.Method;

/**
 *
 * @author angelo
 */
public class MethodsComputation {
    private TFIDF tf_idf_method;
    
    
    public MethodsComputation(){
        
    }

    /**
     * @return the tf_idf_method
     */
    public TFIDF getTf_idf_method() {
        return tf_idf_method;
    }

    /**
     * @param tf_idf_method the tf_idf_method to set
     */
    public void setTf_idf_method(TFIDF tf_idf_method) {
        this.tf_idf_method = tf_idf_method;
    }
    
    
    
    
}
