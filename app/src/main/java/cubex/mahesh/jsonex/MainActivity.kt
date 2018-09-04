package cubex.mahesh.jsonex

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insert.setOnClickListener {

            var list = mutableListOf<Employee>()
            var e = Employee(et1.text.toString().toInt(),
                    et2.text.toString(),et3.text.toString(),et4.text.toString())
            list.add(e)
            var emps = Employees(list)
            var g = Gson( )
            var json_response =  g.toJson(emps)

            var fos = openFileOutput("employees.json",
                                            Context.MODE_PRIVATE)
            fos.write(json_response.toByteArray())
            fos.flush()
            fos.close()
 // Device File Explorer>>Data >>Data>>Pkg Name>>files>>employees.json
        }

        read.setOnClickListener {
            var g = Gson( )
            var fis = openFileInput("employees.json")
            var reader = InputStreamReader(fis) // Convert InputStream >> Reader
            var emps =  g.fromJson(reader,Employees::class.java)
            var list = emps.employees
            var temp_list = mutableListOf<String>()
            for(e in list){
                temp_list.add(e.id.toString()+"\t"+e.name+"\n"+
                                    e.desig+"\t"+e.dept)
            }
            var adapter = ArrayAdapter<String>(this@MainActivity,
                    android.R.layout.simple_list_item_single_choice,temp_list)
            lview.adapter = adapter
        }
    }
}
