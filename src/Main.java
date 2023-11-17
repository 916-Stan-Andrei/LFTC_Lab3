import PIF.ProgramInternalFormTable;
import Scanner.MyScanner;
import Scanner.Token;
import SymbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyScanner myScanner = new MyScanner();
        ProgramInternalFormTable PIF = new ProgramInternalFormTable();
        SymbolTable<Object> symbolTable = new SymbolTable<>(10);

        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.println("Choose a file:");
            System.out.println("1. p1.txt");
            System.out.println("2. p2.txt");
            System.out.println("3. p3.txt");
            System.out.println("4. perr.txt");
            System.out.println("Press 'x' to exit.");

            choice = scanner.next().charAt(0);
            String filePath;

            switch (choice) {
                case '1' -> filePath = "p1.txt";
                case '2' -> filePath = "p2.txt";
                case '3' -> filePath = "p3.txt";
                case '4' -> filePath = "perr.txt";
                case 'x', 'X' -> {
                    System.out.println("Exiting program.");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                StringBuilder codeBuilder = new StringBuilder();
                String line;
                int lineNumber = 0;

                boolean isCorrect = true;
                while ((line = br.readLine()) != null) {
                    codeBuilder.append(line).append("\n");
                    lineNumber++;

                    List<Token> tokens = myScanner.scan(line);

                    for (Token token : tokens) {
                        if (Objects.equals(token.getType(), "Identifier") || Objects.equals(token.getType(), "Constant")) {
                            symbolTable.addHash(token.getValue());
                        }
                    }

                    for (Token token : tokens) {
                        //System.out.println("Type: " + token.getType() + ", Value: " + token.getValue());
                        if (Objects.equals(token.getType(), "ReservedWord") || Objects.equals(token.getType(), "Separator")
                                || Objects.equals(token.getType(), "Operator")) {
                            PIF.addEntry(token.getValue(), -1);
                        } else if (Objects.equals(token.getType(), "Identifier") || Objects.equals(token.getType(), "Constant")
                                || Objects.equals(token.getType(), "IntegerConstant")
                                || Objects.equals(token.getType(), "StringConstant")
                                || Objects.equals(token.getType(), "BooleanConstant")
                                || Objects.equals(token.getType(), "FloatConstant")
                        ) {
                            int index = symbolTable.getPositionHash(token.getValue());
                            PIF.addEntry(token.getValue(), index);
                        } else {
                            System.out.println("Lexical Error at line " + lineNumber);
                            isCorrect = false;
                        }
                    }
                }
                if (isCorrect) System.out.println("Lexically correct");


                try {
                    FileWriter pifFileWriter = new FileWriter("PIF.out");
                    pifFileWriter.write(String.valueOf(PIF));
                    pifFileWriter.close();

                    FileWriter stFileWriter = new FileWriter("ST.out");
                    stFileWriter.write(String.valueOf(symbolTable));
                    stFileWriter.close();
                } catch (IOException e) {
                    System.err.println("Error writing to output files.");
                }
            } catch (IOException e) {
                System.err.println("Error reading from the file.");
            }
        }while (choice != 'x' && choice != 'X');
    }
}
