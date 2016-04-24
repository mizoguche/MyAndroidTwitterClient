package info.mizoguche.mytwitterclient.domain.value

import info.mizoguche.mytwitterclient.domain.collection.TimeLine
import info.mizoguche.mytwitterclient.domain.entity.UserListId
import info.mizoguche.mytwitterclient.domain.repository.TimeLineRepository
import rx.Observable

const val TabDetailHome: String = "Home"
const val TabDetailUserList: String = "UserList"

data class TabName(val value: String)
data class TabDetail(val type: String, val id: Long)
data class Tab(val name: TabName, val tabType: TabDetail) {
    fun timeLine(): Observable<TimeLine>{
        return when(tabType.type){
            TabDetailHome -> TimeLineRepository.fetchHomeTimeLine()
            TabDetailUserList -> TimeLineRepository.fetchUserListTimeLine(UserListId(tabType.id))
            else -> TimeLineRepository.fetchHomeTimeLine()
        }
    }
}
