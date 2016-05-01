package info.mizoguche.mytwitterclient.application.view.picasso

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

object PicassoUtil {
    fun bindImage(context: Context, view: ImageView, url: String) {
        Picasso.with(context)
                .load(url)
                .into(view)
    }
}
