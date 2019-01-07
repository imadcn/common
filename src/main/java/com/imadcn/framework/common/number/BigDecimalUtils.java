package com.imadcn.framework.common.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * BigDecimal 相关常用操作方法
 *
 * @author Hinsteny
 * @version $ID: BigDecimalUtils 2019-01-07 14:50 All rights reserved.$
 */
public class BigDecimalUtils {

    /**
     * 货币保留两位小数
     */
    public static final int MONEY_POINT = 2;

    /**
     * 格式化浮点数
     *
     * @param value 输入值
     */
    public static String formatMoney(BigDecimal value) {
        return formatRoundUp(value, MONEY_POINT, RoundingMode.HALF_DOWN);
    }

    /**
     * 格式化浮点数
     *
     * @param value 输入值
     * @param point 小数位数
     */
    public static String formatRoundUp(BigDecimal value, int point) {
        return formatRoundUp(value, point, RoundingMode.HALF_UP);
    }

    /**
     * 格式化浮点数
     *
     * @param value 输入值
     * @param point 小数位数
     * @param roundingMode 舍位方式
     */
    public static String formatRoundUp(BigDecimal value, int point, RoundingMode roundingMode) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置四舍五入
        nf.setRoundingMode(roundingMode);
        //设置最小保留几位小数
        nf.setMinimumFractionDigits(point);
        //设置最大保留几位小数
        nf.setMaximumFractionDigits(point);
        return nf.format(value);
    }

    /**
     * 格式化精度
     *
     * @param value 输入值
     * @param point 小数位数
     * @return double
     */
    public static Double format(double value, int point) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化浮点数
     *
     * @param value 输入值
     * @param point 小数位数
     */
    public static String formatRoundUp(double value, int point) {
        return formatRoundUp(new BigDecimal(value), point, RoundingMode.HALF_UP);
    }

    /**
     * 格式化浮点数
     *
     * @param value 输入值
     * @param point 小数位数
     * @param roundingMode 舍位方式
     */
    public static String formatRoundUp(double value, int point, RoundingMode roundingMode) {
        return formatRoundUp(new BigDecimal(value), point, roundingMode);
    }

    /**
     * BigDecimal 相加
     *
     * @param addend 加数
     * @param augend 被加数
     * @return double
     */
    public static Double add(double addend, double augend) {
        BigDecimal m = new BigDecimal(Double.toString(addend));
        BigDecimal n = new BigDecimal(Double.toString(augend));
        return m.add(n).doubleValue();
    }

    /**
     * BigDecimal 相数
     *
     * @param subtrahend 减数
     * @param bySubtrahend 被加数
     * @return double
     */
    public static Double subtract(double subtrahend, double bySubtrahend) {
        BigDecimal m = new BigDecimal(Double.toString(subtrahend));
        BigDecimal n = new BigDecimal(Double.toString(bySubtrahend));
        return m.subtract(n).doubleValue();
    }

    /**
     * BigDecimal 相乘
     *
     * @param multiplier 减数
     * @param multiplicand 被加数
     * @return double
     */
    public static Double multiply(double multiplier, double multiplicand) {
        BigDecimal m = new BigDecimal(Double.toString(multiplier));
        BigDecimal n = new BigDecimal(Double.toString(multiplicand));
        return m.multiply(n).doubleValue();
    }

    /**
     * BigDecimal 相乘
     *
     * @param divisor 减数
     * @param dividend 被加数
     * @return double
     */
    public static Double divide(double divisor, double dividend) {
        BigDecimal m = new BigDecimal(Double.toString(divisor));
        BigDecimal n = new BigDecimal(Double.toString(dividend));
        return m.divide(n, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * BigDecimal 相比较
     *
     * @param former  前者
     * @param latter  后者
     * @return double
     */
    public int compare(double former , double latter) {
        BigDecimal m = new BigDecimal(Double.toString(former ));
        BigDecimal n = new BigDecimal(Double.toString(latter));
        return m.compareTo(n);
    }

}
