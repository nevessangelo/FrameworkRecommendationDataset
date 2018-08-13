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
public interface CalculateTFIDF {
    
    public Double calculateTF(Feature feature, Dataset dataset);
    
    public Double calculateIDF(Feature feature, ArrayList<Dataset> datasets, String name_dataset);
    
    public Double calculateIDFTest(Feature feature, ArrayList<Dataset> datasets, String name_dataset);
    
}
