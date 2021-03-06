/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import security.Agent.Status;
import security.Step.Action;
import security.Term.Type;

/**
 *
 * @author Ryan
 */
public class Environment {

    private List<Agent> agents;
    private List<Term> networkBuffer;
    //private List<Term> networkKnowledge;
    private Protocol protocol;
    private Map<String, Integer> roleMap; //map for linking roles loaded from protocol to their posiition in the protocol role list eg. A->0 B->1 S->2
    private Map<Integer, String> agentMap; //map to link created agents to their position in the list eg. their run identifier. 
    private List<String> messageMap; //map to link a run identifier to a message sent to them !REVIEW!

    public Environment(Protocol protocol) {
        this.protocol = protocol;
        agents = new LinkedList<>();
        networkBuffer = new LinkedList<>();
        //networkKnowledge = new LinkedList<>();
        agentMap = new HashMap<>();
        roleMap = new HashMap<>();
        messageMap = new LinkedList<>();
        for (int i = 0; i < protocol.getRole().size(); i++) {
            roleMap.put(protocol.getRole().get(i).getAgent(), i);
        }
    }

    public void addAgent(String nameParam, String roleAgent) {
        System.out.println("Enter an Agent name: ");
        //String name = reader.next();
        int id = createAgent(nameParam);
        System.out.println("My name is: " + agents.get(id).getName() + "\nMy Run Identifier is: " + agents.get(id).getRunIdentifier());
        setRole(id, roleAgent);
    }
    
    public void setRole(int id, String roleChar) {
        System.out.println("Give Role:\n" + protocol.getRole().get(0).getAgent() + "\n" + protocol.getRole().get(1).getAgent() + "\n" + protocol.getRole().get(2).getAgent());
        String roleAgent = roleChar;
        //Role role = protocol.getRole().get(roleMap.get(roleAgent));
        Role role = new Role(agents.get(id).getName());
        role.setAllKnowledge(new LinkedList(protocol.getRole().get(roleMap.get(roleAgent)).getKnowledge()));
        role.setAllSteps(new LinkedList(protocol.getRole().get(roleMap.get(roleAgent)).getSteps()));
        List<Term> knowledge = new LinkedList(role.getKnowledge());
        agents.get(id).setRole(role); //PROBLEM
        agents.get(id).setKnowledge(knowledge); //PROBLEM
        Term newPublic = new Term(agents.get(id).getName(), Type.PUBLIC, 0);
        Term oldTerm = new Term(protocol.getRole().get(roleMap.get(roleAgent)).getAgent(), Type.VARIABLE, 0);
        agents.get(id).correctVariable(newPublic, oldTerm);
        for (Step step : agents.get(id).getRole().getSteps()) {
            System.out.println(step.getAction().toString() + " " + step.getTerm().getTermString());
        }
    }

    public void correctVariable(int runID, int variableIndex, int variableType, String newTString) {
        System.out.println("\nCHOOSE AGENT TO CORRECT KNOWLEDGE:");
        for (Agent agent : agents) {
            System.out.println(agent.getRunIdentifier() + ": " + agent.getName());
        }
        int agentID = runID;
        List<Term> variables = agents.get(agentID).getVariables();
        System.out.println("\nCHOOSE VARIABLE TO CORRECT:");
        for (int i = 0; i < variables.size(); i++) {
            System.out.println(i + ": " + variables.get(i).getTermString());
        }
        int variableID = variableIndex;
        System.out.println("\nCORRECT WITH\n1: Public\n2: Fresh");
        int correctWith = variableType;
        Type type = Type.PUBLIC;
        switch (correctWith) {
            case 1:
                type = Type.PUBLIC;
                break;

            case 2:
                type = Type.FRESH;
                break;
        }

        //correct with agent
        System.out.println("\nCORRECT WITH AGENT:\nTYPE NAME:");
        String newTermString = newTString;
        //agents.get(agentID).correctVariable(new Term(agents.get(replaceID).getName(),Type.PUBLIC,0), variables.get(variableID));
        agents.get(agentID).correctVariable(new Term(newTermString, type, 0), variables.get(variableID)); //always base
        //now correct recipient in steps
        for (int i = 0; i < agents.get(agentID).getRole().getSteps().size(); i++) {
            if (agents.get(agentID).getRole().getSteps().get(i).getAction().equals(Action.SEND)) {
                if (agents.get(agentID).getRole().getSteps().get(i).getRecipiant().equals(variables.get(variableID).getTermString())) {
                    //agents.get(agentID).getRole().getSteps().get(i).setRecipiant(agents.get(replaceID).getName());
                    agents.get(agentID).getRole().getSteps().get(i).setRecipiant(newTermString);
                }
            }
        }

    }

    public int createAgent(String name) {
        Agent agent = new Agent(name, Status.PARTICIPANT, agents.size());
        Term agentTerm = new Term(name, Type.PUBLIC, 0);
        Term publicKey = new Term();
        publicKey.overwriteTerm(Term.registerAsymmetic(agentTerm).get(0));
        this.getProtocol().addNetworkKnowledge(publicKey);
        agents.add(agent);
        agentMap.put(agent.getRunIdentifier(), name);
        //messageMap.add(new LinkedList<>());
        return agents.size() - 1;
    }

    public boolean takeStep(int runIdentifier, int networkBufferIndex) {
        int counter = agents.get(runIdentifier).getStepCounter();
        Step step;
        if (counter > agents.get(runIdentifier).getRole().getSteps().size() - 1) {
            System.out.println("Exceeded Role steps");
            return false;
        } else {
            //int counteragents.get(runIdentifier).getStepCounter()
            step = agents.get(runIdentifier).getRole().getSteps().get(counter);
            System.out.println("Command: " + step.getAction() + " | Term: " + step.getTerm().getTermString() + " | To: " + step.getRecipiant());
            if (step.getAction().equals(Action.SEND)) {
                System.out.println("Ree");
                //add to network buffer
                messageMap.add(step.getTerm().getTermString() + ":" + step.getRecipiant());
                Term knowledgeTerm = new Term();
                knowledgeTerm.overwriteTerm(step.getTerm());
                networkBuffer.add(step.getTerm());
                protocol.addNetworkKnowledge(knowledgeTerm); //.add(knowledgeTerm);
            } else if (step.getAction().equals(Action.RECIEVE)) {
                //if there is a message for me check if it is in the messageMap
                Role oldrole = new Role(agents.get(runIdentifier).getRole()); //this is for keeping a record of old role before variable changes
                for (int i = 0; i < networkBuffer.size(); i++) {
                    System.out.println(i + ":" + networkBuffer.get(i).getTermString());
                }
                //int bufferTerm = reader.nextInt();
                int bufferTerm = networkBufferIndex;
                //if(messageMap.contains(step.getTerm().getTermString()+":"+runIdentifier))
                if (step.getTerm().canRecieve(networkBuffer.get(bufferTerm), agents.get(runIdentifier))) { //need unique comparator for variables                    
                    agents.get(runIdentifier).addKnowledge(networkBuffer.get(bufferTerm));
                } 
                else { //correct role
                    return false;
                }
//                for(String message : messageMap)
//                {
//                    if(message.equals(step.getTerm()+":"+agents.get(runIdentifier).getName()))
//                    {
//                        //add to knowledgeagent
//                        agents.get(runIdentifier).addKnowledge(step.getTerm());
//                        return true;
//                    }
//                }
            } else if (step.getAction().equals(Action.FRESH)) {
                agents.get(runIdentifier).addKnowledge(step.getTerm());
            }
            //step = agents.get(runIdentifier).getRole().getSteps().get(counter);
            agents.get(runIdentifier).incrementStep();
            //networkBufferMethod(step);
        }
        return true;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Term> getNetworkBuffer() {
        return networkBuffer;
    }
    
    public void addToNetowrkBuffer(Term term) {
        networkBuffer.add(term);
    }

}
