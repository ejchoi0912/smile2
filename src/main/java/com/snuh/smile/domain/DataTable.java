package com.snuh.smile.domain;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataTable {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;

    private List data;

    public List getData(){
        if(CollectionUtils.isEmpty(data)){
            data = new ArrayList();
        }
        return data;
    }

}
