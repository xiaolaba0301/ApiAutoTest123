package com.itcast.service;

import com.itcast.bean.ResponseInfo;
import com.itcast.bean.Url;
import com.itcast.utils.ExcelUtil;
import com.itcast.utils.FileUtil;
import com.itcast.utils.HttpClientUtil;
import com.itcast.utils.JsonToMap;
import jxl.read.biff.BiffException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcel {
    /**
     * 读取用例值到testng的dataProvider注解中
     */
    String filePath;
    public ReadExcel(String filePath){
        this.filePath = filePath;
    }
    @DataProvider(name = "data")
    public Object[][] getData() throws IOException, BiffException {
        List<HashMap<String,String>> list = ExcelUtil.readExcel("src/test.xls");
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[][] data = new Object[list.size()][parmsList.size()];
        for (HashMap<String,String> map:list){
            String description = map.get("接口描述");
            String address = map.get("接口地址");
            String method = map.get("请求方式");
            String params = map.get("参数json");
            String public_variable = map.get("取变量");
            String[] caseparam = {description,address,method,params,public_variable};
            parmsList.add(caseparam);
            for (int i=0;i<parmsList.size();i++){
                data[i] = parmsList.get(i);
            }
        }
        return data;
    }
    @Test(dataProvider = "data")
    public static void getParams(String description,String address,String method,String params,String public_variable) throws IOException {
        System.out.println("接口描述："+description);
        System.out.println("接口地址："+address);
        System.out.println("请求方式:"+method);
        System.out.println("参数json:"+params);
        System.out.println("取变量:"+public_variable);
        if (method.equals("post")){
            HashMap<String,Object> map = JsonToMap.jsonToMap(params);
            String token = FileUtil.readFile("src/token.txt");
            String[] token1 = token.split(":");
            map.put("token",token1[0]);
            map.put("uid",token1[1]);
            for (String key:map.keySet()){
                if (map.get(key).toString().contains("$")){
                    int i = map.get(key).toString().indexOf("$");
                    int j = map.get(key).toString().lastIndexOf("$");
                    String value = map.get(key).toString().substring(i+1,j);
                    map.put(key, Url.publicMap.get(value));
                }
            }
            ResponseInfo responseInfo = HttpClientUtil.post1(Url.url,map);
            if (responseInfo.getStatusCode() == 200){
                if (public_variable != null && public_variable.contains("{")){
                    String[] varables = public_variable.split("=");
                    HashMap<String,Object> map1 = JsonToMap.jsonToMap(responseInfo.getHttpEntity());
                    Object ID = map1.get(varables[0]);
                    int i = varables[1].toString().indexOf("{");
                    int j = varables[1].toString().indexOf("}");
                    String key = varables[1].substring(i+1,j);
                    Url.publicMap.put(key,ID);
                }
            }
        }
    }
}
