package com.leo.platform.common.dao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * entity的code配置
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月16日 下午9:34:48
 * 
 */
public class EntityCodePrefixMapping {

    public static final CodeConfig DEFAULT_CONFIG = new CodeConfig("MM", "yyMMdd", 8);

    // public static final Map<Class<?>, CodeConfig> MAP = new HashMap<Class<?>, CodeConfig>();
    public static final Map<String, CodeConfig> MAP = new HashMap<String, CodeConfig>();

    static {
        // cms
        MAP.put("ProSku", new CodeConfig("SKU", "yyMMdd", 6));// skucode生成
        MAP.put("ProCategory", new CodeConfig("PCG", "yyMMdd", 6));// 类目code生成
        MAP.put("ProBrand", new CodeConfig("PB", "yyMMdd", 6));// 品牌code生成
        MAP.put("ProProduct", new CodeConfig("PP", "yyMMdd", 7));// 产品code生成
        MAP.put("ProPackagedGood", new CodeConfig("PPG", "yyMMdd", 7));// 打包品code生成
        MAP.put("ProSubpack", new CodeConfig("PS", "yyMMdd", 7));// 子打包品项code生成
        MAP.put("ProPic", new CodeConfig("PIC", "yyMMdd", 7));// 图片code生成
        MAP.put("ProAttrvalnmRef", new CodeConfig("PAR", "yyMMdd", 7));// 属性名值对code生成
        MAP.put("ProCategoryRef", new CodeConfig("PCR", "yyMMdd", 7));// 类目关联表code生成
        MAP.put("ProAttrnm", new CodeConfig("PAN", "yyMMdd", 7));// 属性值表code生成
        MAP.put("ProAttrval", new CodeConfig("PAV", "yyMMdd", 7));// 属性名表code生成
        // Sys
        MAP.put("Auth", new CodeConfig("A", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Group", new CodeConfig("G", "yyMMdd", 7));// 属性名表code生成
        MAP.put("GroupRelation", new CodeConfig("GR", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Job", new CodeConfig("J", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Organization", new CodeConfig("O", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Permission", new CodeConfig("P", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Resource", new CodeConfig("RES", "yyMMdd", 7));// 属性名表code生成
        MAP.put("Role", new CodeConfig("ROL", "yyMMdd", 7));// 属性名表code生成
        MAP.put("RoleResourcePermission", new CodeConfig("RRP", "yyMMdd", 7));// 属性名表code生成
        MAP.put("User", new CodeConfig("USER", "yyMMdd", 7));// 属性名表code生成
        MAP.put("UserLastOnline", new CodeConfig("ULO", "yyMMdd", 7));// 属性名表code生成
        MAP.put("UserOnline", new CodeConfig("UOL", "yyMMdd", 7));// 属性名表code生成
        MAP.put("UserOrganizationJob", new CodeConfig("UOJ", "yyMMdd", 7));// 属性名表code生成
        MAP.put("UserStatusHistory", new CodeConfig("USH", "yyMMdd", 7));// 属性名表code生成
        // Personal
        MAP.put("Message", new CodeConfig("MES", "yyMMdd", 7));// message表code生成
        MAP.put("MessageContent", new CodeConfig("MESCON", "yyMMdd", 7));// messagecontent表code生成
        MAP.put("Calendar", new CodeConfig("CAL", "yyMMdd", 7));// calendar表code生成
    }

    public static class CodeConfig {
        // code前缀
        private String prefix;
        // 日期格式
        private String dateFormat;
        // 流水号中数字的长度
        private int numberLength;

        public CodeConfig(String prefix, String dateFormat, int numberLength) {
            this.prefix = prefix;
            this.dateFormat = dateFormat;
            this.numberLength = numberLength;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getDateFormat() {
            return dateFormat;
        }

        public int getNumberLength() {
            return numberLength;
        }
    }
}
