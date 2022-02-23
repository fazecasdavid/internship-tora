import { defineConfig } from 'umi';

export default defineConfig({
  title: 'Hackathon',
  nodeModulesTransform: {
    type: 'none',
  },
  routes: [
    {
      path: '/',
      component: '@/layouts/index',
      routes: [
        { path: '/', extract: true, component: 'dashboard' },
        { path: '/dashboard', exact: true, component: 'dashboard' },
        { path: '/weather', exact: true, component: 'weather' },
        { path: '/cool', exact: true, component: 'coolFacts' },
        { path: '/iss', exact: true, component: 'iss' },
        { path: '/people', exact: true, component: 'people' },
      ],
    },
  ],
  fastRefresh: {},
  define: {
    REACT_APP_WEATHER_API_KEY: process.env.REACT_APP_WEATHER_API_KEY,
    REACT_APP_WEATHER_API_ENDPOINT: process.env.REACT_APP_WEATHER_API_ENDPOINT,
    REACT_APP_WEATHER_API_ICONS: process.env.REACT_APP_WEATHER_API_ICONS,
    REACT_APP_N2YO_API_KEY: process.env.REACT_APP_N2YO_API_KEY,
  },
  antd: {
    dark: true, // active dark theme
  },
});
