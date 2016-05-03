package info.mizoguche.mytwitterclient.application.adapter

import android.content.Context
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import info.mizoguche.mytwitterclient.domain.entity.MediaEntities
import info.mizoguche.mytwitterclient.domain.entity.MediaType
import java.util.*

class TweetImagePagerAdapter(context: Context, entities: MediaEntities) : PagerAdapter() {

    private val views: MutableList<ImageView>

    init {
        views = ArrayList<ImageView>(entities.size)
        for (entity in entities) {
            if (entity.type == MediaType.Photo) {
                val imageView = ImageView(context)
                Picasso.with(context).load(Uri.parse(entity.url.value)).noPlaceholder().into(imageView)
                views.add(imageView)
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = views[position]
        container.addView(view)
        return view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return views[position].toString()
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun setCurrentView(i: Int) {
        ViewCompat.setTransitionName(views[i], "image")
    }
}
