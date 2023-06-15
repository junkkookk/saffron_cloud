package com.w.saffron.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * @author w
 * @since 2023/3/6
 */
@Data
public class PageResult<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long total;

    List<T> content;
    public static <T> PageResult<T> of(Page<T> page){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setContent(page.getContent());
        pageResult.setTotal(page.getTotalElements());
        return pageResult;
    }

    public static <T> PageResult<T> of(List<T> items) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setContent(items);
        return pageResult;
    }

    public <U> PageResult<U> map(Function<? super T,U> mapper){
        PageResult<U> pageResult = new PageResult<>();
        pageResult.setContent(this.content.stream().map(mapper).toList());
        pageResult.setTotal(this.total);
        return pageResult;
    }
}
