import { GoogleMap, Marker, withGoogleMap, withScriptjs } from 'react-google-maps';
import React, { useEffect, useState } from 'react';
import { Anchor, Space, Typography } from 'antd';
import service from '@/services/service';
import ReactJson from 'react-json-view';
import PassesTable from '@/components/PassesTable';

const { Link } = Anchor;

const { Paragraph } = Typography;

function MapWithAMarker(props: any) {
  const [markerPosition, setMarkerPosition] = useState<{ lat: any, lng: any }>(props.center);

  const [places, setPlaces] = useState<any>([props.center]);

  const [passes, setPasses] = useState<any>();

  const [links, setLinks] = useState<any>([]);

  useEffect(() => {
    const link1 = service.getIssPassesLink(markerPosition.lat, markerPosition.lng);
    const link2 = service.getVisualPassesLink(markerPosition.lat, markerPosition.lng, 1000);

    console.log(link1);
    console.log(link2);
    setLinks([link1, link2]);
    console.log(links);
  }, [markerPosition]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await service.getVisualPasses(markerPosition.lat, markerPosition.lng, 1000);
      console.log(response.data);
      setPasses(response.data);
    };
    fetchData().catch(console.error);
  }, [markerPosition]);

  const onPosChange = (e: any) => {
    setMarkerPosition({
      lat: e.latLng.lat(),
      lng: e.latLng.lat(),
    });
    console.log(e);
    const newPlace = {
      lat: e.latLng.lat(),
      lng: e.latLng.lng(),
    };
    setPlaces([newPlace]);
  };

  return (
    <>
      <GoogleMap
        defaultZoom={8}
        defaultCenter={props.center}
        onClick={onPosChange}
      >
        {
          places.map((place: any) => {
            return (
              <Marker
                position={{ lat: place.lat, lng: place.lng }}
              />
            );
          })
        }

      </GoogleMap>
      <Paragraph className='dc'>
        The ISS will pass {passes?.info?.passescount} times in the specified location in the next 10 days:
      </Paragraph>
      <div >
        <PassesTable
          dataSource={passes?.passes}
        />
      </div>
      <br/>
      <Paragraph className='dc'>
        For more information from the Request call, use:
        <br />
        <Space direction='vertical'>
          <Anchor>
            <Link href={links[1]} title='API Link' target='_blank'/>
          </Anchor>
        </Space>
      </Paragraph>
      <br/>
    </>
  );
}

export default withScriptjs(withGoogleMap<any>(MapWithAMarker));
