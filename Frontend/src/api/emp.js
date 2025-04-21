import request from "@/utils/request";


// ��ѯԱ���б�����-һ��Ҫexport��Ȼǰ�˽��ܲ���
export const queryPageApi = (name, gender, begin, end, page,pageSize) => 
    request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);

//����
export const addApi = (emp) => request.post('/emps',emp);

// ����id��ѯ����
export const queryInfoApi = (id) => request.get(`/emps/${id}`);

// �޸�
export const updateApi = (emp) => request.put('/emps',emp);

// ɾ��
export const deleteApi = (ids) => request.delete(`/emps?ids=${ids}`);


//��ѯ����Ա��
export const queryAllApi = () => request.get(`/emps/list`);