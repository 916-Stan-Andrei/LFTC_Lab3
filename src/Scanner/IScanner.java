package Scanner;

import java.util.List;

public interface IScanner {
    public List<Token> scan(String inputText);
    public String detect(String token);
}
