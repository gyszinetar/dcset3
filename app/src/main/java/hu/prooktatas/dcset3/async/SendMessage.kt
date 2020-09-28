package hu.prooktatas.dcset3.async

import android.os.AsyncTask
import com.github.kittinunf.fuel.Fuel
import hu.prooktatas.dcset3.key

class SendMessage(var uzenet:String,var kuldo:String,var fogado:String): AsyncTask<Unit, Unit, String>() {
    override fun doInBackground(vararg p0: Unit?): String {
        if(uzenet.length>0) {
            val bodyJson3 = """{ "key" : "$key",
                            "kuldo" : "$kuldo",
                            "uzenet" : "$uzenet",
                            "fogado" : "$fogado"
                          }"""
            val (request, response, result)
                    = Fuel.post("https://dcset.ditusz.hu/dcset/telouzenet")
                .body(bodyJson3)
                .response()

            return String(response.data)
        }

        return "semmi"

    }
}