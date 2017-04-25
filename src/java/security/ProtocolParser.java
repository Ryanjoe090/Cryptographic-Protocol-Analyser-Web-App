/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import security.Step.Action;

/**
 *
 * @author ryanrobinson
 */
public class ProtocolParser {

    Parser parser = new Parser();
    Protocol protocol;

    public Protocol parseProtocol(BufferedReader br,List<String> raw) //soon to be file
    {
        try {
            //File dir = new File("src");
            //File fin = new File(dir.getAbsolutePath() + File.separator + "protocols" + File.separator + "NeedhamSchroederPublicKey.txt");
            //File fin = new File("NeedhamSchroederPublicKey.txt");
            parseProtocolFile(br,raw);
        } catch (IOException e) {
                System.out.println("Didnt Parse");
        }

        //List<Role> protocol = new LinkedList();
        //parseRole(file, protocol);
        return protocol;
    }

    private Role createRole(String line) {
        String agent = line.substring(0, 1);
        String parsee;
        String[] knowledge;
        Role role = new Role(agent);
        parsee = line.replace(" ", "");
        parsee = parsee.substring(parsee.lastIndexOf("knows(") + 6, parsee.length() - 1);
        knowledge = parsee.split(",");
        for (String term : knowledge) {
            role.addKnowledge(parser.parse(term));
        }

        return role;
    }
    
    private void setNetworkKnowledge(String line) {
        String parsee;
        String [] knowledge;
        parsee = line.replace(" ", "");
        parsee = parsee.substring(parsee.lastIndexOf("network(") + 8, parsee.length() - 1);
        knowledge = parsee.split(",");
        for (String term : knowledge) {
            protocol.addNetworkKnowledge(parser.parse(term));
        }
    }

    private void parseStep(String line) {
        if (line.contains("fresh(")) {
            line = line.replace(" ", "");
            String agent = line.substring(0, line.indexOf(":"));
            String fresh = line.substring(line.lastIndexOf("fresh(")+6, line.length()-1);
            Term freshTerm = parser.parse(fresh);
            Step step = new Step(Action.FRESH, freshTerm);
            protocol.addStepToRole(agent, step);
            
        } else {
            //A->B: {[NA,A]}pk(B)        
            String[] stepParts;
            String[] agents;
            line = line.replace(" ", "");
            stepParts = line.split(":");
            agents = stepParts[0].split("->");
            Term term = parser.parse(stepParts[1]);
            Step send = new Step(Action.SEND, term, agents[1]);
            Step recieve = new Step(Action.RECIEVE, term);
        //protocol.add(send);
            //protocol.add(recieve);
            //ADD TO STEPS
            protocol.addStepToRole(agents[0], send);
            protocol.addStepToRole(agents[1], recieve);
        }
    }

    private void parseProtocolFile(BufferedReader br, List<String> raw) throws IOException {
        //FileInputStream stepStream = new FileInputStream(steps);
        //List<Role> roles = new LinkedList();

        //Construct BufferedReader from InputStreamReader
        //BufferedReader br = new BufferedReader(new InputStreamReader(stepStream));

        String line = br.readLine();
        raw.add(line);
        protocol = new Protocol(line);
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            raw.add(line);
            if (line.contains("knows")) {
                protocol.addRole(createRole(line));
            }
            else if(line.contains("network")) {
                setNetworkKnowledge(line);
            }
            else {
                parseStep(line);
            }
        }

        br.close();
    }
}
