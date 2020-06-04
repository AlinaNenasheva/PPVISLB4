package main.java.filechooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Start {
    public static void main(String[] args) {
        FileChooser f = new FileChooser(new Shell(new Display()), SWT.SAVE);
        System.out.println(f.open());
    }
}
