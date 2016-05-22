package com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.query2.common;

import com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.query2.filter.ThinFilter;

import java.util.List;

public interface ThinQuerySet {

		String getName();
		
		void setMdx(String mdxSetExpression);
		
		String getMdx();
		
		void addFilter(ThinFilter filter);
		
		void setFilter(int index, ThinFilter filter);
		
		List<ThinFilter> getFilters();
		
		void clearFilters();
}
