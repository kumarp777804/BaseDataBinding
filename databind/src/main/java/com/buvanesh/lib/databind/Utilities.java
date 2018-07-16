package com.buvanesh.lib.databind;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/*
 * @author Buvaneshkumar
 * © copyrights reserved 2018.
 * */

public class Utilities {

        public static final int EXPAND_VIEW = 0;
        public static final int COLLAPSE_VIEW = 1;
        public static final int FADE_OUT = 0;
        public static final int FADE_IN = 1;
        private static final int MY_PERMISSIONS_REQUEST = 963;
        private static final String DATE_FORMATTING = "MM/dd/yyyy";
        private static final String DATE_FORMATTING_TRACK = "yyyy/MM/dd";
        //TAG for logging
        private static final String TAG = Utilities.class.getSimpleName();

        private Utilities() {

        }


        /**
         * Method to encode the url parameter
         *
         * @param value
         * @return
         */
        public static String encodeUrlParam(String value) {
            return value;
        }

        /**
         * Format the string
         *
         * @param aContext
         * @param aTxtRes
         * @param aFormatText
         * @return
         */
        public static String formatString(final Context aContext, final int aTxtRes, final String aFormatText) {
            return String.format(aContext.getResources().getString(aTxtRes), aFormatText);
        }

        public static <T> T convertStreamToJsonClass(final Context aContext, final String aFileName, Class<T> convertClass) {
      /*  InputStream is=null;
        Reader reader=null;*/
            try {
                InputStream is = aContext.getAssets().open(aFileName);
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                return (new Gson()).fromJson(reader, convertClass);
            } catch (IOException | NullPointerException e) {
                LOG.log("Exception", e);
            }/*finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
            return null;
        }

        /**
         * Setting the language for the application
         *
         * @param aLanguage Language to setting
         */
        public static void setLocale(final Activity aActivity, final String aLanguage) {
            Locale myLocale = new Locale(aLanguage);
            Resources resources = aActivity.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = myLocale;
            resources.updateConfiguration(configuration, dm);
        }

        /**
         * check the internet is available is not
         *
         * @param context
         * @return
         */
        public static boolean isConnected(Context context) {
            final ConnectivityManager conMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
        }

        /**
         * Hides the soft keyboard
         */
        public static void hideSoftKeyboard(Context context) {
            Activity activity = (Activity) context;
            if (activity != null && activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(new View(activity).getWindowToken(), 0);
            }
        }

        public static void hideKeyboard(Activity activity) {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        /**
         * Shows the soft keyboard
         */
        public static void showSoftKeyboard(View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }

        public static void setupUI(final Context context, final View view) {
            //Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(context);
                        return false;
                    }
                });
            }
            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    setupUI(context, innerView);
                }
            }
        }



        public static boolean isPastDate(String pDateString) {
            try {
                Date compareDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault()).parse(pDateString);
                Date today = new Date();
                return today.getTime() > compareDate.getTime();
            } catch (ParseException ex) {
                return true;
            }
        }


        /**
         * Format Phone Number using US format. (xxx) xxx-xxxx
         *
         * @param phoneString Phone string to format
         * @return Formatted Phone Number
         */

        @TargetApi(21)
        public static String formatPhoneNumber(String phoneString) {
            //Because formatNumber(String phoneNumber, String defaultCountryIso) available in API 21 check the SDK.
            if (Build.VERSION.SDK_INT >= 21) {
                return PhoneNumberUtils.formatNumber(phoneString, "US");
            } else {
                //Formatting manual for below lollipop.
                Editable phoneNumber = new SpannableStringBuilder(phoneString);
                PhoneNumberUtils.formatNanpNumber(phoneNumber);
                String[] phoneArray = phoneNumber.toString().split("-");
                StringBuilder convertedPhoneString = new StringBuilder();
                if (phoneArray.length == 1) {
                    convertedPhoneString.append(phoneNumber);
                } else if (phoneArray.length == 3) {
                    convertedPhoneString.append("(");
                    convertedPhoneString.append(phoneArray[0]);
                    convertedPhoneString.append(") ");
                    convertedPhoneString.append(phoneArray[1]);
                    convertedPhoneString.append("-");
                    convertedPhoneString.append(phoneArray[2]);
                } else {
                    convertedPhoneString.append(phoneString);
                }
                return convertedPhoneString.toString();
            }
        }

        public static long getEpochTime() {
            return System.currentTimeMillis() / 1000L;
        }

        public static boolean isResponseError(String response) {
            boolean isError = false;
            try {
                JSONObject obj = new JSONObject(response);
                if (obj.has("error")) {
                    isError = true;
                }
            } catch (JSONException e) {
                LOG.log("Exception", e);
                isError = true;
            }
            return isError;
        }

        public static HashMap<String, String> getCurrentWeekDate() {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.ENGLISH);
            HashMap<String, String> dateMap = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                dateMap.put(df.format(c.getTime()).split(" ")[1], df.format(c.getTime()).split(" ")[0]);
                c.add(Calendar.DATE, 1);
            }
            return dateMap;
        }

        @SuppressWarnings("unused")
        public static String getNextDay() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMATTING, Locale.getDefault());
            c.add(Calendar.DAY_OF_WEEK, 1);
            return String.valueOf(df.format(c.getTime()));
        }

        public static String formateDate(String date) {
            String formattedDate;
            try {
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = sdf.parse(date);
                formattedDate = sdf.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
                return date;
            }
            return formattedDate;
        }

        //status for View is in EditMode
        public static void isInEditMode(View view) {
            if (view.isInEditMode()) {
                return;
            }
        }

        public static boolean isEmptyStr(String str) {
            if (str.equals("")) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean isValidUserName(String userName) {
            if (!userName.matches("^[a-zA-Z]+$$") && userName.length() > 4) {
                System.out.println("username  invalid");
                return true;
            } else {
                return false;
            }
        }

        public static boolean isValidPassword(String password) {
//        if (password.matches("^(?=.*[0-9])(?=.*[a-z])")) {  // (?=.*[@#$%^&+=]) (?=\S+$).{8,}$ (?=.*[A-Z])
            if (!password.matches(" ^.{8,64}$") && password.trim().length() > 8) {
                System.out.println("password  invalid");
                return true;
            } else {
                return false;
            }
        }

        public static boolean isValidEmailId(String emailId) {
         /*if (!emailId.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            System.out.println("email id invalid");
            return true;
        } else {
            return false;
        }*/

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                return true;
            }
            return false;

        }

        public static boolean isValidSSN(String ssn) {
            if (ssn.length() > 3) {
                System.out.println("invalid ssn");
                return false;
            } else {
                return true;
            }
        }

        public static final <T> T getResponseFromResource(Activity activity, String jsonResourcePath, Class<T> type) throws IOException, IOException {
            Gson gson = new Gson();
            return gson.fromJson(convertFileTOString(activity, jsonResourcePath), type);
        }

        public static String parseJsonData(Context con, String fileName) {

            if (con == null) {
                return "";
            } else
                return convertFileTOString(con, fileName);
        }

        public static String convertFileTOString(Context con, String fileName) {
            StringBuilder content = new StringBuilder();
            AssetManager assetManager = con.getResources().getAssets();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));) {


                String line;
                while ((line = reader.readLine()) != null) {
                    content = content.append(line);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return content.toString();
        }

        public static boolean checkPermission(Context context, String permission) {
            int res = context.checkCallingOrSelfPermission(permission);
            return (res == PackageManager.PERMISSION_GRANTED);
        }

        public static void hideKeyboard(Context context, View view) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        public static void disableView(Activity activity) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        public static void enableView(Activity activity) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        public static String changeDateFormat(String date) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            return formatter.format(formatter.parse(date));
            /*2018-02-23T17:18:00Z*/
        }

        /*Change the data formate and display in test result*/
        public static String changeDFTestResult(String date, boolean isTime) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //Now reformat it using desired display pattern:
            String displayDate = null;
            if (isTime) {
                return new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(formatter.parse(date));
            } else {
                return new SimpleDateFormat("MM/dd/yyyy").format(formatter.parse(date));
            }

        }

        public static String getMonthFromDate(String dtStart) {
            String result = dtStart;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date1 = formatter.parse(dtStart);
                String strDateFormat = "MMM d";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                result = sdf.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context, final String permission, String message) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage(message);
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, MY_PERMISSIONS_REQUEST);
                            }
                        });
                        android.support.v7.app.AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, MY_PERMISSIONS_REQUEST);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

        public static void showToast(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

        public static String getInitials(String mInput) {
            StringBuilder sb = new StringBuilder();
            for (String s : mInput.split(" ")) {
                sb.append(s.charAt(0));
            }
            if (sb.length() == 2) {
                return sb.toString();
            } else if (sb.length() > 2) {
                return sb.substring(0, 2);
            } else {
                return sb.toString();
            }
        }

        public static String removeLastCharacter(String mLanguageSelectd) {
            String newString = mLanguageSelectd.toString().trim();
            newString = newString.substring(0, newString.length() - 1);
            return newString;
        }

        public static String getformattedTime(String orderedDate) {
            if (orderedDate != null) {
                SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yy");
                Date date = null;
                try {
                    date = sdfSource.parse(orderedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdfDestination = new SimpleDateFormat("MMM-d-yyyy");
                return sdfDestination.format(date).replace("-", " ");
            }
            return "";
        }

        /**
         * detect are you using real device or emulator
         *
         * @return true
         */
        public static boolean isEmulator() {
            return Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                    || "google_sdk".equals(Build.PRODUCT);
        }


        public static String changeDateFormate(String sourcedatevalue) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date sourceDate = null;
            String targetDateValue = sourcedatevalue;
            try {
                sourceDate = dateFormat.parse(sourcedatevalue);
            } catch (ParseException e) {
                LOG.log("Exception", e);
            }
            if (null != sourceDate) {
                SimpleDateFormat targetFormat = new SimpleDateFormat("mm/dd/yyyy");
                targetDateValue = targetFormat.format(sourceDate);
            }
            return targetDateValue;
        }

        public static String changeDateTimeFormat(String date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");
            Date sourceDate = null;
            String targetDateValue = date;
            try {
                sourceDate = dateFormat.parse(date);
            } catch (ParseException e) {
                LOG.log("Exception", e);
            }
            if (null != sourceDate) {
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                targetDateValue = targetFormat.format(sourceDate);
            }
            return targetDateValue;
            /*2018-02-23T17:18:00Z*/
        }

        public static Bitmap resizeBitMapImage(String filePath, int targetWidth, int targetHeight) {
            Bitmap bitMapImage = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                double sampleSize = 0;
                Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth
                        - targetWidth);
                if (options.outHeight * options.outWidth * 2 >= 1638) {
                    sampleSize = scaleByHeight ? options.outHeight / targetHeight : options.outWidth / targetWidth;
                    sampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));
                }
                options.inJustDecodeBounds = false;
                options.inTempStorage = new byte[128];
                while (true) {
                    try {
                        options.inSampleSize = (int) sampleSize;
                        bitMapImage = BitmapFactory.decodeFile(filePath, options);
                        break;
                    } catch (Exception ex) {
                        try {
                            sampleSize = sampleSize * 2;
                        } catch (Exception ex1) {

                        }
                    }
                }
            } catch (Exception ex) {
                LOG.log("Exception", ex);
            }
            return bitMapImage;
        }


        public static String getMinutesSeconds(String date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date sourceDate = null;
            String targetDateValue = date;
            try {
                sourceDate = dateFormat.parse(date);
            } catch (ParseException e) {
                LOG.log("Exception", e);
            }
            if (null != sourceDate) {
                SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                targetDateValue = targetFormat.format(sourceDate);
            }
            return targetDateValue;
        }

        public static String getDateForMessage(long now, long dateSendOn) {
            long milliseconds = now - dateSendOn;
            int seconds = (int) milliseconds / 1000;
            int hours = seconds / 3600;
            String dateString = "";
            try {
                if (hours >= 24) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    dateString = formatter.format(new Date(dateSendOn));
                } else {
                    SimpleDateFormat formatter2 = new SimpleDateFormat("h.mm aa");
                    dateString = formatter2.format(new Date(dateSendOn));
                }
            } catch (Exception e) {
                LOG.v(TAG, e);
            }
            return dateString;
        }


        /**
         * Convert String to Camel Case
         *
         * @param inputString text
         */
        public static String toCamelCase(String inputString) {
            String result = "";
            if (inputString.length() == 0) {
                return result;
            }
            char firstChar = inputString.charAt(0);
            char firstCharToUpperCase = Character.toUpperCase(firstChar);
            result = result + firstCharToUpperCase;
            for (int i = 1; i < inputString.length(); i++) {
                char currentChar = inputString.charAt(i);
                char previousChar = inputString.charAt(i - 1);
                if (previousChar == ' ') {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                } else {
                    char currentCharToLowerCase = Character.toLowerCase(currentChar);
                    result = result + currentCharToLowerCase;
                }
            }
            return result;
        }

        public static String getformattedDate(String appointmentDate) {
            if (appointmentDate != null) {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
                Date date = null;
                try {
                    date = sdfSource.parse(appointmentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    SimpleDateFormat sdfDestination = new SimpleDateFormat("MMM dd, yyyy");
                    String newDate = sdfDestination.format(date);
                    return newDate;
                }
            }
            return "";

        }


        public static CharSequence getColoredString(String mUsername, int colorId) {
            Spannable spannable = new SpannableString(mUsername);
            spannable.setSpan(new ForegroundColorSpan(colorId), 0, spannable.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannable;
        }

        public static void showAlert(Context context,String title,String message){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton(context.getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        public static void setRecyclerView(Context context,RecyclerView recyclerView){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            RecyclerView.ItemDecoration decorator = new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(decorator);

        }
}
