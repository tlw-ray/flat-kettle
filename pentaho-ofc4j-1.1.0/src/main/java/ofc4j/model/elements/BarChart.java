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

import java.util.Arrays;
import java.util.List;

import ofc4j.model.metadata.Alias;
import ofc4j.model.metadata.Converter;
import ofc4j.util.BarConverter;


public class BarChart extends Element {   
    private String colour;
    
    public BarChart() {
        this(Style.NORMAL);
    }
    
    public BarChart(Style style) {
        super(style.getStyle());
    }
    
    protected BarChart(String style) {
        super(style);
    }
    
    public BarChart addValues(Number... values) {
        getValues().addAll(Arrays.asList(values));
        return this;
    }
    
    public BarChart addValues(List<Number> values) {
        getValues().addAll(values);
        return this;
    }
    
    public BarChart addBars(Bar... bars) {
        getValues().addAll(Arrays.asList(bars));
        return this;
    }
    
    public BarChart addBars(List<Bar> bars) {
        getValues().addAll(bars);
        return this;
    }
    
    public String getColour() {
        return colour;
    }

    public BarChart setColour(String colour) {
        this.colour = colour;
        return this;
    }
    
    public static enum Style {
        NORMAL("bar"),
        THREED("bar_3d"),
        GLASS("bar_glass");
        
        private String style;
        
        Style(String style) {
            this.style = style;
        }
        
        public String getStyle() {
            return style;
        }
    }
    
    @Converter(BarConverter.class)
    public static class Bar {
        private Number top;
        private Number bottom;
        private String colour;
        @Alias("on-click") private String onClick;
        @Alias("tip") private String tooltip;
        
        public Bar(Number top, Number bottom, String colour) {
            setTop(top);
            setBottom(bottom);
            setColour(colour);
        }
        
        public Bar(Number top, Number bottom) {
            this(top, bottom, null);
        }
        
        public Bar(Number top, String colour) {
            this(top, null, colour);
        }
        
        public Bar(Number top) {
            this(top, null, null);
        }
        
        public Number getTop() {
            return top;
        }
        public Bar setTop(Number top) {
            this.top = top;
            return this;
        }
        public Number getBottom() {
            return bottom;
        }
        public Bar setBottom(Number bottom) {
            this.bottom = bottom;
            return this;
        }
        public String getColour() {
            return colour;
        }
        public Bar setColour(String colour) {
            this.colour = colour;
            return this;
        }
        public String getTooltip() {
            return tooltip;
        }
        public Bar setTooltip(String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public String getOnClick() {
          return onClick;
        }

        public void setOnClick(String onClick) {
          this.onClick = onClick;
        }
    }
}
