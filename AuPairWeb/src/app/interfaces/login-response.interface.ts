export interface LoginResponse {
    token: string;
    user: User;
}

export interface User {
    id:string;
    name: string;
    picture: string;
    email: string;
    role: string;
    encuesta: boolean;
    
}
