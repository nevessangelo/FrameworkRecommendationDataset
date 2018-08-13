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
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angelo
 */
public class BuyClass {
    
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        rt.setCloseVM(true);
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "1099");
        AgentContainer ac = rt.createMainContainer(p);

        AgentController agent;
        try {
            agent = ac.createNewAgent("Agent1", "swlab.ic.uff.br.Agent.SampleAgent", new Object[]{"book1"});
            agent.start();
            //ac.kill();
           
        } catch (StaleProxyException ex) {
            Logger.getLogger(TestAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
