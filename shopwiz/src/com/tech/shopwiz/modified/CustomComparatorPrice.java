package com.tech.shopwiz.modified;

import java.util.Comparator;

import com.tech.shopwiz.ProductInfo;
import com.tech.shopwiz.R.*;

public class CustomComparatorPrice implements Comparator<ProductInfo>  {
boolean ascendingBool;
	public CustomComparatorPrice(boolean _ascendingBool) {
		// TODO Auto-generated constructor stub
		this.ascendingBool = _ascendingBool;
	}

	@Override
	public int compare(ProductInfo arg0, ProductInfo arg1) {
		// TODO Auto-generated method stub
		if(ascendingBool){
		if(arg0.getCost()>arg1.getCost()){
			return 10;	
	}
		else{
			return -10; 
		}
		}
		else{
			if(arg0.getCost()>arg1.getCost()){
				return -10;	
		}
			else{
				return 10; 
			
		}
}
}
}