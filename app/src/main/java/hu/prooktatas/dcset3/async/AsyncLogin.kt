package hu.prooktatas.dcset3.async

import android.os.AsyncTask
import com.github.kittinunf.fuel.Fuel
import hu.prooktatas.dcset3.key

interface loginAtempt{
    fun sorf(succes:String)
}
class AsyncLogin(var la:loginAtempt,var kuldo:String,var jelszo:String): AsyncTask<Unit, Unit,String>() {
    override fun doInBackground(vararg p0: Unit?): String {
        val bodyJson2 = """{ "key" : "$key",
                            "kuldo" : "$kuldo",
                            "jelszo" : "$jelszo"
                          }"""
        val (request, response, result)
                = Fuel.post("https://dcset.ditusz.hu/dcset/telologin")
            .body(bodyJson2)
            .response()



        var succes = String(response.data)
        return succes
    }



    override fun onPostExecute(succes: String) {
        succes?.let {
            la.sorf(it)
        }

    }}