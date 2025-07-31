package org.sudoku.model;

import lombok.Getter;

@Getter
public enum GameStatusEnum {
    NON_STARTED("não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private final String label;
    GameStatusEnum(final String label){
        this.label = label;
    }
}
