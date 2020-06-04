package main.java.filechooser.viewmode;

import main.java.filechooser.Controller;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class TextView implements ViewMode {
    Text textField;
    Controller controller;

    public TextView(Composite composite, Controller controller) {
        this.textField = new Text(composite, SWT.READ_ONLY | SWT.SINGLE);
        this.textField.setText(controller.getCurrentDir());
        this.textField.setEditable(true);
        this.controller = controller;
        textField.setVisible(true);
    }

    @Override
    public void changeDirectory(String path) {
        textField.setText(path);
    }

    @Override
    public void changeExtention(String filter) {

    }

    public void setLayoutData(GridData openDirTextGridData) {
        textField.setLayoutData(openDirTextGridData);
    }

}
