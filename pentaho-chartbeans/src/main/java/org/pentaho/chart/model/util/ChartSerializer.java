/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.model.util;

import org.pentaho.chart.model.ChartDataDefinition;
import org.pentaho.chart.model.ChartLegend;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.Grid;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.TwoAxisPlot;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.PiePlot.PieLabels;
import org.pentaho.chart.model.Plot.Orientation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ChartSerializer {
  public enum ChartSerializationFormat {JSON, XML};
  
  private static XStream jsonChartWriter = new XStream(new JettisonMappedXmlDriver());  
  private static XStream jsonChartDefWriter = new XStream(new JettisonMappedXmlDriver());
  private static XStream xmlChartWriter = new XStream(new DomDriver());  
  private static XStream xmlChartDefWriter = new XStream(new DomDriver());
  
  static{
    jsonChartWriter.setMode(XStream.NO_REFERENCES);
    jsonChartWriter.alias("chartModel", ChartModel.class); //$NON-NLS-1$
    jsonChartWriter.useAttributeFor(CssStyle.class);
    jsonChartWriter.useAttributeFor(Orientation.class);
    jsonChartWriter.useAttributeFor(LinePlotFlavor.class);
    jsonChartWriter.useAttributeFor(BarPlotFlavor.class);
    jsonChartWriter.registerConverter(new CssStylesConverter());
    jsonChartWriter.omitField(ChartLegend.class, "visible"); //$NON-NLS-1$
    jsonChartWriter.registerConverter(new StyledTextConverter());
    jsonChartWriter.registerConverter(new PaletteConverter());
    jsonChartWriter.registerConverter(new ScaleConverter());
    jsonChartWriter.registerConverter(new ChartModelConverter());
    jsonChartWriter.registerConverter(new AxisConverter());
    jsonChartWriter.registerConverter(new ChartTitleConverter());
    jsonChartWriter.registerConverter(new GridConverter());
    jsonChartWriter.useAttributeFor(PiePlot.class, "animate"); //$NON-NLS-1$
    jsonChartWriter.useAttributeFor(DialPlot.class, "animate"); //$NON-NLS-1$
    jsonChartWriter.useAttributeFor(PiePlot.class, "startAngle"); //$NON-NLS-1$
    jsonChartWriter.omitField(PiePlot.class, "slices"); //$NON-NLS-1$
    jsonChartWriter.omitField(PiePlot.class, "labels"); //$NON-NLS-1$
    jsonChartWriter.omitField(PieLabels.class, "visible"); //$NON-NLS-1$
    jsonChartWriter.omitField(TwoAxisPlot.class, "horizontalAxis"); //$NON-NLS-1$  
    jsonChartWriter.omitField(TwoAxisPlot.class, "verticalAxis"); //$NON-NLS-1$
    jsonChartWriter.omitField(TwoAxisPlot.class, "grid"); //$NON-NLS-1$
    
    xmlChartWriter.setMode(XStream.NO_REFERENCES);
    xmlChartWriter.alias("chartModel", ChartModel.class); //$NON-NLS-1$
    xmlChartWriter.useAttributeFor(CssStyle.class);
    xmlChartWriter.useAttributeFor(Orientation.class);
    xmlChartWriter.useAttributeFor(LinePlotFlavor.class);
    xmlChartWriter.useAttributeFor(BarPlotFlavor.class);
    xmlChartWriter.registerConverter(new CssStylesConverter());
    xmlChartWriter.omitField(ChartLegend.class, "visible"); //$NON-NLS-1$
    xmlChartWriter.registerConverter(new StyledTextConverter());
    xmlChartWriter.registerConverter(new PaletteConverter());
    xmlChartWriter.registerConverter(new ScaleConverter());
    xmlChartWriter.registerConverter(new ChartModelConverter());
    xmlChartWriter.registerConverter(new AxisConverter());
    xmlChartWriter.registerConverter(new ChartTitleConverter());
    xmlChartWriter.registerConverter(new GridConverter());
    xmlChartWriter.useAttributeFor(PiePlot.class, "animate"); //$NON-NLS-1$
    xmlChartWriter.useAttributeFor(DialPlot.class, "animate"); //$NON-NLS-1$
    xmlChartWriter.useAttributeFor(PiePlot.class, "startAngle"); //$NON-NLS-1$
    xmlChartWriter.omitField(PiePlot.class, "slices"); //$NON-NLS-1$
    xmlChartWriter.omitField(PiePlot.class, "labels"); //$NON-NLS-1$
    xmlChartWriter.omitField(PieLabels.class, "visible"); //$NON-NLS-1$
    xmlChartWriter.omitField(TwoAxisPlot.class, "horizontalAxis"); //$NON-NLS-1$
    xmlChartWriter.omitField(TwoAxisPlot.class, "verticalAxis"); //$NON-NLS-1$
    xmlChartWriter.omitField(TwoAxisPlot.class, "grid"); //$NON-NLS-1$
    
    jsonChartDefWriter.setMode(XStream.NO_REFERENCES);
    jsonChartDefWriter.alias("chartDataModel", ChartDataDefinition.class); //$NON-NLS-1$
    
    xmlChartDefWriter.setMode(XStream.NO_REFERENCES);
    xmlChartDefWriter.alias("chartDataModel", ChartDataDefinition.class); //$NON-NLS-1$
  }
  
  public static String serialize(ChartModel model, ChartSerializationFormat outputFormat){
    String result = null;
    switch (outputFormat) {
      case JSON:
        result = jsonChartWriter.toXML(model);
        break;
      case XML:
        result = xmlChartWriter.toXML(model);
        break;
    }
    return result;
  }
  
  public static ChartModel deSerialize(String input, ChartSerializationFormat inputFormat){
    ChartModel chartModel = null;
    switch (inputFormat) {
      case JSON:
        chartModel = (ChartModel)jsonChartWriter.fromXML(input);
        break;
      case XML:
        chartModel = (ChartModel)xmlChartWriter.fromXML(input);
        break;
    }
    return chartModel;
  }
  
  
  public static String serializeDataDefinition(ChartDataDefinition def, ChartSerializationFormat outputFormat){
    String result = null;
    switch (outputFormat) {
      case JSON:
        result = jsonChartDefWriter.toXML(def);
        break;
      case XML:
        result = xmlChartDefWriter.toXML(def);
        break;
    }
    return result;
  }
  
  public static ChartDataDefinition deSerializeDataDefinition(String input, ChartSerializationFormat inputFormat){
    ChartDataDefinition chartDataDefinition = null;
    switch (inputFormat) {
      case JSON:
        chartDataDefinition = (ChartDataDefinition)jsonChartDefWriter.fromXML(input);
        break;
      case XML:
        chartDataDefinition = (ChartDataDefinition)xmlChartDefWriter.fromXML(input);
        break;
    }
    return chartDataDefinition;
  }
}
