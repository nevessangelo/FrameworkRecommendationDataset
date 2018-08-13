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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.Controller.CreateTFIDF;
import swlab.ic.uff.br.Controller.Feature;
import swlab.ic.uff.br.Controller.MethodsComputation;

/**
 *
 * @author angelo
 */
public class Cosseno extends Strategy {
    
    public static double cosineSimilarity(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }
        double result = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        if(Double.isNaN(result)){
          return 0.0;  
        }else{
            return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        }
    }
    
     public static List<Map.Entry<Dataset, Double>> rankingsorted(HashMap<Dataset, Double> ranking) {
        Set<Map.Entry<Dataset, Double>> set = ranking.entrySet();
        List<Map.Entry<Dataset, Double>> list = new ArrayList<Map.Entry<Dataset, Double>>(set);
        Collections.sort(list, new Comparator<Map.Entry<Dataset, Double>>() {

            @Override
            public int compare(Map.Entry<Dataset, Double> o1, Map.Entry<Dataset, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }

        });
        return list;
    }

    @Override
    public List<Map.Entry<Dataset, Double>> MethodRecomendation(MethodsComputation method, 
            ArrayList<Dataset> all_datasets, Dataset dataset) {
        HashMap<Dataset, Double> ranking = new HashMap<>();
        
        HashMap<Dataset, ArrayList<Double>> tf_idf_traning = method.getTf_idf_method().getTf_idf_traning();
        
        ArrayList<Double> tf_idf_test = method.getTf_idf_method().getTest();
        
        Set<Dataset> datasets = tf_idf_traning.keySet();
        for(Dataset dataset_tranning: datasets){
            ArrayList<Double> vector_tranning = tf_idf_traning.get(dataset_tranning);
            Double score = cosineSimilarity(tf_idf_test, vector_tranning);
            ranking.put(dataset_tranning, score);
        }
        
        List<Map.Entry<Dataset, Double>> ranking_sorted = rankingsorted(ranking);
        
//        for(Map.Entry<Dataset, Double> pair:ranking_sorted){
//                 System.out.println(pair.getKey().getNome() + " = " + pair.getValue());                 
//         }

        return ranking_sorted;

    }

}
