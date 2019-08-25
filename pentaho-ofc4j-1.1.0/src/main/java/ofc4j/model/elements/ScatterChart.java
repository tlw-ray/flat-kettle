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
import java.util.Collection;

import ofc4j.model.metadata.Alias;
import ofc4j.model.metadata.Converter;
import ofc4j.util.ScatterChartPointConverter;

public class ScatterChart extends Element {
    
    private String colour;
    @Alias("dot-size") private Integer dotSize;
    
    public ScatterChart(String type) {
        super("scatter");
    }
    
    public ScatterChart addPoints(Point... points) {
        getValues().addAll(Arrays.asList(points));
        return this;
    }
    
    public ScatterChart addPoint(Number x, Number y) {
        return addPoints(new Point(x, y));
    }
    
    public ScatterChart addPoints(Collection<Point> points) {
        getValues().addAll(points);
        return this;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getDotSize() {
        return dotSize;
    }

    public void setDotSize(Integer dotSize) {
        this.dotSize = dotSize;
    }

    @Converter(ScatterChartPointConverter.class)
    public static class Point {
        private Number x;
        private Number y;
        
        public Point(Number x, Number y) {
            this.x = x;
            this.y = y;
        }

        public Number getX() {
            return x;
        }

        public void setX(Number x) {
            this.x = x;
        }

        public Number getY() {
            return y;
        }

        public void setY(Number y) {
            this.y = y;
        }
    }
}
