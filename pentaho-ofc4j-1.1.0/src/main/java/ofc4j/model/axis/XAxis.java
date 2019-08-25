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

import java.util.List;

import ofc4j.model.metadata.Alias;

public class XAxis extends Axis {
    @Alias("tick-height") private Integer tick_height;
    private XAxisLabels labels = new XAxisLabels();
    
    public XAxis setTickHeight(Integer tick_height) {
        this.tick_height = tick_height;
        return this;
    }
    
    public Integer getTickHeight() {
        return tick_height;
    }
    
    public XAxisLabels getLabels() {
        return labels;
    }
    
    public XAxis setXAxisLabels(XAxisLabels labels) {
        this.labels = labels;
        return this;
    }
    
    public XAxis setLabels(String... labels) {
        this.labels = new XAxisLabels(labels);
        return this;
    }

    public XAxis setLabels(List<String> labels) {
        this.labels = new XAxisLabels(labels);
        return this;
    }
    
    public XAxis addLabels(String... labels) {
        this.labels.addLabels(labels);
        return this;
    }
    
    public XAxis addLabels(Label... labels) {
        this.labels.addLabels(labels);
        return this;
    }
    
    public XAxis addLabels(List<Label> labels) {
        this.labels.addLabels(labels);
        return this;
    }
}
