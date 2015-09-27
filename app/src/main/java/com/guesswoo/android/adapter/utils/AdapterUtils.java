package com.guesswoo.android.adapter.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class AdapterUtils {

    private AdapterUtils() {
    }

    public static final CharSequence getFormattedDate(Date lastUpdateDate) {

        Date today = new Date();

        long duration = today.getTime() - lastUpdateDate.getTime();

        long nbMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if (nbMinutes < 60) {
            return "il y a " + nbMinutes + " minute(s)";
        } else if (nbMinutes < 1440) {
            return "il y a " + nbMinutes / 60 + " heure(s)";
        } else if (nbMinutes < 10080) {
            return "il y a " + nbMinutes / 60 / 24 + " jour(s)";
        } else if (nbMinutes < 312480) {
            return "il y a " + nbMinutes / 60 / 24 / 31 + " mois";
        }

        return null;
    }
}
