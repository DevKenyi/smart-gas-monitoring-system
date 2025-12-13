package com.lumitechsystems.smart_gas_monitoring_system.util;

public class AppConstants {
    public static final String DEFAULT_CYLINDER_TYPE = "LPG_50kg";
    public static final double DEFAULT_FULL_WEIGHT = 50.0;
    public static final double DEFAULT_EMPTY_WEIGHT = 10.0;
    public static final double DEFAULT_GAS_WEIGHT = 40.0;
    public static final double CRITICAL_LEVEL_PERCENTAGE = 20.0;

    // API Responses
    public static final String SUCCESS_DATA_RECEIVED = "Data received successfully";
    public static final String SUCCESS_RETRIEVED_ALL = "Retrieved all readings";
    public static final String SUCCESS_RETRIEVED_LATEST = "Retrieved latest readings";
    public static final String SUCCESS_RETRIEVED_SENSOR = "Retrieved readings for sensor: ";
    public static final String SUCCESS_RETRIEVED_LATEST_SENSOR = "Retrieved latest reading for sensor: ";
    public static final String SUCCESS_NO_DATA_SENSOR = "No data found for sensor: ";
    public static final String SUCCESS_RETRIEVED_CRITICAL = "Retrieved critical cylinders";
    public static final String SUCCESS_RETRIEVED_RECENT = "Retrieved recent data";

    private AppConstants() {
        // Private constructor to hide the implicit public one
    }
}
