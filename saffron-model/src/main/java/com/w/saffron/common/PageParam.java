package com.w.saffron.common;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author w
 * @since 2023/3/15
 */
@UtilityClass
public class PageParam {

    public PageRequest of(Integer page,Integer size){
        if (page==null||page==0|| page<0){
            page=1;
        }
        if (size==null||size>100){
            size=20;
        }
        return PageRequest.of(page - 1,size).withSort(Sort.by(Sort.Direction.DESC,"updateTime","createTime"));
    }

    public PageRequest firstPage(){
        return of(1,20);
    }


}
