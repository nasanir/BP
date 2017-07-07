package com.nasanir.bp.util;

import java.util.HashMap;
import java.util.Random;

import com.nasanir.bp.bean.BpBean;
import com.nasanir.bp.constant.BpConstant;

public class BpUtil {
	
	public BpBean bpBean;
	int[] bpNum;
	public double mobp;//动量系数
    public double rate;//学习系数
	public BpUtil(int[] bpNum, double rate, double mobp) {
		this.bpBean=new BpBean(bpNum);
		this.bpNum=bpNum;
		this.rate=rate;
		Random random=new Random();
		for(int lay=1;lay<bpBean.getLayerNum();lay++){
			for(int cell=0;cell<bpBean.getCellNum(lay);cell++){
				for(int fCell=0;fCell<bpBean.getCellNum(BpConstant.front, lay);fCell++){
					bpBean.setWeight(lay, cell, fCell, random.nextDouble());
				}
			}
		}
	}
	
	public HashMap<String, Double> getWMap(){
		return bpBean.getWMap();
	}
	
	public String computeOut(double[] in){
		for(int x=0;x<bpBean.getCellNum(0);x++){
			bpBean.setLayer(0, x, in[x]);
		}
		
		for(int lay=1;lay<bpBean.getLayerNum();lay++){
			for(int cell=0;cell<bpBean.getCellNum(lay);cell++){
				double U=0;
				for(int fCell=0;fCell<bpBean.getCellNum(BpConstant.front, lay);fCell++){
					U+=bpBean.getLayer(lay+BpConstant.front, fCell)*bpBean.getWeight(lay, cell, fCell);
				}
				bpBean.setLayer(lay, cell, 1/(1+Math.exp(-U)));
			}
		}
		return bpBean.getLayer(bpBean.getLayerNum()-1);
	}
	
	public void updateWeight(double[] tar){
		int lastLayerNum=bpBean.getLayerNum()-1;

		for(int lay=lastLayerNum;lay>0;lay--){
			for(int cell=0;cell<bpBean.getCellNum(lay);cell++){
				double cellValue=bpBean.getLayer(lay, cell);
				double ErrValueInt=cellValue*(1-cellValue);
				if(lay==lastLayerNum){
					double ErrValue=ErrValueInt*(cellValue-tar[cell]);
					bpBean.setErr(lay, cell, ErrValue);
				}else{
					double ErrValue=0.0;
					for(int bCell=0;bCell<bpBean.getCellNum(lay+BpConstant.back);bCell++){
						ErrValue+=bpBean.getWeight(lay+BpConstant.back, bCell, cell)*bpBean.getErr(lay+BpConstant.back, bCell);
					}
					bpBean.setErr(lay, cell, ErrValue*ErrValueInt);
				}
				double delta=0.0;
				for(int fCell=0;fCell<bpBean.getCellNum(BpConstant.front, lay);fCell++){
					delta=mobp*bpBean.getDelta(lay, cell, fCell)-rate*bpBean.getLayer(lay+BpConstant.front, fCell)*bpBean.getErr(lay, cell);
					bpBean.setDelta(lay, cell, fCell, delta);
				}
			}
		}
		
		for(int lay=1;lay<bpBean.getLayerNum();lay++){
			for(int cell=0;cell<bpBean.getCellNum(lay);cell++){
				for(int fCell=0;fCell<bpBean.getCellNum(BpConstant.front, lay);fCell++){
					double wValue=bpBean.getWeight(lay, cell, fCell)+bpBean.getDelta(lay, cell, fCell);
					bpBean.setWeight(lay, cell, fCell, wValue);
				}
			}
		}
	}
	 public void train(double[] in, double[] tar){
	        String out = computeOut(in);
	        updateWeight(tar);
	    }
}
