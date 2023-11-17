package FA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    public Set<String> states;
    public Set<Character> alphabet;
    public Map<String, Map<Character, String>> transitions;
    public String initialState;
    public Set<String> finalStates;

    // Constructor
    public FiniteAutomaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
    }
    public void addTransition(String fromState, char symbol, String toState) {
        alphabet.add(symbol);
        states.add(fromState);
        states.add(toState);

        transitions.computeIfAbsent(fromState, k -> new HashMap<>());
        transitions.get(fromState).put(symbol, toState);
    }

    // Function to check if a sequence is accepted by the FA
    public boolean isAccepted(String sequence) {
        String currentState = initialState;

        for (char symbol : sequence.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                return false;
            }

            if (!transitions.containsKey(currentState) || !transitions.get(currentState).containsKey(symbol)) {
                return false;
            }

            currentState = transitions.get(currentState).get(symbol);
        }

        return finalStates.contains(currentState);
    }

    public void readFAFromFile(String filename){
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");

                switch (parts[0]) {
                    case "state" -> this.states.add(parts[1]);
                    case "alphabet" -> this.alphabet.add(parts[1].charAt(0));
                    case "transition" -> this.addTransition(parts[1], parts[2].charAt(0), parts[3]);
                    case "initial" -> this.initialState = parts[1];
                    case "final" -> this.finalStates.add(parts[1]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

