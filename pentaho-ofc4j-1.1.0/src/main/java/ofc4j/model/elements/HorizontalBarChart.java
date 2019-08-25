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
import ofc4j.util.HorizontalBarChartBarConverter;


public class HorizontalBarChart extends Element {
    private String colour;
    
    public HorizontalBarChart() {
        super("hbar");
    }
    
    public String getColour() {
        return colour;
    }
    
    public HorizontalBarChart setColour(String colour) {
        this.colour = colour;
        return this;
    }
    
    public HorizontalBarChart addBars(Bar... values) {
        getValues().addAll(Arrays.asList(values));
        return this;
    }
    
    public HorizontalBarChart addBars(List<Bar> values) {
        getValues().addAll(values);
        return this;
    }
        
    public HorizontalBarChart addValues(Number... rightValues) {
        Bar[] values = new Bar[rightValues.length];
        for (int i = 0; i < rightValues.length; ++i) {
            values[i] = new Bar(rightValues[i]);
        }
        return addBars(values);
    }
    
    public HorizontalBarChart addValues(List<Number> rightValues) {
        getValues().addAll(rightValues);
        return this;
    }
    
    public HorizontalBarChart addBar(Number left, Number right) {
        return addBars(new Bar(left, right));
    }
    
    @Converter(HorizontalBarChartBarConverter.class)
    public static class Bar {
        private final Number right;
        private Number left;
        @Alias("on-click") private String onClick;

        
        public Bar(Number right) {
            this(null, right);
        }
        
        public Bar(Number left, Number right) {
            if (right == null) throw new NullPointerException("Field is mandatory.");
            this.right = right;
            setLeft(left);
        }
        
        public Number getRight() {
            return right;
        }
        
        public Number getLeft() {
            return left;
        }
        
        public Bar setLeft(Number left) {
            this.left = left;
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
