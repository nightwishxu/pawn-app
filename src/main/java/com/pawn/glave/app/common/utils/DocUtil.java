package com.pawn.glave.app.common.utils;

import com.deepoove.poi.XWPFTemplate;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;


public class DocUtil {

    public static void download(String tempPath, String newWordName, Map dataMap)  {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        XWPFTemplate template = XWPFTemplate.compile(path+"zs.docx").render(dataMap);
        try {
            template.writeToFile(tempPath+newWordName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


