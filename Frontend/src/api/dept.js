import request from "@/utils/request";


// 查询全部部门数据
export const queryAllApi = () => {
    return request.get('/depts');
}

//新增
export const addApi = (dept) => {
    return request.post('/depts', dept);
}

// 根据id查询部门
export const queryByIdApi = (id) => request.get(`/depts/${id}`);

// 修改
export const updateApi = (dept) => request.put('/depts', dept);

// 删除
export const deleteByIdApi = (id) => request.delete(`/depts?id=${id}`);
