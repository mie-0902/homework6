package Controller;
import View.EditorView;
import Model.EditorModel;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditorController {
    private EditorView view;
    private EditorModel model;
    private Stage stage;
    private File currentFile;

    public EditorController(EditorView view, EditorModel model, Stage stage) {
        this.view = view;
        this.model = model;
        this.stage = stage;
        this.currentFile = null;

        initListeners();
    }

    private void initListeners() {
        view.getTextArea().textProperty().addListener((observable, oldValue, newValue) -> {
            // Event handling for text changes
        });

        view.getFileTreeView().setOnMouseClicked(event -> {
            TreeItem<File> selectedItem = view.getFileTreeView().getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue().isFile()) {
                readFile(selectedItem.getValue());
            }
        });

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        openMenuItem.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Open Directory");
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                List<File> fileList = model.listFilesRecursive(selectedDirectory);
                view.getFileTreeView().setRoot(createTreeView(selectedDirectory, fileList));
            }
        });

        saveMenuItem.setOnAction(event -> {
            if (currentFile != null) {
                try {
                    model.saveToFile(currentFile, view.getTextArea().getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File selectedFile = fileChooser.showSaveDialog(stage);
                if (selectedFile != null) {
                    currentFile = selectedFile;
                    try {
                        model.saveToFile(selectedFile, view.getTextArea().getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        exitMenuItem.setOnAction(event -> stage.close());

        view.setTop(menuBar);
    }

    private TreeItem<File> createTreeView(File root, List<File> fileList) {
        TreeItem<File> rootItem = new TreeItem<>(root);
        rootItem.setExpanded(true);
        fileList.stream()
                .filter(File::isFile)
                .forEach(file -> {
                    TreeItem<File> item = new TreeItem<>(file);
                    rootItem.getChildren().add(item);
                });
        return rootItem;
    }

    private void readFile(File file) {
        try {
            view.getTextArea().setText(model.loadFromFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
