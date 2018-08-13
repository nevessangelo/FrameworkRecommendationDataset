/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.Controller.Feature;
import swlab.ic.uff.br.Controller.Linkset;
import swlab.ic.uff.br.Controller.MethodsComputation;

/**
 *
 * @author angelo
 */
public abstract class Strategy {
    
   

    public final Mean Recomendation(MethodsComputation method, ArrayList<Dataset> all_datasets, Dataset dataset,
            ArrayList<Feature> set) {
        Mean mean = new Mean();
        List<Map.Entry<Dataset, Double>> ranking = MethodRecomendation(method, all_datasets, dataset);
        ArrayList<Double> ndcg = calculatenDCG(ranking, dataset, set);
        ArrayList<Double> recall = calculateRecall(ranking, dataset, set);
        mean.setNdcg(ndcg);
        mean.setRecall(recall);
        return mean;

    }

    public Double Relevance(String dataset_tranning, String dataset_test, Double limit_, Double double_delta,
            Double limit_2, ArrayList<String> set_features, HashMap<String, Linkset> hashmap,
            Double min, Double max) {
        if (set_features.contains(dataset_test)) {
            return 0.0;
        } else {
            try {
                Linkset linkset = hashmap.get(dataset_tranning);
                Double triples = linkset.getFrquen();

                if (triples == 0) {
                    return 0.0;
                } else if (triples <= min && triples <= limit_) {
                    return 1.0;
                } else if (triples < limit_ && triples <= limit_2) {
                    return 2.0;
                } else if (triples < limit_2 && triples <= max) {
                    return 3.0;
                }
            } catch (Throwable e) {
                return 0.0;
            }

        }
        return 0.0;
    }
    
    

    public abstract List<Map.Entry<Dataset, Double>> MethodRecomendation(MethodsComputation method, ArrayList<Dataset> all_datasets,
            Dataset dataset);

    final ArrayList<Double> calculatenDCG(List<Map.Entry<Dataset, Double>> ranking, Dataset dataset_test, ArrayList<Feature> set) {
        
        HashMap<String, Linkset> hashmap = new HashMap<>();
        ArrayList<String> set_features = new ArrayList<>();
        for (Feature feature : set) {
            set_features.add(feature.getName() + "-uni-mannheim");
        }

        for (Map.Entry<Dataset, Double> pair : ranking) {
            ArrayList<Linkset> linksets = pair.getKey().getRelevants();
            for (Linkset linkset : linksets) {
                if (dataset_test.getNome().equals(linkset.getName())) {
                    hashmap.put(pair.getKey().getNome(), linkset);
                }
            }
        }
        Double max = 0.0;

        Set<String> datasets = hashmap.keySet();
        for (String dataset_tranning : datasets) {
            Linkset linkset = hashmap.get(dataset_tranning);
            if (linkset.getFrquen() > max) {
                max = linkset.getFrquen();
            }
        }

        Double min = Double.MAX_VALUE;
        Set<String> datasets2 = hashmap.keySet();
        for (String dataset_tranning : datasets2) {
            Linkset linkset = hashmap.get(dataset_tranning);
            if (linkset.getFrquen() < min) {
                min = linkset.getFrquen();
            }
        }
        Double sub = max - min;
        Double delta = sub / 3;

        Double limit_ = min + delta;
        Double double_delta = 2 * delta;
        Double limit_2 = min + double_delta;

        ArrayList<Double> vector_g = new ArrayList<>();
        for (Map.Entry<Dataset, Double> pair : ranking) {
            Double relevancia = Relevance(pair.getKey().getNome(), dataset_test.getNome(),
                    limit_, double_delta, limit_2, set_features, hashmap, min, max);
            vector_g.add(relevancia);
        }
       
        
        ArrayList<Double> vector_dcg = new ArrayList<>();
        for(int i = 0; i < vector_g.size(); i++){
            if(i == 0){
                vector_dcg.add(vector_g.get(i));
            }else{
                double log = Math.log(i + 1) / Math.log(2.0);
                vector_dcg.add(vector_g.get(i) / log + vector_dcg.get(i-1));
            }
            
        }
        Comparator<Double> comparator = Collections.reverseOrder();
        Collections.sort(vector_g, comparator);
        ArrayList<Double> vector_idcg = new ArrayList<>();
        for(int i = 0; i < vector_g.size(); i++){
            if(i == 0){
                vector_idcg.add(vector_g.get(i));
            }else{
                double log = Math.log(i + 1) / Math.log(2.0);
                vector_idcg.add(vector_g.get(i) / log + vector_idcg.get(i-1));
            }
            
        }
       
        ArrayList<Double> vector_ndcg = new ArrayList<>();
        for(int i = 0; i < vector_dcg.size(); i++){
            double result = vector_dcg.get(i) / vector_idcg.get(i);
            if(Double.isNaN(result)){
                vector_ndcg.add(0.0);
            }else{
                vector_ndcg.add(result);
            }
            
        }
     
        
        return vector_ndcg;
    }

    final ArrayList<Double> calculateRecall(List<Map.Entry<Dataset, Double>> ranking, Dataset dataset_test, ArrayList<Feature> set) {
        ArrayList<Double> recall_ = new ArrayList<>();
        Double cont = 0.0;
        ArrayList<Linkset> datasets_relevants = dataset_test.getRelevants();
        ArrayList<String> set_features = new ArrayList<>();
        ArrayList<String> name_datasets_relevants = new ArrayList<>();
        for (Linkset linkset : datasets_relevants) {
            name_datasets_relevants.add(linkset.getName());
        }
        for (Feature feature : set) {
            set_features.add(feature.getName() + "-uni-mannheim");
        }

        for (Map.Entry<Dataset, Double> pair : ranking) {
            String dataset_tranning = pair.getKey().getNome();
            if (name_datasets_relevants.contains(dataset_tranning)
                    && !set_features.contains(dataset_tranning)) {
                cont = cont + 1;
            }
            recall_.add(cont);
        }

        ArrayList<Double> recall = new ArrayList<>();
        for (Double i : recall_) {
            double aux = i / recall_.size();
            recall.add(aux);
        }
       
        return recall;
    }

}
