package com.phonesj.news.app;

import java.io.File;

import android.os.Environment;

/**
 * Created by Phone on 2017/7/14.
 * <p>维持常量的类</p>
 */

public class Constants {

    //================= TYPE ====================

    public static final int TYPE_ZHIHU = 101;

    public static final int TYPE_ANDROID = 102;

    public static final int TYPE_IOS = 103;

    public static final int TYPE_WEB = 104;

    public static final int TYPE_WELFARE = 105;

    public static final int TYPE_WECHAT = 106;

    public static final int TYPE_GANK = 107;

    public static final int TYPE_GOLD = 108;

    public static final int TYPE_VTEX = 109;

    public static final int TYPE_SETTING = 110;

    public static final int TYPE_LIKE = 111;

    public static final int TYPE_ABOUT = 112;

    public static final int ZHIHU_COMMON_TYPE_SHORT = 201;

    public static final int ZHIHU_COMMON_TYPE_LONG = 202;

    //================= PATH ====================

    public static final String PATH_DATA = App
        .getInstance()
        .getCacheDir()
        .getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment
        .getExternalStorageDirectory()
        .getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

    //========================sp=======================
    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    //========================intent=======================
    public static final String INTENT_ZHIHU_DETAIL_ID = "zhihu_detail_id";

    public static final String INTENT_ZHIHU_DETAIL_TRANSITION = "zhihu_detail_transition";

    public static final String INTENT_ZHIHU_COMMON_ID = "zhihu_common_id";

    public static final String INTENT_ZHIHU_COMMON_ALL_NUM = "zhihu_common_all_num";

    public static final String INTENT_ZHIHU_COMMONT_SHORT_NUM = "zhihu_commont_short_num";

    public static final String INTENT_ZHIHU_COMMONT_LONG_NUM = "zhihu_commont_long_num";

    public static final String INTENT_ZHIHU_THEME_ID = "zhihu_theme_id";

    public static final String INTENT_ZHIHU_THEME_TITLE = "zhihu_theme_title";

    public static final String INTENT_ZHIHU_SECTION_ID = "zhihu_section_id";

    public static final String INTENT_ZHIHU_SECTION_TITLE = "zhihu_section_title";

    public static final String INTENT_GANK_TITLE = "gank_title";

    public static final String INTENT_GANK_TYPE_CODE = "gank_type_code";

    public static final String INTENT_TECH_DETAIL_ID = "tech_detail_id";

    public static final String INTENT_TECH_DETAIL_IMAGE = "tech_detail_image";

    public static final String INTENT_TECH_DETAIL_TITLE = "tech_detail_title";

    public static final String INTENT_TECH_DETAIL_URL = "tech_detail_url";

    public static final String INTENT_TECH_DETAIL_TYPE = "tech_detail_type";

    public static final String INTENT_WELFARE_DETAIL_ID = "welfare_detail_id";

    public static final String INTENT_WELFARE_DETAIL_URL = "welfare_detail_url";

    //=======================bundle=========================
    public static final String BUNDLE_ZHIHU_COMMON_ID = "zhihu_common_id";

    public static final String BUNDLE_ZHIHU_COMMON_KIND = "zhihu_common_kind";


}
