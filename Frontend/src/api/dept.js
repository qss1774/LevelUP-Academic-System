import request from "@/utils/request";


// ��ѯȫ����������
export const queryAllApi = () => {
    return request.get('/depts');
}

//����
export const addApi = (dept) => {
    return request.post('/depts', dept);
}

// ����id��ѯ����
export const queryByIdApi = (id) => request.get(`/depts/${id}`);

// �޸�
export const updateApi = (dept) => request.put('/depts', dept);

// ɾ��
export const deleteByIdApi = (id) => request.delete(`/depts?id=${id}`);
