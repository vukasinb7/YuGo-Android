package com.example.uberapp.core.dto;
import com.example.uberapp.core.model.WorkTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class WorkTimeIn {
    private String start;

    public WorkTimeIn(String start) {
        this.start = start;
    }

    public WorkTimeIn(WorkTime workTime){
        this(workTime.getStartTime().toString());
    }
}
