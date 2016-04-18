package info.mizoguche.mytwitterclient.domain.entity

data class UserListId(val value: Long)
data class UserListName(val value: String)
enum class UserListAccessLevel {
    Public, Private
}
data class UserListDescription(val value: String)

class UserList(builder: UserListBuilder) {
    val id: UserListId = UserListId(builder.id)
    val name: UserListName = UserListName(builder.name)
    val accessLevel: UserListAccessLevel = if(builder.accessLevel) UserListAccessLevel.Public else UserListAccessLevel.Private
    val description: UserListDescription = UserListDescription(builder.description)
}

class UserListBuilder(id: Long){
    val id: Long = id
    lateinit var name: String
    var accessLevel: Boolean = true
    lateinit var description: String
}
