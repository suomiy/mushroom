package cz.fi.muni.pa165.rest;

/**
 * @author Filip Krepinsky (410022) on 12/4/16
 */

public class Uri {
    public static final String ROOT_OAUTH = "/oauth";
    public static final String ROOT_URI_REST = "/rest";

    public static final String ROOT_URI_FOREST = ROOT_URI_REST + "/forest";
    public static final String ROOT_URI_HUNTER = ROOT_URI_REST + "/hunter";
    public static final String ROOT_URI_MUSHROOM = ROOT_URI_REST + "/mushroom";
    public static final String ROOT_URI_MUSCHROOM_COUNT = ROOT_URI_REST + "/mushroomcount";
    public static final String ROOT_URI_VISIT = ROOT_URI_REST + "/visit";

    public static class Part {
        public static final String CREATE = "/create";
        public static final String UPDATE = "/update";
        public static final String ALL = "/**";
        public static final String ONE_SEGMENT = "/*";
    }
}
