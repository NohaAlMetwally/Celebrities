package com.na.celebrities.util;

/**
 * Created by Noha on 11/10/2017.
 */

public class ConfigSettings {

    //Celebrities list
    public static final String RESULTS = "results";
    public static final String TOTAL_PAGES = "total_pages";
    public static final String PAGE = "page";
    public static final String NAME = "name";
    public static final String ID = "id";

    //Person Details
    public static final String BIOGRAPHY = "biography";
    public static final String BIRTHDAY = "birthday";
    public static final String DEATHDAY = "deathday";
    public static final String GENDER = "gender";
    public static final String PLACE_OF_BIRTH = "place_of_birth";

    //Image
    public static final String FILE_PATH = "file_path";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String ASPECT_RATIO = "aspect_ratio";
    public static final String PROFILES = "profiles";
    public static final String IMAGE_PATH_PRE_URL = "https://image.tmdb.org/t/p/w500";
    public static final String IMAGES = "/images";


    //Region URL

    public static final String CELEBRITIES_URL =
            "https://api.themoviedb.org/3/person/popular?api_key=6865e3d45733dd359a97be14db5b355a&language=en-US&page=";
    public static final String PERSON_DETAILS_URL =
            "https://api.themoviedb.org/3/person/";
    public static final String API_KEY_AND_LANGUAGE = "?api_key=6865e3d45733dd359a97be14db5b355a&language=en-US";

    //End Region

}
