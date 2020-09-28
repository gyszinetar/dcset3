package hu.prooktatas.dcset3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onesignal.OneSignal
import hu.prooktatas.dcset3.adapter.MyAdapter
import hu.prooktatas.dcset3.async.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), allUsers,messages,lastseensidinterface {
    private lateinit var sendButton: Button
    private lateinit var msgListRv: RecyclerView
    private lateinit var msgText: TextView
    private lateinit var usersSpinner: Spinner
    lateinit var mainHandler: Handler
    lateinit var inte :Intent
    private lateinit var kuldo: String
    private var lastid: Int? = 0

    var uzeneteklista= mutableListOf<Uzenet>()
    var uzeneteklistanum:Int=0
    var uzeneteklistanum2:Int=0
    var i:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainHandler = Handler(Looper.getMainLooper())
        sendButton=findViewById(R.id.sendButton)
        msgListRv=findViewById(R.id.msgListRv)
        msgText=findViewById(R.id.msgText)
        usersSpinner=findViewById(R.id.userListSpinner)
        inte= getIntent()
        kuldo=inte.getStringExtra("kuldo")!!.toString().replace("\"","")
        OneSignal.idsAvailable { userId, registrationId ->
            FetchUsers(userId,kuldo,this).execute()
            GetLastseenState(kuldo,this).execute()
        }




        sendButton.setOnClickListener {
            SendMessage(msgText.text.toString(),kuldo,usersSpinner.getSelectedItem().toString()).execute()
            msgText.text=""

        }
    }

    private val Timer  = object : Runnable {

        override fun run() {
            FetchMessages(kuldo,usersSpinner.getSelectedItem().toString(),this@MainActivity).execute()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun fetchUsers(list:List<JSONObject>){
        var spinnerlist = mutableListOf<String>()
        spinnerlist.add("Mindenki")
        list.forEach {
            spinnerlist.add(it["name"].toString())
        }
        val adapter = ArrayAdapter(
            this,
            R.layout.spinnerrow, spinnerlist
        )
        usersSpinner.adapter = adapter
        mainHandler.post(Timer)
    }



    override fun fetchMessages(list: List<JSONObject>) {
        uzeneteklistanum=uzeneteklistanum2
        uzeneteklista.clear()

        list.forEach {

            uzeneteklista.add(Uzenet(it["id"].toString().toInt(),it["kuldo"].toString(),it["fogado"].toString(),it["uzenet"].toString()))

        }
        uzeneteklistanum2=uzeneteklista.size
        if(i>0){

            msgListRv.adapter!!.notifyDataSetChanged()
            if(uzeneteklistanum!=uzeneteklistanum2){
                msgListRv.scrollToPosition(uzeneteklista.size-1)}


        }
        if(i==0){
            msgListRv.layoutManager = LinearLayoutManager(this)
            msgListRv.adapter = MyAdapter(uzeneteklista,lastid!!,usersSpinner)
            msgListRv.scrollToPosition(uzeneteklista.size-1)
        }
        i++

    }

    override fun sendid(id: Int) {
        lastid=id
    }


}


const val key="A DITUSZ Ipari és Szolgáltató Kft 100%-ban magyar tulajdonban lévő vállalat. A társaság 2006 évi megalakulása óta fejleszt, gyárt, telepít és értékesít párásító és párahűtő rendszereket, berendezéseket. A cég elsősorban olyan klimatizálási illetve párásítási feladatok megoldására szakosodott, mely hagyományos klímaberendezésekkel, párásító berendezésekkel nem, vagy csak nagyon bonyolultan és költségesen oldható meg."

data class Uzenet(val id:Int,val kuldo :String,val fogado:String,val uzenet:String)

