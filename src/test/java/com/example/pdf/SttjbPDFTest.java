package com.example.pdf;

import com.example.pdf.model.SttjzmModel;
import com.example.pdf.utils.ImageUtils;
import org.junit.jupiter.api.Test;

/**
 * @description: 身体证明条件申请表
 * @author: xiewl
 */
public class SttjbPDFTest {

    @Test
    public void testComposePDF() throws Exception {
        String templatePath = "pdf/sttjzm_template.pdf";
        //输出的文件地址
        String toPath = "F:\\Sttjzm.pdf";

        //获取签名文件 (base64图片)
        String signFilePath = "pdf/sign.jpg";
        String base64Image = ImageUtils.getBase64Image(signFilePath);
        //获取头像文件 (二进制图片)
        String photoFilePath = "pdf/photo.jpg";
        byte[] photoImage = ImageUtils.getImageBytes(photoFilePath);

        //构建身体条件证明对象
        SttjzmModel model = new SttjzmModel();
        model.setXm("李小刚");
        model.setXbmc("男");
        model.setCsrq("1992年8月15日");
        model.setGjmc("中国");
        model.setSfzmmc("居民身份证");
        model.setSfzmhm("5303221992081507XX");
        model.setZjcxdh("C1");
        model.setDabh("530101009453");
        model.setYjdz("云南省昆明市盘龙区官方广场7楼");
        model.setLxdh("15087429690");
        model.setBase_photo(photoImage);
        model.setSgsx_jy(true);
        model.setSgsx_bjy(true);
        model.setSgsx_qzxxzb(true);
        model.setSgsx_dx(true);
        model.setSgsx_mnesz(true);
        model.setSgsx_xy(true);
        model.setSgsx_yb(true);
        model.setSgsx_zzmb(true);
        model.setSgsx_jsb(true);
        model.setSgsx_cd(true);
        model.setSgsx_ztjb(true);
        model.setSgsx_jsjb(true);
        model.setSg("178");
        model.setYhlsm(true);
        model.setWhlsm(true);
        model.setSl_zysl("5.0");
        model.setSl_yysl("5.0");
        model.setSl_ydslza(true);
        model.setSl_wdslza(true);
        model.setSl_yyspsy("正常");
        model.setSl_zysfjzs(true);
        model.setSl_zysfjzf(true);
        model.setSl_yysfjzs(true);
        model.setSl_yysfjzf(true);
        model.setTl_ypdzltz(true);
        model.setTl_wpdzltz(true);
        model.setTl_ze("50cm");
        model.setTl_ye("50cm");
        model.setQg_yydgnza(true);
        model.setQg_wydgnza(true);
        model.setSz_zsz("合格");
        model.setSz_ysz("合格");
        model.setXz_yxz("合格");
        model.setXz_zxz("合格");
        model.setXz_ygnza(true);
        model.setXz_wgnza(true);
        model.setBase_yljggz(photoImage);
        model.setYlgzrq("2019年10月01日");
        model.setSqfs_brsq(true);
        model.setSqfs_wt(true);
        model.setSqfs_wtr("张泽坤");
        model.setDlr_xm("朱小明");
        model.setDlr_sfzmc("居民身份证");
        model.setDlr_hm("5303221992081512XX");
        model.setDlr_lxdz("云南省昆明市盘龙区新迎新城官方广场17楼");
        model.setDlr_dh("15087429600");
        model.setSqrqz(base64Image);
        model.setYsqz(base64Image);
        model.setDlrqz(base64Image);
        ImageUtils.createPdfTemplate(templatePath, toPath, model);

    }

}
