package com.patel.akif.meat_o;

import com.patel.akif.meat_o.dto.SaleUnit;
import com.patel.akif.meat_o.utils.CommonUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(250, CommonUtils.calculateAmountByQuantity(50, 1, 5f), 0);
    }
}