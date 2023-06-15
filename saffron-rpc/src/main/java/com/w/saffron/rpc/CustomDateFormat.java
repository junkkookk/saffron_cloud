package com.w.saffron.rpc;


import com.w.saffron.exception.OprException;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author w
 * @since 2023/6/13
 */
public class CustomDateFormat extends DateFormat {
    private DateFormat dateFormat;

    private SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public CustomDateFormat(DateFormat dateFormat){
        this.dateFormat = dateFormat;
        this.calendar = Calendar.getInstance(Locale.CHINA);
        this.numberFormat = NumberFormat.getInstance(Locale.CHINA);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {

        Date date = null;

        try {

            date = format1.parse(source, pos);
        } catch (Exception e) {

            date = dateFormat.parse(source, pos);
        }

        return date;
    }

    // 主要还是装饰这个方法
    @Override
    public Date parse(String source) {

        Date date = null;

        try {

            // 先按我的规则来
            date = format1.parse(source);
        } catch (Exception e) {

            // 不行，那就按原先的规则吧
            try {
                date = dateFormat.parse(source);
            } catch (ParseException ex) {
                throw new OprException(ex.getMessage());
            }
        }

        return date;
    }

    // 这里装饰clone方法的原因是因为clone方法在jackson中也有用到
    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new CustomDateFormat((DateFormat) format);
    }
}
