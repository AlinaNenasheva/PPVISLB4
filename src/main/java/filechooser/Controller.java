package main.java.filechooser;

import main.java.filechooser.viewmode.ViewMode;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
     List<ViewMode> views;
     String currentPath;
     String currentExtention = "*.*";
     String selected;
     Shell  appShell;

    public Controller(String currentPath, String filterExtensions, Shell shell){
        this.currentPath = currentPath;
        this.currentExtention = filterExtensions;
        this.appShell = shell;
        views = new ArrayList<ViewMode>();
    }

    public void addView(ViewMode view){
        views.add(view);
    }

    public void updatePath(String newPath){
        File file = new File(newPath);
        currentPath = newPath;
        if(!file.isDirectory()){
            selectFile(newPath);
        } else {
            views.forEach(viewMode -> viewMode.changeDirectory(newPath));
        }
    }

    public void changeExtension(String extension){
        currentExtention = extension;
        views.forEach(view -> view.changeExtention(currentExtention));
    }
    public void deleteView(ViewMode viewMode){
        views.remove(viewMode);
    }

    public String getSelectedFilePath(){return  selected;}
    public void  selectFile(String path){
        selected = path;
        appShell.dispose();
    }


    public String getCurrentDir() {
        return currentPath;
    }

    public String getCurrentExtension() {
        return currentExtention;
    }
}
