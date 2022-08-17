package com.youfan.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 身份证相关校验
 *
 * @author zhy
 * @date 2022/2/22 10:09
 */
@Slf4j
public class IdCodeUtils {

    /**
     * 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
     * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
     * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
     * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
     * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
     * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
     */
    public static String[][] codeAndCity = {{"11", "北京"}, {"12", "天津"},
            {"13", "河北"}, {"14", "山西"}, {"15", "内蒙古"}, {"21", "辽宁"},
            {"22", "吉林"}, {"23", "黑龙江"}, {"31", "上海"}, {"32", "江苏"},
            {"33", "浙江"}, {"34", "安徽"}, {"35", "福建"}, {"36", "江西"},
            {"37", "山东"}, {"41", "河南"}, {"42", "湖北"}, {"43", "湖南"},
            {"44", "广东"}, {"45", "广西"}, {"46", "海南"}, {"50", "重庆"},
            {"51", "四川"}, {"52", "贵州"}, {"53", "云南"}, {"54", "西藏"},
            {"61", "陕西"}, {"62", "甘肃"}, {"63", "青海"}, {"64", "宁夏"},
            {"65", "新疆"}, {"71", "台湾"}, {"81", "香港"}, {"82", "澳门"},
            {"91", "国外"}};

    public static String[] cityCode = {"11", "12", "13", "14", "15", "21", "22",
            "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
            "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
            "64", "65", "71", "81", "82", "91"};

    // 每位加权因子
    public static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 第18位校检码
    public static String[] verifyCode = {"1", "0", "X", "9", "8", "7", "6", "5",
            "4", "3", "2"};


    /**
     * 验证身份证吗
     * 验证所有的身份证的合法性
     *
     * @param idCode 身份证
     * @return boolean
     */
    public static boolean isValidatedAllIdCode(String idCode) {
        if (com.youfan.common.utils.StringUtils.isEmpty(idCode)) {
            return false;
        }
        if(!( idCode.length()==18 || idCode.length()==15 )){
            return false;
        }
        if (idCode.length() == 15) {
            idCode = convertIdCodeBy15bit(idCode);
        }
        return isValidate18idCode(idCode);
    }

    /**
     * 是validate18id的名片
     * <p>
     * 判断18位身份证的合法性
     * </p>
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * </p>
     * <p>
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * </p>
     * <p>
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2
     * </p>
     * <p>
     * 2.将这17位数字和系数相乘的结果相加。
     * </p>
     * <p>
     * 3.用加出来和除以11，看余数是多少？
     * </p>
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     * <p>
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     * </p>
     *
     * @param idCode 身份证
     * @return boolean
     */
    public static boolean isValidate18idCode(String idCode) {
        // 非18位为假
        if (com.youfan.common.utils.StringUtils.isEmpty(idCode) || idCode.length() < 18) {
            return false;
        }
        // 获取前17位
        String idCode17 = idCode.substring(0, 17);
        // 获取第18位
        String idCode18Code = idCode.substring(17, 18);
        char[] c = null;
        String checkCode = "";
        // 是否都为数字
        if (isDigital(idCode17)) {
            c = idCode17.toCharArray();
        } else {
            return false;
        }

        int[] bit = new int[idCode17.length()];

        bit = converCharToInt(c);

        int sum17 = 0;

        sum17 = getPowerSum(bit);

        // 将和值与11取模得到余数进行校验码判断
        checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {
            return false;
        }
        // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
        return idCode18Code.equalsIgnoreCase(checkCode);
    }

    /**
     * 是validate15id的名片
     * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
     *
     * @param idCode 身份证
     * @return boolean
     */
    public static boolean isValidate15idCode(String idCode) {
        // 非15位为假
        if (idCode.length() != 15) {
            return false;
        }

        // 是否全都为数字
        if (isDigital(idCode)) {
            String provinceid = idCode.substring(0, 2);
            String birthday = idCode.substring(6, 12);
            int year = Integer.parseInt(idCode.substring(6, 8));
            int month = Integer.parseInt(idCode.substring(8, 10));
            int day = Integer.parseInt(idCode.substring(10, 12));

            // 判断是否为合法的省份
            boolean flag = false;
            for (String id : cityCode) {
                if (id.equals(provinceid)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
            // 该身份证生出日期在当前日期之后时为假
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (birthdate == null || new Date().before(birthdate)) {
                return false;
            }

            // 判断是否为合法的年份
            GregorianCalendar curDay = new GregorianCalendar();
            int curYear = curDay.get(Calendar.YEAR);
            int year2bit = Integer.parseInt(String.valueOf(curYear)
                    .substring(2));

            // 判断该年份的两位表示法，小于50的和大于当前年份的，为假
            if ((year < 50 && year > year2bit)) {
                return false;
            }

            // 判断是否为合法的月份
            if (month < 1 || month > 12) {
                return false;
            }

            // 判断是否为合法的日期
            boolean mflag = false;
            curDay.setTime(birthdate);  //将该身份证的出生日期赋于对象curDay
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    mflag = (day >= 1 && day <= 31);
                    break;
                case 2: //公历的2月非闰年有28天,闰年的2月是29天。
                    if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
                        mflag = (day >= 1 && day <= 29);
                    } else {
                        mflag = (day >= 1 && day <= 28);
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    mflag = (day >= 1 && day <= 30);
                    break;
            }
            if (!mflag) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 把身份证by15bit
     * 将15位的身份证转成18位身份证
     *
     * @param idCode 身份证
     * @return {@link String}
     */
    public static String convertIdCodeBy15bit(String idCode) {
        String idCode17 = null;
        // 非15位身份证
        if (idCode.length() != 15) {
            return null;
        }

        if (isDigital(idCode)) {
            // 获取出生年月日
            String birthday = idCode.substring(6, 12);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cday = Calendar.getInstance();
            assert birthdate != null;
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            idCode17 = idCode.substring(0, 6) + year + idCode.substring(8);

            char c[] = idCode17.toCharArray();
            String checkCode = "";

            int[] bit = new int[idCode17.length()];

            // 将字符数组转为整型数组
            bit = converCharToInt(c);
            int sum17 = 0;
            sum17 = getPowerSum(bit);

            // 获取和值与11取模得到余数进行校验码
            checkCode = getCheckCodeBySum(sum17);
            // 获取不到校验位
            if (null == checkCode) {
                return null;
            }

            // 将前17位与第18位校验码拼接
            idCode17 += checkCode;
        } else { // 身份证包含数字
            return null;
        }
        return idCode17;
    }

    /**
     * isid卡
     * 15位和18位身份证号码的基本数字和位数验校
     *
     * @param idCode 身份证
     * @return boolean
     */
    public boolean isidCode(String idCode) {
        return idCode != null && !"".equals(idCode) && Pattern.matches(
                "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idCode);
    }

    /**
     * is15id卡
     * 15位身份证号码的基本数字和位数验校
     *
     * @param idCode 身份证
     * @return boolean
     */
    public boolean is15idCode(String idCode) {
        return idCode != null && !"".equals(idCode) && Pattern.matches(
                "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
                idCode);
    }

    /**
     * is18id卡
     * 18位身份证号码的基本数字和位数验校
     *
     * @param idCode 身份证
     * @return boolean
     */
    public boolean is18idCode(String idCode) {
        return Pattern
                .matches(
                        "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
                        idCode);
    }

    /**
     * 是数字
     * 数字验证
     *
     * @param str str
     * @return boolean
     */
    public static boolean isDigital(String str) {
        return str != null && !"".equals(str) && str.matches("^[0-9]*$");
    }

    /**
     * 获取权力总和
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit 位
     * @return int
     */
    public static int getPowerSum(int[] bit) {

        int sum = 0;

        if (power.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 获取检查代码通过总和
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17 sum17
     * @return 校验位
     */
    public static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }
        return checkCode;
    }

    /**
     * 转换字符,整数
     * 将字符数组转为整型数组
     *
     * @param c c
     * @return {@link int[]}
     * @throws NumberFormatException 数字格式异常
     */
    public static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    /**
     * 根据身份证号获取出生年月日
     *
     * @param idCode 身份证
     * @return yyyy-MM-dd
     */
    public static Date judgeBirthday(String idCode) {
        if (StringUtils.isBlank(idCode)) {
            return null;
        }
        try {
            if (!isValidatedAllIdCode(idCode)) {
                return null;
            }
            // 出生日期
            String birthday = "";
            // 身份证号不为空
            if (idCode.length() == 15) {
                birthday = "19" + idCode.substring(6, 8) + "-" + idCode.substring(8, 10) + "-" + idCode.substring(10, 12);
            } else if (idCode.length() == 18) {
                birthday = idCode.substring(6, 10) + "-" + idCode.substring(10, 12) + "-" + idCode.substring(12, 14);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            return simpleDateFormat.parse(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    /**
     * 根据身份证号判断性别
     *
     * @param idCode 身份证
     * @return 0:男，1：女
     */
    public static String judgeGenderCode(String idCode) {
        try{
    //        System.out.println(idCode.length());
            if (idCode.length() != 18 && idCode.length() != 15) {
                log.error("身份证号{}长度错误", idCode);
                return null;
            }
            int gender = 0;
            char c;
            if (idCode.length() == 18) {
                //如果身份证号18位，取身份证号倒数第二位
                c = idCode.charAt(idCode.length() - 2);
            } else {
                //如果身份证号15位，取身份证号最后一位
                c = idCode.charAt(idCode.length() - 1);
            }
            gender = Integer.parseInt(String.valueOf(c));
    //        System.out.println("gender = " + gender);
            if (gender % 2 == 1) {
                return "0";
            } else {
                return "1";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String judgeGenderName(String idCode) {
        try{
            //        System.out.println(idCode.length());
            if (idCode.length() != 18 && idCode.length() != 15) {
                log.error("身份证号{}长度错误", idCode);
                return null;
            }
            int gender = 0;
            char c;
            if (idCode.length() == 18) {
                //如果身份证号18位，取身份证号倒数第二位
                c = idCode.charAt(idCode.length() - 2);
            } else {
                //如果身份证号15位，取身份证号最后一位
                c = idCode.charAt(idCode.length() - 1);
            }
            gender = Integer.parseInt(String.valueOf(c));
            //        System.out.println("gender = " + gender);
            if (gender % 2 == 1) {
                return "男";
            } else {
                return "女";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 护照验证
     * 规则： G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
     * 例： G12345678, P1234567
     */
    public static Boolean passportCard(String card) {
        String reg = "^([a-zA-z]|[0-9]){5,17}$";
        if (card.matches(reg) == false) {
            //护照号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    /**
     * 台湾居民来往大陆通行证
     * 规则： 新版8位或18位数字 或 旧版9位数字 + 英文字母 或 8位数字 + 英文字母
     * 样本： 12345678
     */
    public static Boolean isTWCard(String card) {
        String reg = "^\\d{8}|^[a-zA-Z0-9]{10}|^[a-zA-Z0-9]{9}|^\\d{18}$";
        if (card.matches(reg) == false) {
            //台湾居民来往大陆通行证号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    /**
     * 港澳居民来往内地通行证
     * 规则： H/M + 10位或6位数字
     * 例：H1234567890
     */
    public static Boolean isHKCard(String card) {
        String reg = "^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)$";
        if (card.matches(reg) == false) {
            //港澳居民来往内地通行证号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    /**
     * 全面检查，只要符合身份证或护照号都可以过
     *
     * @param idCode 身份证、护照、港澳居民来往内地通行证、台湾居民来往大陆通行证
     * @return 结果
     */
    public static boolean comprehensiveCheck(String idCode) {
        return isValidatedAllIdCode(idCode) || passportCard(idCode) || isTWCard(idCode) || isHKCard(idCode);
    }
}
