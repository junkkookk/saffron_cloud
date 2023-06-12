import { QuestionCircleOutlined } from '@ant-design/icons';
import React from 'react';
import {useModel} from "@@/exports";
import {Button, Spin} from "antd";
import {useEmotionCss} from "@ant-design/use-emotion-css";

export type SiderTheme = 'light' | 'dark';
export const Question = () => {
  return (
    <div
      style={{
        display: 'flex',
        height: 26,
      }}
      onClick={() => {
        window.open('https://pro.ant.design/docs/getting-started');
      }}
    >
      <QuestionCircleOutlined />
    </div>
  );
};
export const SelectTheme = () =>{
  const { initialState, setInitialState } = useModel('@@initialState');

  const actionClassName = useEmotionCss(({ token }) => {
    return {
      display: 'flex',
      height: '48px',
      marginLeft: 'auto',
      overflow: 'hidden',
      alignItems: 'center',
      padding: '0 8px',
      cursor: 'pointer',
      borderRadius: token.borderRadius,
      '&:hover': {
        backgroundColor: token.colorBgTextHover,
      },
    };
  });
  const loading = (
    <span className={actionClassName}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );
  if (!initialState){
    return loading
  }
  if (!initialState.settings){
    return loading
  }
  let theme = localStorage.getItem("theme")
  const changeTheme=(theme: 'light'|'realDark')=>{
    localStorage.setItem("theme",theme)
    console.log(theme)
    setInitialState((preInitialState) => ({
      ...preInitialState,
      settings:{
        ...preInitialState?.settings,navTheme: theme
      }
    }));
  }

  if (theme == 'light') {
    return <Button
      onClick={()=>changeTheme('realDark')}
    >切换为夜间</Button>
  } else {
    return <>
    <Button
      onClick={()=>changeTheme('light')}
    >
      切换为白天
    </Button>
    </>
  }
}
