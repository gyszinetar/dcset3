package hu.prooktatas.dcset3.async

import android.os.AsyncTask
import com.github.kittinunf.fuel.Fuel
import hu.prooktatas.dcset3.key
import org.json.JSONArray
import org.json.JSONObject
interface messages{
    fun fetchMessages(list:List<JSONObject>)
}
class FetchMessages(var kuldo:String,var fogado:String,var messages: messages): AsyncTask<Unit, Unit,List<JSONObject>>() {
    override fun doInBackground(vararg p0: Unit?): List<JSONObject> {
        var list = mutableListOf<JSONObject>()
        val bodyJson2 = """{ "key" : "$key",
                            "kuldo" : "$kuldo",
                            "fogado" : "$fogado"
                          }"""
        val (request, response, result)
                = Fuel.post("https://dcset.ditusz.hu/dcset/responsemind")
            .body(bodyJson2)
            .response()
if (response.statusCode==200){
        val a = JSONArray(String(response.data))
        for (i in 0..a.length() - 1) {
            list.add(JSONObject(a[i].toString()))
        }
        return list}
        list.add(JSONObject("""{"id":"0","kuldo":"System","fogado":"Mindenki","uzenet":"No network found!"}"""))
        return list

    }



    override fun onPostExecute(result: List<JSONObject>?) {
        result?.let {
            messages.fetchMessages(it)
        }

    }}