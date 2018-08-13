/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.TestAgent;


import swlab.ic.uff.br.Agent.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import java.util.ArrayList;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author angelo
 */
public class TestAgent {
    public static void main(String[] args) {
        double score = 0.5;
        ArrayList<String> relevants = new ArrayList<>();
        relevants.add("relevant1");
        relevants.add("relevant2");
        String dataset_tranning = "relevant1";
        
//        Runtime rt = Runtime.instance();
//        rt.setCloseVM(true);
//        Profile p = new ProfileImpl();
//        p.setParameter(Profile.MAIN_HOST, "localhost");
//        p.setParameter(Profile.MAIN_PORT, "1099");
//        AgentContainer ac = rt.createMainContainer(p);
//
//        AgentController agent, agent2;
//        try {
//            agent = ac.createNewAgent("Agent1", "swlab.ic.uff.br.Agent.BookSellerAgent", new Object[]{"book1"});
//            agent2 = ac.createNewAgent("Agent2", "swlab.ic.uff.br.Agent.BookBuyerAgent", new Object[]{"book1"});
//            
//            
//            agent.start();
//            agent2.start();
//            
//            
//            
//            //ac.kill();
//           
//        } catch (StaleProxyException ex) {
//            Logger.getLogger(TestAgent.class.getName()).log(Level.SEVERE, null, ex);
//        }
            
        Properties pp = new Properties();
    pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
    Profile p = new ProfileImpl(pp);
    AgentContainer ac = jade.core.Runtime.instance().createMainContainer(p);
    try {
        ac.acceptNewAgent("Agent1", new BookSellerAgent()).start();
        ac.createNewAgent("Agent2",  "swlab.ic.uff.br.Agent.BookBuyerAgent", new Object[]{"book1"}).start();
    } catch (StaleProxyException e) {
        throw new Error(e);
    }
        
        

                

        
       



            
       
        
        
        


        
       
        
        
    }
    
}
