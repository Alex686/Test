package com.alex.test.saiku.olap.query2.util.olap.query2.dto.service.olap.totals.aggregators;

//import mondrian.util.Format;

public class MaxAggregator //extends TotalAggregator
 {

  /*
  MaxAggregator(Format format) {
    super( format );
  }
*/
  private Double max = null;
/*

  @Override
  public void addData( double data ) {
    if ( max == null ) {
      max = data;
    } else if ( max < data ) {
      max = data;
    }
  }

  @Override
  public Double getValue() {
    return max;
  }
  @Override
  public TotalAggregator newInstance( Format format, Measure measure ) {
    return new MaxAggregator( format );
  }
*/
}