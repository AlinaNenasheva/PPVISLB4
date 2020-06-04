package main.java.filechooser.viewmode;

import main.java.filechooser.Controller;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DirFileView implements ViewMode {

    Image dirImage;
    private final String dirImageName = "dir.png";
    Image fileImage;
    private final String fileImageName = "file.png";
    Image lvlUpImage;
    private final String lvlUpImageName = "lvlup.png";
    private final int DEFAULT_COL_NUM = 4;

    private Table table;
    private String currentDir;
    private String currentExtension;
    private Controller controller;

    public DirFileView(Composite composite, Controller controller) {
        this.controller = controller;
        this.currentDir = controller.getCurrentDir();
        this.currentExtension = controller.getCurrentExtension();
        table = new Table(composite, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
        for (int i = 0; i < DEFAULT_COL_NUM; i++) {
            new TableColumn(table, SWT.V_SCROLL);
        }
        table.addListener(SWT.MouseDown, new Listener() {
            public void handleEvent(Event event) {
                Point p = new Point(event.x, event.y);
                TableItem item = table.getItem(p);
                if (item == null) {
                    return;
                }
                int columnCount = table.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    Rectangle rectangle = item.getBounds(i);
                    if (rectangle.contains(p)) {
                        String fileName = item.getText(i);
                        String path = (String) item.getData(fileName);
                        if (path == null) {
                            return;
                        }
                        File file = new File(path);
                        if (file.isDirectory()) {
                            currentDir = path;
                        }
                    }
                }
                updateContent();
            }
        });
        initImages();
        updateContent();

    }

    private void updateContent() {
        table.removeAll();
        File dir = new File(currentDir);
        File[] files = dir.listFiles();

        List<File> filesToAdd = new ArrayList<>();
        if(files != null) {
            for (File file : files) {
                if (file.isDirectory() ||
                        file.getName().endsWith(currentExtension)) {
                    filesToAdd.add(file);
                }
            }
        }

        Iterator<File> itr = filesToAdd.iterator();
        TableItem item = new TableItem(table, SWT.NONE);
        File parentFile = new File(currentDir).getParentFile();
        if(parentFile!= null){
            item.setText(0, "Up");
            item.setImage(0, lvlUpImage);
            item.setData("Up", parentFile.getAbsolutePath());
        };
        while(itr.hasNext()) {
            for (int j = 0; j < DEFAULT_COL_NUM && itr.hasNext(); j++) {
                if(j == 0 && table.getItemCount() == 1 ){
                } else {
                    File file = itr.next();
                    if(file.isDirectory()){
                        item.setImage(j, dirImage);

                    } else{
                        item.setImage(j, fileImage);
                    }
                    item.setText(j, file.getName());
                    item.setData(file.getName(), file.getAbsolutePath());
                }
            }
            item = new TableItem(table, SWT.NONE);
        }
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(i).pack();
        }
        table.setLinesVisible(false);
        table.pack();
    }

    private void initImages() {
    	dirImage = new Image(table.getDisplay(), "./src/main/resources/img/" + dirImageName);
    	fileImage = new Image(table.getDisplay(), "./src/main/resources/img/" + fileImageName);
    	lvlUpImage = new Image(table.getDisplay(), "./src/main/resources/img/" + lvlUpImageName);

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
        return table;
    }
}
