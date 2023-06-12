import { PageContainer } from '@ant-design/pro-components';
import { useModel,useParams } from '@umijs/max';
import {Card, Spin, theme} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import ReactHlsPlayer from "react-hls-player";
import {findVideo, Video} from "@/service/video";
import './style.less'
const VideoPlay: React.FC = () => {
    const { token } = theme.useToken();
    const { initialState } = useModel('@@initialState');
    const params  = useParams();
    const [video,setVideo] = useState<Video>()
    useEffect(()=>{
        console.log(params.id)
        if (params.id){
            findVideo(params.id as string).then(res=>{
                setVideo(res.data)
            })
        }
    },[])

    const videoRef = useRef(null) as React.RefObject<HTMLVideoElement>;
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
                {
                    video?.playUrl?<ReactHlsPlayer
                        src={video.playUrl}
                        autoPlay={true}
                        controls={true}
                        width="100%"
                        height="768px"
                        playerRef={videoRef}
                    />: <Spin tip="Loading...">
                        <div style={{
                            height:"768px",
                            width: "100%"
                        }}>

                        </div>
                    </Spin>
                }
            </Card>
        </PageContainer>
    );
};

export default VideoPlay;
