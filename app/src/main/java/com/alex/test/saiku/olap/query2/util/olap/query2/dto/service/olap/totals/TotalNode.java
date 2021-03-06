package com.alex.test.saiku.olap.query2.util.olap.query2.dto.service.olap.totals;

import com.alex.test.saiku.olap.query2.util.olap.query2.dto.service.olap.totals.aggregators.TotalAggregator;

import org.olap4j.Cell;
import org.olap4j.metadata.Measure;


public class TotalNode {
  private final String[] captions;
  private final TotalAggregator[][] totals;
  private final boolean showsTotals;
  private final int cellsAdded;
  private int span;
  private int width;

  public TotalNode(String[] captions, Measure[] measures, TotalAggregator aggregatorTemplate, FormatList formatList,
                   int count) {
    this.captions = captions;
    showsTotals = aggregatorTemplate != null;

    if ( showsTotals ) {
      cellsAdded = captions != null ? captions.length : 1;
      totals = new TotalAggregator[ cellsAdded ][ count ];

      if ( aggregatorTemplate != null ) {
        for ( int i = 0; i < totals.length; i++ ) {
          for ( int j = 0; j < totals[ 0 ].length; j++ ) {
            //totals[ i ][ j ] = aggregatorTemplate.newInstance( formatList.getValueFormat( j, i ), measures[ i ] );
            // TODO: 13.05.2016  надо обратить внимание на это
          }
        }
      }
    } else {
      totals = new TotalAggregator[ 0 ][ count ];
      cellsAdded = 0;
    }
  }

  public void addData( int member, int index, Cell cell ) {
    totals[ member ][ index ].addData( cell );
  }

  public void setFormattedValue( int member, int index, String value ) {
    totals[ member ][ index ].setFormattedValue( value );
  }

  public int getSpan() {
    return span;
  }

  public void setSpan( int span ) {
    this.span = span;
  }

  private void appendSpan(int append) {
    this.span += append;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  private void appendWidth(int append) {
    this.width += append;
  }

  public void appendChild( TotalNode child ) {
    appendSpan( child.getRenderedCount() );
    appendWidth( child.width );
  }

  public String[] getMemberCaptions() {
    return captions;
  }

  public TotalAggregator[][] getTotalGroups() {
    return totals;
  }

  private int getRenderedCount() {
    return span + cellsAdded;
  }
}