package FA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
}
