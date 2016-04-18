package info.mizoguche.mytwitterclient.domain.factory

import info.mizoguche.mytwitterclient.domain.entity.UserList
import info.mizoguche.mytwitterclient.domain.entity.UserListBuilder

object  UserListFactory {
    fun create(userList: twitter4j.UserList): UserList{
        val builder = UserListBuilder(userList.id)
        builder.name = userList.fullName
        builder.accessLevel = userList.isPublic
        builder.description = userList.description
        return UserList(builder)
    }
}
