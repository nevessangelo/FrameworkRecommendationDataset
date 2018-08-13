/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author angelo
 */
public class Creates {
    
    public static ArrayList<Double> mean_lists(ArrayList<ArrayList<Double>> lists, int size) {
        ArrayList<Double> list_return = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Double sum = 0.0;
            for (ArrayList<Double> ndcg : lists) {
                Double number = ndcg.get(i);
                sum = sum + number;
            }
            Double mean = sum / size;
            list_return.add(mean);
        }

        return list_return;

    }
    
     public static ArrayList<String> VectorFeatures(ArrayList<Dataset> all_datasets_tranning, int entrada) {


        ArrayList<String> vector_features = new ArrayList<>();
        ArrayList<String> datasets = new ArrayList<>();

        for (Dataset dataset : all_datasets_tranning) {
            String nome_dataset = dataset.getNome();
            datasets.add(nome_dataset);
        }

        if (entrada == 1) {
            for (Dataset dataset : all_datasets_tranning) {
                ArrayList<Linkset> linksets = dataset.getType().getLinkset();

                //ArrayList<String> linksets = dataset.getLinkset();
                for (Linkset linkset : linksets) {
                    String aux_dataset = linkset.getName() + "-uni-mannheim";
                    if ((datasets.contains(aux_dataset)) && (!vector_features.contains(aux_dataset))) {
                        vector_features.add(aux_dataset);
                    }

                }
            }
        }
        return vector_features;

    }
    
    public static HashMap<Dataset, ArrayList<ArrayList<Feature>>> CreateSets(ArrayList<Dataset> test, ArrayList<String> vector_features) {
        HashMap<Dataset, ArrayList<ArrayList<Feature>>> hmap = new HashMap<Dataset, ArrayList<ArrayList<Feature>>>();
        for (Dataset dataset : test) {
            ArrayList<Linkset> linksets = dataset.getType().getLinkset();
            ArrayList<Feature> linksets_sets = new ArrayList<>();

            Iterator itr = linksets.iterator();
            while (itr.hasNext()) {
                Linkset linkset = (Linkset) itr.next();
                String aux_dataset = linkset.getName() + "-uni-mannheim";
                if (vector_features.contains(aux_dataset)) {
                    linksets_sets.add(linkset);
                }

                ArrayList<ArrayList<Feature>> sets = Sets.GenerateSets(linksets_sets, 5, 5);
                hmap.put(dataset, sets);
            }

        }
        return hmap;
    }

    
}
