package com.lumitechsystems.smart_gas_monitoring_system.dto;


public class CylinderDataDTO {
    private String sensorId;
    private String cylinderType;
    private Double currentWeight;
    private Double remainingPercent;
    private Double remainingKg;
    private String levelStatus;
    private Double fullWeight;
    private Double emptyWeight;
    private Double gasWeight;
    private Integer potentiometerRaw;
    private Long timestamp;

    // Default constructor
    public CylinderDataDTO() {}

    // Getters and Setters
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

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}