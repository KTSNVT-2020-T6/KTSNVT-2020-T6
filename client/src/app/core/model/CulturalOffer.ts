import { Img } from './Image';
import { Type } from './Type';
export interface CulturalOffer {
    id?: number;
    averageRate?: number;
    description: string;
    name: string;
    city: string;
    date: Date;
    lat: number;
    lon: number;
    typeDTO?: Type;
    imageDTO?: Img[];
    base64image?: string;
}
