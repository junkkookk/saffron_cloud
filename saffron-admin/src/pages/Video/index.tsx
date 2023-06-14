import {ActionType, PageContainer, ProList} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import {Card, Image, List, Row, theme} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import './style.less'
import {searchVideo} from "@/service/video";
import { useNavigate } from '@umijs/max';

const Video: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const { token } = theme.useToken();
  const navigate=useNavigate()
  const { initialState } = useModel('@@initialState');
  const [page,setPage] = useState({
      responsive: true,
      showLessItems: true,
      showTitle: false,
      simple: true,
      current:1,
      pageSize: 8,
      onChange:(current: any,pageSize: any)=>{
          console.log(1)
          window.scrollTo(0,0)
      }

  })
  useEffect(()=>{
      const cachePage = localStorage.getItem("page")
      if (cachePage!=null){
          const storePage = JSON.parse(cachePage)
          setPage({
              ...page,
              current: storePage.current,
              pageSize: storePage.pageSize
          })
      }
  },[])
  return (
    <PageContainer
      title={false}
      subTitle={false}
    >
      <Card
        style={{
          borderRadius: 8,
        }}
        bodyStyle={{
          backgroundImage:
            initialState?.settings?.navTheme === 'realDark'
              ? 'background-image: linear-gradient(75deg, #1A1B1F 0%, #191C1F 100%)'
              : 'background-image: linear-gradient(75deg, #FBFDFF 0%, #F5F7FF 100%)',
        }}
      >
        <div
          style={{
            backgroundPosition: '100% -30%',
            backgroundRepeat: 'no-repeat',
            backgroundSize: '274px auto',
            backgroundImage:
              "url('https://gw.alipayobjects.com/mdn/rms_a9745b/afts/img/A*BuFmQqsB2iAAAAAAAAAAAAAAARQnAQ')",
          }}
        >
          <ProList
            pagination={page}
            actionRef={actionRef}
            grid={{
              gutter: 14,
              xs: 1,
              sm: 2,
              md: 4,
              lg: 4,
              xl: 4,
              xxl: 4,
            }}

            search={{

            }}
            metas={[
              {
                dataIndex:"title",
                valueType:'text'
              },
              {
                dataIndex:"status",
                initialValue: '1',
                valueEnum:{
                  1:'已采集',
                  2:'出错'
                },
              }
            ]}
            request={async (params:{current: number,pageSize: number})=>{
                const {data} = await searchVideo(params)
                if (params.current&&params.pageSize){
                    setPage({
                        ...page,
                        current: params.current,
                        pageSize: params.pageSize
                    })
                    window.scrollTo(0,0)
                    localStorage.setItem("page",JSON.stringify(params))
                }
                return{
                    data: data.content,
                    total: data.total
                }
            }}
            renderItem={(item) => (
              <List.Item>
                <div className={"box"}>
                  <div className={"cover"}>
                    <Image
                      alt={""}
                      src={item.cover}/>
                  </div>
                  <div className={"desc"}>
                    <span className={"author"}>{item.author}</span>
                    <span className={"title"}
                        onClick={()=>{
                            navigate(`/video/${item.id}`)
                        }}
                    >{item.title}</span>
                  </div>
                </div>

              </List.Item>
            )}
          />
        </div>
      </Card>
    </PageContainer>
  );
};

export default Video;
