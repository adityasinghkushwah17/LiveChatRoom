package com.example.livechatroom

import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livechatroom.Data.CHATS
import com.example.livechatroom.Data.ChatData
import com.example.livechatroom.Data.ChatUser
import com.example.livechatroom.Data.Events
import com.example.livechatroom.Data.MESSAGE
import com.example.livechatroom.Data.Message
import com.example.livechatroom.Data.STATUS
import com.example.livechatroom.Data.Status
import com.example.livechatroom.Data.USER_NODE
import com.example.livechatroom.Data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LCviewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {

    var inprogres = mutableStateOf(false)
    var inprogresChats = mutableStateOf(false)
    val eventmutablestate = mutableStateOf<Events<String?>?>(null)
    var SingIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val Chats = mutableStateOf<List<ChatData>>(listOf())
    val ChatMessages = mutableStateOf<List<Message>>(listOf())
    val inprogresschatmessage = mutableStateOf(false)
    var currentmessagechatlistener: ListenerRegistration? = null

    val Status = mutableStateOf<List<Status>>(listOf())
    val inprogressStatus = mutableStateOf(false)

    init {
        val currentuser = auth.currentUser
        SingIn.value = currentuser != null
        currentuser?.uid?.let { getUserData(it) } //if the user is not null then get the user data

    }

    fun populateMessages(chatid: String) {
        inprogresschatmessage.value = true
        currentmessagechatlistener = db.collection(CHATS).document(chatid).collection(MESSAGE)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                }
                if (value != null) {
                    ChatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }.sortedBy { it.timeStamp }
                    inprogresschatmessage.value = false
                }
            }

    }

    //when we comes back we need to depopulate the message so that if we click on another user
    // we can populate its messages
    fun depopulatemessages() {
        ChatMessages.value = listOf()
        currentmessagechatlistener = null
    }

    fun PopulateChats() {
        inprogresChats.value = true
        db.collection(CHATS)
            .where(
                Filter.or(
                    Filter.equalTo("user1.userID", userData.value?.userID),
                    Filter.equalTo("user2.userID", userData.value?.userID)
                )
            ).addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                }
                if (value != null) {
                    // Add debug logs to inspect the data retrieved from Firestore
                    Log.d("PopulateChats", "Retrieved chats: ${value.documents}")

                    Chats.value = value.documents.mapNotNull {
                        it.toObject<ChatData>()
                    }

                    // Add debug logs to verify the data after mapping to ChatData
                    Log.d("PopulateChats", "Mapped chats: ${Chats.value}")

                    inprogresChats.value = false
                }
            }
    }

    fun onSendReply(chatid: String, message: String) {

        val time = Calendar.getInstance().time.toString()
        val msg = Message(sendby = userData.value?.userID, message = message, timeStamp = time)
        db.collection(CHATS).document(chatid).collection(MESSAGE).document().set(msg)

    }

    fun Signup(Name: String, Number: String, Email: String, Password: String) {
        Log.d("LiveChat", "Starting signup process")
        inprogres.value = true

        if (Name.isEmpty() || Number.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
            handleException(message = "Please fill all the fields")
            return
        }

        if (Password.length < 6) {
            handleException(message = "Password should be at least 6 characters")
            return
        }

        db.collection(USER_NODE).whereEqualTo("number", Number).get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("LiveChat", "Checked if user already exists with this number")
                if (querySnapshot.isEmpty) {
                    auth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener { task ->
                            inprogres.value = false
                            if (task.isSuccessful) {
                                Log.d(
                                    "LiveChat",
                                    "User created successfully with email and password"
                                )
                                SingIn.value = true
                                CreateorUpdateProfile(Name, Number)
                            } else {
                                val exception = task.exception
                                if (exception is FirebaseAuthUserCollisionException) {
                                    handleException(message = "Email already exists")
                                } else {
                                    handleException(exception, message = "Signup Failed")
                                }
                            }
                        }
                } else {
                    handleException(message = "User already exists")
                    inprogres.value = false
                }
            }.addOnFailureListener { exception ->
                handleException(exception, message = "Failed to check user existence")
                inprogres.value = false
            }
    }

    fun UploadProfileImage(uri: Uri) {
        uploadImage(uri) { uri ->
            CreateorUpdateProfile(imageURL = uri.toString())
        }
    }

    fun uploadImage(uri: Uri, onSuccess: (String) -> Unit) {
        inprogres.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val Imageref = storageRef.child("images/$uuid")
        val uploadtask = Imageref.putFile(uri)
        uploadtask.addOnSuccessListener {
            Imageref.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())
                inprogres.value = false
            }
        }.addOnFailureListener {
            handleException(it)
        }
    }

    fun Login(Email: String, Password: String) {
        if (Email.isEmpty() or Password.isEmpty()) {
            handleException(message = "Please fill all the fields")
            return
        }
        inprogres.value = true
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
            if (it.isSuccessful) {
                SingIn.value = true
                inprogres.value = false
                auth.currentUser?.uid?.let { it1 -> getUserData(it1) } //Logged in successfully
            } else {
                handleException(it.exception, message = "Login Failed")
            }
        }

    }


    fun CreateorUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageURL: String? = null
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            handleException(message = "User ID is null")
            return
        }

        val newUserData = UserData(
            userID = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageURL = imageURL ?: userData.value?.imageURL
        )

        inprogres.value = true
        db.collection(USER_NODE).document(uid).set(newUserData)
            .addOnSuccessListener {
                Log.d("LiveChat", "User profile created/updated successfully")
                inprogres.value = false
                getUserData(uid)
            }
            .addOnFailureListener { exception ->
                handleException(exception, message = "Failed to create/update User")
            }
    }


    private fun getUserData(uid: String) {
        inprogres.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "Failed to retrieve User")
            }
            if (value != null) {
                userData.value = value.toObject<UserData>()
                inprogres.value = false
                PopulateChats()
                populateStatuses()
            }
        }
    }

    private fun handleException(exception: Exception? = null, message: String = "") {
        Log.e("Live Chat Room", message)
        exception?.printStackTrace()
        val errormsg = exception?.localizedMessage
        val message = if (message.isNullOrEmpty()) errormsg else message
        eventmutablestate.value = Events(message)
        inprogres.value = false
        inprogressStatus.value = false
    }

    fun logout() {
        auth.signOut()
        SingIn.value = false
        userData.value = null
        depopulatemessages()
        currentmessagechatlistener = null
        eventmutablestate.value = Events("Logged Out")
    }

    fun onAddChat(number: String) {
        if (number.isEmpty() || !number.isDigitsOnly()) {
            handleException(message = "Number must contain only digits")
        } else {
            db.collection(CHATS).where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("user1.number", number),
                        Filter.equalTo("user2.number", userData.value?.number)
                    ),
                    Filter.and(
                        Filter.equalTo("user1.number", userData.value?.number),
                        Filter.equalTo("user2.number", number)
                    )
                )
            ).get().addOnSuccessListener {
                if (it.isEmpty) {
                    db.collection(USER_NODE).whereEqualTo("number", number).get()
                        .addOnSuccessListener { it2 ->
                            if (it2.isEmpty) {
                                handleException(message = "Number Not Found")
                            } else {
                                val chatPartner = it2.toObjects<UserData>()[0]
                                val id = db.collection(CHATS).document().id
                                val chat = ChatData(
                                    chatID = id,
                                    user1 = ChatUser(
                                        userData.value?.userID,
                                        userData.value?.name,
                                        userData.value?.imageURL,
                                        userData.value?.number
                                    ),
                                    user2 = ChatUser(
                                        chatPartner.userID,
                                        chatPartner.name,
                                        chatPartner.imageURL,
                                        chatPartner.number
                                    )
                                )
                                db.collection(CHATS).document(id).set(chat)
                            }
                        }
                        .addOnFailureListener {
                            handleException(it)
                        }
                } else {
                    handleException(message = "Chat Already exists")
                }
            }.addOnFailureListener {
                handleException(it)
            }
        }
    }


    fun uploadStatus(uri: Uri) {
        uploadImage(uri) {
            CreateStatus(it.toString())
        }
    }

    fun CreateStatus(imageURL: String) {
        val newstattus = Status(
            user = ChatUser(
                userData.value?.userID,
                userData.value?.name,
                userData.value?.imageURL,
                userData.value?.number
            ), imageURL,
            System.currentTimeMillis()
        )
        db.collection(STATUS).document().set(newstattus)
    }

    fun populateStatuses() {
        val timeDelta = 24L * 60 * 60 * 1000
        val cutofftime = System.currentTimeMillis() - timeDelta

        inprogressStatus.value = true
        Log.d("LCviewModel", "Populating statuses for user: ${userData.value?.userID}")
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userID", userData.value?.userID),
                Filter.equalTo("user2.userID", userData.value?.userID)
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error)
            }
            if (value != null) {
                val currentConnections =
                    arrayListOf(userData.value?.userID)  //it will bring our current connections
                val chats = value.toObjects<ChatData>()
                chats.forEach {
                    if (it.user1.userID == userData.value?.userID) {
                        currentConnections.add(it.user2.userID)
                    } else {
                        currentConnections.add(it.user1.userID)
                    }
                }
                Log.d("LCviewModel", "Current connections: $currentConnections")
                db.collection(STATUS).whereGreaterThan("timeStamp", cutofftime)
                    .whereIn("user.userID", currentConnections)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            handleException(error)
                            return@addSnapshotListener
                        }
                        if (value != null) {
                            Status.value = value.toObjects<Status>()
                            Log.d("LCviewModel", "Fetched statuses: ${Status.value.size}")
                            inprogressStatus.value = false
                        } else {
                            Log.d("LCviewModel", "No statuses found")
                            inprogressStatus.value = false
                        }

                    }

            } else {
                inprogressStatus.value = false
            }
        }
    }

    fun SendPasswordResetEmail(email: String, onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  onComplete(true,"Password Email Sent")
                }else{
                    onComplete(false,"Failed to send Password Email")
                    handleException(task.exception)
                }
            }
        }


    }
}


