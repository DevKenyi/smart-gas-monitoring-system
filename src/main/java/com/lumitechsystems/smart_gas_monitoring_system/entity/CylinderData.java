package com.lumitechsystems.smart_gas_monitoring_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cylinder_data")
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
    private LocalDateTime timestamp;

    // Default constructor
    public CylinderData() {
        this.timestamp = LocalDateTime.now();
    }

    // Full constructor
    public CylinderData(String sensorId, String cylinderType, Double currentWeight,
                        Double remainingPercent, Double remainingKg, String levelStatus,
                        Double fullWeight, Double emptyWeight, Double gasWeight,
                        Integer potentiometerRaw) {
        this();
        this.sensorId = sensorId;
        this.cylinderType = cylinderType;
        this.currentWeight = currentWeight;
        this.remainingPercent = remainingPercent;
        this.remainingKg = remainingKg;
        this.levelStatus = levelStatus;
        this.fullWeight = fullWeight;
        this.emptyWeight = emptyWeight;
        this.gasWeight = gasWeight;
        this.potentiometerRaw = potentiometerRaw;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getCylinderType() { return cylinderType; }
    public void setCylinderType(String cylinderType) { this.cylinderType = cylinderType; }

    public Double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(Double currentWeight) { this.currentWeight = currentWeight; }

    public Double getRemainingPercent() { return remainingPercent; }
    public void setRemainingPercent(Double remainingPercent) { this.remainingPercent = remainingPercent; }

    public Double getRemainingKg() { return remainingKg; }
    public void setRemainingKg(Double remainingKg) { this.remainingKg = remainingKg; }

    public String getLevelStatus() { return levelStatus; }
    public void setLevelStatus(String levelStatus) { this.levelStatus = levelStatus; }

    public Double getFullWeight() { return fullWeight; }
    public void setFullWeight(Double fullWeight) { this.fullWeight = fullWeight; }

    public Double getEmptyWeight() { return emptyWeight; }
    public void setEmptyWeight(Double emptyWeight) { this.emptyWeight = emptyWeight; }

    public Double getGasWeight() { return gasWeight; }
    public void setGasWeight(Double gasWeight) { this.gasWeight = gasWeight; }

    public Integer getPotentiometerRaw() { return potentiometerRaw; }
    public void setPotentiometerRaw(Integer potentiometerRaw) { this.potentiometerRaw = potentiometerRaw; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "CylinderData{" +
                "id=" + id +
                ", sensorId='" + sensorId + '\'' +
                ", currentWeight=" + currentWeight +
                ", remainingPercent=" + remainingPercent +
                ", levelStatus='" + levelStatus + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
