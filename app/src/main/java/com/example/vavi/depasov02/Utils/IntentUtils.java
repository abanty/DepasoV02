package com.example.vavi.depasov02.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

/**
 * Created by Gurotec S.A.C. on 6/03/19.
 */
public class IntentUtils {

    private Context context;

    public IntentUtils(Context context) {
        this.context = context;
    }

    public void intentWEB(String url) {
        try {
            if (url != null && !url.isEmpty()) {
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(web);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void intentCall(String telefono) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(telefono));
        context.startActivity(intent);

    }

    public void intentEmail(String email, String subject, String title) {
        ShareCompat.IntentBuilder.from((Activity) context)
                .setType("message/rfc822")
                .addEmailTo(email)
                .setSubject(subject)
                .setText("")
                .setChooserTitle(title)
                .startChooser();
    }
}
