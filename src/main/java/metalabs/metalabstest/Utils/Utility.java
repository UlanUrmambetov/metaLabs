package metalabs.metalabstest.Utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
    public static String makeSiteUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
