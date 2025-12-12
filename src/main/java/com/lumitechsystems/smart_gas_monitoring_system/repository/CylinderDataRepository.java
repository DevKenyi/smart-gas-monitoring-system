package com.lumitechsystems.smart_gas_monitoring_system.repository;
import com.lumitechsystems.smart_gas_monitoring_system.entity.CylinderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CylinderDataRepository extends JpaRepository<CylinderData, Long> {

    // Find latest reading for each sensor
    @Query(value = "SELECT cd1.* FROM cylinder_data cd1 " +
            "INNER JOIN (SELECT sensor_id, MAX(timestamp) as max_timestamp " +
            "FROM cylinder_data GROUP BY sensor_id) cd2 " +
            "ON cd1.sensor_id = cd2.sensor_id AND cd1.timestamp = cd2.max_timestamp",
            nativeQuery = true)
    List<CylinderData> findLatestReadings();

    // Find by sensor ID ordered by timestamp
    List<CylinderData> findBySensorIdOrderByTimestampDesc(String sensorId);

    // Find recent data (last 24 hours)
    @Query("SELECT c FROM CylinderData c WHERE c.timestamp >= :startTime ORDER BY c.timestamp DESC")
    List<CylinderData> findRecentData(@Param("startTime") LocalDateTime startTime);

    // Find data for specific sensor in time range
    @Query("SELECT c FROM CylinderData c WHERE c.sensorId = :sensorId AND c.timestamp BETWEEN :startTime AND :endTime ORDER BY c.timestamp")
    List<CylinderData> findBySensorIdAndTimestampBetween(
            @Param("sensorId") String sensorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // Find critical level cylinders (below 20%)
    @Query("SELECT c FROM CylinderData c WHERE c.remainingPercent < 20.0 AND c.timestamp IN " +
            "(SELECT MAX(c2.timestamp) FROM CylinderData c2 GROUP BY c2.sensorId)")
    List<CylinderData> findCriticalCylinders();

    // Get latest reading for a specific sensor
    @Query(value = "SELECT * FROM cylinder_data WHERE sensor_id = :sensorId ORDER BY timestamp DESC LIMIT 1",
            nativeQuery = true)
    CylinderData findLatestBySensorId(@Param("sensorId") String sensorId);

    // Delete old records (older than 30 days)
    @Query("DELETE FROM CylinderData c WHERE c.timestamp < :cutoffTime")
    void deleteOldRecords(@Param("cutoffTime") LocalDateTime cutoffTime);
}