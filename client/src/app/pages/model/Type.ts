import { Category } from "./Category";
export interface Type {
	id?: number;
	description: string;
    name: string;
    categoryDTO?: Category;
}
