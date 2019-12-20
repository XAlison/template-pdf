package com.example.pdf.model;

import com.example.pdf.annotation.ImageDomain;
import com.example.pdf.annotation.TextDomain;
import lombok.Data;

/**
 * 科目一考试成绩单
 *
 * @Auther xiewl
 */
@Data
public class kmksModel {
    /**
     * 姓名
     */
    @TextDomain
    private String xm;
    /**
     * 学习驾驶证明
     */
    @TextDomain
    private String xxjszm;
    /**
     * 身份证明号码
     */
    @TextDomain
    private String sfzmhm;
    /**
     * 报考车型
     */
    @TextDomain
    private String bkcx;
    /**
     * 业务类型
     */
    @TextDomain
    private String ywlx;

    /**
     * 考试日期
     */
    @TextDomain
    private String ksrq;

    /**
     * 预约次数
     */
    @TextDomain
    private String yycs;
    /**
     * 考试地点
     */
    @TextDomain
    private String ksdd;
    /**
     * 身份证明条形码
     */
    @ImageDomain
    private String sfzhmtxm;
    /**
     * 驾驶人头像
     */
    @ImageDomain
    private byte[] photo;

    /**
     * 考试时间
     */
    @TextDomain
    private String kssj;
    /**
     * 考试成绩
     */
    @TextDomain
    private String kscj;
    /**
     * 扣分项
     */
    @TextDomain(fontSize = 10)
    private String kfx;

    /**
     * 考试员签名
     */
    @ImageDomain
    private String ksyqm;
    /**
     * 考生签名
     */
    @ImageDomain
    private String ksqm;
    /**
     * 考试照片1
     */
    @ImageDomain
    private String kszp1;
    /**
     * 考试照片2
     */
    @ImageDomain
    private String kszp2;
    /**
     * 考试照片3
     */
    @ImageDomain
    private String kszp3;

    /**
     * 补考考试时间
     */
    @TextDomain
    private String bkkssj;
    /**
     * 补考考试成绩
     */
    @TextDomain
    private String bkkscj;
    /**
     * 补考扣分项
     */
    @TextDomain(fontSize = 10)
    private String bkkfx;

    /**
     * 补考考试员签名
     */
    @ImageDomain
    private String bkksyqm;
    /**
     * 补考考生签名
     */
    @ImageDomain
    private String bkksqm;
    /**
     * 补考考试照片1
     */
    @ImageDomain
    private String bkkszp1;
    /**
     * 补考考试照片2
     */
    @ImageDomain
    private String bkkszp2;
    /**
     * 补考考试照片3
     */
    @ImageDomain
    private String bkkszp3;

}

