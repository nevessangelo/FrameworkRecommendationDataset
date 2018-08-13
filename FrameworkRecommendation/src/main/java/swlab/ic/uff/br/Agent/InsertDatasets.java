/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Agent;

import java.util.ArrayList;
import swlab.ic.uff.br.Agent.DatasetsTranningAgents;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.Controller.Feature_Type;
import swlab.ic.uff.br.Controller.Linkset;

/**
 *
 * @author angelo
 */
public class InsertDatasets {
    
   private DatasetsTranningAgents myAgent;
    
  

    public InsertDatasets(DatasetsTranningAgents a) {
        //super(a.getLocalName());
        this.myAgent = a;
        Dataset dataset_test = new Dataset();
        dataset_test.setNome("acm");
        ArrayList<Linkset> list_feature_test = new ArrayList<>();
        Linkset linkset_test = new Linkset();
        linkset_test.setName("budapest");
        linkset_test.setFrquen(10000.0);
        
        Linkset linkset_test2 = new Linkset();
        linkset_test2.setName("biovida");
        linkset_test2.setFrquen(10.0);
        
        list_feature_test.add(linkset_test);
        list_feature_test.add(linkset_test2);
        
        Feature_Type feature_type_test = new Feature_Type();
        feature_type_test.setLinkset(list_feature_test);
        dataset_test.setType(feature_type_test);
        
        dataset_test.setSize(10.0);
        
        Dataset dataset1 = new Dataset();
        ArrayList<Linkset> list_feature1 = new ArrayList<>();
        
        Linkset linkset = new Linkset();
        linkset.setName("budapest");
        linkset.setFrquen(100.0);
        
        Linkset linkset2 = new Linkset();
        linkset2.setName("ieee");
        linkset2.setFrquen(100.0);
        
        Linkset linkset3 = new Linkset();
        linkset3.setName("bioportal");
        linkset3.setFrquen(100.0);
        
        list_feature1.add(linkset);
        list_feature1.add(linkset2);
        list_feature1.add(linkset3);
        
        Feature_Type featuretype = new Feature_Type();
        featuretype.setLinkset(list_feature1);
        
        dataset1.setNome("ieee");
        dataset1.setType(featuretype);
        dataset1.setRelevants(list_feature1);
        dataset1.setSize(30.0);
        
        
        Dataset dataset2 = new Dataset();
        ArrayList<Linkset> list_feature2 = new ArrayList<>();
        
        Linkset linkset4 = new Linkset();
        linkset4.setName("pisa");
        linkset4.setFrquen(100.0);
        
        Linkset linkset5 = new Linkset();
        linkset5.setName("eprints");
        linkset5.setFrquen(100.0);
        
        Linkset linkset6 = new Linkset();
        linkset6.setName("laas");
        linkset6.setFrquen(100.0);
        
        list_feature2.add(linkset4);
        list_feature2.add(linkset5);
        list_feature2.add(linkset6);
        
        Feature_Type featuretype2 = new Feature_Type();
        featuretype2.setLinkset(list_feature2);
        
        dataset2.setNome("citeseer");
        dataset2.setType(featuretype2);
        dataset2.setRelevants(list_feature2);
        dataset2.setSize(30.0);
        
        ArrayList<Dataset> list_datasets_tranning = new ArrayList<>();
        list_datasets_tranning.add(dataset1);
        list_datasets_tranning.add(dataset2);
        
        for(Dataset dataset_tranning: list_datasets_tranning){
            myAgent.updateRanking(dataset_tranning.getNome(), dataset_tranning.getSize());
          }

    }
    
    
    
}
