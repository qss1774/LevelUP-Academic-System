import request from "@/utils/request";

// ��¼
export const loginApi = (data) => request.post("/login", data);