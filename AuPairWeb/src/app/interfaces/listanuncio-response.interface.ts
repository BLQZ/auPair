import { User } from "../models/user";

export interface ListAnuncioResponse {
    id: String;
    contenido: String;
    ownerId: User;
    createdAt: Date;
}