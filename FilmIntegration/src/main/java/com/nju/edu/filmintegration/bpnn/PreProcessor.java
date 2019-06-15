package com.nju.edu.filmintegration.bpnn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: 2019/6/15 22:16
 * @Description:
 */
public class PreProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    //各个导演权重
    private Map<String, Double> directors;
    //各个体裁权重
    private Map<String, Double> genres;

    private double dates;

    private double[] popularities;

    private double[] durations;

    private double rates;

    private int rates_index;

    /**
     * 数据顺序：导演、体裁、上映年份、看过人数、片长、评分
     * @param original_data
     */
    public PreProcessor(Object[][] original_data){
        rates_index = original_data[0].length - 1;

        directors = this.processEnum(original_data, 0);
        genres = this.processEnum(original_data, 1);
        dates = this.processDate(original_data);
        popularities = this.processNum(original_data, 3);
        durations = this.processNum(original_data, 4);
        rates = 10;
    }


    private Map<String, Double> processEnum(Object[][] original_data, int index){
        Map<String, Double> cache = new HashMap<String, Double>();
        Map<String, Integer> films = new HashMap<String, Integer>();
        Map<String, Double> box = new HashMap<String, Double>();

        for (int i = 0; i < original_data.length; i++) {
            if((double)original_data[i][Processor.parameter_count] == 0){
                continue;
            }
            String column = (String)original_data[i][index];
            if(films.containsKey(column)){
                films.replace(column, films.get(column) + 1);
                box.replace(column,
                        box.get(column) +
                                (Double)original_data[i][rates_index]);
            }else{
                films.put(column, 1);
                box.put(column, (Double)original_data[i][rates_index]);
            }
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (String column : box.keySet()) {
            double bill = box.get(column) / films.get(column);
            min = Math.min(min, bill);
            max = Math.max(max, bill);
            box.replace(column, bill);
        }

        double fenmu = Math.log(max / min);
        for (String name : box.keySet()) {
            cache.put(name, Math.log(box.get(name) / min) / fenmu);
        }
        return cache;
    }


    public double[][] convertTarget(Object[][] original_data){
        double[][] tmp = new double[original_data.length][1];
        for (int i = 0; i < original_data.length; ++i){
            tmp[i][0] = (double)original_data[i][rates_index] / rates;
        }
        return tmp;
    }

    private int processDate(Object[][] original_data){
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < original_data.length; i++) {
            min = Math.min(min, (int)original_data[i][2]);
        }

        return min;

//		double[] dates = new double[5];
//		int[] films = new int[5];
//		double[] box = new double[5];
//
//		for (int i = 0; i < original_data.length; i++) {
//			int index = this.getDateIndex((Date)original_data[i][2]);
//			films[index] ++;
//			box[index] += (Double)original_data[i][price_index];
//		}
//
//		double min = Double.MAX_VALUE;
//		double max = Double.MIN_VALUE;
//		for (int i = 0; i < box.length; i++) {
//			box[i] /= films[i];
//			min = Math.min(min, box[i]);
//			max = Math.max(max, box[i]);
//		}
//
//		double fenmu = Math.log(max - min);
//		for (int i = 0; i < box.length; i++) {
//			dates[i] = Math.log(box[i] - min) / fenmu;
//		}
//		return dates;
    }

    public double[][] convertData(Object[][] original_data){
        int m = original_data.length;
        double[][] data = new double[m][Processor.parameter_count];
        for (int i = 0; i < m; i++) {
            if(directors.containsKey((String)original_data[i][0])){
                data[i][0] = directors.get((String)original_data[i][0]);
            }else{
                data[i][0] = 0.5;
            }

            if(genres.containsKey((String)original_data[i][1])){
                data[i][1] = genres.get((String)original_data[i][1]);
            }else{
                data[i][1] = 0.5;
            }

            data[i][2] = Math.log((int)original_data[i][2] / dates);
            data[i][3] = ((int)original_data[i][3] - popularities[0]) / popularities[1];
            data[i][3] = data[i][3] < 0 ? 0 : data[i][3];
            data[i][4] = ((double)original_data[i][4] - durations[0]) / durations[1];
            data[i][4] = data[i][4] < 0 ? 0 : data[i][4];
            System.out.println(data[i][0] + "; " + data[i][1] + "; " + data[i][2] + "; " + data[i][3] + "; " + data[i][4] + "; " + original_data[i][5]);
        }

        return data;
    }


    private double[] processNum(Object[][] original_data, int index){
        double[] cache = new double[2];
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < original_data.length; i++) {
            Object obj=original_data[i][index];

            double comp=0;
            if (obj instanceof Integer)
                comp=(int)obj;
            else
                comp=(double)obj;
            if(comp == 0){
                continue;
            }
            min = Math.min(min, comp);
            max = Math.max(max, comp);
        }

        cache[0] = min;
//		cache[1] = Math.log(max / min);
        cache[1] = max - min;
        return cache;
    }

    public double getRates(){
        return rates;
    }
}
