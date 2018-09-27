package com.itcast.utils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.SharedStringFormulaRecord;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelUtil {
    public static List<HashMap<String,String>> readExcel(String filePath) throws IOException, BiffException {
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = null;
        InputStream in = new FileInputStream(filePath);
        Workbook workbook = Workbook.getWorkbook(in);
        Sheet[] sheets = workbook.getSheets();
        Sheet sheets1 = sheets[0];
        Cell[] cells0 = sheets1.getRow(0);
        for (int i=0;i<sheets.length;i++){
            Sheet sheet = sheets[i];
            int rows = sheet.getRows();
            for (int j=1;j<rows;j++){
                map = new HashMap<String, String>();
                Cell[] cells = sheet.getRow(j);
                for (int k=0;k<cells.length;k++){
                    Cell cells1 = cells[k];
                    if (cells1 != null){
                        map.put(cells0[k].getContents(),cells1.getContents());
                    }
                }
                list.add(map);
            }
        }
        return list;
    }
    public static List<HashMap<String,String>> readExcel(String filePath,String sheetName) throws IOException, BiffException {
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = null;
        InputStream in = new FileInputStream(filePath);
        Workbook workbook = Workbook.getWorkbook(in);
        Sheet sheet = workbook.getSheet(sheetName);
        Cell[] cells0 = sheet.getRow(0);
        for (int i=1;i<sheet.getRows();i++){
            map = new HashMap<String, String>();
            Cell[] cells1 = sheet.getRow(i);
            for (int j=0;j<cells1.length;j++){
                Cell cells = cells1[j];
                if (cells != null){
                    map.put(cells0[j].getContents(),cells.getContents());
                }
            }
            list.add(map);
        }
        return list;
    }
    public static void main(String[] args) throws IOException, BiffException {
        List<HashMap<String,String>> list = ExcelUtil.readExcel("src/test.xls");
        for (HashMap<String,String> map:list){
            String description = map.get("接口描述");
            String address = map.get("接口地址");
            String method = map.get("请求方式");
            String params = map.get("参数json");
            String public_variable = map.get("取变量");
            System.out.print(description+"\r\n");
            System.out.print(address+"\r\n");
            System.out.print(method+"\r\n");
            System.out.print(params+"\r\n");
            System.out.print(public_variable);
        }
    }
}
