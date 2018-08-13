/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class Sets {
    
    public static boolean VerificaSet(ArrayList<ArrayList<String>> sets, ArrayList<String> list){
        if(sets.size() == 0){
            return true;
        }else{
            Set<String> set = new HashSet<String>(list);
            for(ArrayList<String> set_list: sets){
                Set<String> set_aux = new HashSet<String>(set_list);
                if(set_aux.equals(set))
                    return false;
            }
        }
        
        return true;
    }
    
    public static Feature GetObjetFromList(ArrayList<Feature> list_feature, String search_feature){
        for(Feature feature: list_feature){
            if(feature.getName().equals(search_feature))
                return feature;
        }
        return null;
    }
    
    public static ArrayList<ArrayList<Feature>> GenerateSets(ArrayList<Feature> feature, int k, int size){
        ArrayList<String> sets_features = new ArrayList<>();
        for(Feature fe: feature){
            sets_features.add(fe.getName());
        }
        
        Integer aux = 1;
        ArrayList<ArrayList<Feature>> sets = new ArrayList<>();
        ArrayList<ArrayList<String>> sets_aux = new ArrayList<>();
        if(sets_features.size() <= size){
            sets.add(feature);
        }else{
            while(aux <= k){
                Collections.shuffle(sets_features);
                ArrayList<String> set = new ArrayList<>();
                for(int i = 0; i < size; i++){
                    set.add(sets_features.get(i));
                }
                if(VerificaSet(sets_aux, set)){
                    sets_aux.add(set);
                    ArrayList<Feature> sets_objfeatures = new ArrayList<>();
                    for(String i: set){
                        Feature fe = GetObjetFromList(feature, i);
                        sets_objfeatures.add(fe);
                    }
                    sets.add(sets_objfeatures);
                    aux++;
                }
                
            }
            
        }
        
        return sets;


    }
    
}
