import { Image } from "./Image";
export interface CulturalOffer {
	_id: number;
	averageRate: number;
	description: string;
	name: string;
	city: string;
	date: Date;
	lat: number;
	lon: number;
	typeId: number;
	images: Image[];
	// Set<Image> image;
}
