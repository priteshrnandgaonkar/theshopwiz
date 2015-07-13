package com.tech.shopwiz;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfo{
String shopName;//1
int cost;//1
Bitmap image;  //1
String shopaddress;
String productid;  //1
public ArrayList<String> imageurl;
String description;//1
int rating;
public ArrayList<String> size;
public ArrayList<String> pattern;
public ArrayList<String> style;
public ArrayList<String> fabric;
public ArrayList<String> color;

String shopid;
public String dummy_prod_id;
HashMap<String,String> unknownFields; //1
	public ProductInfo(String _productid,String _shopName,String _shopaddress,int _cost,Bitmap _image,String _description,int _rating,String _shop_id) {
		// TODO Auto-generated constructor stub
		this.productid=_productid;
		this.shopName=_shopName;
		this.cost=_cost;
		this.image=_image;
		this.shopaddress=_shopaddress;
		this.description=_description;
		this.rating=_rating;
		this.shopid=_shop_id;
		unknownFields=new HashMap<String,String>();
	}
	public HashMap<String,String> getUnknownFields(){
		return unknownFields;
	}
	public void addUnknownFields(HashMap<String,String> _unknownFields){
		this.unknownFields=_unknownFields;
	}
	public String getShopAddress(){
		return shopaddress ;
	}
	public String getDescription(){
		return description ;
	}
	public String getShopId(){
		return shopid;
	}
	public int getRating(){
		return rating;
	}
	public ArrayList<String> getImageUrl(){
		return imageurl ;
	}
	public String getProductId(){
		return productid;
	}
	public String getShopName(){
		return shopName;
	}
	public int getCost(){
		return cost;
	}
	public Bitmap getBitmap(){
		return image;
	}
	public void addRating(int rating1){
		rating=rating1;
	}
	public void addDescription(String Description){
		description=Description;
	}
	public void addShopId(String _shopid){
		shopid=_shopid;
	}
	public void addImageUrl(ArrayList<String> s){
		this.imageurl=s;
	}
	public void addShopName(String newshopname){
		this.shopName=newshopname;
	}
	public void addShopAddress(String add){
		this.shopaddress=add;
	}
	public void addProductId(String pid){
		this.productid=pid;
	}
	public void addCost(int newcost){
		this.cost=newcost;
	}
	public void addBitmap(Bitmap b){
		this.image=b;
	}
	/*
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeStringArray(new String[]{shopName,shopaddress,productid,imageurl,description,shopid});
		arg0.writeIntArray(new int[]{cost,rating});
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, stream);
		arg0.writeByteArray(stream.toByteArray());
	}*/

}
