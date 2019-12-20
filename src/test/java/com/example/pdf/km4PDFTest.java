package com.example.pdf;

import com.example.pdf.model.kmksModel;
import com.example.pdf.utils.ImageUtils;
import org.junit.jupiter.api.Test;


/**
 * * @description:科目三安全文明考试成绩单
 *
 * @author: xiewl
 */
public class km4PDFTest {

    @Test
    public void testComposePDF() throws Exception {
        //模板路径
        String templatePath = "pdf/km4_template.pdf";
        //输出路径
        String toPath = "F:\\km4.pdf";

        //获取签名文件 (base64图片)
        String signFilePath = "pdf/sign.jpg";
        String base64Image = ImageUtils.getBase64Image(signFilePath);

        //获取身份证明条形码
        String sfzmTxmPath = "pdf/txm.jpg";
        String sfzmTxm = ImageUtils.getBase64Image(sfzmTxmPath);
        //获取头像文件 (二进制图片)
        String photoFilePath = "pdf/photo.jpg";
        byte[] photoImage = ImageUtils.getImageBytes(photoFilePath);
        //考试照片
        String kszp = ImageUtils.getBase64Image(photoFilePath);

        //构建科目一考试成绩单对象
        kmksModel model = new kmksModel();
        model.setXm("李小刚");
        model.setXxjszm("530102390319");
        model.setSfzmhm("530328199608283318");
        model.setBkcx("B2");
        model.setYwlx("初次申领(一乘驾校)");
        model.setKsrq("2019-10-01");
        model.setYycs("1");
        model.setKsdd("昆明晋宁区考场科目一考场");
        model.setSfzhmtxm(sfzmTxm);
        model.setPhoto(photoImage);
        model.setKssj("2018-12-20  14:02- 14:21");
        model.setKscj("70");
        model.setKsyqm(base64Image);
        model.setKsqm(base64Image);
        model.setKszp1(kszp);
        model.setKszp2(kszp);
        model.setKszp3(kszp);
        model.setBkkssj("2018-12-20  14:24- 14:43");
        model.setBkkscj("100");
        model.setBkksyqm(base64Image);
        model.setBkksqm(base64Image);
        model.setBkkszp1(kszp);
        model.setBkkszp2(kszp);
        model.setBkkszp3(kszp);
        ImageUtils.createPdfTemplate(templatePath, toPath, model);
    }
}
