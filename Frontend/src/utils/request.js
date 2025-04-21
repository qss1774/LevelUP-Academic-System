import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '../router';

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的请求 request 拦截器 - 获取localStorage中的token,在请求头中增加token请求头
request.interceptors.request.use(
  (config) => { //成功回调

    // json格式字符串解析成一个对象
    // 在每一次请求中都会携带令牌
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    
    if (loginUser && loginUser.token) {
      config.headers.token = loginUser.token;
    }
    return config;
  },
  (error) => { //失败回调
    return Promise.reject(error);
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    if(error.response.status === 401) {
      // 提示用户信息
      ElMessage.error('Login expired, please log in again');
      
      // 跳转到登录页面
      router.push('/login');
    }
    return Promise.reject(error)
  }
)



export default request