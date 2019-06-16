package com.nju.edu.filmintegration.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nju.edu.filmintegration.bpnn.PreProcessor;
import com.nju.edu.filmintegration.bpnn.Processor;
import com.nju.edu.filmintegration.model.FilmDataSet;
import com.nju.edu.filmintegration.model.FilmInfo;
import com.nju.edu.filmintegration.model.FilmPredict;
import com.nju.edu.filmintegration.utils.ReadJson;
import com.nju.edu.filmintegration.utils.ReadXMLByJDom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: peng
 * @Date: 2019/5/19 13:35
 * @Description:
 */

@RestController
@RequestMapping("/api")
public class FilmController {

    @GetMapping("/filmInfo/nuomi")
    public List<FilmInfo> getNuoMiFileInfo(@RequestParam("name") String name){
        List<FilmInfo> result = new ArrayList();
        ReadXMLByJDom readXMLByJDom = new ReadXMLByJDom();
        result = readXMLByJDom.getNuoMiFilmInfo(name);
        return result;
    }

    @GetMapping("/filmInfo/meituan")
    public List<FilmInfo> getMeiTuanFileInfo(@RequestParam("name") String name){
        List<FilmInfo> result = new ArrayList();
        ReadXMLByJDom readXMLByJDom = new ReadXMLByJDom();
        result = readXMLByJDom.getMeiTuanFilmInfo(name);
        return result;
    }

    @GetMapping("/getFilmPredictInfo")
    public List<FilmPredict> getFilmPredictInfo(){
    	int testFilmLength = 250;
        ReadJson readJson = new ReadJson();
        Object[][] objects = readJson.getObjects();
        
        
        Processor pro = new Processor();
        pro.train(objects, 10*10000);
        
        Object[][] objectsP = readJson.getTestObjects();
        
        List<FilmPredict> predictResult = new LinkedList<>();
        
        String temp = this.getClass().getClassLoader().getResource("static/dataset/test.json").getPath();
    	String readJsonData = readJson.readJsonData(temp);
    	Gson gson = new Gson();
        TypeToken<FilmDataSet> typeToken = new TypeToken<FilmDataSet>(){};
        FilmDataSet filmDataSet =(FilmDataSet)gson.fromJson(readJsonData, typeToken.getType());
        
        
        
        
        
        try {
        	double[] predict = pro.predict(objectsP);
            
            for (int i = 0; i < predict.length; i++){
            	FilmPredict film = new FilmPredict((String)filmDataSet.getSubjects().get(i).getTitle(), predict[i]);
            	predictResult.add(film);
                System.out.println("电影名：  "+film.getFilmName()+"   预测评分：" + film.getPredict());
                     
            }
            
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return predictResult;
    }
    
    
}
