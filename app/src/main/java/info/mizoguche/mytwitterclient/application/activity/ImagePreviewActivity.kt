package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ActivityImagePreviewBinding
import info.mizoguche.mytwitterclient.domain.entity.Media
import info.mizoguche.mytwitterclient.domain.entity.MediaEntities
import java.util.*

private const val IntentKeyEntities = "entities"
private const val IntentKeyMedia = "media"

class ImagePreviewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        val entities = MediaEntities(intent.getSerializableExtra(IntentKeyEntities) as ArrayList<Media>)
        val media = intent.getSerializableExtra(IntentKeyMedia) as Media
        val binding = DataBindingUtil.setContentView<ActivityImagePreviewBinding>(this, R.layout.activity_image_preview)
        binding.imagePreviewPager.initialize(entities, media)
    }

    companion object {
        fun createIntent (context: Context, entities: MediaEntities, media: Media) : Intent {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra(IntentKeyEntities, entities)
            intent.putExtra(IntentKeyMedia, media)
            return intent
        }
    }
}
