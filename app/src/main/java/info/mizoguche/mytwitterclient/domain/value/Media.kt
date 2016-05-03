package info.mizoguche.mytwitterclient.domain.entity

import android.databinding.BaseObservable
import twitter4j.Status
import java.io.Serializable

data class MediaId(val value: Long): Serializable
data class MediaUrl(val value: String): Serializable
enum class MediaType {
    Photo, Video, AnimatedGif, Unknown;

    companion object {
        fun get(type: String): MediaType{
            return when(type){
                "photo" -> Photo
                "video" -> Video
                "animated_gif" -> AnimatedGif
                else -> Unknown
            }
        }
    }
}

class Media(builder: MediaBuilder) : BaseObservable(), Serializable {
    val id: MediaId = MediaId(builder.id)
    val url: MediaUrl = MediaUrl(builder.url)
    val type: MediaType = builder.type
}

class MediaBuilder(id: Long) {
    val id: Long = id
    lateinit var url: String
    lateinit var type: MediaType

    fun build(): Media {
        return Media(this)
    }
}

class MediaEntities(val mediaEntities: List<Media>) : List<Media> by mediaEntities, Serializable

object  MediaFactory {
    fun create(status: Status): MediaEntities {
        val entities = status.extendedMediaEntities.map {
            val builder = MediaBuilder(it.id)
            builder.url = it.mediaURL
            builder.type = MediaType.get(it.type)
            builder.build()
        }.toList()
        return MediaEntities(entities)
    }
}
