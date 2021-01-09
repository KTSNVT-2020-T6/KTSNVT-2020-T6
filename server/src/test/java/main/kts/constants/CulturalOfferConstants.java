package main.kts.constants;

import java.util.Date;

public class CulturalOfferConstants {
	public static final String DB_CO_NAME = "Cultural offer 1";
	public static final String DB_CO_DESCRIPTION = "Description 1";
	public static final Date DB_CO_DATE = new Date(2021, 2, 1);
	public static final String DB_CO_CITY = "Belgrade";
	public static final double DB_CO_LAT = 44.5;
	public static final double DB_CO_LON = 20.5;
	public static final double DB_CO_AVERAGE_RATE = 5;
    public static final Long DB_CO_ID = 1L;

    public static final String NEW_CO_NAME = "Cultural offer 2";
	public static final String NEW_CO_DESCRIPTION = "Description 2";
	public static final Date NEW_CO_DATE = new Date(2021, 2, 1);
	public static final String NEW_CO_CITY = "Belgrade";
	public static final double NEW_CO_LAT = 44.5;
	public static final double NEW_CO_LON = 20.5;
	public static final double NEW_CO_AVERAGE_RATE = 2;
	
	public static final Long ADMIN_ID = 1L;
	public static final String ADMIN_EMAIL = "admin@gmail.com";
	public static final String ADMIN_PASSWORD = "asdf";

    public static final Long CO_ID = 1L;
    public static final Long FALSE_ID = 2L;
    
    // for integration tests
    public static final Long DB_UPDATE_ID = 3L;
    public static final int DB_SIZE = 2;
    public static final Integer PAGEABLE_PAGE = 0;
    public static final Integer PAGEABLE_SIZE = 2;
    public static final String DB_NAME = "obilazak muzeja";
    public static final Long DB_FALSE_CO_ID= 4L;
    public static final String DB_CITY = "BELGRADE";
    public static final int DB_SIZE_BY_CITY = 2;
    public static final String DB_CONTENT = "OBILAZAK";
    public static final int DB_SIZE_BY_CONTENT = 1;
    public static final int DB_SIZE_BY_COMBINED_SEARCH = 1;
    public static final int DB_ADMIN_CO_SIZE = 4;
    
    public static final String NEW_DATE_FORMAT = "2021-07-11T17:30:00.000+00:00";
    public static final Long DB_TYPE_ID = 1L;
    public static final String DB_TYPE_NAME = "museum";
    public static final String DB_TYPE_DESC = "museums in serbia";
    public static final String DB_CAT_NAME = "institution";
    public static final String DB_CAT_DESC = "institutions in serbia";
    
    public static final Long DB_FALSE_TYPE_ID = 8L;
    public static final double WRONG_LAT = 150;
    public static final double WRONG_LON = 181;
    public static final String CO_NAME3 = "Cultural offer 3";
    
    public static final String USER_EMAIL = "user2@gmail.com";

}
