package com.nju.edu.filmintegration.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nju.edu.filmintegration.model.FilmInfo;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.jdom2.*;

/**
 * @Auther: peng
 * @Date: 2019/5/19 13:42
 * @Description:
 */

public class ReadXMLByJDom {

    private List<FilmInfo> filmInfoList = null;
    private FilmInfo filmInfo = null;

    public List<FilmInfo> getNuoMiFilmInfo(String targetName){
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            String temp = this.getClass().getClassLoader().getResource("static/xml/nuomi.xml").getPath();
            Document document = saxBuilder.build(new FileInputStream(temp));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取根节点的子节点，返回子节点的数组
            List<Element> itemList = rootElement.getChildren();
            filmInfoList = new ArrayList<FilmInfo>();
            for(Element item : itemList){
                filmInfo = new FilmInfo();
                String name = "";
                String cinema = "";
                String date = "";

                List<Element> children = item.getChildren();
                for(Element child : children){
                    if(child.getName().equals("name")){
                        name = child.getValue();
                        if(!name.equals(targetName)){
                            break;
                        }
                    }else if(child.getName().equals("cinema")){
                        cinema = child.getValue();
                    }else if(child.getName().equals("date")){
                        date = child.getValue();
                    }else if(child.getName().equals("screenings")){
                        List<Element> childChildren = child.getChildren();
                        for(Element childChild:childChildren){
                            if(childChild.getName().equals("value")){
                                List<Element> valueList = childChild.getChildren();
                                String auditorium="", time="", price = "";
                                for(Element value:valueList){
                                    if(value.getName().equals("auditorium")){
                                        auditorium = value.getValue();
                                    }else if(value.getName().equals("time")){
                                        time = value.getValue();
                                    }else if(value.getName().equals("price")){
                                        price = value.getValue();
                                    }

                                }

                                filmInfo.setName(targetName);
                                filmInfo.setCinema(cinema);
                                filmInfo.setDate(date);
                                filmInfo.setAuditorium(auditorium);
                                filmInfo.setTime(time);
                                filmInfo.setPrice(price);
                                filmInfoList.add(filmInfo);
                                filmInfo = new FilmInfo();
                            }
                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (JDOMException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return filmInfoList;

    }


    public List<FilmInfo> getMeiTuanFilmInfo(String targetName){
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            String temp = this.getClass().getClassLoader().getResource("static/xml/meituan.xml").getPath();
            Document document = saxBuilder.build(new FileInputStream(temp));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取根节点的子节点，返回子节点的数组
            List<Element> itemList = rootElement.getChildren();
            filmInfoList = new ArrayList<FilmInfo>();
            for(Element item : itemList){
                filmInfo = new FilmInfo();
                String name = "";
                String cinema = "";
                String date = "";

                List<Element> children = item.getChildren();
                for(Element child : children){
                    if(child.getName().equals("name")){
                        name = child.getValue();
                        if(!name.equals(targetName)){
                            break;
                        }
                    }else if(child.getName().equals("cinema")){
                        cinema = child.getValue();
                    }else if(child.getName().equals("date")){
                        date = child.getValue();
                    }else if(child.getName().equals("screenings")){
                        List<Element> childChildren = child.getChildren();
                        for(Element childChild:childChildren){
                            if(childChild.getName().equals("value")){
                                List<Element> valueList = childChild.getChildren();
                                String auditorium="", time="", type = "";
                                for(Element value:valueList){
                                    if(value.getName().equals("auditorium")){
                                        auditorium = value.getValue();
                                    }else if(value.getName().equals("time")){
                                        time = value.getValue();
                                    }else if(value.getName().equals("type")){
                                        type = value.getValue();
                                    }

                                }

                                filmInfo.setName(targetName);
                                filmInfo.setCinema(cinema);
                                filmInfo.setDate(date);
                                filmInfo.setAuditorium(auditorium);
                                filmInfo.setTime(time);
                                filmInfo.setType(type);
                                filmInfoList.add(filmInfo);
                                filmInfo = new FilmInfo();
                            }
                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (JDOMException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return filmInfoList;

    }

}