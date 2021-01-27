import { Img } from './Image';

export interface Post{
    id?: number;
    text?: string;
    date?: Date;
    imageDTO?: Img;
    culturalOfferId?: number;
    src?: any;
    culturalOfferName?: string;
}
