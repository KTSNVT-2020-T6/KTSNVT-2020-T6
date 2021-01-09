import { Img } from "./Image";

export interface Post{
    /*
        private Long id;
	private String text;
	private Date date;
	   
	public ImageDTO imageDTO;
	public Long culturalOfferId;
    */
    id?: number;
    text: string;
    date?: Date;
    imageDTO: Img;
    culturalOfferId?: number;
}