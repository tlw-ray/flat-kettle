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

import ofc4j.model.metadata.Converter;
import ofc4j.util.RotationConverter;

public class Label {
    private String text;
    private String colour;
    private Integer size;
    private Rotation rotate;
    private Boolean visible;
    
    @Converter(RotationConverter.class)
    public static enum Rotation {
        VERTICAL("vertical"),
        DIAGONAL("diagonal"),
        HORIZONTAL("horizontal");
        
        private final String text;
        Rotation(String text) {
            this.text = text;
        }
        
        @Override
        public String toString() {
            return text;
        }
    }
    
    public Label() {
        this(null);
    }
    
    public Label(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }

    public Label setText(String text) {
        this.text = text;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public Label setColour(String colour) {
        this.colour = colour;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public Label setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Rotation getRotation() {
        return rotate;
    }

    public Label setRotation(Rotation rotate) {
        this.rotate = rotate;
        return this;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Label setVisible(Boolean visible) {
        this.visible = visible;
        return this;
    }
}
