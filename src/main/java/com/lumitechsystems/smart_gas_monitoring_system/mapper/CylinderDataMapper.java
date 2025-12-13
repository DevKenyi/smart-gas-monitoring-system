package com.lumitechsystems.smart_gas_monitoring_system.mapper;

import com.lumitechsystems.smart_gas_monitoring_system.dto.CylinderDataDTO;
import com.lumitechsystems.smart_gas_monitoring_system.entity.CylinderData;
import com.lumitechsystems.smart_gas_monitoring_system.util.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class CylinderDataMapper {

    public CylinderData toEntity(CylinderDataDTO dto) {
        if (dto == null) {
            return null;
        }

        return CylinderData.builder()
                .sensorId(dto.getSensorId())
                .cylinderType(
                        dto.getCylinderType() != null ? dto.getCylinderType() : AppConstants.DEFAULT_CYLINDER_TYPE)
                .currentWeight(dto.getCurrentWeight())
                .remainingPercent(dto.getRemainingPercent())
                .remainingKg(dto.getRemainingKg())
                .levelStatus(dto.getLevelStatus())
                .fullWeight(dto.getFullWeight() != null ? dto.getFullWeight() : AppConstants.DEFAULT_FULL_WEIGHT)
                .emptyWeight(dto.getEmptyWeight() != null ? dto.getEmptyWeight() : AppConstants.DEFAULT_EMPTY_WEIGHT)
                .gasWeight(dto.getGasWeight() != null ? dto.getGasWeight() : AppConstants.DEFAULT_GAS_WEIGHT)
                .potentiometerRaw(dto.getPotentiometerRaw())
                .build();
    }
}
