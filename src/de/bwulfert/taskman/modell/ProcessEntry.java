package de.bwulfert.taskman.modell;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProcessEntry {

    private final LongProperty pid = new SimpleLongProperty();
    private final StringProperty processName = new SimpleStringProperty();
    private final StringProperty commandLine = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();

    public ProcessEntry(long pid, String name, String commandLine, String userName) {
        this.pid.set(pid);
        this.processName.set(name);
        this.commandLine.set(commandLine);
        this.userName.set(userName);
    }

    public long getPid() {
        return pid.get();
    }

    public void setPid(long pid) {
        this.pid.set(pid);
    }

    public LongProperty pidProperty() {
        return pid;
    }

    public String getProcessName() {
        return processName.get();
    }

    public void setProcessName(String processName) {
        this.processName.set(processName);
    }

    public StringProperty processNameProperty() {
        return processName;
    }

    public String getCommandLine() {
        return commandLine.get();
    }

    public void setCommandLine(String commandLine) {
        this.commandLine.set(commandLine);
    }

    public StringProperty commandLineProperty() {
        return commandLine;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }
}