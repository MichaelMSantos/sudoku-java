package org.sudoku.ui.custom.input;

import org.sudoku.model.Space;
import org.sudoku.service.EventEnum;
import org.sudoku.service.EventListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static java.awt.Font.PLAIN;
import static org.sudoku.service.EventEnum.CLEAR_SPACE;

public class NumberText extends JTextField implements EventListener {
    private final Space space;

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed()) {
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSpace();
            }

            private void changeSpace(){
                if (getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }
        });
    }

    @Override
    public void update(EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}
