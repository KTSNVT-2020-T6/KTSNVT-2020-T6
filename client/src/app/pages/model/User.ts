import { Image } from "./Image";
export interface User{
    id?: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    active: boolean;
    verified: boolean;
    idImageDTO: any;
}