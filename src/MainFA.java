import FA.FiniteAutomaton;

import java.io.*;
import java.util.Scanner;

public class MainFA {
    public static void main(String[] args) {
        FiniteAutomaton fa = new FiniteAutomaton();
        fa.readFAFromFile("FA.in");

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Set of States");
            System.out.println("2. Alphabet");
            System.out.println("3. All Transitions");
            System.out.println("4. Initial State");
            System.out.println("5. Set of Final States");
            System.out.println("6. Verify Sequence");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> System.out.println("Set of States: " + fa.states);
                case 2 -> System.out.println("Alphabet: " + fa.alphabet);
                case 3 -> {
                    System.out.println("Transitions:");
                    for (String fromState : fa.transitions.keySet()) {
                        for (char symbol : fa.transitions.get(fromState).keySet()) {
                            String toState = fa.transitions.get(fromState).get(symbol);
                            System.out.println(fromState + " -> " + toState + " via " + symbol);
                        }
                    }
                }
                case 4 -> System.out.println("Initial State: " + fa.initialState);
                case 5 -> System.out.println("Set of Final States: " + fa.finalStates);
                case 6 -> {
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter sequence to verify: ");
                    String sequence = scanner.nextLine();
                    boolean accepted = fa.isAccepted(sequence);
                    if (accepted) {
                        System.out.println("Sequence is accepted by the FA.");
                    } else {
                        System.out.println("Sequence is NOT accepted by the FA.");
                    }
                }
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }
}

