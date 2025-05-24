package com.user.dell;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class DellSystemInfoController {
	
	//http://localhost:8080/swagger-ui/index.html#/
	// java -jar UserService-0.0.1-SNAPSHOT.jar  --PORT=8081

    @Operation(summary = "Get Battery Info", description = "Fetch the current battery status and estimated charge percentage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved battery info"),
        @ApiResponse(responseCode = "500", description = "Failed to fetch battery info")
    })
    @GetMapping("/battery-info")
    public Map<String, Object> getBatteryInfo() {
        Map<String, Object> response = new HashMap<>();
        try {
            String command = "wmic path win32_battery get batterystatus,estimatedchargeremaining";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] batteryInfo = line.split("\\s+");
                response.put("BatteryStatus", batteryInfo[0]);
                response.put("EstimatedCharge", batteryInfo[1] + "%");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Error", "Failed to fetch battery info");
        }
        return response;
    }

    @Operation(summary = "Get System Uptime", description = "Fetch the system's uptime since the last boot")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved uptime"),
        @ApiResponse(responseCode = "500", description = "Failed to fetch uptime")
    })
    @GetMapping("/system-uptime")
    public Map<String, Object> getSystemUptime() {
        Map<String, Object> response = new HashMap<>();
        try {
            String command = "net stats srv";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Statistics since")) {
                    response.put("Uptime", line.split("since")[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Error", "Failed to fetch uptime");
        }
        return response;
    }

    @Operation(summary = "Get Disk Info", description = "Fetch the disk model and size")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved disk info"),
        @ApiResponse(responseCode = "500", description = "Failed to fetch disk info")
    })
    @GetMapping("/disk-info")
    public Map<String, Object> getDiskInfo() {
        Map<String, Object> response = new HashMap<>();
        try {
            String command = "wmic diskdrive get model, size";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] diskInfo = line.split("\\s+");
                response.put("DiskModel", diskInfo[0]);
                response.put("DiskSize", diskInfo[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Error", "Failed to fetch disk info");
        }
        return response;
    }

    @Operation(summary = "Get CPU Info", description = "Fetch the system's CPU model")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved CPU info"),
        @ApiResponse(responseCode = "500", description = "Failed to fetch CPU info")
    })
    @GetMapping("/cpu-info")
    public Map<String, Object> getCpuInfo() {
        Map<String, Object> response = new HashMap<>();
        try {
            String command = "wmic cpu get caption";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                response.put("CPU", line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Error", "Failed to fetch CPU info");
        }
        return response;
    }

    @Operation(summary = "Get BIOS Info", description = "Fetch the BIOS version and release date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved BIOS info"),
        @ApiResponse(responseCode = "500", description = "Failed to fetch BIOS info")
    })
    @GetMapping("/bios-info")
    public Map<String, Object> getBiosInfo() {
        Map<String, Object> response = new HashMap<>();
        try {
            String command = "wmic bios get version, releaseDate";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] biosInfo = line.split("\\s+");
                response.put("BiosVersion", biosInfo[0]);
                response.put("ReleaseDate", biosInfo[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("Error", "Failed to fetch BIOS info");
        }
        return response;
    }
    @GetMapping("/bios-info2")
    public Map<String, String> getBiosInfo2() {
        Map<String, String> biosInfo = new HashMap<>();
        
        String biosVersion = "20241106000000.000000+000";

        String formattedBiosVersion = formatBiosVersion(biosVersion);
        
        biosInfo.put("BiosVersion", formattedBiosVersion);
        
        return biosInfo;
    }

    private String formatBiosVersion(String biosVersion) {
        String dateTimePart = biosVersion.substring(0, 14);
        
        String formattedDateTime = dateTimePart.substring(0, 4) + "-" +
                                    dateTimePart.substring(4, 6) + "-" +
                                    dateTimePart.substring(6, 8) + " " +
                                    dateTimePart.substring(8, 10) + ":" +
                                    dateTimePart.substring(10, 12) + ":" +
                                    dateTimePart.substring(12, 14);
        
        return formattedDateTime;
    }
}
