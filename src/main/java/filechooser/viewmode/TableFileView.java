package main.java.filechooser.viewmode;

import main.java.filechooser.Controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableFileView implements ViewMode {

    private Composite parent;
    private Table table;
    private Controller controller;
    private String currentPath;
    private String currentExtension;



    public TableFileView(Composite composite, Controller controller){
        this.controller = controller;
        this.currentPath = controller.getCurrentDir();
        this.currentExtension = controller.getCurrentExtension();
        this.parent = composite;
        initTable();
    }

    private void initTable() {
        table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        String[] titles = {"Имя файла", "Расширение", "Последнее изменение", "Размер"};
        table.setHeaderVisible(true);
//        table.setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
        table.setLinesVisible(true);
        table.setVisible(true);
        for(String title: titles) {
            TableColumn tableColumn = new TableColumn(table, SWT.V_SCROLL);
            tableColumn.setText(title);
        }

        table.getColumn(0).setWidth(400);
        table.getColumn(1).setWidth(200);
        table.getColumn(2).setWidth(200);
        table.getColumn(3).setWidth(200);

        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String newPath = (String)e.item.getData();
                controller.updatePath(newPath);
            }
        });
        updateTable();

    }

    private String[] fileDescription(File file) {
        String name;
        String extention;
        String date;
        String weight ="0";
        if( file.isDirectory()){
            name = file.getName();
            extention = "Папка с файлами";
            weight ="";
        } else {
            String[] nameAndExt = divideName(file.getName());
            name = nameAndExt[0];
            extention = nameAndExt[1];
            weight= computeWeight(file);
        }
        date = getFormattedMoificationDate(file);

        String[] result = {name, extention, date, weight};

        return result;
    }

    private String computeWeight(File file) {
        Long weight = file.length();
        String result;
        if (weight <= 1024 ){
            result = weight.toString() + "б";
        } else if((weight /= 1024) <=1024 ){
            result = weight.toString() + "Кб";
        } else if((weight /= 1024) <= 1024){
            result = weight.toString() + "Мб";
        } else {
            weight /= 1024;
            result = weight.toString() + "Гб";
        }
        return result;
    }

    private String[] divideName(String name) {
        int lastPointI = name.lastIndexOf('.');
        String[] result;
        if(lastPointI > 0) {
            result = new String[]{name.substring(0, lastPointI), name.substring(lastPointI + 1)};
        } else {
            result = new String[]{name, ""};
        }
        return result;
    }

    private String getFormattedMoificationDate(File file) {
        Date date = new Date(file.lastModified());
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date);
    }

    public void changeDirectory(String path) {
        currentPath = path;
        updateTable();
    }

    @Override
    public void changeExtention(String filter) {

    }

    private void updateTable() {
        table.removeAll();
        File startFile = new File(currentPath);
        File parentFile = startFile.getParentFile();
        if(parentFile != null){
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText("↑");
            item.setData(parentFile.getAbsolutePath());
        }
        File[] files = startFile.listFiles();
        if(files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if(fileName.endsWith(currentExtension) ||
                        file.isDirectory()) {
                    String[] fileDescr = fileDescription(file);
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(fileDescr);
                    item.setData(file.getAbsolutePath());
                }
            }
        }
        table.pack();
    }

    public Control getControl() {
        return table;

    }
}
