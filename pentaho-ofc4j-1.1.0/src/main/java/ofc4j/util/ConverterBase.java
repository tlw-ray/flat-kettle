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

import java.util.Locale;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public abstract class ConverterBase<T> implements Converter {
    static java.text.NumberFormat nf;
    static
    {
      nf = java.text.NumberFormat.getNumberInstance(Locale.US);
      nf.setGroupingUsed(false);
    }
    
    // @Override
    public final Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
        return null;
    }
    
    // @Override
    @SuppressWarnings("unchecked")
    public final void marshal(Object o, HierarchicalStreamWriter hsw, MarshallingContext mc) {
        convert((T) o, (PathTrackingWriter) hsw, mc);
    }
    
    public final void writeNode(PathTrackingWriter writer, String name, Object o) {
        if (o != null) {
            writer.startNode(name, o.getClass());
            if(o instanceof Number)
            {
              writer.setValue(nf.format(o));
            }
            else
              writer.setValue(o.toString());
            writer.endNode();
        }
    }
    
    public abstract void convert(T o, PathTrackingWriter writer, MarshallingContext mc);
}
