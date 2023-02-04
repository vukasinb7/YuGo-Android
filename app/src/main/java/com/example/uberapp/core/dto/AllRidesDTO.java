package com.example.uberapp.core.dto;

import java.util.List;

public class AllRidesDTO {

    private long totalCount;
    private List<RideDetailedDTO> results;

    public AllRidesDTO(){}

    public AllRidesDTO(long totalCount, List<RideDetailedDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<RideDetailedDTO> getResults() {
        return results;
    }

    public void setResults(List<RideDetailedDTO> results) {
        this.results = results;
    }
}
