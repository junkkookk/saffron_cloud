import {
  AlipayCircleOutlined,
  LockOutlined,
  MobileOutlined,
  TaobaoCircleOutlined,
  UserOutlined,
  WeiboCircleOutlined,
} from '@ant-design/icons';
import {
  LoginFormPage,
  ProFormCaptcha,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import {  history, useModel, Helmet } from '@umijs/max';
import {Alert, ConfigProvider, message, Tabs} from 'antd';
import Settings from '../../../config/defaultSettings';
import React, { useState } from 'react';
import { flushSync } from 'react-dom';
import './style.css'
import {login, LoginParams} from "@/service/login";
const ActionIcons = () => {
  const langClassName = useEmotionCss(({ token }) => {
    return {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    };
  });

  return (
    <>
      <AlipayCircleOutlined key="AlipayCircleOutlined" className={langClassName} />
      <TaobaoCircleOutlined key="TaobaoCircleOutlined" className={langClassName} />
      <WeiboCircleOutlined key="WeiboCircleOutlined" className={langClassName} />
    </>
  );
};

const Login: React.FC = () => {
  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');
  const containerClassName = useEmotionCss(() => {
    return {
      height: '100%'
    };
  });


  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      flushSync(() => {
        setInitialState((s) => ({
          ...s,
          currentUser: userInfo,
        }));
      });
    }
  };

  const handleSubmit = async (values: LoginParams) => {
    try {
      // 登录
      const msg = await login({ ...values, type });
      if (msg.code === 200) {
        const defaultLoginSuccessMessage = '登录成功！';
        localStorage.setItem("token",msg.data.tokenValue)
        message.success(defaultLoginSuccessMessage);
        await fetchUserInfo();
        const urlParams = new URL(window.location.href).searchParams;
        history.push(urlParams.get('redirect') || '/');
        return;
      }
      console.log(msg);
      // 如果失败去设置用户错误信息
    } catch (error) {
      const defaultLoginFailureMessage = '登录失败，请重试！'
      console.log(error);
      message.error(defaultLoginFailureMessage);
    }
  }

  return (
    <ConfigProvider
      theme={{
        token: {
          colorPrimary: initialState?.settings?.colorPrimary,
          borderRadius: 5
        } }}
    >
    <div className={containerClassName}>
      <Helmet>
        <title>
          {'登录页'}
          - {Settings.title}
        </title>
      </Helmet>
      <LoginFormPage
        backgroundImageUrl={"http://127.0.0.1:9000/saffron/wallhaven-572k81.webp?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=JU6QNWKC3JK68TZJNN3P%2F20230611%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230611T055203Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJKVTZRTldLQzNKSzY4VFpKTk4zUCIsImV4cCI6MTY4NjUwNTg1MSwicGFyZW50Ijoic2FmZnJvbiJ9.d9fFM5x_Ae2a25dl5b91wUjGYT_qT6N1XfKCQ7bNbAMbylZUy45_YuUk9yppJDtXDcmSg4If6wV_Gp84LXcjIA&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=bc15476353ff9e5e364f3884f46fa595220fdfa98bb17590eead1779526f0d82"}
        logo={<img alt="logo" src="/logo.png" />}
        title={'Saffron-Server'}
        subTitle={false}
        initialValues={{
          autoLogin: true,
        }}
        actions={[
           "其他登录方式",
          <ActionIcons key="icons" />,
        ]}
        onFinish={async (values) => {
          await handleSubmit(values as LoginParams);
        }}
      >
        <Tabs
          activeKey={type}
          onChange={setType}
          centered
          items={[
            {
              key: 'account',
              label:'账户密码登录',
            },
            {
              key: 'mobile',
              label: '手机号登录',
            },
          ]}
        />

        {type === 'account' && (
          <>
            <ProFormText
              name="username"
              fieldProps={{
                size: 'large',
                prefix: <UserOutlined />,
              }}
              placeholder={'用户名: admin or user'}
              rules={[
                {
                  required: true,
                  message: ("请输入用户名!"),
                },
              ]}
            />
            <ProFormText.Password
              name="password"
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined />,
              }}
              placeholder={'密码: ant.design'}
              rules={[
                {
                  required: true,
                  message: "请输入密码！"
                },
              ]}
            />
          </>
        )}

        {type === 'mobile' && (
          <>
            <ProFormText
              fieldProps={{
                size: 'large',
                prefix: <MobileOutlined />,
              }}
              name="mobile"
              placeholder={'手机号'}
              rules={[
                {
                  required: true,
                  message: "请输入手机号！"
                },
                {
                  pattern: /^1\d{10}$/,
                  message: ("手机号格式错误！"),
                },
              ]}
            />
            <ProFormCaptcha
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined />,
              }}
              captchaProps={{
                size: 'large',
              }}
              placeholder={'请输入验证码'}
              captchaTextRender={(timing, count) => {
                if (timing) {
                  return `${count} ${'获取验证码'}`;
                }
                return '获取验证码';
              }}
              name="captcha"
              rules={[
                {
                  required: true,
                  message: (
                    "请输入验证码！"
                  ),
                },
              ]}
              onGetCaptcha={async (phone) => {
                message.success('获取验证码成功！验证码为：1234');
              }}
            />
          </>
        )}
        <div
          style={{
            marginBottom: 12,
          }}
        >
          <ProFormCheckbox noStyle name="autoLogin">
            自动登录
          </ProFormCheckbox>
          <a
            style={{
              float: 'right',
            }}
          >
          忘记密码
          </a>
        </div>
      </LoginFormPage>
    </div>
    </ConfigProvider>
  );
};

export default Login;
