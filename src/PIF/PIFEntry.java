package PIF;

public class PIFEntry {
    private final String tokenValue;    // Token value (e.g., the actual token)
    private final int extraInfo;   // Extra information (e.g., symbol table reference or line number)

    public PIFEntry(String value, int extraInfo) {
        this.tokenValue = value;
        this.extraInfo = extraInfo;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public int getExtraInfo() {
        return extraInfo;
    }

    @Override
    public String toString() {
        return "Value: " + tokenValue + ", Extra Info: " + extraInfo;
    }
}
