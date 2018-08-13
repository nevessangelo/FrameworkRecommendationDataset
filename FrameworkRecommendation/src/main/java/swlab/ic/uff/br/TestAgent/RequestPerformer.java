/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.TestAgent;

import swlab.ic.uff.br.Agent.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author angelo
 */
public class RequestPerformer extends Behaviour {
    private AID bestSeller;
    private int bestPrice;
    private int repliesCnt = 0;
    private MessageTemplate mt; 
    private int step = 0;
    
   
    private AID[] sellerAgents;
    private String targetBookTitle;
    
    public RequestPerformer(AID[] sellerAgents, String targetBookTitle) {
        
        this.sellerAgents = sellerAgents;
        this.targetBookTitle = targetBookTitle;
        
    }


    @Override
    public void action() {
        switch (step) {
            case 0:
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < sellerAgents.length; ++i) {
                    cfp.addReceiver(sellerAgents[i]);
                }
                cfp.setContent(targetBookTitle);
                cfp.setConversationId("book-trade");
                cfp.setReplyWith("cfp"+System.currentTimeMillis());
                myAgent.send(cfp);
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                        MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                step = 1;
                break;
                
            case 1:
                ACLMessage reply = myAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.PROPOSE) {
                        int price = Integer.parseInt(reply.getContent());
                        if (bestSeller == null || price < bestPrice) {
                            bestPrice = price;
                            bestSeller = reply.getSender();
                        }
                    }
                    repliesCnt++;
                    if (repliesCnt >= sellerAgents.length) {
                        step = 2; 
                    }
                }else{
                    block();
                }
                break;
            
            case 2:
                ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                order.addReceiver(bestSeller);
                order.setContent(targetBookTitle);
                order.setConversationId("book-trade");
                order.setReplyWith("order"+System.currentTimeMillis());
                myAgent.send(order);
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                     MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                step = 3;
                break;
                
            case 3:
                reply = myAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.INFORM) {
                        System.out.println(targetBookTitle+"successfully purchased.");
                        System.out.println("Price = "+bestPrice);
                        myAgent.doDelete();
                    }
                    step = 4;
                }else{
                    block();
                }
                break;
                
        }
        
    }

    @Override
    public boolean done() {
        return ((step == 2 && bestSeller == null) || step == 4);
        
    }
    
    
}
