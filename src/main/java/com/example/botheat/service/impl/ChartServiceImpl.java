package com.example.botheat.service.impl;

import com.example.botheat.entity.HeatData;
import com.example.botheat.service.ChartService;
import com.example.botheat.service.HeatDataService;
import com.example.botheat.util.PageModel2;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChartServiceImpl implements ChartService {
    @Autowired
    private HeatDataService heatDataService;

    public static void main(String[] args) {
        ChartServiceImpl chartService = new ChartServiceImpl();
		chartService.heatDataService = (HeatDataService) new ClassPathXmlApplicationContext( "spring.xml" ).getBean( "heatDataService" );
		ChartFrame frame = new ChartFrame("日趋势曲线", chartService.createChart("瑞达",new Date(110,1,3,0,0,0),new Date(110,1,3,23,59,59)));
		frame.pack();
        frame.setVisible( true );
    }

    @Override
    public JFreeChart createChart(String custName, Date startDate, Date endDate) {

		JFreeChart localJFreeChart = ChartFactory.createTimeSeriesChart(custName+"日趋势曲线", "时间", "流量", null, true, true, false);
		XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
		localXYPlot.setBackgroundPaint(Color.BLACK);

		Font font = new Font("宋体", Font.PLAIN + Font.BOLD, 12);
		localJFreeChart.getTitle().setFont(font); // 标题
		localJFreeChart.getLegend().setItemFont(font); // 状态栏
		localXYPlot.getDomainAxis().setLabelFont(font); // X轴标题
		localXYPlot.getRangeAxis().setLabelFont(font); // Y轴标题

		localXYPlot.setRangeAxis(1, new NumberAxis("压力"));
		localXYPlot.setRangeAxis(2, new NumberAxis("温度"));

        XYDataset[] arrayOfXYDataset = createDataset( custName, startDate, endDate );

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                localXYPlot.setDataset( arrayOfXYDataset[i] );
            } else {
                localXYPlot.setDataset( i, arrayOfXYDataset[i] );
                localXYPlot.mapDatasetToRangeAxis( i, i );
                localXYPlot.setRenderer( i, new XYLineAndShapeRenderer( true, false ) );
            }
        }


        for (int i = 0; i < 3; i++) {
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) localXYPlot.getRenderer( i );
            xylineandshaperenderer.setSeriesStroke( 0, new BasicStroke( 1.5f ) );
        }

        return localJFreeChart;
    }

    private XYDataset[] createDataset(String custName, Date startDate, Date endDate) {
        TimeSeries[] series = new TimeSeries[3];
        TimeSeriesCollection[] datasets = new TimeSeriesCollection[3];
		series[0] = new TimeSeries("流量");
		series[1] = new TimeSeries("压力");
		series[2] = new TimeSeries("温度");

        RegularTimePeriod period;
        PageModel2<HeatData> allHeatData = heatDataService.findAllHeatData( custName, startDate, endDate, 1, Integer.MAX_VALUE );
        if (allHeatData == null) {
            return datasets;
        }
        List<HeatData> list = allHeatData.getList();
        for (HeatData heatData : list) {
            period = new Minute( new Date( heatData.getAcquireTime() ) );
            series[0].add( period, heatData.getFlow() );
            series[1].add( period, heatData.getPressure() );
            series[2].add( period, heatData.getTemperature() );
        }


        for (int i = 0; i < 3; i++) {
            datasets[i] = new TimeSeriesCollection();
            datasets[i].addSeries( series[i] );
        }
        return datasets;
    }

    public HeatDataService getHeatDataService() {
        return heatDataService;
    }

    public void setHeatDataService(HeatDataService heatDataService) {
        this.heatDataService = heatDataService;
    }
}
