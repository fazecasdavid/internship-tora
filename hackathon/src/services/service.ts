import { WeatherRootObject } from '@/domain/weather';
import axios from 'axios';
import { NasaObject } from '@/domain/nasa';
import { IssLocation } from '@/domain/IssLocation';

class Service {
  public getWeather(latitude: number, longitude: number) {
    // @ts-ignore
    const query = `${REACT_APP_WEATHER_API_ENDPOINT}/data/2.5/weather?lat=${latitude}&lon=${longitude}&units=metrics&appid=${REACT_APP_WEATHER_API_KEY}`;
    console.log(query);
    return axios.get<WeatherRootObject>(query);
  }

  public getNasaObject() {
    const query = 'https://api.nasa.gov/planetary/apod?api_key=ZzsOUzAiWaVTXpkLpq89NQXWTkhZYrhfSmBbiNcE';
    console.log(query);
    return axios.get<NasaObject>(query);
  }

  public getISSPosition() {
    const query = 'http://api.open-notify.org/iss-now.json';
    return axios.get<IssLocation>(query);
  }

  public getPeopleInSpace() {
    const query = 'http://api.open-notify.org/astros.json';
    return axios.get(query);
  }

  public getVisualPasses(lat: any, lng: any, alt: any, days = 10, min_visibility = 100) {
    //@ts-ignore
    const query = `https://api.n2yo.com/rest/v1/satellite/visualpasses/25544/${lat}/${lng}/${alt}/${days}/${min_visibility}/&apiKey=${REACT_APP_N2YO_API_KEY}`;
    return axios.get(query);
  }

  public getIssPasses(lat: any, lng: any) {
    const query = `http://api.open-notify.org/iss-pass.json?lat=${lat}&lon=${lng}`;
    return axios.get(query);
  }

  public getVisualPassesLink(lat: any, lng: any, alt: any, days = 10, min_visibility = 100) {
    //@ts-ignore
    return `https://api.n2yo.com/rest/v1/satellite/visualpasses/25544/${lat}/${lng}/${alt}/${days}/${min_visibility}/&apiKey=${REACT_APP_N2YO_API_KEY}`;
  }

  public getIssPassesLink(lat: any, lng: any) {
    return `http://api.open-notify.org/iss-pass.json?lat=${lat}&lon=${lng}`;
  }


}

export default new Service();
