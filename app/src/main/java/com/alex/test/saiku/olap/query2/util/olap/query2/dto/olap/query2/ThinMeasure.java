package com.alex.test.saiku.olap.query2.util.olap.query2.dto.olap.query2;


public class ThinMeasure {
	
	
	private String name;
	private String uniqueName;
	private String caption;
	private Type type;
	
	
	public enum Type {
		CALCULATED,
		EXACT
	}
	
	public ThinMeasure(){}

  public ThinMeasure(String name, String uniqueName, String caption, Type type) {
		this.name = name;
		this.uniqueName = uniqueName;
		this.caption = caption;
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the uniqueName
	 */
	public String getUniqueName() {
		return uniqueName;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}


}
