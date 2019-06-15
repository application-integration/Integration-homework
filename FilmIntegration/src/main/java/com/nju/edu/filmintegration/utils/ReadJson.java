package com.nju.edu.filmintegration.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nju.edu.filmintegration.model.FilmDataSet;

import java.io.*;

/**
 * @Auther: peng
 * @Date: 2019/6/15 16:44
 * @Description:
 */
public class ReadJson {

    public Object[][] getObjects() {
        String temp1 = this.getClass().getClassLoader().getResource("static/dataset/train0.json").getPath();
        String temp2 = this.getClass().getClassLoader().getResource("static/dataset/train1.json").getPath();
        String temp3 = this.getClass().getClassLoader().getResource("static/dataset/train2.json").getPath();
        String temp4 = this.getClass().getClassLoader().getResource("static/dataset/train3.json").getPath();
        String temp5 = this.getClass().getClassLoader().getResource("static/dataset/train4.json").getPath();
        String readJsonData1 = readJsonData(temp1);
        String readJsonData2 = readJsonData(temp1);
        String readJsonData3 = readJsonData(temp1);
        String readJsonData4 = readJsonData(temp1);
        String readJsonData5 = readJsonData(temp1);
        Gson gson = new Gson();
        TypeToken<FilmDataSet> typeToken = new TypeToken<FilmDataSet>(){};
        FilmDataSet filmDataSet1 =(FilmDataSet)gson.fromJson(readJsonData1, typeToken.getType());
        FilmDataSet filmDataSet2 =(FilmDataSet)gson.fromJson(readJsonData2, typeToken.getType());
        FilmDataSet filmDataSet3 =(FilmDataSet)gson.fromJson(readJsonData3, typeToken.getType());
        FilmDataSet filmDataSet4 =(FilmDataSet)gson.fromJson(readJsonData4, typeToken.getType());
        FilmDataSet filmDataSet5 =(FilmDataSet)gson.fromJson(readJsonData5, typeToken.getType());
        Object[][] objects = new Object[250][6];
        int i=0;
        for(int j=0;j<filmDataSet1.getSubjects().size();j++,i++){
            objects[i] = filmDataSet1.getSubjects().get(j).toObjArr();
        }
        for(int j=0;j<filmDataSet2.getSubjects().size();j++,i++){
            objects[i] = filmDataSet2.getSubjects().get(j).toObjArr();
        }
        for(int j=0;j<filmDataSet3.getSubjects().size();j++,i++){
            objects[i] = filmDataSet3.getSubjects().get(j).toObjArr();
        }
        for(int j=0;j<filmDataSet4.getSubjects().size();j++,i++){
            objects[i] = filmDataSet4.getSubjects().get(j).toObjArr();
        }
        for(int j=0;j<filmDataSet5.getSubjects().size();j++,i++){
            objects[i] = filmDataSet5.getSubjects().get(j).toObjArr();
        }
        return objects;
    }


    /**   将本地文本中的内容读取到string字符串
     * @param fileName   文件路径
     * @return   文件中的字符串
     * @throws IOException
     */
    public String readJsonData(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
