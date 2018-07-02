package com.bccns.umsserviceweb.common.util;

import org.apache.commons.lang.StringUtils;

@Deprecated
public class BrowserDetector {
    public static final String MSIE            = "MSIE";
    public static final String OPERA           = "Opera";
    public static final String MOZILLA         = "Mozilla";
    public static final String CHROME          = "Chrome";
    public static final String NETSCAPE        = "Navigator";

    public static final String WINDOWS         = "Windows";
    public static final String UNIX            = "Unix";
    public static final String MACINTOSH       = "Macintosh";

    /** The user agent string. */
    private String             userAgentString = "";

    /** The browser name specified in the user agent string. */
    private String             browserName     = "";

    /**
     * The browser version specified in the user agent string. If we can't parse
     * the version just assume an old browser.
     */
    private float              browserVersion  = (float) 1.0;

    /**
     * The browser platform specified in the user agent string.
     */
    private String             browserPlatform = "unknown";

    /** Whether or not javascript works in this browser. */
    private boolean            javascriptOK    = false;

    /** Whether or not CSS works in this browser. */
    private boolean            cssOK           = false;

    /** Whether or not file upload works in this browser. */
    private boolean            fileUploadOK    = false;

    /**
     * Constructor used to initialize this class.
     * 
     * @param userAgentString
     *            A String with the user agent field.
     */
    public BrowserDetector(String userAgentString) {
        this.userAgentString = userAgentString;
        parse();
    }

    /**
     * Whether or not CSS works in this browser.
     * 
     * @return True if CSS works in this browser.
     */
    public boolean isCssOK() {
        return cssOK;
    }

    /**
     * Whether or not file upload works in this browser.
     * 
     * @return True if file upload works in this browser.
     */
    public boolean isFileUploadOK() {
        return fileUploadOK;
    }

    /**
     * Whether or not Javascript works in this browser.
     * 
     * @return True if Javascript works in this browser.
     */
    public boolean isJavascriptOK() {
        return javascriptOK;
    }

    /**
     * The browser name specified in the user agent string.
     * 
     * @return A String with the browser name.
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * The browser platform specified in the user agent string.
     * 
     * @return A String with the browser platform.
     */
    public String getBrowserPlatform() {
        return browserPlatform;
    }

    /**
     * The browser version specified in the user agent string.
     * 
     * @return A String with the browser version.
     */
    public float getBrowserVersion() {
        return browserVersion;
    }

    /**
     * The user agent string for this class.
     * 
     * @return A String with the user agent.
     */
    public String getUserAgentString() {
        return userAgentString;
    }

    /**
     * Helper method to initialize this class.
     */
    private void parse() {
        int versionStartIndex = userAgentString.indexOf("/");
        int versionEndIndex = userAgentString.indexOf(" ");

        // Get the browser name and version.
        browserName = userAgentString.substring(0, versionStartIndex);
        try {
            // Not all user agents will have a space in the reported
            // string.
            String agentSubstring = null;
            if ( versionEndIndex < 0 ) {
                agentSubstring = userAgentString.substring(versionStartIndex + 1);
            }
            else {
                agentSubstring = userAgentString.substring(versionStartIndex + 1, versionEndIndex);
            }
            browserVersion = toFloat(agentSubstring);
        }
        catch ( NumberFormatException e ) {
            // Just use the default value.
            throw e;
        }

        // MSIE lies about its name. Of course...
        if ( userAgentString.indexOf(MSIE) != -1 ) {
            // Ex: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)
            versionStartIndex = userAgentString.indexOf(MSIE) + MSIE.length() + 1;
            versionEndIndex = userAgentString.indexOf(";", versionStartIndex);

            browserName = MSIE;
            try {
                browserVersion = toFloat(userAgentString.substring(versionStartIndex,
                        versionEndIndex));
            }
            catch ( NumberFormatException e ) {
                // Just use the default value.
                throw e;
            }

            // PHP code
            // $Browser_Name = "MSIE";
            // $Browser_Version = strtok("MSIE");
            // $Browser_Version = strtok(" ");
            // $Browser_Version = strtok(";");
        }

        // Opera isn't completely honest, either...
        // Modificaton by Chris Mospaw <mospaw@polk-county.com>
        if ( userAgentString.indexOf(OPERA) != -1 ) {
            // Ex: Mozilla/4.0 (Windows NT 4.0;US) Opera 3.61 [en]
            // Ex: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; en) Opera
            // Ex: Opera/10.00 (Windows NT 5.1; U; en) Presto/2.2.0
            // 8.02
            versionStartIndex = userAgentString.indexOf(OPERA) + OPERA.length() + 1;
            versionEndIndex = userAgentString.indexOf(" ", versionStartIndex);
            if ( versionEndIndex == -1 ) {
                versionEndIndex = userAgentString.length();
            }

            browserName = OPERA;
            try {
                browserVersion = toFloat(userAgentString.substring(versionStartIndex, versionEndIndex));
            }
            catch ( NumberFormatException e ) {
                // Just use the default value.
                throw e;
            }

            // PHP code
            // $Browser_Name = "Opera";
            // $Browser_Version = strtok("Opera");
            // $Browser_Version = strtok("/");
            // $Browser_Version = strtok(";");
        }
        if ( userAgentString.indexOf(CHROME) != -1 ) {
            // Ex: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/530.5 (KHTML, like
            // Gecko) Chrome/2.0.172.39 Safari/530.5
            versionStartIndex = userAgentString.indexOf(CHROME) + CHROME.length() + 1;
            versionEndIndex = userAgentString.indexOf(" ", versionStartIndex);
            if ( versionEndIndex == -1 ) {
                versionEndIndex = userAgentString.length();
            }

            browserName = CHROME;
            try {
                String tempVersion1 = userAgentString.substring(versionStartIndex, versionEndIndex);
                String tempVersion2 = null;
                if ( StringUtils.countMatches(tempVersion1, ".") > 1 ) {
                    tempVersion2 = StringUtils.substringAfter(tempVersion1, ".");
                    tempVersion2 = StringUtils.substringAfter(tempVersion2, ".");
                    tempVersion1 = StringUtils.remove(tempVersion1, "." + tempVersion2);
                }
                browserVersion = toFloat(tempVersion1);
            }
            catch ( NumberFormatException e ) {
                // Just use the default value.
                throw e;
            }
        }
        if ( userAgentString.indexOf(NETSCAPE) != -1 ) {
            // Ex: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080219
            // Firefox/2.0.0.12 Navigator/9.0.0.6
            versionStartIndex = userAgentString.indexOf(NETSCAPE) + NETSCAPE.length() + 1;
            versionEndIndex = userAgentString.indexOf(" ", versionStartIndex);
            if ( versionEndIndex == -1 ) {
                versionEndIndex = userAgentString.length();
            }

            browserName = "Netscape";
            try {
                String tempVersion1 = userAgentString.substring(versionStartIndex, versionEndIndex);
                String tempVersion2 = null;
                if ( StringUtils.countMatches(tempVersion1, ".") > 1 ) {
                    tempVersion2 = StringUtils.substringAfter(tempVersion1, ".");
                    tempVersion2 = StringUtils.substringAfter(tempVersion2, ".");
                    tempVersion1 = StringUtils.remove(tempVersion1, "." + tempVersion2);
                }
                browserVersion = toFloat(tempVersion1);
            }
            catch ( NumberFormatException e ) {
                // Just use the default value.
                throw e;
            }
        }

        // Try to figure out what platform.
        if ( userAgentString.indexOf("Windows") != -1 
                || userAgentString.indexOf("WinNT") != -1 
                || userAgentString.indexOf("Win98") != -1 
                || userAgentString.indexOf("Win95") != -1 ) {
            browserPlatform = WINDOWS;
        }

        if ( userAgentString.indexOf("Mac") != -1 ) {
            browserPlatform = MACINTOSH;
        }

        if ( userAgentString.indexOf("X11") != -1 ) {
            browserPlatform = UNIX;
        }

        if ( browserPlatform == WINDOWS ) {
            if ( browserName.equals(MOZILLA) ) {
                if ( browserVersion >= 3.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }
                if ( browserVersion >= 4.0 ) {
                    cssOK = true;
                }
            }
            else if ( browserName == MSIE ) {
                if ( browserVersion >= 4.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                    cssOK = true;
                }
            }
            else if ( browserName == OPERA ) {
                if ( browserVersion >= 3.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                    cssOK = true;
                }
            }
        }
        else if ( browserPlatform == MACINTOSH ) {
            if ( browserName.equals(MOZILLA) ) {
                if ( browserVersion >= 3.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }
                if ( browserVersion >= 4.0 ) {
                    cssOK = true;
                }
            }
            else if ( browserName == MSIE ) {
                if ( browserVersion >= 4.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }
                if ( browserVersion > 4.0 ) {
                    cssOK = true;
                }
            }
        }
        else if ( browserPlatform == UNIX ) {
            if ( browserName.equals(MOZILLA) ) {
                if ( browserVersion >= 3.0 ) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }
                if ( browserVersion >= 4.0 ) {
                    cssOK = true;
                }
            }
        }
    }

    /**
     * Helper method to convert String to a float.
     * 
     * @param s
     *            A String.
     * @return The String converted to float.
     */
    private static final float toFloat(String s) {
        return Float.valueOf(s).floatValue();
    }

}
