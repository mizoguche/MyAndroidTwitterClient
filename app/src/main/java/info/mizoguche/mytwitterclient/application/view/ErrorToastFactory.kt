package info.mizoguche.mytwitterclient.application.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import twitter4j.TwitterException

object ErrorToastFactory {
    fun show(context: Context, exception: Throwable, length: Int = Toast.LENGTH_SHORT) {
        if(exception is TwitterException) {
            val e = exception as TwitterException
            Log.d(this.javaClass.simpleName, "statusCode: ${e.statusCode}")
            when(e.statusCode){
                401 -> showToast(context, "アクセスが許可されませんでした。", length)
                429 -> showToast(context, "API制限を越えました: ${e.rateLimitStatus.secondsUntilReset}秒後に制限がリセットされます。", length)
                else -> showToast(context, e.message as String, length)
            }
        }
    }

    private fun showToast(context: Context, message: String, length: Int) {
        Toast.makeText(context, message, length).show()
    }
}
