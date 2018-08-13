/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import swlab.ic.uff.br.Controller.CreateTFIDF;
import static swlab.ic.uff.br.Controller.Creates.CreateSets;
import static swlab.ic.uff.br.Controller.Creates.VectorFeatures;
import static swlab.ic.uff.br.Controller.Creates.mean_lists;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.Controller.Feature;
import swlab.ic.uff.br.Controller.Lists;
import swlab.ic.uff.br.Controller.MeanRetrivieal;
import swlab.ic.uff.br.Controller.MethodsComputation;
import swlab.ic.uff.br.Controller.TFIDF;
import swlab.ic.uff.br.Strategy.Cosseno;
import swlab.ic.uff.br.Strategy.Mean;

/**
 *
 * @author angelo
 */
public class task implements  Callable<MeanRetrivieal> {
    
    public int entrada;
    public ArrayList<Dataset> test;
    public ArrayList<Dataset> tranning_1;
    public ArrayList<Dataset> tranning_2;

    public task(int entrada, ArrayList<Dataset> test, ArrayList<Dataset> tranning_1, ArrayList<Dataset> tranning_2) {
        this.entrada = entrada;
        this.test = test;
        this.tranning_1 = tranning_1;
        this.tranning_2 = tranning_2;
    }

    @Override
    public MeanRetrivieal call() throws Exception {
        Lists lists = new Lists();
        ArrayList<Dataset> all_datasets_tranning = new ArrayList<>(tranning_1.size() + tranning_2.size());
        all_datasets_tranning.addAll(tranning_1);
        all_datasets_tranning.addAll(tranning_2);

        ArrayList<Dataset> all_datasets = new ArrayList<>(test.size() + tranning_1.size() + tranning_2.size());
        all_datasets.addAll(test);
        all_datasets.addAll(tranning_1);
        all_datasets.addAll(tranning_2);

        ArrayList<String> vector_features = VectorFeatures(all_datasets_tranning, entrada);

        lists.setTest(test);
        lists.setTranning_1(tranning_1);
        lists.setTranning_2(tranning_2);

        HashMap<Dataset, ArrayList<ArrayList<Feature>>> hmap = CreateSets(test, vector_features);

        ArrayList<String> name_datasets = new ArrayList<>();
        for (Dataset dataset : all_datasets_tranning) {
            String name_dataset = dataset.getNome();
            name_datasets.add(name_dataset);
        }

        MethodsComputation computation = new MethodsComputation();
        TFIDF tfidf = new TFIDF();

        HashMap<Dataset, ArrayList<Double>> tf_idf_traning = CreateTFIDF.createTF_IDF(
                vector_features, all_datasets_tranning, name_datasets);

        tfidf.setTf_idf_traning(tf_idf_traning);

        Cosseno cosseno = new Cosseno();
        Set<Dataset> datasets_test = hmap.keySet();
        Integer size = 0;
        ArrayList<ArrayList<Double>> list_ndcg = new ArrayList<>();
        ArrayList<ArrayList<Double>> list_recall = new ArrayList<>();
        for (Dataset dataset : datasets_test) {
            ArrayList<ArrayList<Feature>> set_dataset = hmap.get(dataset);
            for (ArrayList<Feature> set : set_dataset) {
                ArrayList<Double> vector_set = CreateTFIDF.createTFIDFTest(vector_features, set, all_datasets, dataset);
                tfidf.setTest(vector_set);
                computation.setTf_idf_method(tfidf);
                Mean mean = cosseno.Recomendation(computation, all_datasets_tranning, dataset, set);
                list_ndcg.add(mean.getNdcg());
                list_recall.add(mean.getRecall());
                size = mean.getNdcg().size();
            }
        }

        ArrayList<Double> mean_ndcg = mean_lists(list_ndcg, size);
        ArrayList<Double> mean_recall = mean_lists(list_recall, size);

        MeanRetrivieal meanRetrivieal = new MeanRetrivieal();
        meanRetrivieal.setNdcg_mean(mean_ndcg);
        meanRetrivieal.setRecall_mean(mean_recall);
        
        return meanRetrivieal;

        
    }

}
