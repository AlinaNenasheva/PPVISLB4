package main.java.filechooser;

import main.java.filechooser.viewmode.DirFileView;
import main.java.filechooser.viewmode.ListFileView;
import main.java.filechooser.viewmode.TableFileView;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;

public class FileArea {
    TabFolder tabFolder;

    public FileArea(Shell shell, main.java.filechooser.Controller controller){

        tabFolder = new TabFolder(shell, SWT.BORDER | SWT.V_SCROLL);
        TabItem tabViewItem = new TabItem(tabFolder, SWT.V_SCROLL);
        tabViewItem.setText("Таблица");
        TableFileView tableViewMode = new TableFileView(tabFolder,controller);
        controller.addView(tableViewMode);
        tabViewItem.setControl(tableViewMode.getControl());

        TabItem listViewItem = new TabItem(tabFolder, SWT.V_SCROLL);
        listViewItem.setText("Список");
        ListFileView listViewMode = new ListFileView(tabFolder, controller);
        controller.addView(listViewMode);
        listViewItem.setControl(listViewMode.getControl());

        TabItem dirViewItem = new TabItem(tabFolder, SWT.V_SCROLL);
        dirViewItem.setText("Папки");
        DirFileView dirViewMode = new DirFileView(tabFolder, controller);
        controller.addView(dirViewMode);
        dirViewItem.setControl(dirViewMode.getControl());

        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int selectedIndex = tabFolder.getSelectionIndex();
                TabItem selected = tabFolder.getItem(selectedIndex);

            }
        });
        tabFolder.pack();
    }

    public void setLayoutData(GridData gridData) {
        tabFolder.setLayoutData(gridData);
    }
}
