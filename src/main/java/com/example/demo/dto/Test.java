package com.example.demo.dto;

import jakarta.persistence.Entity;


/**
 * 
 */
public class Test {
	private int seq;
	private String year;
	private String month;
	private String days;
	private String content;
	private String income;
	private String spending;
	private String balance;
	
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getSpending() {
		return spending;
	}
	public void setSpending(String spending) {
		this.spending = spending;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
	
	public Test() {
		super();
	}
	
	public Test(int seq, String year, String month, String days, String content, String income, String spending,
			String balance) {
		super();
		this.seq = seq;
		this.year = year;
		this.month = month;
		this.days = days;
		this.content = content;
		this.income = income;
		this.spending = spending;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "Test [seq=" + seq + ", year=" + year + ", month=" + month + ", days=" + days + ", content=" + content
				+ ", income=" + income + ", spending=" + spending + ", balance=" + balance + "]";
	}
	
	
	

	
	
}
