<script setup>

import { ref, onMounted, watch } from 'vue'
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi} from '@/api/emp';
import { queryAllApi as queryAllDeptApi } from '@/api/dept';
import { ElMessage, ElMessageBox} from 'element-plus';


// token
const token = ref(localStorage.getItem('token'))

// 元数据
//职位列表数据
const jobs = ref([{ name: 'Math Tutor', value: 1 },{ name: 'Reading Tutor', value: 2 },{ name: 'Lead assistant', value: 3 },
{ name: 'Front desk support', value: 4 },{ name: 'Manager', value: 5 },{ name: 'Else', value: 6 }])
//性别列表数据
const genders = ref([{ name: 'Male', value: 1 }, { name: 'Female', value: 2 }])
// 部门列表数据
const depts = ref([])


// 查询员工列表函数
const search = async() =>{
  const result = await queryPageApi(searchEmp.value.name, 
                                    searchEmp.value.gender, 
                                    searchEmp.value.begin, 
                                    searchEmp.value.end,
                                    currentPage.value,
                                    pageSize.value);

  if(result.code){//返回的记录数
    empList.value = result.data.rows;
    total.value = result.data.total;
  }

}

// 钩子函数->进入这个页面之后就开始加载数据
onMounted(() => {
  search();//查询员工列表数据
  queryAllDepts();//查询所有部门数据
  getToken();//获取token
})

// 获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
}

// 查询所有部门数据
const queryAllDepts = async() =>{
  const result = await queryAllDeptApi();
  if(result.code){
    depts.value = result.data;
  }
}
// 清空员工列表函数 
const clear = () =>{
  searchEmp.value = {name: '', gender:'', date:[], begin:'', end:''}
  search();
}

// watch监听
// 搜索表单对象
const searchEmp = ref({name: '', gender:'', date:[], begin:'', end:''})

watch(()=> searchEmp.value.date, (newVal, oldVal) => {
  if(newVal.length == 2){
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  }else{
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
})
// 监听一个响应式数据
// const a = ref('');
// watch(a, (newVal, oldVal) => {
//   console.log(`${newVal}, ${oldVal}`);
// })

// // 监听一个对象(监听对象的全部属性)
// const user = ref({ name: '', age: 0});
// watch(user, (newVal, oldVal) => {
//   {deep: true}
// })

// 监听对象的某一个属性
// const user = ref({ name: '', age: 0});
// watch(() => { return user.value.name} , (newVal, oldVal) => {

// })

// 员工列表数据
const empList = ref([]);

// 分页相关的数据模型
// 页码
const currentPage = ref(1)
// 每页展示记录数
const pageSize = ref(10)
const background = ref(true)
// 总记录数
const total = ref(0)

// 每页展示记录数变化
const handleSizeChange = (val) => {
  search();//会将当前的页码和每页展示的记录数发送给服务器端，查询对应的数据
  // console.log(`${val} items per page`)
}
// 页码变化时触发
const handleCurrentChange = (val) => {
  search();
  // console.log(`current page: ${val}`)
}


// 新增员工
const addEmp = () => {
  dialogVisible.value = true;
  dialogTitle.value = 'Add Employee'; 
  employee.value = {
    username: '',
    name:'',
    gender:'',
    phone:'',
    job:'',
    salary:'',
    deptId:'',
    entryDate:'',
    image:'',
    exprList: []
  }
  // 重制表单校验规则-提示信息
  if(empFormRef.value) {
    empFormRef.value.resetFields();
  }
}

//新增/修改表单
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('Add Employee')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  // 上传图片之后回显
  employee.value.image = response.data;
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('Image upload only supported')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('Supported format: Image only, max size 10MB')
    return false
  }
  return true
}

// 添加工作经历
// 时间绑定到exprDate中，给服务器端提交数据的时候要的是begin和end，调用watch监听
const addExprItem = () => {
  employee.value.exprList.push({
    company: '',
    job: '',
    exprDate:[],
    begin: '',
    end: ''
  })
}

// 删除工作经历
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}

// 监听employee员工对象中的工作经历信息 - 深度监听：监听数组中的元素变化
watch(() => employee.value.exprList, (newVal, oldVal) => {
 if(employee.value.exprList && employee.value.exprList.length > 0) 
  employee.value.exprList.forEach((expr) => {
    expr.begin = expr.exprDate[0]
    expr.end = expr.exprDate[1]
  })
}, {deep: true})

// 保存员工
const save = async() => {
    // 表单校验
    if(!empFormRef.value) return;

    empFormRef.value.validate( async(valid) => {
      if(valid){ 

        let result; 

        if(employee.value.id){
          // 存在则执行修改
          // 表单校验
          result = await updateApi(employee.value);
        }else{
          // 不存在执行新增
          // 表单校验
          result = await addApi(employee.value);
        }
        
        if(result.code){
            // 提示成功信息
            ElMessage.success('Save successfully');
            // 关闭对话框
            dialogVisible.value = false;
            // 再次查询
            search();
          }else{
            ElMessage.error(result.msg);
          }
        }else{
        ElMessage.error('Please fill in the correct information');
      }
    })
  }

//表单校验规则
const rules = ref({
  username: [
    { required: true, message: 'Please enter a username', trigger: 'blur' },
    { min: 2, max: 20, message: 'Username must be between 2 and 20 characters', trigger: 'blur' }
  ],
  name: [
    { required: true, message: 'Please enter full name', trigger: 'blur' },
    { min: 2, max: 10, message: 'Full name must be between 2 and 10 characters', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: 'Please select gender', trigger: 'change' }
  ],
  // /^1[3-9]\d{9}$/ : 以1开头，第二位是3-9，后面9位是0-9的数字->一共11位
  phone: [
    { required: true, message: 'Please enter a phone number', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: 'Please enter a valid phone number', trigger: 'blur' }
  ]
});

// 表单引用
const empFormRef = ref();

// 编辑
const edit = async (id) => {

  const result = await queryInfoApi(id);
  if (result.code) {
    // 显示对话框
    dialogVisible.value = true;
    // 修改标题
    dialogTitle.value = 'Edit staff';
    // 服务端返回的结果赋值给数据模型employee，页面展示也会跟着变化
    employee.value = result.data;

    // 对工作经历进行处理
    // 因为服务器端返回来的并不是这样的一个数组，而是两个属性，begin和end
    let exprList = employee.value.exprList;
    if (exprList && exprList.length > 0) {
      exprList.forEach((expr) => {
        expr.exprDate = [expr.begin, expr.end];
      });
    }
    
  }
};

// 删除员工
const deleteById = (id) => {
  // 提示确认框
  ElMessageBox.confirm(
    'Are you sure permanently delete this employee?',
    'Warning',
    {confirmButtonText: 'OK',cancelButtonText: 'Cancel',type: 'warning'}
  )//点击确认
    .then(async() => {
      const result = await deleteApi(id);

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

// 记录勾选员工的id
const selectedIds = ref([]);

// 复选框勾选发生变化时触发
const handleSelectionChange = (selection) => {
  // 只会一直添加值而不会删除
  // selection.forEach(item => {
  //   selectedIds.value.push(item.id);
  // })
  selectedIds.value = selection.map( item => item.id);
  
}

// 批量删除
const deleteByIds = () => {
   // 提示确认框
   ElMessageBox.confirm(
    'Are you sure permanently delete this employee?',
    'Warning',
    {confirmButtonText: 'OK',cancelButtonText: 'Cancel',type: 'warning'}
  )//点击确认
    .then(async() => {
      if(selectedIds.value && selectedIds.value.length > 0){

        const result = await deleteApi(selectedIds.value);

        if(result.code){
          ElMessage.success('success'),
          // 删除之后再查询一次
          search();
        }else{
          ElMessage.error(result.msg);
        }
      }else{
          ElMessage({
          type: 'info',
          message: 'You have to select employee',
        })
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

  <h1>Staff management</h1>

  <div class="table-wrapper">

    <el-form :inline="true" :model="searchEmp" class="form-bar"
    style="display: flex; align-items: center; flex-wrap: nowrap; overflow-x: auto;">

      <el-form-item label="Name" style="margin-right: 16px;">
        <el-input v-model="searchEmp.name" placeholder="Employee name"/>
      </el-form-item>

      <el-form-item label="Gender" style="margin-right: 16px;">
        <el-select v-model="searchEmp.gender" placeholder="select">
          <el-option label="Male" value="1" />
          <el-option label="Female" value="2" />
        </el-select>
      </el-form-item>

      <el-form-item label="Employment Time" style="margin-right: 16px;">
        <el-date-picker v-model="searchEmp.date" type="daterange" range-separator="To" 
          start-placeholder="Start date" 
          end-placeholder="End date"
          value-format="YYYY-MM-DD"
          style="width: 250px;"/>
      </el-form-item>

      <el-form-item style="margin-left: 16px;">
        <div style="display: flex; gap: 12px;">
          <el-button type="primary" @click="search">Query</el-button>
          <el-button type="info" @click="clear">Clear</el-button>
        </div>
      </el-form-item>

    </el-form>

    <!-- 功能按钮 -->

    <div class="table" style="margin-bottom: 16px;">
      <el-button type="primary" @click="addEmp">+ Add employee</el-button>
      <el-button type="info" @click="deleteByIds">- Delete employee</el-button>
    </div>

    <!-- 数据表格 -->

    <div class="container">
      <el-table :data="empList" border stripe style="width: 100% " @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"  align="center"/>
        <el-table-column prop="name" label="Name" width="90" align="center"/>

        <el-table-column label="Gender" width=“70” align="center">
          <template #default="scope">
            {{scope.row.gender == 1 ? 'Male' : 'Female' }}
          </template>
        </el-table-column>

        <el-table-column prop="image" label="Picture" width="120" align="center">
          <template #default="scope">
            <img :src="scope.row.image" alt="" width="50" height="50" />
          </template>
        </el-table-column>

        <el-table-column prop="deptName" label="Department" width="100" align="center"/>

        <el-table-column prop="job" label="Job title" width="140" align="center">
          <template #default="scope">
            <span v-if="scope.row.job == 1">Math tutor</span>
            <span v-else-if="scope.row.job == 2">Reading tutor</span>
            <span v-else-if="scope.row.job == 3">Lead assistant</span>
            <span v-else-if="scope.row.job == 4">Front desk support</span>
            <span v-else-if="scope.row.job == 5">Manager</span>
            <span v-else>Else</span>
          </template>
        </el-table-column>

        <el-table-column prop="entryDate" label="Entry date" width="130" align="center"/>
        <el-table-column prop="updateTime" label="Last update" width="180" align="center"/>
        <el-table-column label="Operation" width="180" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" style="margin-right: 10px" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> Edit</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon><DeleteFilled /></el-icon> Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页条 -->
     <!-- 测试{{currentPage}} : {{pageSize}} -->
     <!-- 加了v-model双向绑定，将当前页码和每页展示的记录数发送给服务器端，查询对应的数据 -->
      <div>
        <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 15, 20, 25]"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
     </div>
       
  <!-- 新增/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="60%">
    <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="150px">

      <!-- {{employee}} -->

      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Username" prop="username">
            <el-input v-model="employee.username" class="input-short" placeholder="Enter a username, 2-20 characters"></el-input>
          </el-form-item>
        </el-col>
        
        <el-col :span="12">
          <el-form-item label="Full Name" prop="name">
            <el-input v-model="employee.name" class="input-short" placeholder="Enter full name, 2-10 characters"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Gender" prop="gender">
            <el-select v-model="employee.gender" class="input-short" placeholder="Select" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
              
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="Phone Number" prop="phone">
            <el-input v-model="employee.phone" class="input-short" placeholder="Enter employee’s phone number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Job Title">
            <el-select v-model="employee.job" class="input-short" placeholder="Select" style="width: 100%;">
              <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
              <!-- <el-option label="Math tutor" value="1"></el-option>
              <el-option label="Reading tutor" value="2"></el-option>
              <el-option label="Lead assistant" value="3"></el-option>
              <el-option label="Front desk support" value="4"></el-option>
              <el-option label="Manager" value="5"></el-option> -->
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Salary">
            <el-input v-model="employee.salary" class="input-short" placeholder="Enter employee’s salary"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Department">
            <el-select v-model="employee.deptId" class="input-short" placeholder="Select" style="width: 100%;">
              <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
              <!-- <el-option label="Reading" value="2"></el-option>
              <el-option label="Help desk" value="3"></el-option>
              <el-option label="Class work" value="4"></el-option>
              <el-option label="Home work" value="5"></el-option> -->
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Entry Date">
            <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" class="input-short" placeholder="Select date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="Profile Picture">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{'token' : token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      

      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="Work Experience" label-width="140px">
            <el-button type="success" size="small" @click="addExprItem">+ Add Work Experience</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      
      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="5" v-for="(expr,index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="Duration" label-width="80px">
            <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="to" start-placeholder="Start Date" end-placeholder="End Date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="Company" label-width="75px">
            <el-input class="input-short" placeholder="Enter company name" v-model="expr.company"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="Position" label-width="75px">
            <el-input class="input-short" placeholder="Enter position" v-model="expr.job"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="10px">
            <el-button type="danger" @click="delExprItem(index)" >- Delete</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    
    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </span>
    </template>
</el-dialog>
    

  </div>

</template>

<style scoped>
 .table-wrapper {
    position: relative;
    padding: 20px 30px;
    margin: 20px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    max-width: 100%;
    overflow-x: auto;
  }

  /* 控制表单排列 */
  .form-bar {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 16px;
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

  :deep(.el-form-item__label){
  font-weight: 600;
  color: #333;       /* 比默认灰色更深，更容易看清 */
  font-size: 15px;   /* 稍微大一点，更清楚 */
}

:deep(.el-dialog__title) {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  border-left: 4px solid #409EFF;
  padding-left: 12px;
}


.input-short {
  max-width: 300px;
  width: 100%;
}

/* .input-short :deep(.el-input),
.input-short :deep(.el-date-editor) {
  max-width: 300px;
  width: 100%;
} */


.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}


</style>