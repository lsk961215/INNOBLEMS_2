package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.aop.genSaveFileName;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class FileController {
	
	@Value("${userImages.path}")
	private String userImagesPath;
	
	@RequestMapping("/addImage")
    public String addImage(@RequestParam("image") MultipartFile multi,HttpServletRequest request,HttpServletResponse response, Model model)
    {
        String url = null;
        
        try {
            String uploadpath = userImagesPath;
            String originFilename = multi.getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
            long size = multi.getSize();
            
            if(size <= 41943040) {
            	new genSaveFileName();
            	
            	String saveFileName = genSaveFileName.returnSaveFileName(extName);
                
                if(!multi.isEmpty())
                {
                    File file = new File(uploadpath, saveFileName);
                    multi.transferTo(file);
                    
                    return saveFileName;
                }
    
            } else {
            	return "-1";
            }
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return "-1";
    }
}
