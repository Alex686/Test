package com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.util.exception;

import com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.dto.ISaikuObject;

import java.util.Comparator;

public class SaikuUniqueNameComparator implements Comparator<ISaikuObject> {

    public int compare( ISaikuObject o1, ISaikuObject o2 ) {
        return o1.getUniqueName().compareTo( o2.getUniqueName() );
    }

}
