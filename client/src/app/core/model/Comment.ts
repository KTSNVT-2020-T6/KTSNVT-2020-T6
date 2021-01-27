import { Img } from './Image';
export interface Comment {
    id?: number;
    text?: string;
    date?: Date;
    nameSurname?: string;
    userId?: number;
    userImage?: Img;
    srcUser?: any;
    srcComment?: any;
    imageDTO?: Img;
    culturalOfferId?: number;
}
