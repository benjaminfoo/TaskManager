package de.bwulfert.taskman.controller;

import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    TableView processTable;

    public void initialize() {
        List<ProcessHandle> collect = ProcessHandle.allProcesses()
                .filter(ph -> ph.info().command().isPresent())
                .collect(Collectors.toList());

        for (int i = 0; i < collect.size(); i++) {
            processTable.getItems().add(i);
        }

        TableColumn<Integer, Number> intColumn = new TableColumn<>("PID");
        intColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyLongWrapper(collect.get(rowIndex).pid());
        });

        TableColumn<Integer, String> nameColumn = new TableColumn<>("Process Name");
        nameColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(collect.get(rowIndex).info().command().orElse("n/a"));
        });

        TableColumn<Integer, String> commandLineColumn = new TableColumn<>("Command-Line");
        commandLineColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(collect.get(rowIndex).info().commandLine().orElse("n/a"));
        });


        TableColumn<Integer, String> commandColumn = new TableColumn<>("Arguments");
        commandColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            Optional<String[]> arguments = collect.get(rowIndex).info().arguments();
            return new ReadOnlyStringWrapper(Arrays.asList(arguments.orElse(new String[]{""})).toString());
        });

        TableColumn<Integer, String> userColumn = new TableColumn<>("User");
        userColumn.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(collect.get(rowIndex).info().user().get());
        });


        processTable.getColumns().add(intColumn);
        processTable.getColumns().add(nameColumn);
        processTable.getColumns().add(commandLineColumn);
        processTable.getColumns().add(commandColumn);
        processTable.getColumns().add(userColumn);

        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Terminate");
        mi1.setOnAction(event -> {
            Integer item = (Integer) processTable.getSelectionModel().getSelectedItem();


            System.out.println("item.destroyForcibly()? ->  " +
                    ProcessHandle.allProcesses().filter(processHandle -> processHandle.pid() == item).collect(Collectors.toList()).get(0).destroyForcibly()
            );

        });
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Menu 2");
        cm.getItems().add(mi2);

        processTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    cm.show(processTable, t.getScreenX(), t.getScreenY());
                }
            }
        });

    }
}
