import { useEffect, useState } from 'react';
import MapWithAMarker from '@/components/MyMapComponent';
import { IssLocation } from '@/domain/IssLocation';
import { Typography } from 'antd';
import service from '@/services/service';

const { Paragraph } = Typography;

export default function app() {
  const center = { lat: 47.0479, lng: 21.9189 };

  const [issLocation, setIssLocation] = useState<IssLocation>();

  useEffect(() => {
    const fetchData = async () => {
      const response = await service.getISSPosition();
      setIssLocation(response.data);
    };
    fetchData().catch(console.error);
  }, []);

  return (
    <>
      <Paragraph className='dc'>
        ISS Current Location: Lat: {issLocation?.iss_position.latitude}, Lng: {issLocation?.iss_position.longitude}
      </Paragraph>
      <Paragraph className='dc'>
        Click to select a location and to see when the ISS will be visible from that location :)
      </Paragraph>
      <MapWithAMarker
        googleMapURL='https://maps.googleapis.com/maps/api/js?key=_____________&v=3.exp&libraries=geometry,drawing,places'
        loadingElement={<div className='map-element-container' />}
        containerElement={<div className='map-content-container' />}
        mapElement={<div className='map-element-container' />}
        center={center}
        issLocation={{ lat: issLocation?.iss_position.latitude, lng: issLocation?.iss_position.longitude }}
      />
    </>
  );
}
