package main.java.filechooser.viewmode;

import main.java.filechooser.Controller;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;

import java.io.File;


public class ListFileView implements ViewMode {
    private Controller controller;
    List list;
    private String currentDir;
    private String currentExtension;

    public ListFileView(Composite composite, Controller controller) {
        this.controller = controller;
        this.list = new List(composite, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL |SWT.H_SCROLL);
        this.currentDir = controller.getCurrentDir();
        this.currentExtension = controller.getCurrentExtension();
        list.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String[] selected = list.getSelection();
                String path = (String) list.getData(selected[0]);
                controller.updatePath(path);
            }
        });
        updateContent();
    }

    private void updateContent() {
        list.removeAll();
        File dir = new File(currentDir);
        File parentFile = dir.getParentFile();
        if(parentFile != null) {
            list.setData("↑", parentFile.getAbsolutePath());
            list.add("↑");
        }
        File[] files = dir.listFiles();
        if(files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if(fileName.endsWith(currentExtension) ||
                        file.isDirectory()) {
                    list.add(fileName);
                    list.setData(fileName, file.getAbsolutePath());
                }
            }
        }
        list.pack();
    }


    public void changeDirectory(String path) {
        currentDir = path;
        updateContent();

    }

    @Override
    public void changeExtention(String filter) {
        currentExtension = filter;
        updateContent();
    }

    public Control getControl() {
        return list;
    }
}
