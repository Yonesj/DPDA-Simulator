package model;

import model.graph.AdjacencyMapGraph;
import model.graph.Vertex;

import java.util.Set;
import java.util.Stack;

public class PDA {
    private Stack<String> stack;
    private AdjacencyMapGraph<String, String> automata;
    private Vertex<String> initialState;
    private Set<Vertex<String>> finalStates;
    private Vertex<String> currentState;
    private String specialSymbol;

    public PDA(AdjacencyMapGraph<String, String> automata, Vertex<String> initialState, Set<Vertex<String>> finalStates, String specialSymbol) {
        this.automata = automata;
        this.initialState = initialState;
        this.currentState = initialState.clone();
        this.finalStates = finalStates;
        this.stack = new Stack<>();
        this.specialSymbol = specialSymbol;
        this.stack.push(specialSymbol);
    }

    public String readNextInput(char c){
        Object[] info = automata.getTransitionInfo(currentState, String.valueOf(c), stack.pop());
        if (info[1] == null) return null;

        currentState = (Vertex<String>)info[1];
        String pushSymbol = info[0].toString();

        if (!pushSymbol.equals("#")) {
            for (char i : pushSymbol.toCharArray()){
                stack.push(String.valueOf(i));
            }
        }

        return pushSymbol;
    }

    public boolean isInFinalState(){
        for (Vertex<String> v : finalStates){
            if (v.getElement().equals(currentState.getElement())) return true;
        }
        return false;
    }

    public void reset(){
        stack.clear();
        stack.push(specialSymbol);
        currentState = initialState.clone();
    }

    public Vertex<String> getInitialState(){
        return initialState;
    }

    public String getSpecialSymbol() {
        return specialSymbol;
    }

    public Vertex<String> getCurrentState() {
        return currentState;
    }

    public String getStackTopItem(){
        return stack.peek();
    }

    public Set<Vertex<String>> getFinalStates() {
        return finalStates;
    }

    public AdjacencyMapGraph<String, String> getAutomata() {
        return automata;
    }
}
