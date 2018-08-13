/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import Run.ExportGraphic;
import Run.LineChartDemo6;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.ui.RefineryUtilities;
import swlab.ic.uff.br.Model.DatasetDAO;
import swlab.ic.uff.br.Strategy.Cosseno;
import swlab.ic.uff.br.Strategy.Mean;
import swlab.ic.uff.br.task.task;

/**
 *
 * @author angelo
 */
public class Methods {

    public static void PopuledLists(int entrada) {
        DatasetDAO datasetdao = new DatasetDAO();

        ArrayList<Dataset> test_0 = new ArrayList<>();
        ArrayList<Dataset> tranning_1 = new ArrayList<>();
        ArrayList<Dataset> tranning_2 = new ArrayList<>();

        ArrayList<Dataset> datasets = null;
        if (entrada == 1) {
            datasets = datasetdao.GetDatasetsLS();
        }

        Integer cont = 1;
        for (Dataset dataset : datasets) {
            if (cont == 4) {
                cont = 1;
            } else if (cont == 1) {
                test_0.add(dataset);
                cont++;
            } else if (cont == 2) {
                tranning_1.add(dataset);
                cont++;
            } else if (cont == 3) {
                tranning_2.add(dataset);
                cont++;
            }
        }

        for (Dataset dataset : datasets) {
            dataset.setRelevants(datasetdao.getRelevants(dataset.getNome()));
            dataset.setSize(datasetdao.getDatasetSize(dataset.getNome()));
            Feature_Type feature_type = new Feature_Type();
            ArrayList<Linkset> ls = datasetdao.getLinksets(dataset.getNome());
            feature_type.setLinkset(ls);
            ArrayList<Types> types = datasetdao.getTypes(dataset.getNome());
            feature_type.setTypes(types);
            dataset.setType(feature_type);

        }
        CrossValidation cross_1 = new CrossValidation(test_0, tranning_1, tranning_2);
        CrossValidation cross_2 = new CrossValidation(tranning_1, test_0, tranning_2);
        CrossValidation cross_3 = new CrossValidation((tranning_2), tranning_1, test_0);

        ArrayList<CrossValidation> list_cross = new ArrayList<>();
        list_cross.add(cross_1);
        list_cross.add(cross_2);
        list_cross.add(cross_3);
        
        ArrayList<ArrayList<Double>> mean_ndcg = new ArrayList<>();
        ArrayList<ArrayList<Double>> mean_recall = new ArrayList<>();
        int size = 0;
        for (CrossValidation cross : list_cross) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<MeanRetrivieal> futureResult = executor.submit(new task(entrada, cross.getTest(), cross.getTranning_1(), cross.getTranning_2()));
            try {
                MeanRetrivieal resultado = futureResult.get();
                mean_ndcg.add(resultado.getNdcg_mean());
                mean_recall.add(resultado.getRecall_mean());
                size = resultado.getNdcg_mean().size();
            } catch (InterruptedException ex) {
                Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            }
            executor.shutdown();

        }
        
        System.out.println(Creates.mean_lists(mean_ndcg, size));
        System.out.println(Creates.mean_lists(mean_recall, size));
        
        final ExportGraphic demo = new ExportGraphic("Chart", Creates.mean_lists(mean_ndcg, size), Creates.mean_lists(mean_recall, size));
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        

    }

}
