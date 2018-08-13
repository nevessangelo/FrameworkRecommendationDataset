/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.TestAgent;

import swlab.ic.uff.br.Agent.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;

/**
 *
 * @author angelo
 */
public class SampleAgent extends Agent {

    String targetBookTitle;
    private AID[] sellerAgents = {new AID("seller1", AID.ISLOCALNAME), 
                                  new AID("seller2", AID.ISLOCALNAME)};

    protected void setup() {
        
        System.out.println("Hello! Buyer-agent "+getAID().getName()+" is ready.");
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            targetBookTitle = (String) args[0];
            System.out.println("Trying to buy "+targetBookTitle);
            addBehaviour(new TickerBehaviour(this, 6000) {
                @Override
                protected void onTick() {
                    myAgent.addBehaviour(new RequestPerformer(sellerAgents, targetBookTitle));
                }
                
            });
        }else{
            System.out.println("No book title specified");
            doDelete();
        }
        
        
    }
    
    protected void takeDown() {
        System.out.println("Buyer-agent "+getAID().getName()+" terminating.");
    }
    

    

}
