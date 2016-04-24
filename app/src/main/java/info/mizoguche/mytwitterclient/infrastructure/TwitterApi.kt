package info.mizoguche.mytwitterclient.infrastructure

import com.orhanobut.hawk.Hawk
import info.mizoguche.mytwitterclient.BuildConfig
import info.mizoguche.mytwitterclient.domain.entity.UserListId
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.schedulers.Schedulers
import twitter4j.*
import twitter4j.auth.AccessToken

object  TwitterApi {
    var didSetConsumerKey: Boolean = false

    val twitter: Twitter
        get()  {
            val twitter = TwitterFactory.getSingleton()
            if(!didSetConsumerKey){
                twitter.setOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET)
                didSetConsumerKey = true
            }
            return twitter
        }

    val KEY_TOKEN = "AccessToken"
    val KEY_SECRET = "AccessSecret"

    fun init(){
        if(isOwnUserSaved()){
            twitter.oAuthAccessToken = getAccessToken()
        }
    }

    fun addOwnUser(accessToken: AccessToken) {
        Hawk.chain()
                .put(KEY_TOKEN, accessToken.token)
                .put(KEY_SECRET, accessToken.tokenSecret)
                .commit()
    }

    fun isOwnUserSaved() : Boolean{
        return Hawk.contains(KEY_TOKEN) && Hawk.contains(KEY_SECRET)
    }

    private fun getAccessToken() : AccessToken {
        val token = Hawk.get(KEY_TOKEN, "")
        val secret = Hawk.get(KEY_SECRET, "")
        return AccessToken(token, secret)
    }

    fun homeTimeLine() : Observable<ResponseList<Status>> {
        return fetchResponseList<Status> { twitter.getHomeTimeline(Paging(1, 50)) }
    }

    fun userListTimeLine(userListId: UserListId) : Observable<ResponseList<Status>> {
        return fetchResponseList<Status> { twitter.getUserListStatuses(userListId.value, Paging(1, 50)) }
    }

    fun ownLists(): Observable<ResponseList<UserList>> {
        return fetchResponseList<UserList> { twitter.getUserLists(twitter.id, true) }
    }

    fun <T> fetchResponseList(action: () -> ResponseList<T>) : Observable<ResponseList<T>> {
        return observable<ResponseList<T>> {
            it.onNext(action())
            it.onCompleted()
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}