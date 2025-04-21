<script setup>
  import { ref } from 'vue'
  import { loginApi } from '@/api/login'
  import { ElMessage } from 'element-plus'
  import { useRouter } from 'vue-router'
  
  const loginForm = ref({username:'', password:''})
  const router = useRouter();

  // 登陆
  const login = async() => {
    // 调用服务器端执行登陆操作
    const result = await loginApi(loginForm.value);

    if (result.code) {
      // 登陆成功
      ElMessage.success("login success")
      // 存储当前登陆员工的信息
      localStorage.setItem('loginUser', JSON.stringify(result.data))
      // 跳转到首页
      router.push('/');
    } else {
      // 登陆失败
      ElMessage.error(result.msg)
      
    }

  }

  // 重置
  const clear = () => {
    loginForm.value = {username: '', password: ''};
  }
  
</script>

<template>
  <div id="container">
    <div class="login-form">
      <el-form label-width="80px">
        <p class="title">LevelUp Academy Assistance System</p>
        <el-form-item label="username" prop="username">
          <el-input v-model="loginForm.username" placeholder="Enter your username"></el-input>
        </el-form-item>

        <el-form-item label="password" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="Enter your password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">Login</el-button>
          <el-button class="button" type="info" @click="clear">Reset</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
#container {
  height: 100vh;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('../../assets/bg1.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
}

.login-form {
  width: 100%;
  max-width: 460px;
  padding: 40px 32px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.7); /* 更低透明度 */
  backdrop-filter: blur(12px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.title {
  font-size: 22px;
  line-height: 1.4;
  font-family: 'Noto Sans SC', 'Microsoft YaHei', 'PingFang SC', sans-serif;
  text-align: center;
  margin-bottom: 24px;
  font-weight: 600;
  color: #1a1a1a;
}

.button {
  width: 120px;
  font-size: 15px;
  border-radius: 8px;
  margin: 24px 20px 0 20px; /* 上左右下边距 */ 
}
</style>