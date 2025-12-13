package com.lumitechsystems.smart_gas_monitoring_system.service;

import com.lumitechsystems.smart_gas_monitoring_system.entity.CylinderData;
import com.lumitechsystems.smart_gas_monitoring_system.repository.CylinderDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CylinderDataService {

    private final CylinderDataRepository cylinderDataRepository;

    public CylinderData saveReading(CylinderData cylinderData) {
        return cylinderDataRepository.save(cylinderData);
    }

    public List<CylinderData> getAllReadings() {
        return cylinderDataRepository.findAll();
    }

    public Optional<CylinderData> getReadingById(Long id) {
        return cylinderDataRepository.findById(id);
    }

    public List<CylinderData> getReadingsBySensorId(String sensorId) {
        return cylinderDataRepository.findBySensorIdOrderByTimestampDesc(sensorId);
    }

    public CylinderData getLatestReadingBySensorId(String sensorId) {
        return cylinderDataRepository.findLatestBySensorId(sensorId);
    }

    public List<CylinderData> getLatestReadings() {
        return cylinderDataRepository.findLatestReadings();
    }

    public List<CylinderData> getRecentData() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        return cylinderDataRepository.findRecentData(oneDayAgo);
    }

    public List<CylinderData> getCriticalCylinders() {
        return cylinderDataRepository.findCriticalCylinders();
    }

    public List<CylinderData> getSensorDataInRange(String sensorId, LocalDateTime start, LocalDateTime end) {
        return cylinderDataRepository.findBySensorIdAndTimestampBetween(sensorId, start, end);
    }

    // Clean up old records every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupOldRecords() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        cylinderDataRepository.deleteOldRecords(thirtyDaysAgo);
        log.info("Cleaned up records older than: {}", thirtyDaysAgo);
    }
}
