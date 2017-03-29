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
 * @author ryanrobinson
 */
public class Agent {
    
    public enum Status {PARTICIPANT, ADVERSARY}
    
    private List<Term> knowledge;
    private String name;
    private Role role;
    private Status status;
    private int runIdentifier;
    private int stepCounter;
    
    
    public Agent(String name, Status status, int runIdentifier)
    {
        this.name = name;
        this.status = status;
        this.runIdentifier = runIdentifier;
        this.knowledge = new LinkedList<>();
        stepCounter = 0;
    }
    
    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public void setKnowledge(List<Term> knowledge)
    {
        this.knowledge = knowledge;
    }
    
    public void addKnowledge(Term term)
    {
        if(knowledge.contains(term))
        {
            System.out.println("Already in knowledge");
        }
        else if(term.getType().equals(Type.LIST))
        {
            List<Term> listTerm = Derivation.breakdownTermList(term);
            for(Term t : listTerm)
            {
                if(!knowledge.contains(t))
                {
                    knowledge.add(t);
                }
            }
        }
        else
        {
            knowledge.add(term);
        }
    }
    
    public void correctVariable(Term newTerm, Term oldVariable)
    {
        for(int i=0;i<knowledge.size();i++) //if its in it knowledge
        {
            if(knowledge.get(i).equals(oldVariable))
            {
                knowledge.get(i).overwriteTerm(newTerm);// = newTerm;
            }
            else if(!(knowledge.get(i).getArity() == 0))
            {
                //corectSubterms & correct termString
                correctSubTerms(newTerm, oldVariable, knowledge.get(i));
            }
        }
        
        for(Step step : role.getSteps())
        {
            if(step.getTerm().equals(oldVariable))
            {
                step.setTerm(newTerm);
            }
            else if(!(step.getTerm().getArity() == 0))
            {
                correctSubTerms(newTerm, oldVariable, step.getTerm());
            }
        }
    }
    
    public void correctSubTerms(Term newTerm, Term oldVariable, Term tree)
    {
        for(int i=0;i<tree.getSubTerms().size();i++)
        {
            if(tree.getSubTerms().get(i).equals(oldVariable))
            { //need new overwrite method in term
                tree.setTermString(Parser.correctParse(tree.getTermString(),oldVariable.getTermString(),newTerm.getTermString()));
                tree.overwriteSubTerm(i, newTerm);// = newTerm;
            }
            else if(!(tree.getSubTerms().get(i).getArity() == 0))
            {
                //corectSubterms// here correct termstring
                tree.setTermString(Parser.correctParse(tree.getTermString(),oldVariable.getTermString(),newTerm.getTermString()));
                correctSubTerms(newTerm, oldVariable, tree.getSubTerms().get(i));
            }
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getRunIdentifier()
    {
        return runIdentifier;
    }
    
    public Role getRole()
    {
        return role;
    }
    
    public int getStepCounter()
    {
        return stepCounter;
    }
    
    public List<Term> getKnowledge() {
        return knowledge;
    }
    
    public void incrementStep()
    {
        stepCounter++;
    }
    
    public List<Term> getVariables()
    {
        List<Term> variables = new LinkedList<>();
        for(int i=0;i<knowledge.size();i++) //if its in it knowledge
        {
            if(knowledge.get(i).getType().equals(Type.VARIABLE))
            {
                if(!variables.contains(knowledge.get(i)))
                {
                    Term term = new Term();
                    term.overwriteTerm(knowledge.get(i));
                    variables.add(term);// = newTerm;
                }
            }
            else if(!(knowledge.get(i).getArity() == 0))
            {
                //corectSubterms & correct termString
                getSubVariables(knowledge.get(i), variables);
            }
        }
        
        for(Step step : role.getSteps())
        {
            if(step.getTerm().getType().equals(Type.VARIABLE))
            {
                if(!variables.contains(step.getTerm()))
                {
                    Term term = new Term();
                    term.overwriteTerm(step.getTerm());
                    variables.add(term);
                }
            }
            else if(!(step.getTerm().getArity() == 0))
            {
                getSubVariables(step.getTerm(),variables);
            }
        }
        return variables;
    }
    
    public void getSubVariables(Term tree, List<Term> variables)
    {
        for(int i=0;i<tree.getSubTerms().size();i++)
        {
            if(tree.getSubTerms().get(i).getType().equals(Type.VARIABLE))
            { //need new overwrite method in term
                if(!variables.contains(tree.getSubTerms().get(i)))
                {
                    Term term = new Term();
                    term.overwriteTerm(tree.getSubTerms().get(i));
                    variables.add(term);// = newTerm;
                }
            }
            else if(!(tree.getSubTerms().get(i).getArity() == 0))
            {
                //corectSubterms// here correct termstring               
                getSubVariables(tree.getSubTerms().get(i),variables);
            }
        }
    }
    
    public String getAgentDescription() {
        return "Name: " + name + " | Run ID: " + runIdentifier + " | Step Counter: " + stepCounter;
    }
    
}
