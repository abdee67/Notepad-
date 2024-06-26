//package MiniProject_03_Create_Own_NotePad;

import java.awt.*;
// for transferring data between and within applications
import java.awt.datatransfer.Clipboard;//It allows data to be transferred between different parts of an application or between different applications.
import java.awt.datatransfer.DataFlavor;// It is used to specify the type of data being transferred.
import java.awt.datatransfer.StringSelection;// It is often used for transferring text data to and from the clipboard.
import java.awt.datatransfer.Transferable;//: An interface that defines the methods for implementing the transfer of data between sources and targets in a Java application.
import java.awt.event.*;
import java.io.*;

class My_Notepad extends Frame implements ActionListener {

    private TextArea txta_show;
    private Menu file, edit;
    private MenuItem New, Open, Save_as, Exit, Cut, Copy, Paste,Select_all;
    private MenuBar mb = new MenuBar();

    public My_Notepad() {
        file = new Menu("File");
        New = new MenuItem("New");
        Open = new MenuItem("Open");
        Save_as = new MenuItem("Save as");
        Exit = new MenuItem("Exit");
        Cut = new MenuItem("Cut");
        edit = new Menu("Edit");
        Copy = new MenuItem("Copy");
        Paste = new MenuItem("Paste");
        Select_all = new MenuItem("Select All");

        file.add(New);
        file.add(Open);
        file.add(Save_as);
        file.addSeparator();
        file.add(Exit);
        edit.add(Cut);
        edit.add(Copy);
        edit.add(Paste);
        edit.addSeparator();
        edit.add(Select_all);

        mb.add(file);
        mb.add(edit);

        New.addActionListener(this);
        Open.addActionListener(this);
        Save_as.addActionListener(this);
        Exit.addActionListener(this);
        Cut.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);
        Select_all.addActionListener(this);

        setTitle("AB Notpad");
        setSize(400, 400);
        setLocation(100, 100);
        setMenuBar(mb);

        txta_show = new TextArea();
        add(txta_show);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == New) {
            txta_show.setText(" ");
        } else if (ae.getSource() == Open) {
            try {
                FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD);
                fd.setVisible(true);
                String dir = fd.getDirectory();
                String fname = fd.getFile();
                FileInputStream fis = new FileInputStream(dir + fname);
                DataInputStream dis = new DataInputStream(fis);
                String str = " ", msg = " ";
                while ((str = dis.readLine()) != null) {
                    msg = msg + str;
                    msg += "\n";
                }
                txta_show.setText(msg);
                dis.close();
            } catch (Exception e) {
            }
        } else if (ae.getSource() == Save_as) {
            try {
                FileDialog fd = new FileDialog(this, "Save File", FileDialog.SAVE);
                fd.setVisible(true);
                String txt = txta_show.getText();
                String dir = fd.getDirectory();
                String fname = fd.getFile();
                FileOutputStream fos = new FileOutputStream(dir + fname);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeBytes(txt);
                dos.close();
            } catch (Exception e) {
            }
        } else if (ae.getSource() == Cut) {
            cutText();
        } else if (ae.getSource() == Copy) {
            CopyText();
        } else if (ae.getSource() == Paste) {
            PateText();
        } else if (ae.getSource() == Select_all) {
            txta_show.selectAll();
        } else if (ae.getSource() == Exit) {
            System.exit(0);
        }
    }

    private void cutText() {
        String selectedText = txta_show.getSelectedText();
        String newText = txta_show.getText().replace(selectedText, " ");
        txta_show.setText(newText);
    }

    private void CopyText() {
        String selectedText = txta_show.getSelectedText();
        StringSelection stringSelection = new StringSelection(selectedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void PateText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(this);
        try {
            String pastedText = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            int caretPosition = txta_show.getCaretPosition();
            txta_show.insert(pastedText, caretPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class NotePad {

    public static void main(String[] args) {
        new My_Notepad().setVisible(true);
    }
}
