package com.example.pdf;

import com.example.pdf.annotation.CheckBoxDomain;
import com.example.pdf.annotation.ImageDomain;
import com.example.pdf.annotation.TextDomain;
import com.example.pdf.enums.PdfElementType;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.sun.deploy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.pdf.enums.PdfElementType.CheckBox;
import static com.example.pdf.enums.PdfElementType.Text;


@Slf4j
public class PDFUtil {

    private static final String FONT_FILE_PATH = "pdf/simsun.ttf.bin";

    private static PdfFont font = null;

    private static byte[] pdfFontBytes = null;

    /**
     * 图片分辨率
     */
    private static int IMAGE_DPI = 200;

    /**
     * 合成PDF
     *
     * @param pdfDoc
     * @param form
     * @param object
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static void composePDF(PdfDocument pdfDoc, PdfAcroForm form, Object object) throws Exception {

        //解析传入的Object中自定义注解(@TextDomain, @ImageDomain, @CheckBoxDomain)
        List<PdfFormElement> formElements = analysis(object);

        //合成PDF
        composePDF(pdfDoc, form, formElements);
    }

    /**
     * 合成PDF
     *
     * @param pdfDoc
     * @param form
     * @param elements
     */
    public static void composePDF(PdfDocument pdfDoc, PdfAcroForm form, List<PdfFormElement> elements) throws Exception {

        if (null == elements || elements.isEmpty()) {
            return;
        }

        for (PdfFormElement element : elements) {
            switch (element.getType()) {
                case Text:
                    insertText(form, element.getField(), element.getText(), element.getFontSize());
                    break;
                case Image:
                    insertImage(pdfDoc, form, 1, element.getField(), element.getBytes());
                    break;
                case CheckBox:
                    checked(form, element.getField(), element.getText());
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 解析传入的Object中自定义注解(@TextDomain, @ImageDomain, @CheckBoxDomain)转换为PdfFormElement-为合成PDF做准备
     *
     * @param object
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static List<PdfFormElement> analysis(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        List<PdfFormElement> result = new ArrayList<>();

        if (null == object) {
            return result;
        }

        Class type = object.getClass();

        BeanInfo beanInfo = null;
        beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        //遍历JavaBean的所有属性
        String propertyName = "";
        Class clazz = null;
        for (PropertyDescriptor descriptor : propertyDescriptors) {

            //属性名称
            propertyName = descriptor.getName();
            //属性的类型
            clazz = descriptor.getPropertyType();

            if (!"class".equals(propertyName)) {

                //通过反射获取该属性的值
                Method readMethod = descriptor.getReadMethod();
                Object value = readMethod.invoke(object, new Object[0]);

                //仅处理value不为null
                if (value != null) {
                    Field field = getDeclaredField(object, propertyName);

                    //处理@TextDomain注解
                    if (field.isAnnotationPresent(TextDomain.class)) {

                        TextDomain textDomain = field.getAnnotation(TextDomain.class);
                        if (null == textDomain) {
                            continue;
                        }

                        //判断是否需要按字符拆分
                        if (!textDomain.isSplit()) {

                            //String类型
                            if (String.class.isAssignableFrom(clazz)) {

                                String strValue = (String) value;
                                if (org.apache.commons.lang3.StringUtils.isNotEmpty(strValue)) {
                                    result.add(new PdfFormElement(Text, propertyName, strValue, textDomain.fontSize()));
                                }

                                //List类型
                            } else if (List.class.isAssignableFrom(clazz)) {

                                String text = org.apache.commons.lang3.StringUtils.join(((List) value).toArray(), "");
                                result.add(new PdfFormElement(Text, propertyName, text, textDomain.fontSize()));
                            } else {
                                //后期可以支持其它类型
                            }

                            //需要按字符拆分
                        } else {

                            //String类型
                            if (String.class.isAssignableFrom(clazz)) {

                                //将字符串按字符拆分到List中
                                List<String> valueList = ((String) value).chars().mapToObj(item -> String.valueOf((char) item)).collect(Collectors.toList());
                                for (int i = 0; i < valueList.size(); i++) {
                                    result.add(new PdfFormElement(Text, propertyName + (i + 1), valueList.get(i), textDomain.fontSize()));
                                }

                                //List类型
                            } else if (List.class.isAssignableFrom(clazz)) {

                                List list = (List) value;
                                for (int i = 0; i < list.size(); i++) {
                                    result.add(new PdfFormElement(Text, propertyName + (i + 1), String.valueOf(list.get(i)), textDomain.fontSize()));
                                }

                            } else {
                                //后期可以支持其它类型
                            }
                        }

                        //处理@CheckBoxDomain注解
                    } else if (field.isAnnotationPresent(CheckBoxDomain.class)) {

                        CheckBoxDomain checkBoxDomain = field.getAnnotation(CheckBoxDomain.class);
                        if (null == checkBoxDomain) {
                            continue;
                        }

                        //仅支持Boolean类型
                        if (Boolean.class.isAssignableFrom(clazz)) {

                            Boolean checked = (Boolean) value;
                            if (checked) {
                                result.add(new PdfFormElement(CheckBox, propertyName, checkBoxDomain.text()));
                            }
                        }

                        //处理@ImageDomain注解
                    } else if (field.isAnnotationPresent(ImageDomain.class)) {

                        ImageDomain imageDomain = field.getAnnotation(ImageDomain.class);
                        if (null == imageDomain) {
                            continue;
                        }

                        //byte[] 类型
                        if (byte[].class.isAssignableFrom(clazz)) {

                            result.add(new PdfFormElement(PdfElementType.Image, propertyName, (byte[]) value));

                            //String 类型 (注：base64编码图片)
                        } else if (String.class.isAssignableFrom(clazz)) {

                            result.add(new PdfFormElement(PdfElementType.Image, propertyName, Base64.getDecoder().decode((String) value)));
                        }

                    } else {
                        //若以后有新注解需要处理则在这里添加
                    }
                }
            }
        }

        return result;
    }

    /**
     * 获取指定属性名称的Field
     *
     * @param object
     * @param fieldName 属性名称
     * @return
     */
    private static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {

            }
        }
        return field;
    }


    /**
     * 选中checkbox
     *
     * @param form  PdfAcroForm
     * @param field 文本域名称
     * @param text  选中的值
     */
    public static boolean checked(PdfAcroForm form, String field, String text) {
        if (form == null || org.apache.commons.lang3.StringUtils.isEmpty(field)) {
            return false;
        }
        PdfFormField pf = form.getField(field);
        if (pf != null) {
            pf.setValue(text, false);
            if (!pf.isFlushed()) {
                pf.flush();
            }
            return true;
        }
        return true;
    }

    /**
     * 选中checkbox(默认选中的值是On)
     *
     * @param form  PdfAcroForm
     * @param field 文本域名称
     */
    public static boolean checked(PdfAcroForm form, String field) {
        return checked(form, field, "On");
    }


    /**
     * 向文本域中插入图片
     *
     * @param pdfDoc  PdfDocument
     * @param form    PdfAcroForm
     * @param pageNum pdf文档第几页，从1开始
     * @param field   图片域名称
     * @param bs      字节数组
     */
    public static boolean insertImage(PdfDocument pdfDoc, PdfAcroForm form, int pageNum, String field, byte[] bs) {
        PdfFormField pdfFormField = form.getField(field);
        if (pdfFormField == null) {
            return false;
        }
        PdfDictionary pdfDictionary = pdfFormField.getPdfObject();
        Rectangle rect = pdfDictionary.getAsRectangle(PdfName.Rect);
        float x = rect.getLeft();
        float y = rect.getBottom();
        float width = rect.getWidth();
        float height = rect.getHeight();
        ImageData imageData = ImageDataFactory.create(bs);
        Image image = new Image(imageData);
        image.setWidth(width);
        image.setHeight(height);

        // 创建一个canvas来装载图片
        PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc.getPage(pageNum));
        try (Canvas canvas = new Canvas(pdfCanvas, pdfDoc, rect)) {
            canvas.add(image.setFixedPosition(x, y));
            pdfCanvas.fill();
            canvas.flush();
        }
        pdfDictionary.clear();// 清除区域，不然该区域把图片遮住了(但是form.flattenFields()会报错)
//        form.removeField(field);
        return true;
    }

    /**
     * 向文本域中插入文字
     *
     * @param form     PdfAcroForm
     * @param field    文本域名称
     * @param text     插入文本
     * @param fontSize 字体大小
     */
    public static boolean insertText(PdfAcroForm form, String field, String text, int fontSize) throws Exception {
        if (form == null || org.apache.commons.lang3.StringUtils.isEmpty(field)) {
            return false;
        }
        PdfFormField pf = form.getField(field);
        if (pf != null) {
            try {
                if (pf instanceof PdfTextFormField) {
                    if (pdfFontBytes == null) {
                        //加载字体
                        ClassPathResource resource = new ClassPathResource(FONT_FILE_PATH);
                        if (resource.exists()) {
                            pdfFontBytes = Files.readAllBytes(resource.getFile().toPath());
                        }
                    }
                    font = PdfFontFactory.createFont(pdfFontBytes, PdfEncodings.IDENTITY_H, true);
                    pf.setValue(text, false).setFont(font).setFontSize(fontSize);
                    pf.setVisibility(PdfFormField.VISIBLE);
                    pf.setReadOnly(true);
                }
                if (!pf.isFlushed()) {
                    pf.flush();
                }
            } catch (Exception e) {
                throw new  Exception("pdf初始化中文字体出错", e);
            }
        }
        return true;
    }

    /**
     * 将PDF转换为Image第一页
     *
     * @param data
     * @return
     */
    public static byte[] converPdf2Image(byte[] data) {
        return converPdf2Image(data, 0);
    }

    /**
     * 将PDF转换为Image
     *
     * @param data pdf文件数据
     * @param page 转换第几页
     * @return
     */
    public static byte[] converPdf2Image(byte[] data, Integer page) {

        if (null == data && data.length == 0) {
            return null;
        }

        if (null == page || page < 0) {
            page = 0;
        }

        PDDocument pdf = null;
        PDFRenderer pdfRenderer = null;
        BufferedImage image = null;
        try (
                ByteArrayOutputStream outStream = new ByteArrayOutputStream()
        ) {
            pdf = PDDocument.load(data);
            pdfRenderer = new PDFRenderer(pdf);

            for (int i = 0; i < pdf.getNumberOfPages(); i++) {
                if (i == page.intValue()) {
                    image = pdfRenderer.renderImageWithDPI(i, IMAGE_DPI, ImageType.RGB);
                    break;
                }
            }

            if (image != null) {
                ImageIO.write(image, "jpg", outStream);
                return outStream.toByteArray();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), "PDF转图片出错");
        }

        return null;
    }


}
