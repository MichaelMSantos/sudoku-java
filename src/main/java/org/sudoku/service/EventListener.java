package org.sudoku.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EventListener {
    void update(final EventEnum eventType);
}
