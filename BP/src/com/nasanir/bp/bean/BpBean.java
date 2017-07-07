package com.nasanir.bp.bean;

import java.util.HashMap;

public class BpBean {
	private HashMap<String, Double> LayValue;
	private HashMap<String, Double> layerErr;
	private HashMap<String, Double> weight;
	private HashMap<String, Double> delta;
	private int[] layer;
	private int layerNum;

	/**
	 * 根据传入的数据初始化神经网络节点
	 * 
	 * @param bpNum
	 * @param rate
	 * @param mobp
	 */
	public BpBean(int[] bpNum) {
		this.layer = bpNum;
		this.layerNum = bpNum.length;
		this.LayValue = new HashMap<String, Double>();
		this.layerErr = new HashMap<String, Double>();
		this.weight = new HashMap<String, Double>();
		this.delta = new HashMap<String, Double>();
	}
	
	
	public HashMap<String, Double> getWMap(){
		return this.weight;
	}

	/**
	 * 取得网络层数
	 * 
	 * @return
	 */
	public int getLayerNum() {
		return this.layerNum;
	}

	/**
	 * 取得当前层神经节点数
	 * 
	 * @param layer
	 * @return
	 */
	public int getCellNum(int layer) {
		return this.layer[layer];
	}

	/**
	 * 取得前一层或后一层神经节点数 layer为当前层
	 * 
	 * @param layer
	 * @return
	 */
	public int getCellNum(int flag, int layer) {
		return this.layer[layer + flag];
	}

	/**
	 * 设置节点数据(神经节点输出值)
	 * 
	 * @param layer
	 * @param cell
	 * @param value
	 */
	public void setLayer(int layer, int cell, double value) {
		this.LayValue.put(intToStr(layer, cell), value);
	}

	/**
	 * 设置节点误差值
	 * 
	 * @param layer
	 * @param cell
	 * @param value
	 */
	public void setErr(int layer, int cell, double value) {
		this.layerErr.put(intToStr(layer, cell), value);
	}

	/**
	 * 设置节点参数值
	 * 
	 * @param layer
	 * @param cell
	 * @param parm
	 * @param value
	 */
	public void setWeight(int layer, int cell, int parm, double value) {
		this.weight.put(intToStr(layer, cell, parm), value);
	}

	/**
	 * 设置节点参数调值
	 * 
	 * @param layer
	 * @param cell
	 * @param parm
	 * @param value
	 */
	public void setDelta(int layer, int cell, int parm, double value) {
		this.delta.put(intToStr(layer, cell, parm), value);
	}

	/**
	 * 取得节点输出值
	 * 
	 * @param layer
	 * @param cell
	 * @return
	 */
	public double getLayer(int layer, int cell) {
		double value = 0;
		if (this.LayValue.get(intToStr(layer, cell)) != null)
			value = this.LayValue.get(intToStr(layer, cell));
		return value;
	}

	/**
	 * 取得节点输出值
	 * 
	 * @param layer
	 * @param cell
	 * @return
	 */
	public String getLayer(int layer) {
		double value;
		StringBuffer sf = new StringBuffer();
		for (int cell = 0; cell < getCellNum(layer); cell++) {
			if (this.LayValue.get(intToStr(layer, cell)) != null)
				value = this.LayValue.get(intToStr(layer, cell));
			else
				value = 0;
			sf.append(value).append(",");
		}

		return sf.toString();
	}

	/**
	 * 取得节点误差值
	 * 
	 * @param layer
	 * @param cell
	 * @return
	 */
	public double getErr(int layer, int cell) {
		double value = 0;
		if (this.layerErr.get(intToStr(layer, cell)) != null)
			value = this.layerErr.get(intToStr(layer, cell));
		return value;
	}

	/**
	 * 取得节点参数值
	 * 
	 * @param layer
	 * @param cell
	 * @param parm
	 * @return
	 */
	public double getWeight(int layer, int cell, int parm) {
		double value = 0;
		if (this.weight.get(intToStr(layer, cell, parm)) != null)
			value = this.weight.get(intToStr(layer, cell, parm));
		return value;
	}

	/**
	 * 取得节点参数调整值
	 * 
	 * @param layer
	 * @param cell
	 * @param parm
	 * @return
	 */
	public double getDelta(int layer, int cell, int parm) {
		double value = 0;
		if (this.delta.get(intToStr(layer, cell, parm)) != null)
			value = this.delta.get(intToStr(layer, cell, parm));
		return value;
	}

	private String intToStr(int... values) {
		StringBuffer sf = new StringBuffer();
		for (int arg : values) {
			sf.append(arg).append(",");
		}
		return sf.toString();
	}
}
