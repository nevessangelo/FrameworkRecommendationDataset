/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Hashtable;
import swlab.ic.uff.br.Controller.Dataset;
import swlab.ic.uff.br.Controller.Linkset;
import swlab.ic.uff.br.Agent.InsertDatasets;

/**
 *
 * @author angelo
 */
public class DatasetsTranningAgents extends Agent {

    private Hashtable catalogue;
    private InsertDatasets insert;

    protected void setup() {
        catalogue = new Hashtable();
        insert = new InsertDatasets(this);
        
        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("ranking");
        sd.setName("JADE-ranking");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new LinksetRequestsServer());
        addBehaviour(new PurchaseOrdersServer());

    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Printout a dismissal message
        System.out.println("Dataset-agent " + getAID().getName() + " terminating.");
    }

  
    public void updateRanking(final String dataset, final double triples) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                catalogue.put(dataset, new Double(triples));
                System.out.println(dataset + " inserted into ranking agent. Triples = " + triples);
            }
        });
    }

    private class LinksetRequestsServer extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String linkset = msg.getContent();
                ACLMessage reply = msg.createReply();

                Double triples = (Double) catalogue.get(linkset);
                if (triples != null) {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(triples.doubleValue()));
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }

        }
    }
    
     private class PurchaseOrdersServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();

                Double triples = (Double) catalogue.remove(title);
                if (triples != null) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println(title+" sold to agent "+msg.getSender().getName());
                } else {
                    // The requested book has been sold to another buyer in the meanwhile .
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }  // End of inner class OfferRequestsServer
    

}
