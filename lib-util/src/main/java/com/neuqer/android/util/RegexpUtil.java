package com.neuqer.android.util;

import java.util.regex.Pattern;

/**
 * @author fhyPayaso
 * @since 2017/12/26 on 下午4:10
 * fhyPayaso@qq.com
 */
public class RegexpUtil {


    /**
     * 验证全部为数字
     */
    private static final String NUMBER_REGEX = "^[0-9]*$";

    /**
     * 手机号正则
     */
    private static final String MOBILE_REGEX = "[1][3,4,5,7,8][0-9]{9}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";


    /**
     * 车牌号正则
     */
    private static final String PLATE_NUMBER_REGEX = "^[\u0391-\uFFE5]{1}[a-zA-Z0-9]{6}$";


    /**
     * 身份证号正则（18位）
     */
    private static final String ID_CARD_REGEX = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";


    /**
     * URL正则
     */
    private static final String URL_REGEX = "[a-zA-z]+://[^\\s]*";


    /**
     * IP地址正则
     */
    public static final String IP_REGEX = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";


    /**
     * 银行卡号正则
     */
    public static final String BANK_CARD_REGEX = "^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$";


    /**
     * 邮政编码正则
     */
    private static final String POSTAL_CODE_REGEX = "\\d{6}";


    /**
     * 验证全部为数字正则
     *
     * @param number
     * @return
     */
    public static boolean checkAllNumber(String number) {
        return Pattern.matches(NUMBER_REGEX, number);
    }

    /**
     * 验证手机号正则
     *
     * @param phone
     * @return
     */
    public static boolean checkMobile(String phone) {
        return Pattern.matches(MOBILE_REGEX, phone);
    }

    /**
     * 验证邮箱正则
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * 验证车牌号正则
     *
     * @param plateNumber
     * @return
     */
    public static boolean checkPlateNumber(String plateNumber) {
        return Pattern.matches(PLATE_NUMBER_REGEX, plateNumber);
    }


    /**
     * 验证身份证号正则
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        return Pattern.matches(ID_CARD_REGEX, idCard);
    }


    /**
     * 验证url正则
     *
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        return Pattern.matches(URL_REGEX, url);
    }


    /**
     * 验证ip正则
     *
     * @param ip
     * @return
     */
    public static boolean checkIP(String ip) {
        return Pattern.matches(IP_REGEX, ip);
    }


    /**
     * 验证银行卡号正则
     *
     * @param bankCard
     * @return
     */
    public static boolean checkBankCard(String bankCard) {
        return Pattern.matches(BANK_CARD_REGEX, bankCard);
    }


    /**
     * 验证邮政编码正则
     *
     * @param postalCode
     * @return
     */
    public static boolean checkPostalCode(String postalCode) {
        return Pattern.matches(POSTAL_CODE_REGEX, postalCode);
    }


}
