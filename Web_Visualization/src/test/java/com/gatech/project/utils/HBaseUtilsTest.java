package com.gatech.project.utils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import com.gatech.project.params.Params;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HBaseUtilsTest {
    @Test
    public void HBaseUtilsTest() {
        long value = 2;
        HBaseUtils connect = HBaseUtils.getInstance(Params.QUORUM, Params.PORT);
        connect.put(Params.TABLENAME, Params.ROWKEY_TEST, Params.COLUMN_FAMILY, Params.COLUMN, value);

        Map<String, Long> map = connect.query(Params.COLUMN_FAMILY, Params.COLUMN, Params.TABLENAME, Params.ROWKEY_TEST);
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            if(entry.getKey() == "20200328") {
                assert(entry.getValue() == value);
            }
        }
    }

}