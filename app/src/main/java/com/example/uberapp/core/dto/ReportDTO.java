package com.example.uberapp.core.dto;

import java.time.LocalDate;
import java.util.List;

public class ReportDTO {
    private List<List<Integer>> keys;
    private List<Double> values;
    public ReportDTO(){
    }

    public ReportDTO(List<List<Integer>> keys, List<Double> values) {
        this.keys = keys;
        this.values = values;
    }

    public List<List<Integer>> getKeys() {
        return keys;
    }

    public void setKeys(List<List<Integer>> keys) {
        this.keys = keys;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
