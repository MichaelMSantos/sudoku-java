package org.sudoku.ui.custom.screen;

import org.sudoku.model.Space;
import org.sudoku.service.BoardService;
import org.sudoku.service.NotifierService;
import org.sudoku.ui.custom.button.CheckGameStatusButton;
import org.sudoku.ui.custom.button.FinishGameButton;
import org.sudoku.ui.custom.button.ResetButton;
import org.sudoku.ui.custom.frame.MainFrame;
import org.sudoku.ui.custom.input.NumberText;
import org.sudoku.ui.custom.panel.MainPanel;
import org.sudoku.ui.custom.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.*;
import static org.sudoku.service.EventEnum.CLEAR_SPACE;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int r = 0; r < 9; r += 3) {
            for (int c = 0; c < 9; c += 3) {
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, c + 3, r, r + 3);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
                                            final int initCol,
                                            final int endCol,
                                            final int initRow,
                                            final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for (int r = initRow; r < endRow; r++) {
            for (int c = initCol; c < endCol; c++) {
                spaceSector.add(spaces.get(r).get(c));
            }
        }
        return spaceSector;
    }


    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = spaces.stream()
                .map(NumberText::new)
                .collect(Collectors.toList());

        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }


    private void addFinishGameButton(JPanel mainPanel) {
         finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null,
                        "Parabéns você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                var message ="Seu jogo tem alguma inconsistencia, ajuste e tente novamente";
                JOptionPane.showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
         checkGameStatusButton = new CheckGameStatusButton(e -> {
             var hasErrors = boardService.hasErrors();
             var gameStatus = boardService.getStatus();
             var message = switch (gameStatus) {
                 case NON_STARTED -> "O jogo não foi iniciado";
                 case INCOMPLETE -> "O jogo está incompleto";
                 case COMPLETE -> "O jogo está completo";
             };
             message += hasErrors ? " e contém erros" : " e não contém erros";
             showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
         resetButton = new ResetButton(e -> {
            var dialogResult = showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);

    }

}
