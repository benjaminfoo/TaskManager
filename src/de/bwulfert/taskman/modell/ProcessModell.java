package de.bwulfert.taskman.modell;

public class ProcessModell {
    private final int intValue;
    private final String stringValue;

    public ProcessModell(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
