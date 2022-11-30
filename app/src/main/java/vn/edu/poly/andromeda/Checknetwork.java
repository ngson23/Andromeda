package vn.edu.poly.andromeda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

public class Checknetwork extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Objeck.CheckInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_check_internet = LayoutInflater.from(context).inflate(R.layout.check_internet, null);
            builder.setView(layout_check_internet);

            AppCompatButton button = layout_check_internet.findViewById(R.id.btnOk);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }
    }
}
