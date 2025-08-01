package org.sudoku.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sudoku.service.EventEnum.CLEAR_SPACE;

public class NotifierService {

    private final Map<EventEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscribe(final EventEnum eventType, EventListener listener){
        var selectedListener = listeners.get(eventType);
        selectedListener.add(listener);
    }

    public void notify(final EventEnum eventType){
        listeners.get(eventType).forEach(
                l -> l.update(eventType)
        );
    }
}
