/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author Ryan Robinson
 */
public class Step {
    public enum Action {SEND, RECIEVE, FRESH}
    
    private Term term;
    private Action action;
    private String recipiant;
    
    public Step(Action action, Term term)
    {
        this.action = action;
        this.term = term;
    }
    
    public Step(Action action, Term term, String recipiant)
    {
        this.action = action;
        this.term = term;
        this.recipiant = recipiant;
    }
    
    public Step()
    {
        
    }
    
    public Action getAction()
    {
        return action;
    }
    
    public Term getTerm()
    {
        return term;
    }
    
    public String getRecipiant()
    {
        return recipiant;
    }
    
    public void setTerm(Term term)
    {
        this.term = term;
    }
    
    public void setAction(Action action)
    {
        this.action = action;
    }
    
    public void setRecipiant(String recipiant)
    {
        this.recipiant = recipiant;
    }
    
    
    public void overwriteStep(Step step)
    {
        this.term.overwriteTerm(step.term);
        this.action = step.action;
        this.recipiant = step.recipiant;
    }
    
    public String getDescription()
    {
        return "Command: " + getAction() + " | Term: " + getTerm().getTermString() + " | To: " + getRecipiant();
    }
}
