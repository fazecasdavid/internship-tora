import React from 'react';
import { Typography } from 'antd';


const { Title, Paragraph,  } = Typography;

export default function page() {
  return (
    <>
      <Title level={2} className='dc'>
        Dashboard
      </Title>
      <Paragraph className='dc'>
        Welcome to my page. <br /> Here you can find information about the Weather and the International Space Station
      </Paragraph>
    </>
  );
}
