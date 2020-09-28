package hu.prooktatas.dcset3.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.prooktatas.dcset3.MainActivity
import hu.prooktatas.dcset3.R
import hu.prooktatas.dcset3.async.AsyncLogin
import hu.prooktatas.dcset3.async.loginAtempt
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() ,loginAtempt{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        username.setText(sharedPref.getString("user",""))
        password.setText(sharedPref.getString("password",""))


            login.setOnClickListener {
                AsyncLogin(this,username.text.toString(),password.text.toString()).execute()
            }

    }



    override fun sorf(succes: String) {
            if(succes.replace("\"","")!="NG"  &&  succes.replace("\"","")!=""){
            Toast.makeText(applicationContext, "Sikerült belépni! $succes", Toast.LENGTH_SHORT).show()

            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()){
                clear()
                commit()
            }

            val sharedPref2 = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref2.edit()){
                putString("user",username.text.toString())
                putString("password",password.text.toString() )
                commit()
            }
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("kuldo", succes )
            startActivity(i)
            finish()
        }
        else{Toast.makeText(applicationContext, "Nem sikerült belépni!", Toast.LENGTH_SHORT).show()}
    }
}

