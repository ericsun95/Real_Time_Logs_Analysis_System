package com.gatech.project.spark;


import com.gatech.project.dao.LanguageSearchCountDAO;
import com.gatech.project.domain.LanguageSearchCount;
import com.gatech.project.params.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class StatsStreaming {
    @Autowired
    LanguageSearchCountDAO languageSearchCountDAO;

    @RequestMapping(value = "/language_search_dynamic", method = RequestMethod.POST)
    @ResponseBody
    public List<LanguageSearchCount> languageSearchCount() throws Exception {

        List<LanguageSearchCount> list = languageSearchCountDAO.query(Params.TABLENAME, Params.ROWKEY_TEST);
        for(LanguageSearchCount model : list) {
            model.setName(Params.courses.get(model.getName().substring(9)));
        }
        return list;
    }

    @RequestMapping(value = "/echarts", method = RequestMethod.GET)
    public ModelAndView echarts() {
        return new ModelAndView("echarts");
    }
}
