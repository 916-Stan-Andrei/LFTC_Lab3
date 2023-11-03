import PIF.ProgramInternalFormTable;
import Scanner.MyScanner;
import Scanner.Token;
import SymbolTable.SymbolTable;

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

        //p1

        String p1 = "var a int;" +
                "var b int;" +
                "var c int;" +
                "var max int;" +
                "var min int;" +
                "max = a;" +
                "if (b > max) {" +
                "    max = b;" +
                "}" +
                "if (c > max) {" +
                "    max = c;" +
                "}" +
                "write(max);";

        //p2

        String p2 = "var a int;" +
                "var b int;" +
                "var r int;" +
                "if (a == 0 || b == 0) {" +
                "    write(\"GCD=0\");" +
                "}" +
                "else {" +
                "    while (b != 0) {" +
                "        r = a % b;" +
                "        a = b;" +
                "        b = r;" +
                "    }" +
                "    write(\"GCD=\" + a);" +
                "}";

        //p3

        String p3 = "var n int;" +
                "var numbers int[];" +
                "var i int;" +
                "var sum int;" +
                "int n, numbers, i, sum;" +
                "write(n);" +
                "sum = 0;" +
                "for (i = 0; i < n; i++) {" +
                "    readInt(numbers[i]);" +
                "    sum = sum + numbers[i];" +
                "}" +
                "write(sum);";

        //perr

        String perr = "var a int;" +
                "var b int;" +
                "var c int;" +
                "var max int;" +
                "var min int;" +
                "max = a;" +
                "if (b > max) {" +
                "    max = b;" +
                "}" +
                "if (c > max) {" +
                "    max = c;" +
                "}" +
                "write(max);";

        List<Token> tokens = myScanner.scan(p1);
        for (Token token : tokens){
            if (Objects.equals(token.getType(), "Identifier") || Objects.equals(token.getType(), "Constant")){
                symbolTable.addHash(token.getValue());
            }
        }

        for (Token token : tokens) {
            System.out.println("Type: " + token.getType() + ", Value: " + token.getValue());
            if (Objects.equals(token.getType(), "ReservedWord") || Objects.equals(token.getType(), "Separator")
                || Objects.equals(token.getType(), "Operator")){
                PIF.addEntry(token.getValue(), -1);
            }
            else if (Objects.equals(token.getType(), "Identifier") || Objects.equals(token.getType(), "Constant")){
                int index = symbolTable.getPositionHash(token.getValue());
                PIF.addEntry(token.getValue(), index);
            }
            else{
                System.out.println("Lexical Error");
            }
        }

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
    }
}