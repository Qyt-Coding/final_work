package com.bean;

public class PPoint {
	public float x;
	public float y;
	public int flag = -1;
	
	public PPoint(){
		
	}
	public PPoint(float x,float y){
		this.x = x;
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "PPoint [x=" + x + ", y=" + y + ", flag=" + flag + ", getX()=" + getX() + ", getY()=" + getY()
				+ ", getFlag()=" + getFlag() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
