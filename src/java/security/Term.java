/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ryanrobinson
 */
public class Term {

    public enum Type {

        PUBLIC, FRESH, HASH, AENC, ADEC, PK, SK, SYMM_KEY, VARIABLE, SENC, SDEC, EMPTY, LIST, HEAD, TAIL
    }

    private String termString;
    private Type type;
    private List<Term> subTerms;
    private int arity;

    public Term(String name, Type type, int arity) {
        this.termString = name;
        this.type = type;
        this.subTerms = new ArrayList<>();
        this.arity = arity;
    }

    public Term() {

    }

    /**
     *
     * @param encryptee The term to be encrypted
     * @param key The key which we encrypt with
     * @return encrypt(h(NA), pk(A)) returns {h(NA)}pk(A)
     */
    public static Term encrypt(Term encryptee, Term key) {
        String termString = "{" + encryptee.termString + "}" + key.termString;
        Term term = new Term(termString, Type.AENC, 2);
        term.subTerms.add(encryptee);
        term.subTerms.add(key);

        return term;
    }

    /**
     *
     * @param decryptee The Term Being Decrypted
     * @param key Key
     * @return possibly just return decrypt function on top and get derivation
     * to simplify
     */
    public static List<Term> decrypt(Term decryptee, Term key) {
        if (decryptee.subTerms.contains(key)) {
            return decryptee.subTerms;
        } else {
            return null;
        }
    }

    /**
     *
     * @param agent The agent for whom we are generating a key pair
     * @return The private and public key pair sk(Agent), pk(Agent)
     */
    public static List<Term> registerAsymmetic(Term agent) {
        List<Term> keys = new ArrayList<>();
        String publicKeyString = "pk(" + agent.termString + ")";
        String privateKeyString = "sk(" + agent.termString + ")";
        Term publicKey = new Term(publicKeyString, Type.PK, 1);
        Term privateKey = new Term(privateKeyString, Type.SK, 1);
        publicKey.subTerms.add(agent);
        privateKey.subTerms.add(agent);

        keys.add(publicKey);
        keys.add(privateKey);

        return keys;
    }

    public static List<Term> registerAsymmetics(Term agent) {
        List<Term> keys = new ArrayList<>();
        String publicKeyString = "pk(" + agent.termString + ")";
        String privateKeyString = "sk(" + agent.termString + ")";
        Term publicKey = new Term(publicKeyString, Type.PK, 1);
        Term privateKey = new Term(privateKeyString, Type.SK, 1);
        publicKey.subTerms.add(agent);
        privateKey.subTerms.add(agent);

        keys.add(publicKey);
        keys.add(privateKey);

        return keys;
    }

    public static Term hash(Term hashee) {
        String hashString = "h(" + hashee.termString + ")";
        Term hash = new Term(hashString, Type.HASH, 1);
        hash.subTerms.add(hashee);
        return hash;
    }

    public static Term createList(LinkedList<Term> terms) {
        String listString = "[" + terms.peek().termString + ",";
        Term list;
        Term head = new Term("HEAD", Type.HEAD, 1);
        head.setSubTerms(terms.pop());
        Term tail = new Term("TAIL", Type.TAIL, 1);

        if (terms.size() == 0) {
            listString = listString.substring(0, listString.length() - 1);
            listString += "]";
            list = new Term(listString, Type.LIST, 2);
            Term none = new Term("END_OF_LIST", Type.EMPTY, 0);
            tail.setSubTerms(none);
            list.setSubTerms(head);
            list.setSubTerms(tail);
        } else {
            //listString += (head.termString + ",");           
            for (Term term : terms) {
                listString += (term.termString + ",");
            }
            listString = listString.substring(0, listString.length() - 1);
            listString += "]";
            list = new Term(listString, Type.LIST, 2);
            list.setSubTerms(head);
            tail.setSubTerms(createList(terms));
            list.setSubTerms(tail);
            //git plz
        }
        return list;
    }

    public static Term generateFresh(String name) {
        Term term = new Term(name, Type.FRESH, 0);
        return term;
    }

    public void setSubTerms(Term term) {
        subTerms.add(term);
    }

    public List<Term> getSubTerms() {
        return subTerms;
    }

    public String getTermString() {
        return termString;
    }

    public int getArity() {
        return arity;
    }

    public Type getType() {
        return type;
    }

    public void setTermString(String termString) {
        this.termString = termString;
    }

    public void overwriteTerm(Term term) {
        this.termString = term.termString;
        this.type = term.type;
        this.subTerms = new LinkedList<>(); //HAHAHAHA
        for (int i = 0; i < term.arity; i++) {
            Term newTerm = new Term();
            newTerm.overwriteTerm(term.subTerms.get(i));
            this.subTerms.add(newTerm);
        }
        this.arity = term.arity;
    }

    public void overwriteSubTerm(int index, Term term) {
        //parseCorrect ahhhhhhhhhhhhhhhhhhhhh i have to fix termstrings above
        this.subTerms.get(index).overwriteTerm(term);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Term)) {
            return false;
        }
        Term comp = (Term) obj;

        return arity == comp.arity
                && Objects.equals(termString, comp.termString)
                && Objects.equals(type, comp.type)
                && Objects.equals(subTerms, comp.subTerms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arity, termString, type, subTerms);
    }

    public boolean canRecieve(Term bufferTerm, Agent agent) {
        if (this.equals(bufferTerm)) {
            return true;
        } 
        else {
            boolean myBool = (arity == bufferTerm.arity
                    && (Objects.equals(type, bufferTerm.type) || type.equals(Type.VARIABLE))
                    && Term.recieveSubterms(subTerms, bufferTerm.subTerms, agent));

            if (myBool == true && type.equals(Type.VARIABLE)) { //this means the old term is a variable so is open to accepting a public or fresh in its place
                //maybe set old term string here to search recipiants
                Term term = new Term();
                term.overwriteTerm(this);
                agent.correctVariable(bufferTerm, term); //always base
                //now correct recipient in steps
//                for (int i = 0; i < agent.getRole().getSteps().size(); i++) {
//                    if (agent.getRole().getSteps().get(i).getAction().equals(Step.Action.SEND)) {
//                        if (agent.getRole().getSteps().get(i).getRecipiant().equals(variables.get(variableID).getTermString())) {
//                            //agents.get(agentID).getRole().getSteps().get(i).setRecipiant(agents.get(replaceID).getName());
//                            agents.get(agentID).getRole().getSteps().get(i).setRecipiant(newTermString);
//                        }
//                    }
//                }
            }
            return myBool;
        }

    }

    public static boolean recieveSubterms(List<Term> termList, List<Term> bufferList, Agent agent) {
        boolean myBoolean = true;
        for (int i = 0; i < termList.size(); i++) {
            myBoolean = termList.get(i).canRecieve(bufferList.get(i), agent);
            if (myBoolean == false) {
                return false;
            }
        }

        return myBoolean;
    }

}
