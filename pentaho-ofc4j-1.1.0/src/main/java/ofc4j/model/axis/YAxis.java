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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YAxis extends Axis {
    private Integer tick_length;
    private List<String> labels;
    
    public YAxis setTickLength(Integer tick_length) {
        this.tick_length = tick_length;
        return this;
    }
    
    public Integer getTickLength() {
        return tick_length;
    }
    
    public YAxis setLabels(String... labels) {
        checkLabels();
        this.labels.clear();
        return addLabels(labels);
    }
    
    public YAxis addLabels(String... labels) {
        checkLabels();
        this.labels.addAll(Arrays.asList(labels));
        return this;
    }
    
    public YAxis addLabels(List<String> labels) {
        checkLabels();
        this.labels.addAll(labels);
        return this;
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    private synchronized void checkLabels() {
        if (labels == null) labels = new ArrayList<String>();
    }    
}
