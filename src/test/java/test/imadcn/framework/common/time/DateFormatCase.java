package test.imadcn.framework.common.time;

import com.imadcn.framework.common.time.DateFormatUtil;
import java.sql.Time;
import org.testng.annotations.Test;

/**
 * @author Hinsteny
 * @version $ID: DateFormatCase 2019-01-02 10:18 All rights reserved.$
 */
public class DateFormatCase {

    @Test
    public void  testTimeIsBetween() {
        Time now = Time.valueOf("09:00:00");
        Time start = Time.valueOf("00:15:00");
        Time end = Time.valueOf("08:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end, now));

        start = Time.valueOf("09:15:00");
        end = Time.valueOf("12:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end, now));

        start = Time.valueOf("00:15:00");
        end = Time.valueOf("12:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end, now));

        start = Time.valueOf("08:15:00");
        end = Time.valueOf("02:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end, now));

        now = Time.valueOf("01:00:00");
        start = Time.valueOf("08:15:00");
        end = Time.valueOf("02:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end, now));

        start = Time.valueOf("08:15:00");
        end = Time.valueOf("02:15:00");
        System.out.println(DateFormatUtil.isBetween(start, end));
    }
}
