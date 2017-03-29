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
public class Derivation {
    
    public Derivation()
    {
        
    }
    
    public static List<Term> breakdownTermList(Term list)
    {
        List<Term> finalList = new LinkedList<>();
        //helper method, pointer to finalList
        iterateList(finalList, list);
        return finalList;
    }
    
    public static void iterateList(List<Term> finalList,Term tail)
    {
        finalList.add(tail.getSubTerms().get(0).getSubTerms().get(0)); //get head
        //if tail is End of List then return
        //if not pass the tail into this method again.
        if(!tail.getSubTerms().get(1).getSubTerms().get(0).getType().equals(Type.EMPTY))
        {
               iterateList(finalList, tail.getSubTerms().get(1).getSubTerms().get(0)); //recursive call tail
        }
    
    }
    

}
