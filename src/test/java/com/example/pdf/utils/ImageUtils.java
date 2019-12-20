package com.example.pdf.utils;

import com.example.pdf.PDFUtil;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * 图片操作
 *
 * @Auther xiewl
 */
public class ImageUtils {
    /**
     * 获取图片 byte[]
     *
     * @param filePath
     * @return
     */
    public static byte[] getImageBytes(String filePath) throws Exception {
        byte[] data = null;
        try {
            if (StringUtils.isNotEmpty(filePath)) {
                Resource resource = new ClassPathResource(filePath);
                if (resource.getFile().exists()) {
                    File file = resource.getFile();
                    data = Files.readAllBytes(file.toPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取图片Base64
     *
     * @param filePath
     * @return
     */
    public static String getBase64Image(String filePath) throws Exception {
        String base64Image = "";
        try {
            if (StringUtils.isNotEmpty(filePath)) {
                Resource resource = new ClassPathResource(filePath);
                if (resource.getFile().exists()) {
                    FileInputStream inputStream = new FileInputStream(resource.getFile());
                    byte[] data = new byte[inputStream.available()];
                    inputStream.read(data);
                    inputStream.close();
                    base64Image = new String(Base64.getEncoder().encode(data), "utf-8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }

    /**
     * 获取图片Base64
     *
     * @param templatePath 模板路径
     * @param toPath       模板输出路径
     * @param model        模板对象
     * @return
     */
    public static Boolean createPdfTemplate(String templatePath, String toPath, Object model) throws Exception {
        Boolean isSuccess = false;
        Resource resource = new ClassPathResource(templatePath);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            try (
                    PdfReader pr = new PdfReader(file);
                    PdfDocument pdfDoc = new PdfDocument(pr, new PdfWriter(toPath))
            ) {
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
                //合成PDF
                PDFUtil.composePDF(pdfDoc, form, model);
                isSuccess = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }


}
