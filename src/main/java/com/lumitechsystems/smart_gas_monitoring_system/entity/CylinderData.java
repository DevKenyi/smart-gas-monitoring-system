package com.lumitechsystems.smart_gas_monitoring_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cylinder_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CylinderData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    @Column(name = "cylinder_type")
    private String cylinderType;

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "remaining_percent")
    private Double remainingPercent;

    @Column(name = "remaining_kg")
    private Double remainingKg;

    @Column(name = "level_status")
    private String levelStatus;

    @Column(name = "full_weight")
    private Double fullWeight;

    @Column(name = "empty_weight")
    private Double emptyWeight;

    @Column(name = "gas_weight")
    private Double gasWeight;

    @Column(name = "potentiometer_raw")
    private Integer potentiometerRaw;

    @Column(name = "timestamp", updatable = false)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    }

