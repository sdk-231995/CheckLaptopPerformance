package com.user.dell;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dell.service.BatteryMonitorService;

@RestController
public class BatteryMonitorController {

    private final BatteryMonitorService batteryMonitorService;

    public BatteryMonitorController(BatteryMonitorService batteryMonitorService) {
        this.batteryMonitorService = batteryMonitorService;
    }

    @GetMapping("/monitor-battery")
    public String startBatteryMonitoring() {
        batteryMonitorService.startMonitoring();
        return "Battery monitoring started for 1 hour.";
    }
}
