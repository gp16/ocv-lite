/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.api;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author Amr_Ayman
 */
public class autoCompletionComboBox extends JComboBox{
    
    public int caretPos=0;
   public JTextField inputField=null;
  
   public autoCompletionComboBox() {
      setEditor(new BasicComboBoxEditor());
      setEditable(true);
   }
   public void add(final Object element) {
      
      this.add(element);
   }
  
    @Override
   public void setSelectedIndex(int index) {
      super.setSelectedIndex(index);
  
      inputField.setText(getItemAt(index).toString());
      inputField.setSelectionEnd(caretPos + inputField.getText().length());
      inputField.moveCaretPosition(caretPos);
   }
  
    @Override
   public void setEditor(ComboBoxEditor editor) {
      super.setEditor(editor);
      if (editor.getEditorComponent() instanceof JTextField) {
         inputField = (JTextField) editor.getEditorComponent();
  
         inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased( KeyEvent ev ) {
               char key=ev.getKeyChar();
               if (! (Character.isLetterOrDigit(key) || Character.isSpaceChar(key) ))
               {
                   return;
               }
               caretPos=inputField.getCaretPosition();
               String text="";
               try {
                  text=inputField.getText(0, caretPos);
               }
               catch (javax.swing.text.BadLocationException e) {
               }
  
               for (int i=0; i<getItemCount(); i++) {
                  String element = (String) getItemAt(i);
                  if (element.startsWith(text)) {
                     setSelectedIndex(i);
                     return;
                  }
               }
            }
         });
      }
   }
   
    }

