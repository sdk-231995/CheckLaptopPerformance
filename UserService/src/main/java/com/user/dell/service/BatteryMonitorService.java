package com.user.dell.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class BatteryMonitorService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> monitoringTask;

    public void startMonitoring() {
        if (monitoringTask != null && !monitoringTask.isDone()) {
            System.out.println("Monitoring already in progress...");
            return;
        }

        Runnable batteryCheckTask = new Runnable() {
            private int runCount = 0;

            @Override
            public void run() {
                runCount++;
                double batteryLevel = getBatteryLevel();
                System.out.println("[" + LocalTime.now() + "] Battery Level: " + batteryLevel + "%");

                if (runCount >= 60) {
                    monitoringTask.cancel(false);
                    System.out.println("Battery monitoring finished.");
                }
            }
        };

        monitoringTask = scheduler.scheduleAtFixedRate(batteryCheckTask, 0, 1, TimeUnit.MINUTES);
    }

    private double getBatteryLevel() {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "WMIC Path Win32_Battery Get EstimatedChargeRemaining");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.readLine(); // skip header
            String levelStr = reader.readLine();
            return levelStr != null ? Double.parseDouble(levelStr.trim()) : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
