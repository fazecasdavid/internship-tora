import React from 'react';
import { Layout } from 'antd';
import image from '../assets/images/Tora_logo.png';
import SideMenu from '@/components/SideMenu';

const { Footer, Sider, Content } = Layout;

const BasicLayout: React.FC = ({ children }) => {
  return (
    <Layout>
      <Layout>
        <Sider className='sider-layout'>
          <div className='logo'>
            <img src={image} className='img' alt='Logo' />
            <div className='txt'>Hackathon</div>
          </div>
          <SideMenu />
        </Sider>
        <Content className='content-layout'>{children}</Content>
      </Layout>
      <Footer className='footer'>Hackathon ©2021 Created by Fazecas David: Tora</Footer>
    </Layout>

  );
}

export default BasicLayout;
