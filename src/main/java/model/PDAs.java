package model;

import model.graph.AdjacencyMapGraph;
import model.graph.Vertex;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PDAs {
    private static final Map<String, PDA> PDAs = new HashMap<>();

    public static void addPDA(String language, PDA pda){
        PDAs.put(language, pda);
    }

    public static Map<String, PDA> getPDAs(){
        return PDAs;
    }

    private static String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
        StringBuilder content = new StringBuilder();

        int character;
        while ((character = fileReader.read()) != -1) {
            content.append((char) character);
        }
        fileReader.close();

        return content.toString();
    }

    public static void configure() throws IOException {
        JSONObject jsonObject = new JSONObject(readFile("config.json"));
        JSONArray pdasArray = jsonObject.getJSONArray("PDAs");

        String languageName;
        String specialSymbol;
        AdjacencyMapGraph<String, String> graph;
        Map<String, Vertex<String>> states;
        Vertex<String> originState;
        Vertex<String> destination;
        String inputSymbol;
        String popSymbol;
        String pushSymbol;
        Vertex<String> initialState;
        Set<Vertex<String>> finalStates;

        for (int i = 0; i < pdasArray.length(); i++) {
            JSONObject pdaJson = pdasArray.getJSONObject(i);

            languageName = pdaJson.getString("languageName");
            specialSymbol = pdaJson.getString("specialSymbol");
            graph = new AdjacencyMapGraph<>(true);

            // Extract states
            states = new HashMap<>();
            JSONObject automata = pdaJson.getJSONObject("automata");
            JSONArray statesArray = automata.getJSONArray("states");
            String stateName;

            for (int j = 0; j < statesArray.length(); j++) {
                stateName = statesArray.getString(j);
                states.put(stateName, graph.insertVertex(stateName));
            }

            // Extract transitions
            JSONArray transitionsArray = automata.getJSONArray("transitions");

            for (int j = 0; j < transitionsArray.length(); j++) {
                JSONObject transitionJson = transitionsArray.getJSONObject(j);
                originState = states.get(transitionJson.getString("originState"));
                destination = states.get(transitionJson.getString("destination"));
                inputSymbol = transitionJson.getString("inputSymbol");
                popSymbol = transitionJson.getString("popSymbol");
                pushSymbol = transitionJson.getString("pushSymbol");
                graph.insertEdge(originState, destination, inputSymbol, popSymbol, pushSymbol);
            }

            initialState = states.get(pdaJson.getString("initialState"));

            // Extract final states
            finalStates = new HashSet<>();
            JSONArray finalStatesArray = pdaJson.getJSONArray("finalStates");

            for (int j = 0; j < finalStatesArray.length(); j++) {
                finalStates.add(states.get(finalStatesArray.getString(j)));
            }

            addPDA(languageName, new PDA(graph, initialState, finalStates, specialSymbol));
        }
    }
}
