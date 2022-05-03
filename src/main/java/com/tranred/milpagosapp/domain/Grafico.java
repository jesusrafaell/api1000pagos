package com.tranred.milpagosapp.domain;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Clase que representa un tipo de grafico
 *
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class Grafico {
    
    public JFreeChart crearGrafico(PieDataset pdset, String titulo){
        JFreeChart grafico = ChartFactory.createPieChart3D(titulo, pdset,true,true,false);
        PiePlot3D plot = (PiePlot3D) grafico.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);        
        
        return grafico;
    }
}
