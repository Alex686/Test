package com.alex.test.saiku.olap.query2.util.olap.query2.dto.service.util;



import com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.dto.SaikuDimension;

import java.util.Comparator;


public class SaikuDimensionCaptionComparator implements Comparator<SaikuDimension> {

    public int compare( SaikuDimension o1, SaikuDimension o2 ) {
        if ( o1.getCaption() == null || o2.getCaption() == null ) {
            return 0;
        }
        return o1.getCaption().compareTo( o2.getCaption() );
    }
}
