package it.unicam.cs.ids.DOIT.history;

import java.time.LocalDateTime;

public class HistoryUnit implements IHistoryUnit {
    private LocalDateTime date;
    private boolean bool;

    public HistoryUnit(boolean bool){
        this.date = LocalDateTime.now();
        this.bool = bool;
    }

    @Override
    public String toString(){
        return "data :"+date+" entrata/uscita "+bool;
    }

    public boolean getBool(){
        return bool;
    }
}
