package com.example.botheat.service;

import org.jfree.chart.JFreeChart;

import java.util.Date;

/**
 * @author haya
 */
public interface ChartService {
    JFreeChart createChart(String custName, Date startDate, Date endDate);
}
