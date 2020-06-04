package main.java.filechooser;

import main.java.filechooser.viewmode.ViewMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.io.File;

public class FileTree  implements ViewMode {
    Tree directoryTree;
    String currentPath;
    Controller controller;

    public FileTree(Shell shell, Controller controller) {
        this.controller = controller;
        directoryTree = new Tree(shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        initDirectoryTree();
    }

    public void setLayoutData(GridData treeGridData) {
        directoryTree.setLayoutData(treeGridData);
    }
    private void initDirectoryTree() {
        File[] logicDisks = File.listRoots();
        for (File logicDisk : logicDisks) {
            final TreeItem logicDiskItem = new TreeItem(directoryTree, SWT.NONE);
            logicDiskItem.setText(logicDisk.toString());
            logicDiskItem.setData(logicDisk.getAbsolutePath());
            TreeItem emptyItem = new TreeItem(logicDiskItem, SWT.NONE);
            logicDiskItem.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                    changeDirectory((String) logicDiskItem.getData());
                }
            });

        }
        directoryTree.addListener(SWT.Expand, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item = (TreeItem) event.item;
                if (item == null) {
                    return;
                }
                if (item.getItems().length == 1){
                    item.removeAll();
                }
                String path = (String) item.getData();
                File file = new File(path);
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            TreeItem subDir = new TreeItem(item, SWT.NONE);
                            new TreeItem(subDir, SWT.NONE);
                            subDir.setText(f.getName());
                            subDir.setData(f.getAbsolutePath());
                        }
                    }
                }
            }

        });
        directoryTree.addListener(SWT.Collapse, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item = (TreeItem) event.item;
                if (item == null) {
                    return;
                }
                item.removeAll();
                TreeItem emptyItem = new TreeItem(item, SWT.NONE);
            }
        });
        directoryTree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item  = (TreeItem) event.item;
                String path  = (String) item.getData();
                controller.updatePath(path);
            }
        });
    }

    public void changeDirectory(String path) {
    }

    @Override
    public void changeExtention(String filter) {

    }
}
