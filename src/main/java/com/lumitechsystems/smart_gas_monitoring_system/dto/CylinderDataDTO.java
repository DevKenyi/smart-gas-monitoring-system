package com.lumitechsystems.smart_gas_monitoring_system.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CylinderDataDTO {

    @NotBlank(message = "Sensor ID is required")
    private String sensorId;

    private String cylinderType;

    @NotNull(message = "Current weight is required")
    @PositiveOrZero(message = "Weight must be positive")
    private Double currentWeight;

    @NotNull(message = "Remaining percent is required")
    @Min(value = 0, message = "Percentage cannot be less than 0")
    @Max(value = 100, message = "Percentage cannot be more than 100")
    private Double remainingPercent;

    @NotNull(message = "Remaining Kg is required")
    @PositiveOrZero
    private Double remainingKg;

    @NotBlank(message = "Level status is required")
    private String levelStatus;

    @Positive(message = "Full weight must be positive")
    private Double fullWeight;

    @Positive(message = "Empty weight must be positive")
    private Double emptyWeight;

    @Positive(message = "Gas weight must be positive")
    private Double gasWeight;

    private Integer potentiometerRaw;

    private Long timestamp;
}