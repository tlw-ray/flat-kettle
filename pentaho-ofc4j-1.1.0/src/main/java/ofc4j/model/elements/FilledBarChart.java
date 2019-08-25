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

package ofc4j.model.elements;

import ofc4j.model.metadata.Alias;

public class FilledBarChart extends BarChart {
    private static final transient String TYPE = "bar_filled";
    @Alias("outline-colour") private String outlineColour;
    
    public FilledBarChart() {
        super(TYPE);
    }
    
    public FilledBarChart(String colour, String outlineColour) {
        super(TYPE);
        setColour(colour);
        setOutlineColour(outlineColour);
    }
    
    protected FilledBarChart(String style) {
        super(style);
    }
    
    public String getOutlineColour() {
        return outlineColour;
    }
    
    public BarChart setOutlineColour(String outlineColour) {
        this.outlineColour = outlineColour;
        return this;
    }
    
    public static class Bar extends BarChart.Bar {
        @Alias("outline-colour") private String outlineColour;
        
        public Bar(Number top, Number bottom) {
            super(top, bottom);
        }
        
        public Bar(Number top, Number bottom, String colour, String outlineColour) {
            super(top, bottom);
            setColour(colour);
            setOutlineColour(outlineColour);
        }
        
        public Bar(Number top) {
            super(top);
        }
        
        public Bar setOutlineColour(String outlineColour) {
            this.outlineColour = outlineColour;
            return this;
        }
        
        public String getOutlineColour() {
            return outlineColour;
        }
    }
}
