package com.tech.shopwiz.modified;
import java.util.Comparator;

import com.tech.shopwiz.ProductInfo;
import com.tech.shopwiz.R.*;

public class CustomComparatorTrending implements Comparator<ProductInfo> {

	

	@Override
	public int compare(ProductInfo lhs, ProductInfo rhs) {
		// TODO Auto-generated method stub
		if(lhs.popularity > rhs.popularity){
			return 10;
		}
		else{
			return -10;
		}
	}

}
