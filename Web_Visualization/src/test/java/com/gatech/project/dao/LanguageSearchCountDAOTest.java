package com.gatech.project.dao;

import com.gatech.project.domain.LanguageSearchCount;
import com.gatech.project.params.Params;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class LanguageSearchCountDAOTest {
    @Test
    public void LanguageSearchCountDAOTest() {
        String tableName = Params.TABLENAME;
        Long check = new Long(3);
        LanguageSearchCountDAO dao = new LanguageSearchCountDAO();
        List<LanguageSearchCount> list = dao.query(tableName, Params.ROWKEY_TEST);
        for (LanguageSearchCount model : list) {
            System.out.println(model.getName() + " : " + model.getValue());
            if(model.getName() == "20200328") {
                assert(model.getValue() == check);
            }
        }
    }
}