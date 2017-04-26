/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.util.LinkedList;
import java.util.List;
import security.Term.Type;

/**
 *
 * @author Think
 */
public class Protocol {

    private List<Role> roles;
    private List<Term> networkKnowledge;
    String name;

    public Protocol(String name) {
        this.name = name;
        roles = new LinkedList<>();
        networkKnowledge = new LinkedList<>();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addStepToRole(String agent, Step step) //could maybe change to boolean
    {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getAgent().equals(agent)) {
                roles.get(i).add(step);
            }
        }
    }

    public List<Role> getRole() {
        return roles;
    }

    public List<Term> getNetworkKnowledge() {
        return networkKnowledge;
    }

    public void addNetworkKnowledge(Term term) {
        if (term.getType().equals(Type.LIST)) {
            List<Term> tempList = Derivation.breakdownTermList(term);
            for (Term newTerm : tempList) {
                networkKnowledge.add(newTerm);
            }
        } else {
            networkKnowledge.add(term);
        }
    }
}
