package info.mizoguche.mytwitterclient.domain.value

import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.entity.UserId
import info.mizoguche.mytwitterclient.domain.entity.UserListId
import info.mizoguche.mytwitterclient.domain.repository.TimeLineRepository
import rx.Observable

const val TabDetailHome: String = "Home"
const val TabDetailUserList: String = "UserList"
const val TabDetailUserTimeLine: String = "UserTimeLine"
const val TabDetailMentionsTimeLine: String = "MentionsTimeLine"

data class TabName(val value: String)
data class TabDetail(val type: String, val id: Long)
data class Tab(val name: TabName, val tabType: TabDetail) {
    fun timeLine(): Observable<TimeLine>{
        return when(tabType.type){
            TabDetailHome -> TimeLineRepository.fetchHomeTimeLine()
            TabDetailUserList -> TimeLineRepository.fetchUserListTimeLine(UserListId(tabType.id))
            TabDetailUserTimeLine -> TimeLineRepository.fetchUserTimeLine(UserId(tabType.id))
            TabDetailMentionsTimeLine -> TimeLineRepository.fetchMentionsTimeLine()
            else -> TimeLineRepository.fetchHomeTimeLine()
        }
    }
}
