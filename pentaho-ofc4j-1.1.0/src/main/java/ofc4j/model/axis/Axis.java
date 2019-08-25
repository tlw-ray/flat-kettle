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

package ofc4j.model.axis;

import ofc4j.model.metadata.Alias;

public abstract class Axis {
    private Integer stroke;
    private String colour;
    @Alias("grid-colour") private String grid_colour;
    private Integer steps;
    private Integer offset;
    @Alias("3d") private Integer threed;
    private Integer min;
    private Integer max;
    
    public Integer getStroke() {
        return stroke;
    }
    public Axis setStroke(Integer stroke) {
        this.stroke = stroke;
        return this;
    }
    public String getColour() {
        return colour;
    }
    public Axis setColour(String colour) {
        this.colour = colour;
        return this;
    }
    public String getGridColour() {
        return grid_colour;
    }
    public Axis setGridColour(String grid_colour) {
        this.grid_colour = grid_colour;
        return this;
    }
    public Integer getSteps() {
        return steps;
    }
    public Axis setSteps(Integer steps) {
        this.steps = steps;
        return this;
    }
    public Integer getOffset() {
        return offset;
    }
    public Axis setOffset(Boolean offset) {
        if (offset == null) this.offset = null;
        this.offset = offset ? 1 : 0;
        return this;
    }
    public Integer get3D() {
        return threed;
    }
    public Axis set3D(Integer threed) {
        this.threed = threed;
        return this;
    }
    public Integer getMin() {
        return min;
    }
    public Axis setMin(Integer min) {
        this.min = min;
        return this;
    }
    public Integer getMax() {
        return max;
    }
    public Axis setMax(Integer max) {
        this.max = max;
        return this;
    }
    
    public Axis setRange(Integer min, Integer max, Integer step) {
        setMin(min);
        setMax(max);
        setSteps(step);
        return this;
    }
    
    public Axis setRange(Integer min, Integer max) {
        return setRange(min, max, getSteps());
    }
}
