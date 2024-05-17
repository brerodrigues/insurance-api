package com.audsat.insurance.dto;

import com.audsat.insurance.model.Insurance;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsuranceDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long customerId;
    private Long carId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double quote;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double percentageRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    private Boolean isActive;

    public InsuranceDTO() {

    }

    public InsuranceDTO(Insurance insurance) {
        this.id = insurance.getId();
        this.customerId = insurance.getCustomer().getId();
        this.carId = insurance.getCar().getId();
        this.quote = insurance.getQuote();
        this.percentageRate = insurance.getPercentageRate();
        this.creationDate = insurance.getCreationDate();
        this.updatedAt = insurance.getUpdatedAt();
        this.isActive = insurance.getIsActive();
    }
}

