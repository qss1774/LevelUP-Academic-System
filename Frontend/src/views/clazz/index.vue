<script setup>
import { onMounted, ref, watch } from 'vue'
import { queryPageApi , addApi, queryInfoApi, updateApi, deleteApi} from '@/api/clazz'
import { queryAllApi as queryAllEmpApi } from '@/api/emp'
import { ElMessage, ElMessageBox } from 'element-plus'

//学科列表数据
const subjects = ref([{ name: 'Math', value: 1 },
                      { name: 'Reading', value: 2 },
                      { name: 'Science', value: 3 },
                      { name: 'French', value: 4 },
                      { name: 'Chinese', value: 5 },
                      { name: 'Biology', value: 6 }])
//搜索表单对象
let searchClazz = ref({begin:'', end:'', date:[], name:''})
//列表展示数据
let tableData = ref([])

//钩子函数 - 页面加载时触发
onMounted(() => {
  queryPage()
  queryAllEmp()
})

//所有的员工数据
let emps = ref([])
//加载所有的员工数据
const queryAllEmp = async () => {
  let result = await queryAllEmpApi()
  if(result.code) {
    emps.value = result.data
  }
}

//分页组件
const pagination = ref({currentPage: 1, pageSize: 10, total: 0})
//每页展示记录数发生变化时触发
const handleSizeChange = (pageSize) => {
  pagination.value.pageSize = pageSize
  queryPage()
}
//当前页码发生变化时触发
const handleCurrentChange = (page) => {
  pagination.value.currentPage = page
  queryPage()
}

//分页条件查询
const queryPage = async () => {
  const result = await queryPageApi(
      searchClazz.value.begin,
      searchClazz.value.end,
      searchClazz.value.name,
      pagination.value.currentPage,
      pagination.value.pageSize
  );

  if(result.code) {
    tableData.value = result.data.rows
    pagination.value.total = result.data.total
  }
}

//清空搜索栏
const clear = () => {
  searchClazz.value = {begin:'', end:'', date:[], name:''}
  queryPage()
}

//监听searchEmp的date属性
watch(() => searchClazz.value.date, (newVal, oldVal) => {
  console.log(`newVal : ${newVal} ; oldVal: ${oldVal} `)
  if(newVal.length > 0) {
    console.log('-----------');
    searchClazz.value.begin = newVal[0]
    searchClazz.value.end = newVal[1]
  }else {
    console.log('==========');
    searchClazz.value.begin = ''
    searchClazz.value.end = ''
  }
})



//----------- 新增 / 修改 ---------------------------
let dialogFormVisible = ref(false) //控制新增/修改的对话框的显示与隐藏
let labelWidth = ref(140) //form表单label的宽度
let formTitle = ref('') //表单的标题
//员工对象-表单数据绑定
let clazz = ref({
  id: '',
  name: '',
  room: '',
  beginDate: '',
  endDate: '',
  subject: '',
  masterId: ''
})

//清空表单
const clearClazz = () => {
  clazz.value = {
    id: '',
    name: '',
    room: '',
    beginDate: '',
    endDate: '',
    subject: '',
    masterId: ''
  }
}

//新增班级
const addClazz = () => {
  dialogFormVisible.value = true
  formTitle.value = 'Add Class'
  clearClazz()
}

//修改班级
const updateClazz = async (id) => {
  clearClazz()
  dialogFormVisible.value = true
  formTitle.value = 'Edit Class'
  let result = await queryInfoApi(id)
  if(result.code){
    clazz.value = result.data
  }
}

//表单校验规则
const clazzFormRef = ref()
const rules = ref({
  name: [
    { required: true, message: 'Class name is required', trigger: 'blur' },
    { min: 4, max: 30, message: 'Username length is 4-30 characters', trigger: 'blur' }
  ],
  room: [
    { min: 1, max: 20, message: 'The length of the classroom is 1-20 characters', trigger: 'blur' }
  ],
  beginDate: [{ required: true, message: 'Course start time is required', trigger: 'change' }],
  endDate: [{ required: true, message: 'Course end time is required', trigger: 'change' }],
  subject: [{ required: true, message: 'Subject is required', trigger: 'change' }]
})

//重置表单
const resetForm = (clazzForm) => {
  if (!clazzForm) return
  clazzForm.resetFields()
}

//-------------保存班级信息 
const save = (clazzForm) => {
  console.log('clazzForm: ' + clazz);
  // debugger;
  //表单校验
  if(!clazzForm) return
  clazzForm.validate(async (valid) => {
    if(valid) {
      let api 
      if(clazz.value.id) {
        api = updateApi(clazz.value)
      }else {
        api = addApi(clazz.value)
      }

      let result = await api
      if(result.code) {
        ElMessage.success('successful!')
        dialogFormVisible.value = false
        queryPage()
      }else {
        ElMessage.error(result.msg)
      }
    }else {
      console.log('valid: '+ valid);
      
      return false
    }
  })
}


//------- 删除班级
//根据ID删除单个班级
const delById = async (id) => {
  ElMessageBox.confirm('Are you sure about deleting this class?' , 'Delete class', {confirmButtonText:'confirm', cancelButtonText:'cancel',type:'warning'})
    .then(async () => {
      let result =  await deleteApi(id)
      if(result.code) {
        ElMessage.success('delete success')
        queryPage()
      }else {
        ElMessage.error(result.msg)
      }
    }).catch(() => {
      ElMessage.info('delete canceled')
    })
}
</script>

<template>

    <!-- 顶部标题 -->
    <div>
      <div id="title">Course Management</div><br>
    </div>

    <!-- 条件搜索表单 -->
    <el-form :inline="true" :model="searchClazz" class="demo-form-inline">
      <el-form-item label="Class Name">
        <el-input v-model="searchClazz.name" placeholder="Select class name"/>
      </el-form-item>
      
      <el-form-item label="End Date">
        <el-date-picker
          v-model="searchClazz.date"
          type="daterange"
          range-separator=" to "
          start-placeholder="Start Date"
          end-placeholder="End Date"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="queryPage()">Search</el-button>
        <el-button type="info" @click="clear()">Clear</el-button>
      </el-form-item>
    </el-form>
    
    <!-- 功能按钮 -->
    <el-button type="success" @click="addClazz();resetForm(clazzFormRef)">+ Add Class</el-button>
    <br><br>
    
    <!-- 列表展示 -->
    <el-table :data="tableData" border style="width: 100%" fit>
      <el-table-column type="index" label="No." width="55" align="center"/>
      <el-table-column prop="name" label="Class Name" align="center" width="150px" />
      <el-table-column prop="room" label="Room" align="center" width="80px"/>
      <el-table-column prop="masterName" label="Head Teacher" align="center" width="130px"/>
      <el-table-column prop="beginDate" label="Start Date" align="center" width="150px"/>
      <el-table-column prop="endDate" label="End Date" align="center" width="150px"/>
      <el-table-column prop="status" label="Status" align="center" width="130px" />
      <el-table-column prop="updateTime" label="Last Modified Time" align="center" />
      <el-table-column label="Operation" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="updateClazz(scope.row.id); resetForm(clazzFormRef)">Edit</el-button>
          <el-button type="danger" size="small" @click="delById(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>

    <!-- 分页组件Pagination -->
    <el-pagination
      v-model:current-page="pagination.currentPage"
      v-model:page-size="pagination.pageSize"
      :page-sizes="[5, 10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />



  
  <!-- 新增/修改员工对话框 -->
  <!-- <el-dialog v-model="dialogFormVisible" :title="formTitle" width="35%"> -->
    <el-dialog v-model="dialogFormVisible" width="35%">
    <template #title>
      <div class="custom-dialog-title">{{ formTitle }}</div>
    </template>

    <el-form :model="clazz" ref="clazzFormRef" :rules="rules" label-width="140px">
      <el-form-item label="Class Name" :label-width="labelWidth" prop="name">
        <el-input v-model="clazz.name" placeholder="Enter class name" />
      </el-form-item>

      <el-form-item label="Room" :label-width="labelWidth" prop="room">
        <el-input v-model="clazz.room" placeholder="Enter classroom"/>
      </el-form-item>
      
      <el-form-item label="Start Date" :label-width="labelWidth" prop="beginDate">
        <el-date-picker v-model="clazz.beginDate" type="date" placeholder="Select start date" value-format="YYYY-MM-DD" style="width: 100%;"/>
      </el-form-item>

      <el-form-item label="End Date" :label-width="labelWidth" prop="endDate">
        <el-date-picker v-model="clazz.endDate" type="date" placeholder="Select end date" value-format="YYYY-MM-DD" style="width: 100%;"/>
      </el-form-item>

      <el-form-item label="Head Teacher" :label-width="labelWidth">
        <el-select v-model="clazz.masterId" placeholder="Select head teacher" style="width: 100%;">
          <el-option v-for="emp in emps" :label="emp.name" :value="emp.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="Subject" :label-width="labelWidth" prop="subject">
        <el-select v-model="clazz.subject" placeholder="Select subject" style="width: 100%;">
          <el-option v-for="sub in subjects" :label="sub.name" :value="sub.value" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false; resetForm(clazzFormRef)">Cancel</el-button>
        <el-button type="primary" @click="save(clazzFormRef)">Save</el-button>
      </span>
    </template>
  </el-dialog>

</template>


<style scoped>
#title {
  font-size: 20px;
  font-weight: 600;
}

:deep(.el-form-item__label) {
  font-weight: bold;
  color: #333;
}

::v-deep(.el-table__header .el-table__cell) {
  font-weight: bold !important;
  color: #333 !important;
  background-color: #f8f9fa !important;
  text-align: center;
}

.form-item-gap {
  margin-bottom: 16px;
}

.custom-dialog-title {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  border-left: 4px solid #409EFF;
  padding-left: 12px;
}

</style>