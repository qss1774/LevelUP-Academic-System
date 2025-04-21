<script setup>
import { onMounted, ref } from 'vue'
import { queryPageApi , addApi, queryInfoApi, updateApi, deleteApi, handleViolationApi} from '@/api/stu'
import { queryAllApi as queryAllClazzApi } from '@/api/clazz'
import { ElMessage, ElMessageBox } from 'element-plus'


//学历列表数据
const degrees = ref([
  { name: 'Grade-1', value: 1 },
  { name: 'Grade-2', value: 2 },
  { name: 'Grade-3', value: 3 },
  { name: 'Grade-4', value: 4 },
  { name: 'Grade-5', value: 5 },
  { name: 'Grade-6', value: 6 },
  { name: 'Grade-7', value: 7 },
  { name: 'Grade-8', value: 8 },
  { name: 'Grade-9', value: 9 },
  { name: 'Grade-10', value: 10 }
])
//性别列表数据
const genders = ref([{ name: 'Male', value: 1 }, { name: 'Female', value: 2 }])
//是否是院校学员列表数据
const whethers = ref([{ name: 'Y', value: 1 }, { name: 'N', value: 0 }])
//搜索表单对象
let searchStu = ref({clazzId: '', degree: '',name: ''}) 
//列表展示数据
let tableData = ref([])

//钩子函数 - 页面加载时触发
onMounted(() => {
  queryPage()
  queryAllClazz()
})


//所有的班级数据
let clazzs = ref([])
//加载所有的班级数据
const queryAllClazz = async () => {
  let result = await queryAllClazzApi()
  if(result.code) {
    clazzs.value = result.data
  }
}


//复选框
let selectIds = ref([])
const handleSelectionChange = (selection) => {
  selectIds.value = selection.map(item => item.id)
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
      searchStu.value.clazzId,
      searchStu.value.degree,
      searchStu.value.name,
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
  searchStu.value = {clazzId:'', degree:'', name:'',}
  queryPage()
}


//----------- 新增 / 修改 ---------------------------
let dialogFormVisible = ref(false) //控制新增/修改的对话框的显示与隐藏
let labelWidth = ref(145) //form表单label的宽度
let formTitle = ref('') //表单的标题
let stu = ref({ //学生对象-表单数据绑定
  name: '',
  no: '',
  gender: '',
  phone: '',
  idCard: '',
  isCollege: '',
  address: '',
  degree: '',
  graduationDate: '',
  clazzId: ''
})

//清空表单
const clearStu = () => {
  stu.value = {
    name: '',
    no: '',
    gender: '',
    phone: '',
    idCard: '',
    isCollege: '',
    address: '',
    degree: '',
    graduationDate: '',
    clazzId: ''
  }
}

//新增学员
const addStu = () => {
  dialogFormVisible.value = true
  formTitle.value = 'Add student'
  clearStu()
}

//修改学生
const updateStu = async (id) => {
  clearStu()
  dialogFormVisible.value = true
  formTitle.value = 'Edit student'
  let result = await queryInfoApi(id)
  if(result.code){
    stu.value = result.data
  }
}

//表单校验规则
const stuFormRef = ref()
const rules = ref({
  name: [
    { required: true, message: 'Name is required', trigger: 'blur' },
    { min: 2, max: 10, message: 'Name must be between 2 and 10 characters', trigger: 'blur' }
  ],
  no: [
    { required: true, message: 'Student number is required', trigger: 'blur' },
    { min: 10, max: 10, message: 'Student number must be exactly 10 characters', trigger: 'blur' }
  ],
  gender: [{ required: true, message: 'Gender is required', trigger: 'change' }],
  phone: [
    { required: true, message: 'Phone number is required', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/g, message: 'Please enter a valid phone number', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: 'ID card number is required', trigger: 'blur' },
    { pattern: /^\d{18}$/g, message: 'Please enter a valid 18-digit ID card number', trigger: 'blur' }
  ],
  isCollege: [
    { required: true, message: 'Please specify if the student is from a college', trigger: 'change' }
  ]
})

//重置表单
const resetForm = (stuForm) => {
  if (!stuForm) return
  stuForm.resetFields()
}

//-------------保存学生信息 
const save = (stuForm) => {
  //表单校验
  if(!stuForm) return
  stuForm.validate(async (valid) => {
    if(valid) {
      let api 
      if(stu.value.id) {
        api = updateApi(stu.value)
      }else {
        api = addApi(stu.value)
      }
      
      let result = await api
      if(result.code) {
        ElMessage.success('Successful!')
        dialogFormVisible.value = false
        queryPage()
      }else {
        ElMessage.error(result.msg)
      }
    }else {
      return false
    }
  })
}


//------- 删除学生
//根据ID删除单个学生
const delById = async (id) => {
  ElMessageBox.confirm('Are you sure you want to delete this record?' , 'Delete Student', {confirmButtonText:'Confirm', cancelButtonText:'Cancel', type:'warning'})
    .then(async () => {
      let result =  await deleteApi(`${id}`)
      if(result.code) {
        ElMessage.success('Delete Success')
        queryPage()
      }else {
        ElMessage.error(result.msg)
      }
    }).catch(() => {
      ElMessage.info('Deletion Canceled')
    })
}

//批量删除员工
const delByIds = async () => {
  ElMessageBox.confirm('Are you sure you want to delete these records?' , 'Delete Students', {confirmButtonText:'Confirm', cancelButtonText:'Cancel',type:'warning'})
    .then(async () => {
      let result =  await deleteApi(selectIds.value.join(','))
      if(result.code) {
        ElMessage.success('Delete Success')
        queryPage()
      }else {
        ElMessage.error(result.msg)
      }
    }).catch(() => {
      ElMessage.info('Deletion Canceled')
    })
}


const openViolation = (id) => {
  ElMessageBox.prompt('Please enter violation penalty points', 'Notice', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    inputPattern: /\d?/,
    inputErrorMessage: 'Invalid input',
  }).then(async (val) => {    
    let result = await handleViolationApi(id, val.value);
    if(result.code) {
      ElMessage.success('Processed successfully')
      queryPage()
    }else {
      ElMessage.error(result.msg)
    }
  })
}
</script>

<template>
    <!-- 顶部标题 -->
    <div>
      <div id="title">Student Management</div><br>
    </div>

    <!-- 条件搜索表单 -->
    <el-form :inline="true" :model="searchStu" class="demo-form-inline">
      <el-form-item label="Name">
        <el-input v-model="searchStu.name" placeholder="Enter student name"/>
      </el-form-item>

      <el-form-item label="Grade">
        <el-select v-model="searchStu.degree" placeholder="Select grade">
          <el-option v-for="degree in degrees" :label="degree.name" :value="degree.value" />
        </el-select>
      </el-form-item>

      <el-form-item label="Class">
        <el-select v-model="searchStu.clazzId" placeholder="Select class">
          <el-option v-for="clazz in clazzs" :label="clazz.name" :value="clazz.id" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="queryPage()">Search</el-button>
        <el-button type="info" @click="clear()">Reset</el-button>
      </el-form-item>
    </el-form>
    
    <!-- 功能按钮 -->
    <el-button type="success" @click="addStu();resetForm(stuFormRef)">+ Add Student</el-button>
    <el-button type="danger" @click="delByIds()">- Delete Selected</el-button>
    <br><br>
    
    
    <!-- 列表展示 -->
    <el-table :data="tableData" border style="width: 100%" fit @selection-change="handleSelectionChange">
      <el-table-column type="selection"  align="center" width="35" />
      <el-table-column prop="name" label="Name" align="center" width="80px" />
      <el-table-column prop="no" label="Stu ID" align="center" width="100px" />
      <el-table-column prop="clazzName" label="Class" align="center" width="120px"/>
      <el-table-column prop="gender" label="Gender" align="center" width="75px" >
        <template #default="scope">
          {{ scope.row.gender == 1 ? 'Male': 'Female' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="Phone Number" align="center" width="120px"/>
      <el-table-column prop="degree" label="Current Grade" align="center" width="80px">
        <template #default="scope">
          <span v-if="scope.row.degree == 1">Grade-1</span>
          <span v-else-if="scope.row.degree == 2">Grade-2</span>
          <span v-else-if="scope.row.degree == 3">Grade-3</span>
          <span v-else-if="scope.row.degree == 4">Grade-4</span>
          <span v-else-if="scope.row.degree == 5">Grade-5</span>
          <span v-else-if="scope.row.degree == 6">Grade-6</span>
          <span v-else-if="scope.row.degree == 7">Grade-7</span>
          <span v-else-if="scope.row.degree == 8">Grade-8</span>
          <span v-else-if="scope.row.degree == 9">Grade-9</span>
          <span v-else-if="scope.row.degree == 10">Grade-10</span>
          <span v-else>Else</span>
        </template>
      </el-table-column>
      <el-table-column prop="violationCount" label="Violation Count" align="center" width="85px" />
      <el-table-column prop="violationScore" label="Violation Penalty" align="center" width="85px" />
      <el-table-column prop="updateTime" label="Last Modified Time" align="center" width="160px"/>
      <el-table-column label="Actions" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="updateStu(scope.row.id) ;resetForm(stuFormRef)">Edit</el-button>
          <el-button type="warning" size="small" @click="openViolation(scope.row.id)">Violation</el-button>
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



  
  <!-- 新增/修改学员对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="60%">
    <template #title>
      <div class="custom-dialog-title">{{ formTitle }}</div>
    </template>
    <el-form :model="stu" ref="stuFormRef" :rules="rules">
      <!-- 第一行 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="Name" :label-width="labelWidth" prop="name">
            <el-input v-model="stu.name" placeholder="Enter student's name"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Stu ID" :label-width="labelWidth" prop="no">
            <el-input v-model="stu.no" placeholder="Enter student's ID"/>
          </el-form-item>
        </el-col>
      </el-row>
      
      <!-- 第二行 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="Gender" :label-width="labelWidth"  prop="gender">
            <el-select v-model="stu.gender" placeholder="Select" style="width: 100%;">
              <el-option v-for="gender in genders" :label="gender.name" :value="gender.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Phone Number" :label-width="labelWidth"  prop="phone">
            <el-input v-model="stu.phone" placeholder="Enter student’s phone number"/>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="ID Card Number" :label-width="labelWidth"  prop="idCard">
            <el-input v-model="stu.idCard" placeholder="Enter ID card number"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="From school" :label-width="labelWidth" prop="isCollege">
            <el-select v-model="stu.isCollege" placeholder="Select" style="width: 100%;">
              <el-option v-for="w in whethers" :label="w.name" :value="w.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="Contact Address" :label-width="labelWidth">
            <el-input v-model="stu.address" placeholder="Enter contact address"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Current Grade" :label-width="labelWidth">
            <el-select v-model="stu.degree" placeholder="Select" style="width: 100%;">
              <el-option v-for="degree in degrees" :label="degree.name" :value="degree.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 第五行 -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="Planned Graduation" :label-width="labelWidth">
            <el-date-picker v-model="stu.graduationDate" type="date" placeholder="Select graduation date" value-format="YYYY-MM-DD" style="width: 100%;"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Class" :label-width="labelWidth">
            <el-select v-model="stu.clazzId" placeholder="Select" style="width: 100%;">
              <el-option v-for="clazz in clazzs" :label="clazz.name" :value="clazz.id" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false; resetForm(stuFormRef)">Cancel</el-button>
        <el-button type="primary" @click="save(stuFormRef)">Save</el-button>
      </span>
    </template>
  </el-dialog>


  <!-- 违纪处理 -->

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

.custom-dialog-title {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  border-left: 4px solid #409EFF;
  padding-left: 12px;
}

</style>