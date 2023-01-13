package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter @Setter
public class WorkTime {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Driver driver;
    private Integer id;
}
