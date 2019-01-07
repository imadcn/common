package test.imadcn.framework.common.number;

import com.imadcn.framework.common.number.BigDecimalUtils;
import java.io.IOException;
import java.math.BigDecimal;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Hinsteny
 * @version $ID: BigDecimalUtilsTest 2019-01-07 15:21 All rights reserved.$
 */
public class BigDecimalUtilsTest {

    @Test
    public void testReplaceSensInfo() throws IOException {
        Assert.assertEquals(111.12, BigDecimalUtils.format(111.123, 2));
        Assert.assertEquals(111.13, BigDecimalUtils.format(111.125, 2));

        Assert.assertEquals("111.12", BigDecimalUtils.formatRoundUp(111.121, 2));
        Assert.assertEquals("111.13", BigDecimalUtils.formatRoundUp(111.125, 2));

        Assert.assertEquals("111.12", BigDecimalUtils.formatRoundUp(new BigDecimal("111.123"), 2));
        Assert.assertEquals("111.13", BigDecimalUtils.formatRoundUp(new BigDecimal("111.125"), 2));
        Assert.assertEquals("111.10", BigDecimalUtils.formatRoundUp(new BigDecimal("111.1"), 2));
        Assert.assertEquals("111.00", BigDecimalUtils.formatRoundUp(new BigDecimal("111"), 2));
        Assert.assertEquals("-111.12", BigDecimalUtils.formatRoundUp(new BigDecimal("-111.123"), 2));
        Assert.assertEquals("-111.13", BigDecimalUtils.formatRoundUp(new BigDecimal("-111.125"), 2));

        Assert.assertEquals("111.12", BigDecimalUtils.formatMoney(new BigDecimal("111.123")));
        Assert.assertEquals("111.12", BigDecimalUtils.formatMoney(new BigDecimal("111.125")));
        Assert.assertEquals("111.10", BigDecimalUtils.formatMoney(new BigDecimal("111.1")));
        Assert.assertEquals("111.00", BigDecimalUtils.formatMoney(new BigDecimal("111")));

        Assert.assertEquals(222.242, BigDecimalUtils.add(111.121, 111.121));
        Assert.assertEquals(10.11, BigDecimalUtils.subtract(111.121, 101.011));
        Assert.assertEquals(233.100, BigDecimalUtils.multiply(111.00, 2.1));
        Assert.assertEquals(11.011, BigDecimalUtils.divide(121.121, 11));

    }

}
