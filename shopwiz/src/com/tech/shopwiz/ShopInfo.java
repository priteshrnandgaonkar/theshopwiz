package com.tech.shopwiz;

import java.util.ArrayList;

public class ShopInfo {
public int id;
public String shopName;
public String address;
public String oneLineAddress;
public double lat;
public double lon;
public String description;
public String imageURL;
public String landline_no;
public String mobile_no;
public String website;
public String categories;
public ArrayList<String> gendersList;
public ArrayList<String> types;
public ArrayList<String> queryNamesForTypes;
	public ShopInfo(int _id,String _shopName,String _address, String _description,
			String _imageURL,String _landline_no,String _mobile_no,String _website,
			String _categories, double _lat, double _lon ) {
		this.id=_id;
		this.shopName=_shopName;
		this.address=_address;
		this.lat=_lat;
		this.lon=_lon;
		this.description=_description;
		this.imageURL=_imageURL;
		this.landline_no=_landline_no;
		this.mobile_no=_mobile_no;
		this.website=_website;
		this.categories=_categories;
		// TODO Auto-generated constructor stub
	}

}
