import { request } from '@umijs/max';
import {PageParam, PageResult, Result} from "@/service/common";

const ServerApi = "/saffron-server"

export type Video = {
    id: string,
    title: string,
    author: string,

    cover: string,
    playUrl: string
};

/** 获取当前的用户 GET /api/currentUser */
export async function searchVideo(pageParam: PageParam,options?: { [key: string]: any }) {
    return request<Result<PageResult<Video>>>('/saffron-server/video/search', {
        method: 'GET',
        params: pageParam,
        ...(options || {}),
    });
}

export async function findVideo(id: string,options?: { [key: string]: any }) {
    return request<Result<Video>>('/saffron-server/video/find-by-id', {
        method: 'GET',
        params: {
            id: id
        },
        ...(options || {}),
    });
}

