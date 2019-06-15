package com.nju.edu.filmintegration.controller;

import com.nju.edu.filmintegration.model.FilmInfo;
import com.nju.edu.filmintegration.utils.ReadJson;
import com.nju.edu.filmintegration.utils.ReadXMLByJDom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
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

    @GetMapping("/test")
    public void test(){

        ReadJson readJson = new ReadJson();
        Object[][] objects = readJson.getObjects();

    }
}
