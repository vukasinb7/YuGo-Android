package org.yugo.backend.YuGo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@NoArgsConstructor
public class NoteOut {
    Integer id;
    String message;
    String date;

    public NoteOut(Integer id, String message, LocalDateTime date){
        this.id = id;
        this.message = message;
        this.date = date.toString();
    }
}
