import request from "@/utils/request";

// µÇÂ¼
export const loginApi = (data) => request.post("/login", data);