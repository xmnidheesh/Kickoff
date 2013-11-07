
package com.jn.kickoff.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;

public class Util {

    private static final String PROPERTY_APP_VERSION = "appVersion";

    private static final String Tag = Util.class.getName();

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void WriteIframeFileToEXternal(String iframecontent) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();

        String fname = "iframe_content.html";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter outwriter = new OutputStreamWriter(out);
            outwriter.write(iframecontent);

            outwriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTimesince(String date) {

        // Date :2013-05-14 11:25:10

        SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        parserSDF.setTimeZone(TimeZone.getTimeZone("UTC/Etc"));
        Date datePosted = null;
        try {
            datePosted = parserSDF.parse(date);

        } catch (ParseException e) {
            System.out.println("Invalid date format " + e);
        }

        if (datePosted != null) {
            Date now = new Date();

            // in sec
            long diff = (now.getTime() - datePosted.getTime()) / 1000;

            long minutes = diff / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (diff < 30) {
                return "few seconds ago";
            } else if (minutes < 1 && diff > 20) {
                return diff + "s ago";

            } else if (minutes < 60 && minutes >= 1) {
                return minutes + "m ago";
            } else if (hours < 24 && hours >= 1) {
                return hours + "h ago";
            } else if (days >= 1 && days <= 31) {
                return days + "d ago";
            } else if (days > 31 && days <= 365) {
                return days / 12 + "month ago";
            } else if (days > 365) {

                return days / 365 + "yr ago";

            }

        }

        return null;
    }

    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface
                    .getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        }
        return "";

    }

    /**
     * It will return android device id
     * 
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.appsfoundry.scoopnews", PackageManager.GET_SIGNATURES); // Your
            // package
            // name
            // here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
            int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float)height / (float)reqHeight);
            final int widthRatio = Math.round((float)width / (float)reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String getDevicemodel() {
        return android.os.Build.MODEL;
    }

    public static boolean doesContainGsfPackage(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);

        for (PackageInfo pi : list) {
            if (pi.packageName.equals("com.google.android.gsf"))
                return true; // ACCUWX.GSF_PACKAGE = com.google.android.gsf

        }

        return false;
    }

    // method for truncating long strings
    public static StringBuffer truncate(String value, int limit) {
        StringBuffer shopName = new StringBuffer();
        if (value != null && value.length() > limit) {
            limit = limit - 3;
            shopName.append(value.substring(0, limit));
            shopName.append("...");
        } else {
            shopName.append(value);
        }
        return shopName;
    }

    public static void filterHtml(Document document) {

        if (UtilValidate.isNotNull(document)) {

            Elements headElements = document.select("head");

            if (UtilValidate.isNotNull(headElements)) {

                headElements.remove();

            }

            /*
             * Elements imageElements = document.select("img");
             * if(UtilValidate.isNotNull(imageElements)){
             * imageElements.remove(); }
             */

            Elements embedElemts = document.select("embed");

            if (UtilValidate.isNotNull(embedElemts)) {

                embedElemts.remove();

            }

            Elements scriptsElements = document.select("script");

            if (UtilValidate.isNotNull(scriptsElements)) {

                scriptsElements.remove();

            }

            Elements scriptsJsElements = document.select("script[type=text/javascript]");

            if (UtilValidate.isNotNull(scriptsJsElements)) {

                scriptsJsElements.remove();

            }

            Elements scriptsJscriptElements = document.select("script[language=javascript]");

            if (UtilValidate.isNotNull(scriptsJscriptElements)) {

                scriptsJscriptElements.remove();

            }

            Elements scriptsTextcriptElements = document.select("script[type=text/html");

            if (UtilValidate.isNotNull(scriptsTextcriptElements)) {

                scriptsTextcriptElements.remove();

            }

            Elements styleElements = document.select("style");

            if (UtilValidate.isNotNull(styleElements)) {

                styleElements.remove();

            }

            Elements styleCssElements = document.select("style[type=text/css]");

            if (UtilValidate.isNotNull(styleCssElements)) {

                styleCssElements.remove();

            }

            Elements linkElements = document.select("link[rel=stylesheet]");

            if (UtilValidate.isNotNull(linkElements)) {

                linkElements.remove();

            }

            Elements socialElements = document.getElementsByAttributeValueContaining("class",
                    "social");

            if (UtilValidate.isNotNull(socialElements)) {

                socialElements.remove();

            }

            Elements adElements = document.getElementsByAttributeValueContaining("class", "ads");

            if (UtilValidate.isNotNull(adElements)) {

                adElements.remove();

            }

            Elements advElements = document.getElementsByAttributeValueContaining("class", "adv");

            if (UtilValidate.isNotNull(advElements)) {

                advElements.remove();

            }

            // Remove Styles
            Elements elms = document.getAllElements();
            for (int i = 0; i < elms.size(); i++) {
                elms.get(i).removeAttr("style");
            }

        }

    }

    public static int getMonthFromDate(String date) {

        SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        parserSDF.setTimeZone(TimeZone.getTimeZone("UTC/Etc"));
        Date datePosted = null;
        int month = 0;
        try {
            datePosted = parserSDF.parse(date);

            month = datePosted.getMonth();

        } catch (ParseException e) {
            System.out.println("Invalid date format " + e);
        }

        return month;

    }

    public static int getYearFromDate(String date) {

        SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        parserSDF.setTimeZone(TimeZone.getTimeZone("UTC/Etc"));
        Date datePosted = null;
        int year = 0;
        try {
            datePosted = parserSDF.parse(date);

            year = datePosted.getYear();

        } catch (ParseException e) {
            System.out.println("Invalid date format " + e);
        }

        return year;

    }

    public static int getMonthFromCurrentDate() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH);

        return month;
    }

    public static int getYearFromCurrentDate() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);

        return year;
    }

    public static long getDateDifference(String dateInDb) {

        long days = 0;
        try {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Date date = formatter.parse(dateInDb);
            
            Date todayDate = new Date();
            
            long difference = todayDate.getTime() - date.getTime();
            
            Log.e(Tag,"todayDate :"+todayDate.getTime()/(24 * 60 * 60 * 1000));
            
            Log.e(Tag,"date :"+date.getTime()/(24 * 60 * 60 * 1000));
            
            days = difference / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
           Log.e(Tag,"Exception :",e);
        }

        return days;

    }

}
