<script setup>

import { ref, onMounted } from 'vue';
import { queryAllApi, addApi, queryByIdApi, updateApi, deleteByIdApi} from '@/api/dept';
import { ElMessage, ElMessageBox } from 'element-plus'

// 钩子函数->页面加载完毕后会执行search函数，发送请求获取所有的部门数据，并将数据赋值给deptList，并在列表展示
onMounted(() => {
  search();
})

// 查询
const search = async() =>{
  //  const result = await axios.get('https://apifoxmock.com/m1/3128855-1224313-default/depts');
  //  if(result.data.code == 1){
  //   deptList.value = result.data.data;
  //  }
    const result = await queryAllApi();
    if(result.code == 1){
    deptList.value = result.data;
   }

}

const deptList = ref([])

// 对话框
const dialogFormVisible = ref(false);
const dept = ref({name:''});
const formTitle = ref('');

// 新增部门点击执行的函数
const addDept = () => {

  dialogFormVisible.value = true;
  formTitle.value = 'Add Department';
  dept.value = {name:''};
  // 重制表单校验规则-提示信息
  if(deptFormRef.value) {
    deptFormRef.value.resetFields();
  }
}

// 保存部门执行的函数
  const save = async() =>{
    // 表单校验
    if(!deptFormRef.value) return;

    deptFormRef.value.validate(async(valid) => {
      if(valid){
        
        let result;

        if(dept.value.id){
          // 修改->有id
          result = await updateApi(dept.value);
        }else{
          // 新增->没有id
          result = await addApi(dept.value);
        }

        if(result.code == 1){
          // 提示信息
          // alert('success');
          ElMessage.success('Congrats, this is a success message.');

          // 关闭对话框
          dialogFormVisible.value = false;

          // 查询
          search(); 
        }else{
          ElMessage.error(result.msg);
        }
      }else{
        ElMessage.error('Please fill in the correct information');
      }
    })
  }

  // 表单校验
  const rules = ref({
    name: [
      { required: true, message: 'Please input Activity name', trigger: 'blur' },
      { min: 2, max: 10, message: 'Length should be 2 to 10', trigger: 'blur' }
    ]
  })

  const deptFormRef = ref();


  // 编辑
  const edit = async(id) => {
    formTitle.value = 'Edit Department';

      // 重制表单校验规则-提示信息
    if(deptFormRef.value) {
      deptFormRef.value.resetFields();
    }

    const result = await queryByIdApi(id);
    if(result.code){
      dept.value = result.data;
      dialogFormVisible.value = true;
    }

  }

  // 删除
  const delById = async(id) => {
    // 弹出确认框
    ElMessageBox.confirm(
    'Are you sure permanently delete this department?',
    'Warning',
    {confirmButtonText: 'OK',cancelButtonText: 'Cancel',type: 'warning'}
  )//点击确认
    .then(async() => {
      const result = await deleteByIdApi(id);

      if(result.code){
        ElMessage.success('success'),
        // 删除之后再查询一次
        search();
      }else{
        ElMessage.error(result.msg);
      }
    })//点击取消
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })
    })

  }

</script>

<template>

  <h1>Department management</h1>

  <div class="table-wrapper">
      <div class="table-tools">
        <el-button type="primary" plain round @click="addDept"><el-icon><Plus /></el-icon> Add new department</el-button>
      </div>

      <div class="table">

        <el-table :data="deptList" border stripe style="width: 100%">
        <el-table-column type="index" label="No." width="100" align="center"/>
        <el-table-column prop="name" label="Department Name" width="260"  align="center"/>
        <el-table-column prop="updateTime" label="Last Modified Time" width="300" align="center"/>

        <el-table-column label="Operation" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" style="margin-right: 10px" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> Edit</el-button>
            <el-button type="danger" size="small" @click="delById(scope.row.id)"><el-icon><DeleteFilled /></el-icon> Delete</el-button>
          </template>
        </el-table-column>

        </el-table>
      </div>
  </div>

  <!-- 对话框 -->
  <!-- <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500"> -->
    <el-dialog v-model="dialogFormVisible" width="500">
    <template #title>
      <div class="custom-title">
        {{ formTitle }}
      </div>
    </template>
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="New Department" label-width="120px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>

  /* .container {
    margin: 10px 8px;
  } */
  .table-wrapper {
    position: relative;
    padding-top: 50px; /* 上边空出按钮位置 */
    margin: 20px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }

  .table-tools {
    position: absolute;
    top: 10px;
    right: 16px;
    z-index: 10;
  }

  
  :deep(.el-table th) {
    font-weight: bold;
    text-align: center;
    background-color: #f8f9fa;
    color: #333;
  }

  :deep(.el-table td) {
    color: #444;
    font-size: 14px;
  }

  .custom-title {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  border-left: 4px solid #409EFF;
  padding-left: 10px;
}

/* :deep(.el-dialog__title) {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  border-left: 4px solid #409EFF;
  padding-left: 12px;
} */

</style>
