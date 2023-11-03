package PIF;

import java.util.ArrayList;
import java.util.List;

public class ProgramInternalFormTable {
    private final List<PIFEntry> entries;

    public ProgramInternalFormTable() {
        entries = new ArrayList<>();
    }

    public void addEntry(String value, int extraInfo) {
        PIFEntry entry = new PIFEntry(value, extraInfo);
        entries.add(entry);
    }

    @Override
    public String toString() {
        StringBuilder tableString = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            PIFEntry entry = entries.get(i);
            String tokenValue = entry.getTokenValue();
            int extraInfo = entry.getExtraInfo();
            tableString.append("PIF Entry ").append(i).append(": Token Value: ").append(tokenValue)
                    .append(", Extra Info: ").append(extraInfo).append("\n");
        }
        return tableString.toString();
    }
}