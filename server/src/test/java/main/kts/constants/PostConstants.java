package main.kts.constants;

import java.util.Date;

public class PostConstants {
	public static final String DB_POST_TEXT = "gosti iznenadjenja";
    public static final Long DB_POST_ID = 1L;
    public static final Date DB_POST_DATE = new Date();
    
    public static final String DB_IMAGE_NAME = "image";
    public static final String DB_IMAGE_RELATIVE_PATH = "path";
    public static final Long DB_IMAGE_ID = 4L;
    
    public static final String DB_IMAGE_NAME1 = "image1";
    public static final String DB_IMAGE_RELATIVE_PATH1 = "path1";
    public static final Long DB_IMAGE_ID1 = 2L;

    public static final String NEW_POST_TEXT = "new text";
    public static final Date NEW_POST_DATE = new Date();
    public static final Long NEW_IMAGE_ID = 3L;
    
    public static final String UPDATED_POST_TEXT = "updated";
    public static final Date UPDATED_POST_DATE = new Date();

    
    public static final long FIND_ALL_NUMBER_OF_ITEMS = 1;

    public static final Integer PAGEABLE_PAGE = 0;
    public static final Integer PAGEABLE_SIZE = 2;
    public static final Integer PAGEABLE_TOTAL_ELEMENTS = 1;

    public static final Long POST_ID = 1L;
    public static final Long FALSE_POST_ID = 2L;


    //------INTEGRATION TEST
    public static final int DB_SIZE = 2;
    public static final Long DB_FALSE_POST_ID = 4L;
    public static final Long IMAGE_ID = 9L;

    public static final Long DELETE_ID = 2L;
    public static final String ADMIN_EMAIL_LOGIN = "admin@gmail.com";
	public static final String ADMIN_PASSWORD_LOGIN = "asdf";
	public static final String NEW_DATE_FORMAT = "2021-07-11T17:30:00.000+00:00";
	public static final String INVALID_DATE_FORMAT = "2020-07-11T17:30:00.000+00:00";
	public static final Long DB_CULTURAL_OFFER_ID = 1L;
}
