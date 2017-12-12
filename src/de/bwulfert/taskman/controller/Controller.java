package de.bwulfert.taskman.controller;

import de.bwulfert.taskman.modell.ProcessEntry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    MenuItem miClose, miRefresh, miAbout;

    @FXML
    TableView processTable;
    TableColumn<Integer, Number> pidCol = new TableColumn<>("PID");
    TableColumn<Integer, String> nameColumn = new TableColumn<>("Process Name");
    TableColumn<Integer, String> commandLineColumn = new TableColumn<>("Command-Line");
    TableColumn<Integer, String> userColumn = new TableColumn<>("User");
    private ObservableList<ProcessEntry> processEntries = FXCollections.observableArrayList();

    public void initialize() {
        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("processName"));
        commandLineColumn.setCellValueFactory(new PropertyValueFactory<>("commandLine"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        processTable.getColumns().add(pidCol);
        processTable.getColumns().add(nameColumn);
        processTable.getColumns().add(userColumn);
        processTable.getColumns().add(commandLineColumn);

        updateTable();
        updateMenuitems();
    }

    private void updateMenuitems() {
        miClose.setOnAction(action -> System.exit(0));
        miAbout.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Java9 Task-Manager\nBy Benjamin Wulfert\nwulfert.benjamin@googlemail.com");
            alert.showAndWait();
        });
        miRefresh.setOnAction(action -> {
            Platform.runLater(() -> {
                updateMenuitems();
            });
        });
    }

    private void updateTable() {
        System.out.println("Updating entries ...");
        processEntries.clear();

        List<ProcessHandle> collect = ProcessHandle
                .allProcesses()
                .filter(ph -> ph.info().command().isPresent())
                .collect(Collectors.toList());

        collect.forEach(pHandle -> {
            processEntries.add(
                    new ProcessEntry(
                            pHandle.pid(),
                            pHandle.info().command().orElse("n/a"),
                            pHandle.info().commandLine().orElse("n/a"),
                            pHandle.info().user().orElse("n/a")
                    )
            );
        });

        processTable.setItems(processEntries);
    }
}
