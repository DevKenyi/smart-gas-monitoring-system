package com.lumitechsystems.smart_gas_monitoring_system.controller;

import com.lumitechsystems.smart_gas_monitoring_system.dto.ApiResponse;
import com.lumitechsystems.smart_gas_monitoring_system.dto.CylinderDataDTO;
import com.lumitechsystems.smart_gas_monitoring_system.entity.CylinderData;
import com.lumitechsystems.smart_gas_monitoring_system.mapper.CylinderDataMapper;
import com.lumitechsystems.smart_gas_monitoring_system.service.CylinderDataService;
import com.lumitechsystems.smart_gas_monitoring_system.util.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cylinder-data")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CylinderDataController {

    private final CylinderDataService cylinderDataService;
    private final CylinderDataMapper cylinderDataMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> receiveCylinderData(@Valid @RequestBody CylinderDataDTO dataDTO) {
        log.info("Received cylinder data from sensor: {}", dataDTO.getSensorId());

        // Convert DTO to Entity using Mapper
        CylinderData cylinderData = cylinderDataMapper.toEntity(dataDTO);

        CylinderData savedData = cylinderDataService.saveReading(cylinderData);

        // Log critical levels
        if (savedData.getRemainingPercent() < AppConstants.CRITICAL_LEVEL_PERCENTAGE) {
            log.warn("🚨 CRITICAL: Cylinder {} is at {}% - Level: {}",
                    savedData.getSensorId(),
                    savedData.getRemainingPercent(),
                    savedData.getLevelStatus());
        }

        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_DATA_RECEIVED,
                savedData));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllReadings() {
        List<CylinderData> readings = cylinderDataService.getAllReadings();
        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_RETRIEVED_ALL,
                readings));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse> getLatestReadings() {
        List<CylinderData> latestReadings = cylinderDataService.getLatestReadings();
        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_RETRIEVED_LATEST,
                latestReadings));
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<ApiResponse> getReadingsBySensor(@PathVariable String sensorId) {
        List<CylinderData> readings = cylinderDataService.getReadingsBySensorId(sensorId);
        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_RETRIEVED_SENSOR + sensorId,
                readings));
    }

    @GetMapping("/sensor/{sensorId}/latest")
    public ResponseEntity<ApiResponse> getLatestBySensor(@PathVariable String sensorId) {
        CylinderData latest = cylinderDataService.getLatestReadingBySensorId(sensorId);
        if (latest != null) {
            return ResponseEntity.ok(ApiResponse.success(
                    AppConstants.SUCCESS_RETRIEVED_LATEST_SENSOR + sensorId,
                    latest));
        } else {
            return ResponseEntity.ok(ApiResponse.success(
                    AppConstants.SUCCESS_NO_DATA_SENSOR + sensorId,
                    null));
        }
    }

    @GetMapping("/critical")
    public ResponseEntity<ApiResponse> getCriticalCylinders() {
        List<CylinderData> criticalCylinders = cylinderDataService.getCriticalCylinders();
        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_RETRIEVED_CRITICAL,
                criticalCylinders));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getRecentData() {
        List<CylinderData> recentData = cylinderDataService.getRecentData();
        return ResponseEntity.ok(ApiResponse.success(
                AppConstants.SUCCESS_RETRIEVED_RECENT,
                recentData));
    }

    @GetMapping("/sensor/{sensorId}/range")
    public ResponseEntity<ApiResponse> getDataInRange(
            @PathVariable String sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<CylinderData> dataInRange = cylinderDataService.getSensorDataInRange(sensorId, start, end);
        return ResponseEntity.ok(ApiResponse.success(
                "Retrieved data for sensor " + sensorId + " in specified range",
                dataInRange));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Cylinder Monitor API is running"));
    }
}