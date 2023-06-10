package com.w.saffron.tool;

import cn.hutool.core.util.StrUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author w
 * @since 2023/4/29
 */
public class JiebaContext {
    private static final  JiebaSegmenter SEGMENT;

    static {
        URL dict = Thread.currentThread().getContextClassLoader().getResource("dict/tags.dict");
        if (dict != null) {
            try {
                WordDictionary.getInstance().loadUserDict(Paths.get(dict.toURI()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        SEGMENT = new JiebaSegmenter();
    }

    public static List<String> getWordsBySegment(String content){
        return SEGMENT.sentenceProcess(content).stream()
                .filter(StrUtil::isNotBlank)
                .filter(s -> s.length() > 1).toList();
    }

}
