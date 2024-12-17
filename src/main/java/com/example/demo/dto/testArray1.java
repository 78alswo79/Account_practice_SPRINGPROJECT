package com.example.demo.dto;

import java.util.Arrays;

public class testArray1 {
	String bb = "";
	
	int segNo = 0;
	
	testArray2[] testArray2Bean;
	
	public String getBb() {
		return bb;
	}
	public void setBb(String bb) {
		this.bb = bb;
	}
	public int getSegNo() {
		return segNo;
	}
	public void setSegNo(int segNo) {
		this.segNo = segNo;
	}
	public testArray2[] getTestArray2Bean() {
		return testArray2Bean;
	}
	public void setTestArray2Bean(testArray2[] testArray2Bean) {
		this.testArray2Bean = testArray2Bean;
	}
	@Override
	public String toString() {
		return "testArray1 [bb=" + bb + ", segNo=" + segNo + ", testArray2Bean=" + Arrays.toString(testArray2Bean)
				+ "]";
	}
	
	
	
	
}
