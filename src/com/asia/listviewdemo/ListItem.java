package com.asia.listviewdemo;

public class ListItem {
	
	/*** 存放进度条的进度*/
	private int process ;
	/*** 存放文本显示的文字*/
	private String text;
	
	private boolean isHidden;
	
	/**
	 * @return the process
	 */
	public int getProcess() {
		return process;
	}
	
	/**
	 * @param process the process to set
	 */
	public void setProcess(int process) {
		this.process = process;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the isHidden
	 */
	public boolean isHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden the isHidden to set
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
