/*
This file is part of OFC4J.

OFC4J is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

OFC4J is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ofc4j.util;

import ofc4j.model.elements.BarChart;
import ofc4j.model.elements.FilledBarChart;
import ofc4j.model.elements.SketchBarChart;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public class BarConverter extends ConverterBase<BarChart.Bar> {
    @Override
    public void convert(BarChart.Bar b, PathTrackingWriter writer, MarshallingContext mc) {
       // MAJOR HACK: XStream cannot produce null values in the JSON format that we need. 
       // So we set the value in the null instance to hack-null, then in the OFC engine,
       // on render, we do a String replaceall() on the hack-null references. 
       
       if (b.getTop()==null){
           writeNode(writer, "hack", "hack-null");
           return;
       }
        writeNode(writer, "top", b.getTop());
        writeNode(writer, "bottom", b.getBottom());
        writeNode(writer, "colour", b.getColour());
        writeNode(writer, "on-click", b.getOnClick());
        writeNode(writer, "tip", b.getTooltip());
        if (b instanceof FilledBarChart.Bar) {
            writeNode(writer, "outline-colour", ((FilledBarChart.Bar)b).getOutlineColour());
        }
        if (b instanceof SketchBarChart.Bar) {
            writeNode(writer, "offset", ((SketchBarChart.Bar)b).getFunFactor());
        }
    }
    
    // @Override
    @SuppressWarnings("unchecked")
    public boolean canConvert(Class clazz) {
        return BarChart.Bar.class.isAssignableFrom(clazz);
    }
}
