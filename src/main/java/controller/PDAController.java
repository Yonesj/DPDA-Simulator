package controller;


import model.PDA;
import model.PDAs;
import model.StateRecord;
import model.TransitionRecord;
import model.graph.AdjacencyMapGraph;
import model.graph.Edge;
import model.graph.Vertex;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PDAController {
    private AdjacencyMapGraph<String, String> graph;
    private Vertex<String> initialState;
    private Set<Vertex<String>> finalStates;

    public PDAController() {
        graph = new AdjacencyMapGraph<>(true);
        initialState = null;
        finalStates = new HashSet<>();
    }

    public StateRecord addState(String stateName, boolean isFinalState) {
        Vertex<String> newState = graph.insertVertex(stateName);
        if (initialState == null) initialState = newState;
        if (isFinalState) finalStates.add(newState);

        return new StateRecord(stateName, isFinalState ? "True" : "False");
    }

    public void removeState(String stateName) {
        // Remove from graph
        for (Vertex<String> v : graph.vertices()) {
            if (v.getElement().equals(stateName)) {
                graph.removeVertex(v);
                break;
            }
        }
        finalStates.removeIf(v -> Objects.equals(v.getElement(), stateName));
    }

    public TransitionRecord addTransition(String originState, String destinationState, String inputSymbol, String popSymbol, String pushSymbol) {
        Vertex<String> origin = null;
        Vertex<String> destination = null;

        for (Vertex<String> v : graph.vertices()) {
            if (v.getElement().equals(originState)) origin = v;
            if (v.getElement().equals(destinationState)) destination = v;
        }

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origin or destination state does not exist");
        }

        graph.insertEdge(origin, destination, inputSymbol, popSymbol, pushSymbol);

        return new TransitionRecord(originState, destinationState, inputSymbol, popSymbol, pushSymbol);
    }

    public void removeTransition(String originState, String destinationState, String inputSymbol, String popSymbol, String pushSymbol) {
        // Remove from graph
        for (Edge<String> e : graph.edges()) {
            Vertex<String>[] endPoints = graph.endVertices(e);
            if (e.getInputSymbol().equals(inputSymbol) &&
                    e.getPopSymbol().equals(popSymbol) &&
                    e.getPushSymbol().equals(pushSymbol) &&
                    endPoints[0].getElement().equals(originState) &&
                    endPoints[1].getElement().equals(destinationState)){
                graph.removeEdge(e);
                break;
            }
        }
    }

    public void addPDA(String language) {
        if (initialState == null) {
            throw new IllegalArgumentException("Initial state is not defined");
        }
        PDAs.addPDA(language, new PDA(graph, initialState, finalStates, "Z"));
    }
}
