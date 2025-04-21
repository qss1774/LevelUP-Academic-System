<script setup>
  import { ref, onMounted } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useRouter } from 'vue-router';

  // 记录当前登陆的员工
  const loginName = ref('');
  const router = useRouter();

  // 钩子函数
  onMounted(() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if(loginUser && loginUser.name){
      loginName.value = loginUser.name;
    }
  })

  // 退出登陆
  const logout = () => {
    // 二次确认

    ElMessageBox.confirm(
    'Are you sure you want to log out?',
    'Warning',
    {confirmButtonText: 'OK',cancelButtonText: 'Cancel',type: 'warning'}
  )//点击确认
    .then(async() => {

        ElMessage.success('Log out successfully!');
        localStorage.removeItem('loginUser');
        // 跳转登陆页面
        router.push('/login');

    })//点击取消
    .catch(() => {
      ElMessage.info('Log out canceled');
    })
  }

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">LevelUp Academy Intelligent Assistance System</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> change password &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
          </a>
          <!-- 变成死链接，页面不会跳转也不会刷新 -->
          <a href="javascript:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon> logout {{ loginName }}
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <!-- 左侧菜单栏 -->
          <el-menu router>
            <!-- 首页菜单 -->
             <!-- <router-link to="/index"> -->
              <el-menu-item index="/index">
                <el-icon><Opportunity /></el-icon> Home
              </el-menu-item>
             <!-- </router-link> -->
            
            
            <!-- 班级管理菜单 -->
            <el-sub-menu index="/manage">
              <template #title>
                <el-icon><Promotion /></el-icon> Study Program
              </template>
              <el-menu-item index="/clazz">
                <el-icon><HomeFilled /></el-icon> Course management
              </el-menu-item>
              <el-menu-item index="/stu">
                <el-icon><UserFilled /></el-icon> Student management
              </el-menu-item>
            </el-sub-menu>
            
            <!-- 系统信息管理 -->
            <el-sub-menu index="/system">
              <template #title>
                <el-icon><Tools /></el-icon> Center Information
              </template>
              <el-menu-item index="/dept">
                <el-icon><HelpFilled /></el-icon> Department management
              </el-menu-item>
              <el-menu-item index="/emp">
                <el-icon><Avatar /></el-icon> Staff management
              </el-menu-item>
            </el-sub-menu>

            <!-- 数据统计管理 -->
            <el-sub-menu index="/report">
              <template #title>
                <el-icon><Histogram /></el-icon> Data statistics
              </template>
              <el-menu-item index="/empReport">
                <el-icon><InfoFilled /></el-icon> Employee information
              </el-menu-item>
              <el-menu-item index="/stuReport">
                <el-icon><Share /></el-icon> Student information
              </el-menu-item>
              <el-menu-item index="/log">
                <el-icon><Document /></el-icon> Log information
              </el-menu-item>
            </el-sub-menu>
          </el-menu>

            
        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-color: #6ac6e4;
  /* background-image: linear-gradient(to right, #6ac6e4, #a2dff2); */
}

.title {
  color: white;
  font-size: 40px;
  font-family: 'Noto Sans SC', 'Microsoft YaHei', 'PingFang SC', sans-serif;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
