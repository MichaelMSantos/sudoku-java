package org.sudoku.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Space {
    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Space(final int expected, final boolean fixed){
        this.expected = expected;
        this.fixed = fixed;
        if (fixed){
            actual = expected;
        }
    }

    public void setActual(final Integer actual){
        if (fixed) return;
        this.actual = actual;
    }

    public void clearSpace(){
        setActual(null);
    }
}
