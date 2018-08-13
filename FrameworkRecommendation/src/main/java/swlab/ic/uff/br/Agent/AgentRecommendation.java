/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.Agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angelo
 */
public class AgentRecommendation extends Agent {

    private String targetdatasetRelevant;
    private AID[] RankingAgents;

    protected void setup() {
        System.out.println("Hallo! Dataset Relevant " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            targetdatasetRelevant = (String) args[0];
            System.out.println("Target dataset is " + targetdatasetRelevant);

            addBehaviour(new TickerBehaviour(this, 10000) {
                protected void onTick() {
                    System.out.println("Trying to Recommendation " + targetdatasetRelevant);
                    // Update the list of seller agents
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("ranking");
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Found the following dataset in ranking:");
                        RankingAgents = new AID[result.length];
                        for (int i = 0; i < result.length; ++i) {
                            RankingAgents[i] = result[i].getName();
                            System.out.println(RankingAgents[i].getName());
                        }
                    } catch (FIPAException ex) {
                        Logger.getLogger(AgentRecommendation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myAgent.addBehaviour(new RequestPerformer());
                }
            });
        } else {
            // Make the agent terminate
            System.out.println("No target book title specified");
            doDelete();
        }
    }

    protected void takeDown() {
        System.out.println("Buyer-agent " + getAID().getName() + " terminating.");
    }

    private class RequestPerformer extends Behaviour {

        private AID bestDataset;
        private Double bestTriples;
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;

        @Override
        public void action() {
            switch (step) {
                case 0:
                    // Send the cfp to all sellers
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < RankingAgents.length; ++i) {
                        cfp.addReceiver(RankingAgents[i]);
                    }
                    cfp.setContent(targetdatasetRelevant);
                    cfp.setConversationId("dataset-trade");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("dataset-trade"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;

                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            //Double triples = Double.parseDouble(reply.getContent());
                            if (bestDataset == null) {
                                bestDataset = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= RankingAgents.length) {
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;

                case 2:
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestDataset);
                    order.setContent(targetdatasetRelevant);
                    order.setConversationId("dataset-trade");
                    order.setReplyWith("order" + System.currentTimeMillis());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("dataset-trade"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    break;

                case 3:
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println(targetdatasetRelevant + " successfully recommended from agent " + reply.getSender().getName());
                            myAgent.doDelete();
                        } else {
                            System.out.println("Attempt failed");
                        }
                        step = 4;
                    } else {
                        block();
                    }
                    break;
            }

        }

        @Override
        public boolean done() {
            if (step == 2 && bestDataset == null) {
                System.out.println("Attempt failed: " + targetdatasetRelevant + " not available for ranking");
                doDelete();
            }
            return ((step == 2 && bestDataset == null) || step == 4);
        }

    }

}
