package info.mizoguche.mytwitterclient.domain.entity

import android.databinding.BaseObservable

class Tweet(text: String, screenName: String) : BaseObservable() {
    val text: String = text
    val screenName: String = screenName
}

