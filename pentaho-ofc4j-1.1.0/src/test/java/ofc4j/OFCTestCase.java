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

package ofc4j;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class OFCTestCase extends TestCase {
    public void testStringifyArray() {
        Object[] objects = new Object[] { "hello", 5, 8.3f, String.class };
        String[] strings = OFC.stringify(objects);
        assertEquals(objects.length, strings.length);
        for (int i = 0; i < objects.length; ++i) {
            assertEquals(objects[i].toString(), strings[i]);
        }
    }
    
    public void testStringifyCollection() {
        List<Object> objects = new ArrayList<Object>();
        objects.add("hello");
        objects.add(5);
        objects.add(8.3f);
        objects.add(String.class);
        List<String> strings = OFC.stringify(objects);
        assertEquals(objects.size(), strings.size());
        for (int i = 0; i < objects.size(); ++i) {
            assertEquals(objects.get(i).toString(), strings.get(i));
        }
    }
}
