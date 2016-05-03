package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import info.mizoguche.mytwitterclient.application.adapter.TweetImagePagerAdapter
import info.mizoguche.mytwitterclient.domain.entity.Media
import info.mizoguche.mytwitterclient.domain.entity.MediaEntities

class ImagePreviewViewPager : ViewPager {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    fun initialize(entities: MediaEntities, initialEntity: Media) {
        val adapter = TweetImagePagerAdapter(context, entities)
        setAdapter(adapter)
        for (i in entities.indices) {
            if (entities[i].url == initialEntity.url) {
                currentItem = i
                adapter.setCurrentView(i)
                return
            }
        }
    }
}
