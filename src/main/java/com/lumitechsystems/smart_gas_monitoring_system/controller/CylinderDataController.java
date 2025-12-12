package com.lumitechsystems.smart_gas_monitoring_system.controller;

import com.lumitechsystems.smart_gas_monitoring_system.dto.ApiResponse;
import com.lumitechsystems.smart_gas_monitoring_system.dto.CylinderDataDTO;
import com.lumitechsystems.smart_gas_monitoring_system.entity.CylinderData;
import com.lumitechsystems.smart_gas_monitoring_system.service.CylinderDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cylinder-data")
@CrossOrigin(origins = "*")
public class CylinderDataController {

    private static final Logger logger = LoggerFactory.getLogger(CylinderDataController.class);

    @Autowired
    private CylinderDataService cylinderDataService;

    @PostMapping
    public ResponseEntity<ApiResponse> receiveCylinderData(@RequestBody CylinderDataDTO dataDTO) {
        try {
            logger.info("Received cylinder data from sensor: {}", dataDTO.getSensorId());

            // Convert DTO to Entity
            CylinderData cylinderData = new CylinderData(
                    dataDTO.getSensorId(),
                    dataDTO.getCylinderType() != null ? dataDTO.getCylinderType() : "LPG_50kg",
                    dataDTO.getCurrentWeight(),
                    dataDTO.getRemainingPercent(),
                    dataDTO.getRemainingKg(),
                    dataDTO.getLevelStatus(),
                    dataDTO.getFullWeight() != null ? dataDTO.getFullWeight() : 50.0,
                    dataDTO.getEmptyWeight() != null ? dataDTO.getEmptyWeight() : 10.0,
                    dataDTO.getGasWeight() != null ? dataDTO.getGasWeight() : 40.0,
                    dataDTO.getPotentiometerRaw()
            );

            CylinderData savedData = cylinderDataService.saveReading(cylinderData);

            // Log critical levels
            if (savedData.getRemainingPercent() < 20.0) {
                logger.warn("🚨 CRITICAL: Cylinder {} is at {}% - Level: {}",
                        savedData.getSensorId(),
                        savedData.getRemainingPercent(),
                        savedData.getLevelStatus());
            }

            return ResponseEntity.ok(ApiResponse.success(
                    "Data received successfully",
                    savedData
            ));

        } catch (Exception e) {
            logger.error("Error processing cylinder data", e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Error processing data: " + e.getMessage())
            );
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllReadings() {
        try {
            List<CylinderData> readings = cylinderDataService.getAllReadings();
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved all readings",
                    readings
            ));
        } catch (Exception e) {
            logger.error("Error retrieving all readings", e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving data")
            );
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse> getLatestReadings() {
        try {
            List<CylinderData> latestReadings = cylinderDataService.getLatestReadings();
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved latest readings",
                    latestReadings
            ));
        } catch (Exception e) {
            logger.error("Error retrieving latest readings", e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving latest data")
            );
        }
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<ApiResponse> getReadingsBySensor(@PathVariable String sensorId) {
        try {
            List<CylinderData> readings = cylinderDataService.getReadingsBySensorId(sensorId);
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved readings for sensor: " + sensorId,
                    readings
            ));
        } catch (Exception e) {
            logger.error("Error retrieving readings for sensor: {}", sensorId, e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving sensor data")
            );
        }
    }

    @GetMapping("/sensor/{sensorId}/latest")
    public ResponseEntity<ApiResponse> getLatestBySensor(@PathVariable String sensorId) {
        try {
            CylinderData latest = cylinderDataService.getLatestReadingBySensorId(sensorId);
            if (latest != null) {
                return ResponseEntity.ok(ApiResponse.success(
                        "Retrieved latest reading for sensor: " + sensorId,
                        latest
                ));
            } else {
                return ResponseEntity.ok(ApiResponse.success(
                        "No data found for sensor: " + sensorId,
                        null
                ));
            }
        } catch (Exception e) {
            logger.error("Error retrieving latest reading for sensor: {}", sensorId, e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving latest sensor data")
            );
        }
    }

    @GetMapping("/critical")
    public ResponseEntity<ApiResponse> getCriticalCylinders() {
        try {
            List<CylinderData> criticalCylinders = cylinderDataService.getCriticalCylinders();
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved critical cylinders",
                    criticalCylinders
            ));
        } catch (Exception e) {
            logger.error("Error retrieving critical cylinders", e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving critical cylinders")
            );
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getRecentData() {
        try {
            List<CylinderData> recentData = cylinderDataService.getRecentData();
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved recent data",
                    recentData
            ));
        } catch (Exception e) {
            logger.error("Error retrieving recent data", e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving recent data")
            );
        }
    }

    @GetMapping("/sensor/{sensorId}/range")
    public ResponseEntity<ApiResponse> getDataInRange(
            @PathVariable String sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        try {
            List<CylinderData> dataInRange = cylinderDataService.getSensorDataInRange(sensorId, start, end);
            return ResponseEntity.ok(ApiResponse.success(
                    "Retrieved data for sensor " + sensorId + " in specified range",
                    dataInRange
            ));
        } catch (Exception e) {
            logger.error("Error retrieving data in range for sensor: {}", sensorId, e);
            return ResponseEntity.internalServerError().body(
                    ApiResponse.error("Error retrieving data in range")
            );
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Cylinder Monitor API is running"));
    }
}