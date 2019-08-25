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

public class AreaHollowChart extends LineChart {
    private static transient final Float DEFAULT_ALPHA = 0.35f;
    
    @Alias("fill-alpha") private Float fillAlpha;
    private String fill;
    
    public AreaHollowChart() {
        super("area_hollow");
        setFillAlpha(DEFAULT_ALPHA);
    }

    public Float getFillAlpha() {
        return fillAlpha;
    }

    public AreaHollowChart setFillAlpha(Float fillAlpha) {
        this.fillAlpha = fillAlpha;
        return this;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getFill() {
        return fill;
    }
}
