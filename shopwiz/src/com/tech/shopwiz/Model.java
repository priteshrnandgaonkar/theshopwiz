package com.tech.shopwiz;
public class Model {

	  private String name;
	  private boolean selected;
	  private String header_name;

	  public Model(String name,String _header_name) {
	    this.name = name;
	    selected = false;
	    this.header_name=_header_name;
	  }

	  public String getName() {
	    return name;
	  }
	  public String getHeaderName() {
		    return header_name;
		  }
	  
	  public void setName(String name) {
	    this.name = name;
	  }
	  public void setHeaderName(String name) {
		    this.header_name = name;
		  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }

	} 