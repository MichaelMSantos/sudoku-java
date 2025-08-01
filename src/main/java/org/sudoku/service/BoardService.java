package org.sudoku.service;

import org.sudoku.model.Board;
import org.sudoku.model.GameStatusEnum;
import org.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {
    private final static int BOARD_LIMIT = 9;
    private final Board board;

    public BoardService(final Map<String, String> gameConfig){
        board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces(){
        return board.getSpaces();
    }

    public void reset(){
        board.reset();
    }

    public boolean hasErrors(){
        return board.hasError();
    }

    public GameStatusEnum getStatus(){
        return board.getStatus();
    }

    public boolean gameIsFinished(){
        return board.gameIsFinished();
    }

    private List<List<Space>> initBoard(final Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());

            for (int j = 0; j < BOARD_LIMIT; j++) {
                String key = "%s,%s".formatted(i, j);
                String positionConfig = gameConfig.get(key);

                if (positionConfig != null) {
                    String[] split = positionConfig.split(",");
                    int expected = Integer.parseInt(split[0]);
                    boolean fixed = Boolean.parseBoolean(split[1]);
                    spaces.get(i).add(new Space(expected, fixed));
                } else {
                    spaces.get(i).add(new Space(0, false));
                }
            }
        }
        return spaces;
    }

}
