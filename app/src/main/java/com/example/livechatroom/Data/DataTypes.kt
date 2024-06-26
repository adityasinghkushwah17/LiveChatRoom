package com.example.livechatroom.Data

data class UserData(
    val userID: String? = "",
    val name: String? = "",
    val number: String? = "",
    val imageURL: String? = ""
) {
    fun toMAP() {
        mapOf(
            "userID" to userID, "name" to name, "number" to number, "imageURL" to imageURL
        )
    }
}//firebase we cant directly use our data class so we have to map them in Key Value .

data class ChatData(
    val chatID: String? = "",
    val user1: ChatUser = ChatUser(),
    val user2: ChatUser = ChatUser(),
)




data class Message(
    val sendby: String? = "",
    val message: String? = "",
    val timeStamp: String?=""
)


data class Status(
    val user: ChatUser = ChatUser(),
    val imageURL: String? = null,
    val timeStamp: Long? = null
) {
    // No-argument constructor for Firebase
    constructor() : this(ChatUser(), null, null)
}

data class ChatUser(
    val userID: String? = "",
    val name: String? = "",
    val imageURL: String? = "",
    val number: String? = ""
) {
    // No-argument constructor for Firebase
    constructor() : this("", "", "", "")
}