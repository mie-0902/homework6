package View;
import Model.EditorModel;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.List;

public class EditorView extends BorderPane {
    private TextArea textArea;
    private TreeView<File> fileTreeView;

    public EditorView() {
        textArea = new TextArea();
        setCenter(textArea);

        fileTreeView = new TreeView<>();
        setLeft(fileTreeView);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TreeView<File> getFileTreeView() {
        return fileTreeView;
    }

    public void setFileTreeView(TreeView<File> treeView) {
        this.fileTreeView = treeView;
    }
}
