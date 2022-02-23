import { Typography } from 'antd';
import React, { useEffect, useState } from 'react';
// @ts-ignore
import ReactWeather, { useOpenWeather } from 'react-open-weather';
import customStyles from '@/weatherStyles';


const { Title } = Typography;

export default function page() {
  const [latitude, setLatitude] = useState<number>(47.0479);
  const [longitude, setLongitude] = useState<number>(21.9189);

  const { data, isLoading, errorMessage } = useOpenWeather({
    // @ts-ignore
    key: REACT_APP_WEATHER_API_KEY,
    lat: latitude,
    lon: longitude,
    lang: 'en',
    unit: 'metric',
  });

  useEffect(() => {
      const fetchData = async () => {
        navigator.geolocation.getCurrentPosition(position => {
          setLatitude(position.coords.latitude);
          setLongitude(position.coords.longitude);
        });
      };
      fetchData().catch(console.error);
    }, [latitude, longitude],
  );


  return (
    <>
      <Title level={2} className='dc'>
        Weather
      </Title>
      <ReactWeather
        theme={customStyles}
        isLoading={isLoading}
        errorMessage={errorMessage}
        data={data}
        lang='en'
        locationLabel={`Lon: ${longitude}, Lat: ${latitude}`}
        unitsLabels={{ temperature: 'C', windSpeed: 'Km/h' }}
        showForecast
      />
    </>
  );
}
