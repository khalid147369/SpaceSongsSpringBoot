package com.example.spctn.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spctn.Dto.Response.DashboardMetricsDTO;
import com.example.spctn.Service.Impl.MetricServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/counts")
@RequiredArgsConstructor
public class MetricController {


	private final MetricServiceImpl metricServiceImpl;

    @GetMapping("/getAllCounts")
    public ResponseEntity<DashboardMetricsDTO> getAllCounts() {
        return ResponseEntity.ok(metricServiceImpl.getDashboardMetrics());
    }
	
}
