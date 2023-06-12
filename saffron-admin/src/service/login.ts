import { request } from '@umijs/max';

const ServerApi = "/saffron-server"

export type CurrentUser = {
  username?: string;
  avatar?: string;
  userid?: string;
  email?: string;
  signature?: string;
  title?: string;
  group?: string;
  tags?: { key?: string; label?: string }[];
  notifyCount?: number;
  unreadCount?: number;
  country?: string;
  access?: string;
  geographic?: {
    province?: { label?: string; key?: string };
    city?: { label?: string; key?: string };
  };
  address?: string;
  phone?: string;
};

export type LoginParams = {
  username?: string;
  password?: string;
  autoLogin?: boolean;
  type?: string;
};

/** 获取当前的用户 GET /api/currentUser */
export async function findCurrentUser(options?: { [key: string]: any }) {
  return request<{
    data: CurrentUser;
  }>('/saffron-server/profile', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/login/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/login/outLogin', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/login/account */
export async function login(params: LoginParams, options?: { [key: string]: any }) {
  return request<{
    code: number
    data: {
      tokenValue: string
    }
  }>(`${ServerApi}/login`, {
    method: 'POST',
    headers:{
      contentType:''
    },
    params: {...params,loginType:params.type=='account'?1:2},
    ...(options || {}),
  });
}
