package com.nju.edu.filmintegration.bpnn;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.io.Serializable;

/**
 * @Auther: peng
 * @Date: 2019/6/15 22:43
 * @Description:
 */
public class Processor implements Serializable{
    private static final long serialVersionUID = 1L;

    private BasicNetwork network;

    private PreProcessor processor;

    public final static int parameter_count = 5;

    public Processor(){
        this(new int[]{3});
    }

    public Processor(int[] hidden_layer) {

        //create a neural network, without using a factory
        network = new BasicNetwork();

        //BasicLayer 参数： 激活函数、是否偏移、该层神经元数目
        network.addLayer(new BasicLayer(null, true, parameter_count));
        for (int i = 0; i < hidden_layer.length; ++i){
            network.addLayer(new BasicLayer(new ActivationSigmoid(),true,hidden_layer[i]));
        }
        network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
        network.getStructure().finalizeStructure();

        network.reset();
    }

    /**
     * 数据顺序：导演（String）、国家（String）、上映年份（int）、评分人数（double）、片长（double）、评分（double）
     * @param original_data
     * @param iterator_count 训练次数（测试网络时训练次数设置的为100000次）
     */
    public void train(Object[][] original_data, int iterator_count){
        processor = new PreProcessor(original_data);
        System.out.println("完成数据预处理");
        //初始化神经网络的基本配置
        //第一个参数是一个整型数组，表示神经网络的层数和每层节点数，比如{3,10,10,10,10,2}表示输入层是3个节点，输出层是2个节点，中间有4层隐含层，每层10个节点
        //第二个参数是学习步长，第三个参数是动量系数

        //设置样本数据，对应上面的5维坐标数据
        double[][] data = processor.convertData(original_data);
        System.out.println("完成参数转换");
        //设置目标数据，对应1个坐标数据的分类
        double[][] target = processor.convertTarget(original_data);

        MLDataSet trainingSet = new BasicMLDataSet(data, target);

        //训练网络
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        for (int i = 0; i < iterator_count; i++){
            train.iteration();
//			System.out.println("Epoch #" + i + " Error: " + train.getError());
        }

        train.finishTraining();

        //用训练数据检测网络
        System.out.println("Neural Network Results: ");
        for(MLDataPair pair: trainingSet){
            final MLData output = network.compute(pair.getInput());
            System.out.println(	"actual=" + output.getData(0) + ", ideal=" +
                    pair.getIdeal().getData(0));
        }
        Encog.getInstance().shutdown();
    }

    public double[] predict(Object[][] original_data) throws IllegalAccessException{
        if(processor == null){
            throw new IllegalAccessException("需要先完成训练才可以预测");
        }
        double[] box = new double[original_data.length];
        System.out.println("开始预测数据");
        double[][] data = processor.convertData(original_data);
        for(int j=0;j<data.length;j++){
            MLData output = network.compute(new BasicMLData(data[j]));
            box[j] = output.getData(0) * processor.getRates();
        }
        System.out.println("完成预测");
        return box;
    }


}