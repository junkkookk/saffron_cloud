import Footer from '@/components/Footer';
import {Question, SelectTheme} from '@/components/RightContent';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import {ProConfigProvider, SettingDrawer} from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import React from 'react';
import { AvatarDropdown, AvatarName } from './components/RightContent/AvatarDropdown';
import {errorConfig} from "@/requestErrorConfig";
import {CurrentUser, findCurrentUser} from "@/service/login";
import {ConfigProvider} from "antd";
const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/login';

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<CurrentUser | undefined>;
}> {
  const fetchUserInfo = async () => {
    try {
      const msg = await findCurrentUser({
        skipErrorHandler: true,
      });
      return msg.data;
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };
  // 如果不是登录页面，执行
  const { location } = history;
  if (location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings as Partial<LayoutSettings>,
    };
  }
  return {
    fetchUserInfo,
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {

  return {
    actionsRender: () => [<Question key="doc" />,<SelectTheme key={'theme'}/>],
    avatarProps: {
      src: initialState?.currentUser?.avatar,
      title: <AvatarName />,
      render: (_, avatarChildren) => {
        return <AvatarDropdown>{avatarChildren}</AvatarDropdown>;
      },
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      let theme = localStorage.getItem("theme")
      if (theme!=null){
        setInitialState((preInitialState) => ({
          ...preInitialState,
          settings:{
            ...preInitialState?.settings,navTheme: theme as 'light'|'realDark'
          }
        }))
      }
      const { location } = history;
      // 如果没有登录，重定向到 api
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    layoutBgImgList: [
    ],
    links:  [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <ConfigProvider
        theme={{
          token:{
            fontFamily:"HarmonyOS Sans SC Medium",
            fontFamilyCode:"PingFang SC"
          }
        }}
        >
          {children}
          {/*<SettingDrawer*/}
          {/*  disableUrlParams*/}
          {/*  enableDarkTheme*/}
          {/*  settings={initialState?.settings}*/}
          {/*  onSettingChange={(settings) => {*/}
          {/*    setInitialState((preInitialState) => ({*/}
          {/*      ...preInitialState,*/}
          {/*      settings,*/}
          {/*    }));*/}
          {/*  }}*/}
          {/*/>*/}
        </ConfigProvider>
      );
    },
    ...initialState?.settings,
  };
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
  ...errorConfig,
};
