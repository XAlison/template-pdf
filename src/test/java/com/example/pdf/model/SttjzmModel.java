package com.example.pdf.model;

import com.example.pdf.annotation.CheckBoxDomain;
import com.example.pdf.annotation.ImageDomain;
import com.example.pdf.annotation.TextDomain;
import lombok.Data;

/**
 * 身体条件证明
 *
 * @Auther xiewl
 */
@Data
public class SttjzmModel {
    /**
     * 姓名
     */
    @TextDomain
    private String xm;
    /**
     * 性别名称
     */
    @TextDomain
    private String xbmc;
    /**
     * 出生日期
     */
    @TextDomain
    private String csrq;
    /**
     * 国籍
     */
    @TextDomain
    private String gjmc;
    /**
     * 身份证名称
     */
    @TextDomain
    private String sfzmmc = "居民身份证";
    /**
     * 身份证号码
     */
    @TextDomain(isSplit = true)
    private String sfzmhm;

    /**
     * 准驾车型代号
     */
    @TextDomain
    private String zjcxdh;
    /**
     * 档案编号
     */
    @TextDomain
    private String dabh;
    /**
     * 邮寄地址
     */
    @TextDomain
    private String yjdz;
    /**
     * 联系电话
     */
    @TextDomain
    private String lxdh;
    /**
     * 申请人头像
     */
    @ImageDomain
    private byte[] base_photo;
    /**
     * 申告事项-具有
     */
    @CheckBoxDomain
    private Boolean sgsx_jy;
    /**
     * 申告事项-不具有
     */
    @CheckBoxDomain
    private Boolean sgsx_bjy;
    /**
     * 申告事项-器质性心脏病
     */
    @CheckBoxDomain
    private Boolean sgsx_qzxxzb;
    /**
     * 申告事项-癫痫
     */
    @CheckBoxDomain
    private Boolean sgsx_dx;
    /**
     * 申告事项-美尼尔氏症
     */
    @CheckBoxDomain
    private Boolean sgsx_mnesz;

    /**
     * 申告事项-眩晕
     */
    @CheckBoxDomain
    private Boolean sgsx_xy;
    /**
     * 申告事项-癔病
     */
    @CheckBoxDomain
    private Boolean sgsx_yb;
    /**
     * 申告事项-震颤麻痹
     */
    @CheckBoxDomain
    private Boolean sgsx_zzmb;
    /**
     * 申告事项-精神病
     */
    @CheckBoxDomain
    private Boolean sgsx_jsb;
    /**
     * 申告事项-痴呆
     */
    @CheckBoxDomain
    private Boolean sgsx_cd;
    /**
     * 申告事项-影响肢体活动的神经系统疾病等妨碍安全驾驶疾病
     */
    @CheckBoxDomain
    private Boolean sgsx_ztjb;
    /**
     * 申告事项-三年内有吸食、注射毒品行为或者解除强制隔离戒毒措施未满三年，或者长期服用依
     */
    @CheckBoxDomain
    private Boolean sgsx_jsjb;
    /**
     * 个人身高
     */
    @TextDomain
    private String sg;
    /**
     * 辨色力-有红绿色盲
     */
    @CheckBoxDomain
    private Boolean yhlsm;
    /**
     * 辨色力-无红绿色盲
     */
    @CheckBoxDomain
    private Boolean whlsm;

    /**
     * 视力-左眼
     */
    @TextDomain
    private String sl_zysl;
    /**
     * 视力-右眼
     */
    @TextDomain
    private String sl_yysl;
    /**
     * 视力-有单眼视力障碍
     */
    @CheckBoxDomain
    private Boolean sl_ydslza;
    /**
     * 视力-无单眼视力障碍
     */
    @CheckBoxDomain
    private Boolean sl_wdslza;
    /**
     * 视力-优眼水平视野
     */
    @TextDomain
    private String sl_yyspsy;
    /**
     * 视力-左眼有矫(是)
     */
    @CheckBoxDomain
    private Boolean sl_zysfjzs;
    /**
     * 视力-左眼无矫正(否)
     */
    @CheckBoxDomain
    private Boolean sl_zysfjzf;
    /**
     * 视力-右眼有矫(是)
     */
    @CheckBoxDomain
    private Boolean sl_yysfjzs;
    /**
     * 视力-右眼无矫正(否)
     */
    @CheckBoxDomain
    private Boolean sl_yysfjzf;

    /**
     * 听力-佩戴助听装置(是)
     */
    @CheckBoxDomain
    private Boolean tl_ypdzltz;
    /**
     * 听力-佩戴助听装置(否)
     */
    @CheckBoxDomain
    private Boolean tl_wpdzltz;
    /**
     * 听力-左耳
     */
    @TextDomain
    private String tl_ze;
    /**
     * 听力-右耳
     */
    @TextDomain
    private String tl_ye;

    /**
     * 躯干和颈部-运动功能障碍(有)
     */
    @CheckBoxDomain
    private Boolean qg_yydgnza;
    /**
     * 躯干和颈部-运动功能障碍(无)
     */
    @CheckBoxDomain
    private Boolean qg_wydgnza;
    /**
     * 上肢-左上肢
     */
    @TextDomain
    private String sz_zsz;
    /**
     * 上肢-右上肢
     */
    @TextDomain
    private String sz_ysz;
    /**
     * 下肢-左下肢
     */
    @TextDomain
    private String xz_zxz;
    /**
     * 下肢-右下肢
     */
    @TextDomain
    private String xz_yxz;
    /**
     * 双下肢缺或者丧失运动功能障碍是否能否坐立(是)
     */
    @CheckBoxDomain
    private Boolean xz_ygnza;
    /**
     * 双下肢缺或者丧失运动功能障碍是否能否坐立(否)
     */
    @CheckBoxDomain
    private Boolean xz_wgnza;

    /**
     * 医疗机构公章
     */
    @ImageDomain
    private byte[] base_yljggz;
    /**
     * 医疗机构盖章日期
     */
    @TextDomain
    private String ylgzrq;
    /**
     * 申请方式-本人申请
     */
    @CheckBoxDomain
    private Boolean sqfs_brsq;
    /**
     * 申请方式-委托
     */
    @CheckBoxDomain
    private Boolean sqfs_wt;
    /**
     * 申请方式-委托人
     */
    @TextDomain
    private String sqfs_wtr;

    /**
     * 委托代理人信息-姓名
     */
    @TextDomain
    private String dlr_xm;
    /**
     * 委托代理人信息-身份证名称
     */
    @TextDomain
    private String dlr_sfzmc;
    /**
     * 委托代理人信息-号码
     */
    @TextDomain
    private String dlr_hm;
    /**
     * 委托代理人信息-联系地址
     */
    @TextDomain
    private String dlr_lxdz;
    /**
     * 委托代理人信息-电话
     */
    @TextDomain
    private String dlr_dh;
    /**
     * 申请人签字
     */
    @ImageDomain
    private String sqrqz;
    /**
     * 医生签字
     */
    @ImageDomain
    private String ysqz;
    /**
     * 代理人签字
     */
    @ImageDomain
    private String dlrqz;


}

