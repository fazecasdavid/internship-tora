import React, { useEffect, useState } from 'react';
import { Typography } from 'antd';
import ReactJson from 'react-json-view';
import service from '@/services/service';


const { Title, Paragraph,  } = Typography;

export default function page() {

  const [people, setPeople] = useState<any>();

  useEffect(() => {
    const fetchData = async () => {
      const response = await service.getPeopleInSpace();
      setPeople(response.data);
    };
    fetchData().catch(console.error);
  }, []);

  return (
    <>
      <Title level={2} className='dc'>
        People currently in space
      </Title>
      <Paragraph className='dc'>
        <ReactJson src={people}/>
      </Paragraph>
    </>
  );
}
