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
public class CalculeTFIDFLS implements CalculateTFIDF {

    @Override
    public Double calculateTF(Feature feature, Dataset dataset) {
        Double tf = 0.0;
        tf = feature.getFrquen() / dataset.getSize();
        return tf;

    }

    @Override
    public Double calculateIDF(Feature feature, ArrayList<Dataset> datasets, String name_dataset) {
        Double idf = 0.0;
        for (Dataset dataset : datasets) {
            if (!dataset.getNome().equals(name_dataset)) {
                ArrayList<Linkset> linksets = dataset.getType().getLinkset();
                for (Linkset linkset : linksets) {
                    if (linkset.getName().equals(feature.getName())) {
                        idf = idf + 1;
                    }
                }

            }

        }
        return idf;
    }
    
    @Override
    public Double calculateIDFTest(Feature feature, ArrayList<Dataset> datasets, String name_dataset){
        Double idf = 0.0;
        for (Dataset dataset : datasets) {
            if (!dataset.getNome().equals(name_dataset)) {
                ArrayList<Linkset> linksets = dataset.getType().getLinkset();
                for (Linkset linkset : linksets) {
                    if (linkset.getName().equals(feature.getName())) {
                        idf = idf + 1;
                    }
                }
            }
        }
        return idf;
    }
            

}
