package com.gatech.project.dao;


import com.gatech.project.domain.LanguageSearchCount;
import com.gatech.project.params.Params;
import com.gatech.project.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * click count DAO
 */
@Component
public class LanguageSearchCountDAO {
    /**
     * Query the data from the HBase
     * @param tableName table name
     * @param day day to fetch
     * @return list of Course Click Count
     */
    public List<LanguageSearchCount> query(String tableName, String day) {
        List<LanguageSearchCount> list = new ArrayList<LanguageSearchCount>();
        // fetch from hbase
        HBaseUtils connect = HBaseUtils.getInstance(Params.QUORUM, Params.PORT);
        Map<String, Long> map = connect.query(Params.COLUMN_FAMILY, Params.COLUMN, tableName, day);
        for(Map.Entry<String, Long> entry : map.entrySet()) {
            LanguageSearchCount model = new LanguageSearchCount();
            if(entry.getKey().length() > 9) {
                model.setName(entry.getKey());
                model.setValue(entry.getValue());
                list.add(model);
            }
        }
        return list;
    }
}
