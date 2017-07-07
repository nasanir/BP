package com.nasanir.bp.main;

import java.util.Arrays;
import java.util.Random;


import com.nasanir.bp.constant.BpConstant;
import com.nasanir.bp.util.BpUtil;

public class Main {
	public static void main(String[] arg){
		//初始化神经网络的基本配置
        //第一个参数是一个整型数组，表示神经网络的层数和每层节点数，比如{3,10,10,10,10,2}表示输入层是3个节点，输出层是2个节点，中间有4层隐含层，每层10个节点
        //第二个参数是学习步长，第三个参数是动量系数
		BpUtil bp = new BpUtil(new int[]{15,3,4}, 0.005, 0.7);

        //设置样本数据，对应上面的4个二维坐标数据
        double[][] data = random();
        //设置目标数据，对应4个坐标数据的分类
        double[][] target = randomY();

        //迭代训练5000次
        for(int n=0;n<7000;n++)
            for(int i=0;i<data.length;i++)
                bp.train(data[i], target[i]);

        //根据训练结果来检验样本数据
        for(int j=0;j<data.length;j++){
            String result = bp.computeOut(data[j]);
            System.out.println(Arrays.toString(data[j])+":"+result);
        }

        //根据训练结果来预测一条新数据的分类

        //0.147046,0.00364964,0.67903,1.66344,0.0540356
        double[] x = new double[]{0,1,0,0,1,0,0,1,0,0,1,0,0,1,0};
        String result = bp.computeOut(x);
        StringBuffer sf=new StringBuffer();
        for(int i=1;i<3;i++){
        	for(int j=0;j<bp.bpBean.getCellNum(i);j++){
        		for(int z=0;z<bp.bpBean.getCellNum(BpConstant.front,i);z++){
        			sf.append(bp.bpBean.getWeight(i, i, z)).append("|-|");
        			
        	}sf.append("//n");
        }
        	}
        System.out.println(sf.toString());
        System.out.println(Arrays.toString(x)+":"+result);
    }
	
	public static double[][] random(){
		boolean flag;
		double[][] parms = new double[100][15];
		Random random = new Random();
		for (int j = 0; j < parms.length; j++) {
			for (int i = 0; i < parms[j].length; i++) {
				
				double parm = random.nextDouble();
				if ((0.9-parm)<0.8) {
					parm=0.9+parm/10;
				}
				if(j<=parms.length/3){
					flag=(i-1)%3==0;
				}else if((j<=2*parms.length/3)&&j>parms.length/3){
					flag=((i-2)%3==0)||i<3;
				}else{
					flag=((i)%3==0)||((i-2)%3==0)||i%6==1;
				}
				if(flag){
					parms[j][i]=parm;
				}else{
					parms[j][i]=0;
				}
				
			}

		}
		return parms;
	}
	
	public static double[][] randomY(){
		double[][] parms = new double[100][4];
		for (int j = 0; j < parms.length; j++) {
			if(j<=parms.length/3){
				parms[j]=new double[]{0,0,0,1};
			}else if((j<=2*parms.length/3)&&j>parms.length/3){
				parms[j]=new double[]{0,1,1,1};
			}else{
				parms[j]=new double[]{1,0,0,0};
			}
			
		}
		return parms;
	}
	}
	