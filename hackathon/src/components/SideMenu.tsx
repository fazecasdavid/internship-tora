import React, { useState } from 'react';
import { history } from 'umi';
import { Menu, Space } from 'antd';
import { AppstoreOutlined } from '@ant-design/icons';
import { GoRocket } from 'react-icons/go';
import { TiWeatherPartlySunny } from 'react-icons/ti';
import { BiGame } from 'react-icons/bi';
import { IoPeople } from 'react-icons/io5';


export default function SideMenu() {
  const [selectedKey, setSelectedKey] = useState('/dashboard');
  const handleMenuClick = (option: { key: any; }) => {
    if (!option?.key) return;
    setSelectedKey(option?.key);
    history.push(option?.key);
  };

  return (
    <Menu mode='inline' defaultSelectedKeys={[selectedKey]} onClick={handleMenuClick} className='side-menu'
          selectedKeys={[history?.location?.pathname]}>
      <Menu.Item key='/dashboard'>
        <Space size='small'><AppstoreOutlined /> Dashboard</Space>
      </Menu.Item>
      <Menu.Item key='/weather'>
        <Space size='small'><TiWeatherPartlySunny /> Weather</Space>
      </Menu.Item>
      <Menu.Item key='/iss'>
        <Space size='small'><GoRocket /> ISS</Space>
      </Menu.Item>
      <Menu.Item key='/cool'>
        <Space size='small'><BiGame /> Cool Facts</Space>
      </Menu.Item><Menu.Item key='/people'>
        <Space size='small'><IoPeople /> People in space</Space>
      </Menu.Item>
    </Menu>
  );
}

