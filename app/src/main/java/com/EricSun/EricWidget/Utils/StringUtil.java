package com.EricSun.EricWidget.Utils;


import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java字符串工具类
 *
 * @author Administrator
 */
public class StringUtil {

    /**
     * sha1加密
     *
     * @param paramString
     * @return
     */
    public static String sha1(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest
                    .getInstance("SHA-1");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * md5加密
     *
     * @param paramString
     * @return
     */
    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toLowerCase());
        }
        return hexString.toString();
    }

    /**
     * 获取当前的时间
     *
     * @param created
     * @return
     */
    public static String getUploadtime(long created) {
        StringBuffer when = new StringBuffer();
        int difference_seconds;
        int difference_minutes;
        int difference_hours;
        int difference_days;
        int difference_months;
        long curTime = System.currentTimeMillis();
//        difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
//        if (difference_months > 0) {
//            when.append(difference_months + "月");
//        }

        difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
        if (difference_days > 0) {
            when.append(difference_days + "天");
        }

        difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
        if (difference_hours > 0) {
            when.append(difference_hours + "小时");
        }

        difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
        if (difference_minutes > 0) {
            when.append(difference_minutes + "分钟");
        }

        difference_seconds = (int) ((curTime % 60) - (created % 60));
        if (difference_seconds > 0) {
            when.append(difference_seconds + "秒");
        }

        return when.append("前").toString();
    }

    /**
     * 格式化百分比，#代表一个数字占位符，不包括‘0’，0代表一个数字， DecimalFormat percentFormat = new
     * DecimalFormat("##.00%");
     *
     * @param d
     * @return
     */
    public static String formatPercent(double d) {
        DecimalFormat df = new DecimalFormat("##.##%");
        return df.format(d);
    }

    /**
     * 格式化本地货币符号
     *
     * @param d
     * @return
     */
    public static String formatLocalCurrency(double d) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String result = currencyFormat.format(d);
        return result.substring(1);
    }

    /**
     * 按条件格式化数字 格式化百分比，#代表一个数字占位符，不包括‘0’，0代表一个数字， DecimalFormat percentFormat =
     * new DecimalFormat("##.00%");
     *
     * @param d
     * @param formatString
     * @return
     */
    public static String formatNumber(double d, String formatString) {
        DecimalFormat df = new DecimalFormat(formatString);
        return df.format(d);
    }


    /**
     * 英文数字混合规则
     *
     * @param transaction
     * @return
     */
    public static boolean isValidPassword(String transaction) {
        boolean isValidWord = false;
        boolean isVaildNumber = false;
        String expression = ".*[0-9]+.*";
        CharSequence inputStr = transaction;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValidWord = true;
        }

        String expression1 = ".*[a-zA-z]+.*";

        CharSequence inputStr1 = transaction;
        Pattern pattern1 = Pattern.compile(expression1);
        Matcher matcher1 = pattern1.matcher(inputStr1);
        if (matcher1.matches()) {
            isVaildNumber = true;
        }

        if (isVaildNumber == true && isValidWord == true)
            return true;
        else
            return false;
    }


    /**
     * 是否为真实姓名
     *
     * @param name
     * @return
     */
    public static boolean isValidName(String name) {
        boolean result = false;
        if (name == null || name.length() <= 0) {
            return result;
        }
        String expression = "^[A-Za-z\\u4e00-\\u9fa5]+$";
        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            result = true;
        }
        return result;
    }

    /**
     * 校验身份证件是否合法
     *
     * @param id
     * @return
     */
    public static boolean validId18(String id) {

        int[] powers = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2};

        String[] parityBit = new String[]{"1", "0", "X", "9", "8", "7", "6",
                "5", "4", "3", "2"};

        String sex = "";

        if (id.length() != 18)
            return false;
        String num = id.substring(0, 17);
        String last = id.substring(17);
        int power = 0;
        for (int i = 0; i < 17; i++) {
            if (num.charAt(i) < '0' || num.charAt(i) > '9') {
                return false;
            } else {
                String s = String.valueOf(num.charAt(i));
                power += Integer.parseInt(s) * powers[i];
                if (i == 16 && Integer.parseInt(s) % 2 == 0) {
                    sex = "female";
                } else {
                    sex = "male";
                }
            }
        }

        int mod = power % 11;
        if (parityBit[mod].equals(last)) {
            return true;
        }
        return false;
    }

    /**
     * 1.鍒ゆ柇瀛楃涓叉槸鍚︿粎涓烘暟瀛�
     *
     * @param str
     * @return
     */

    public static boolean isNumeric1(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 鐢ㄦ鍒欒〃杈惧紡 鍒ゆ柇瀛楃涓叉槸鍚︿粎涓烘暟瀛�
     *
     * @param str
     * @return
     */
    public static boolean isNumeric2(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 检验邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean res = false;
        String expression = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            res = true;
        }
        return res;
    }

    /**
     * 检验是否为手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isTelephoneNumber(String mobile) {
        boolean res = false;
        String expression = "^(13|14|15|17|18)[0-9]{9}$";
        CharSequence inputStr = mobile;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            res = true;
        }
        return res;
    }

    /**
     * 检查销售单号是否正确
     *
     * @param saleNumber
     * @return
     */
    public static boolean isSaleNumber(String saleNumber) {
        boolean res = false;
        String expression = "^([a-zA-Z]{2}[0-9]{14})$";
        CharSequence inputStr = saleNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            res = true;
        }
        return res;
    }

    /**
     * 检查是否为座机号码
     *
     * @param phone
     * @return
     */
    public static boolean isFixLineTelephoneNumber(String phone) {
        boolean res = false;
        String expression = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
        CharSequence inputStr = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            res = true;
        }
        return res;
    }

    /**
     * 判断数字
     *
     * @param str
     * @return true 全部数字，false 不全为数字
     */
    public static boolean isNumeric3(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    /**
     * 2.判断首字母为英文（不区分大小写）
     *
     * @param s
     * @return
     */
    public static boolean test(String s) {
        char c = s.charAt(0);
        int i = c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断首字母为英文（不区分大小写）
     *
     * @param fstrData
     * @return
     */
    public static boolean check(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符是GB2312编码
     *
     * @param str
     * @return boolean
     */
    public static boolean vd(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String formatDate() {
        return getCurrentDate();
    }

    /**
     * eric
     *
     * @param cTime
     * @param format "yyyy/MM/dd HH:mm"
     * @return
     */
    public static String formatUnixTime(Date cTime, String format) {
        SimpleDateFormat formatString = new SimpleDateFormat(format);
        return formatString.format(cTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    public static String getCurrentDate() {
        Date currentTime = new Date();
        return formatUnixTime(currentTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间字符串
     *
     * @param cTime  long (ms)
     * @param format
     * @return
     */
    public static String formatUnixTime(long cTime, String format) {
        return formatUnixTime(new Date(cTime), format);
    }

    /**
     * 倒计时
     *
     * @param cTime
     * @return
     */
    public static String formatCountDownTime(long cTime) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = (int) (cTime / 1000) % 60;
        minute = (int) (cTime / (1000 * 60)) % 60;
        hour = (int) (cTime / (1000 * 60 * 60));
        return StringUtil.formatNumber(hour, "00")
                + ":"
                + StringUtil.formatNumber(minute, "00")
                + ":"
                + StringUtil.formatNumber(second, "00");
    }

    /**
     * 倒计时
     *
     * @param cTime
     * @return
     */
    public static String formatDayTime(long cTime) {
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = (int) (cTime / 1000) % 60;
        minute = (int) (cTime / (1000 * 60)) % 60;
        hour = (int) (cTime / (1000 * 60 * 60)) % 24;
        day = (int) (cTime / (1000 * 86400));

        return StringUtil.formatNumber(day, "0")
                + "天"
                + StringUtil.formatNumber(hour, "00")
                + "时"
                + StringUtil.formatNumber(minute, "00")
                + "分"
                + StringUtil.formatNumber(second, "00")
                + "秒";
    }

    /**
     * 获取随机时间
     *
     * @return
     */
    public static String getRandomString() {
        StringBuffer result = new StringBuffer();
        result.append(String.valueOf((int) (Math.random() * 10))).append("_");
        String str = formatUnixTime(new Date(System.currentTimeMillis()),
                "yyyy-MM-dd_HH:mm:ss");
        result.append(str);
        return result.toString();
    }

    /**
     * 是否为网址
     *
     * @param data
     * @return
     */
    public static boolean hasScheme(String data) {
        boolean is = false;
        if (null != data && data.toUpperCase().contains("HTTP")) {
            is = true;
        }
        return is;
    }

    public static String stringBanknameWithCode(String code) {
        String res = "";
        if ("ICBC".equalsIgnoreCase(code)) {
            res = "工商银行";
        } else if ("CCB".equalsIgnoreCase(code)) {
            res = "建设银行";
        } else if ("BOC".equalsIgnoreCase(code)) {
            res = "中国银行";
        } else if ("ABC".equalsIgnoreCase(code)) {
            res = "中国农业银行";
        } else if ("CEB".equalsIgnoreCase(code)) {
            res = "中国光大银行";
        } else if ("SPDB".equalsIgnoreCase(code)) {
            res = "浦发银行";
        } else if ("CIB".equalsIgnoreCase(code)) {
            res = "兴业银行";
        } else if ("BOCO".equalsIgnoreCase(code)) {
            res = "交通银行";
        } else if ("CMBCHINA".equalsIgnoreCase(code)) {
            res = "招商银行";
        } else if ("CMBC".equalsIgnoreCase(code)) {
            res = "民生银行";
        } else if ("HXB".equalsIgnoreCase(code)) {
            res = "华夏银行";
        } else if ("ECITIC".equalsIgnoreCase(code)) {
            res = "中信银行";
        } else if ("GDB".equalsIgnoreCase(code)) {
            res = "广发银行";
        }
        return res;
    }


    /*
    public static int imageBanknameWithCode(String code) {
        int image = R.mipmap.bank_gongshang;
        if ("ICBC".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_gongshang;
        } else if ("CCB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_jianshe;
        } else if ("BOC".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_china;
        } else if ("ABC".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_nongye;
        } else if ("CEB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_guangda;
        } else if ("SPDB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_pufa;
        } else if ("CIB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_xingye;
        } else if ("BOCO".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_jiaotong;
        } else if ("CMBCHINA".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_zhaoshang;
        } else if ("CMBC".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_minsheng;
        } else if ("HXB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_huaxia;
        } else if ("ECITIC".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_zhongxin;
        } else if ("GDB".equalsIgnoreCase(code)) {
            image = R.mipmap.bank_guangfa;
        }
        return image;
    }
*/
    /**
     * 鏌ユ壘xml涓嬬涓�釜鍑虹幇鐨勫叧閿�
     *
     * @param xml  瀛楃婧�
     * @param name key鍊�
     */
    public String getXmlValue(String xml, String name) {

        int beginIndex = xml.indexOf("<" + name + ">");
        int endIndex = xml.indexOf("</" + name + ">");
        if (beginIndex == -1 || endIndex == -1) {
            return null;
        }
        String retsult = xml
                .substring(beginIndex + name.length() + 2, endIndex);
        return retsult;
    }


}
