import { useEffect, useState } from 'react';
import { Image, Space, Typography } from 'antd';
import service from '@/services/service';
import { NasaObject } from '@/domain/nasa';


const { Title, Paragraph } = Typography;

export default function page() {

  const [nasaObject, setNasaObject] = useState<NasaObject>();
  const [visible, setVisible] = useState(false);


  useEffect(() => {
    const fetchData = async () => {
      const response = await service.getNasaObject();
      setNasaObject(response.data);
    };
    fetchData().catch(console.error);
  }, []);

  return (
    <>
      <Title level={2} className='dc'>
        Cool Facts from NASA API
      </Title>

      <Space direction='vertical'>
        <Paragraph className='dc'>
          {nasaObject?.title}
        </Paragraph>
        <Paragraph className='dc'>
          {nasaObject?.date}
        </Paragraph>
        <Paragraph className='dc'>
          {nasaObject?.explanation}
        </Paragraph>

        <br />
        <Image
          preview={{ visible: false }}
          width='30%'
          src={nasaObject?.url}
          onClick={() => setVisible(true)}
        />
        <div className='image-preview-none'>
          <Image.PreviewGroup preview={{ visible, onVisibleChange: vis => setVisible(vis) }}>
            <Image src={nasaObject?.url} />
          </Image.PreviewGroup>
        </div>
      </Space>
    </>
  );
}
