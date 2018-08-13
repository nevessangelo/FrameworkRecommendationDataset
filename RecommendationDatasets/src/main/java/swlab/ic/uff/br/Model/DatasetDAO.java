/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.ConnectionMysql.ConnectionBD;
import swlab.ic.uff.br.Controller.Linkset;
import swlab.ic.uff.br.Controller.Types;

/**
 *
 * @author angelo
 */
public class DatasetDAO {
    
    public static ArrayList<Linkset> getRelevants(String name_dataset){
        ArrayList<Linkset> datasets_relevants = new ArrayList<>();
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT DISTINCT features, frequencia FROM  `Features'` WHERE nome_dataset = ? AND "
                        + "tipo_feature = ?";
                 PreparedStatement preparedStmt = conn.prepareStatement(query);
                 preparedStmt.setString(1, name_dataset);
                 preparedStmt.setString(2, "Linkset");
                 ResultSet rs = preparedStmt.executeQuery();
                 while (rs.next()) {
                     Linkset linkset = new Linkset();
                     String feature = rs.getString("features")+"-uni-mannheim";
                     Double frequen = rs.getDouble("frequencia");
                     linkset.setName(feature);
                     linkset.setFrquen(frequen);
                     datasets_relevants.add(linkset);
                 }
                 
                
            }
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datasets_relevants;
    }
    
    public static Double getDatasetSize(String nome_dataset){
        Double size = 0.0;
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT DISTINCT dataset_size FROM  `Features'` WHERE nome_dataset = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, nome_dataset);
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    size = rs.getDouble("dataset_size");
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return size;
    }
    public static ArrayList<Types> getTypes(String nome_dataset){
        ArrayList<Types> list_types = new ArrayList<>();
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT DISTINCT features, frequencia FROM Features WHERE tipo_feature = 'Types' AND nome_dataset = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, nome_dataset);
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    Types types = new Types();
                    String type = rs.getString("features");
                    Double frequen = rs.getDouble("frequencia");
                    types.setName(type);
                    types.setFrquen(frequen);
                    list_types.add(types);
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list_types;
    }
    
    public static ArrayList<Linkset> getLinksets(String nome_dataset){
        ArrayList<Linkset> list_linkset = new ArrayList<>();
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT DISTINCT features, frequencia FROM Features WHERE tipo_feature = 'Linkset' AND nome_dataset = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, nome_dataset);
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    Linkset linkset = new Linkset();
                    String feature = rs.getString("features");
                    Double frequen = rs.getDouble("frequencia");
                    linkset.setName(feature);
                    linkset.setFrquen(frequen);
                    list_linkset.add(linkset);
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list_linkset;
    }
    
    public static ArrayList<Dataset> GetDatasetsLS(){
        ArrayList<Dataset> datasets = new ArrayList<>();
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT id_dataset, nome_dataset FROM Dataset WHERE qLS >= 5";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    Dataset dataset = new Dataset();
                    dataset.setId(rs.getInt("id_dataset"));
                    dataset.setNome(rs.getString("nome_dataset"));
                    datasets.add(dataset);
                }
            }
            conn.close();    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datasets;
    }
    
    public static ArrayList<Dataset> GetDatasetsTypes(){
        ArrayList<Dataset> datasets = new ArrayList<>();
        try {
            Connection conn = ConnectionBD.Connect();
            if (conn != null) {
                String query = "SELECT id_dataset, nome_dataset FROM Dataset WHERE qLS >= 5 AND qType >= 1";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    Dataset dataset = new Dataset();
                    dataset.setId(rs.getInt("id_dataset"));
                    dataset.setNome(rs.getString("nome_dataset"));
                    datasets.add(dataset);
                }
            }
            conn.close();    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatasetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datasets;
    }
    
}
