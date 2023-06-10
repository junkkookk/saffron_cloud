package com.w.saffron.schdule;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.w.saffron.common.exception.OprException;

import java.util.List;

/**
 * @author w
 * @since 2023/4/28
 */
public class XxlClient {

    static XxlJobProperties xxlJobProperties = SpringUtil.getBean(XxlJobProperties.class);

    public static XxlJobGroup findJobGroupByAppName(String appName){
        String url = xxlJobProperties.getUrl() + "/jobgroup/find-by-appname";
        HttpResponse response = HttpRequest.post(url)
            .form("appname", appName)
            .execute();
        JSON json = handleError(JSONUtil.parse(response.body()));
        return json.getByPath("content", XxlJobGroup.class);
    }


    public static int createGroup(XxlJobGroup xxlJobGroup) {
        String url = xxlJobProperties.getUrl() + "/jobgroup/save";
        HttpResponse response = HttpRequest.post(url)
            .form("appname",xxlJobGroup.getAppname())
            .form("title",xxlJobGroup.getTitle())
            .form("addressType","0")
            .form("order","1")
            .execute();
        JSON json = handleError(JSONUtil.parse(response.body()));
        return json.getByPath("content", Integer.class);
    }

    public static List<XxlJobInfo> getJobsByGroup(Integer groupId) {
        String url = xxlJobProperties.getUrl() + "/joblog/getJobsByGroup";
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup",groupId)
                .execute();
        handleError(JSONUtil.parse(response.body()));
        JSONObject jsonObject = JSONUtil.parseObj(response.body());
        return jsonObject.getBeanList("content",XxlJobInfo.class);
    }

    public static void createJobInfo(Integer groupId,BaseJob baseJob) {
        String url = xxlJobProperties.getUrl() + "/jobinfo/add";
        JobInfo jobInfo = baseJob.getJobInfo();
        HttpRequest httpRequest = HttpRequest.post(url)
                .form("executorHandler", jobInfo.getJobId())
                .form("jobDesc", jobInfo.getDesc())
                .form("scheduleConf", baseJob.getCron())
                .form("jobGroup", groupId)
                .form("author", jobInfo.getAuthor())
                .form("glueType", "BEAN")
                .form("scheduleType", "CRON")
                .form("misfireStrategy", jobInfo.getMisfireStrategy())
                .form("executorFailRetryCount", jobInfo.getFailRetryCount())
                .form("executorRouteStrategy", jobInfo.getRouteStrategy())
                .form("executorBlockStrategy", jobInfo.getBlockStrategy());
        if (StrUtil.isNotBlank(jobInfo.getParam())){
            httpRequest.form("param",jobInfo.getParam());
        }
        HttpResponse response = httpRequest.execute();
        JSON json = handleError(JSONUtil.parse(response.body()));
        String jobId = json.getByPath("content", String.class);
        startJob(jobId);
    }

    private static JSON handleError(JSON json) {
        Integer code = json.getByPath("code", Integer.class);
        String msg = json.getByPath("msg", String.class);
        if (code!=200){
            throw new OprException(msg);
        }
        return json;
    }

    private static void startJob(String jobId) {
        String url = xxlJobProperties.getUrl() + "/jobinfo/start";
        HttpResponse response = HttpRequest.post(url)
                .form("id",jobId)
                .execute();
        handleError(JSONUtil.parse(response.body()));
    }


}
